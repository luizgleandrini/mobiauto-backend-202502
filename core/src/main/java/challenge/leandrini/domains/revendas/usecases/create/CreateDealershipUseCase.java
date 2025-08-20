package challenge.leandrini.domains.revendas.usecases.create;

import challenge.leandrini.domains.revendas.models.Dealership;
import challenge.leandrini.domains.users.common.ICurrentUserGateway;
import challenge.leandrini.domains.users.models.User;
import challenge.leandrini.domains.users.models.UserRole;
import challenge.leandrini.exceptions.UniqueConstraintException;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;

import java.util.Date;
import java.util.regex.Pattern;

@Named
@RequiredArgsConstructor
public class CreateDealershipUseCase implements ICreateDealershipUseCase {

    private final ICreateDealershipGateway createDealershipGateway;
    private final IFindDealershipByCnpjGateway findByCnpjGateway;
    private final ICurrentUserGateway currentUserGateway;

    private static final Pattern VALID_NAME_PATTERN = Pattern.compile("^[A-Za-zÀ-ÿ0-9\\s]+$");

    @Override
    public void execute(CreateDealershipParameters parameters) {

        validateSocialName(parameters.getSocialName());

        User current = currentUserGateway.currentUser();
        if (current == null || !UserRole.ADMIN.name().equals(current.getRole().name())) {
            throw new AccessDeniedException("Only ADMIN can create dealerships");
        }

        String cnpj = Cnpj.onlyDigits(parameters.getCnpj());
        if (!Cnpj.isValid(cnpj)) {
            throw new IllegalArgumentException("Invalid CNPJ");
        }

        findByCnpjGateway.execute(cnpj).ifPresent(d -> {
            throw new UniqueConstraintException("Dealership already exists for CNPJ: " + cnpj);
        });

        Date now = new Date();
        createDealershipGateway.execute(new Dealership(
                null,
                cnpj,
                parameters.getSocialName().trim(),
                now,
                now
        ));
    }

    private void validateSocialName(String name) {
        if (!VALID_NAME_PATTERN.matcher(name).matches()) {
            throw new IllegalArgumentException("Name must contain only letters, numbers and spaces");
        }
    }

    static final class Cnpj {
        static String onlyDigits(String s) { return s == null ? null : s.replaceAll("\\D", ""); }

        static boolean isValid(String cnpj) {
            if (cnpj == null || cnpj.length() != 14) return false;
            if (cnpj.chars().distinct().count() == 1) return false;
            int d1 = digit(cnpj, new int[]{5,4,3,2,9,8,7,6,5,4,3,2});
            int d2 = digit(cnpj, new int[]{6,5,4,3,2,9,8,7,6,5,4,3,2});
            return d1 == (cnpj.charAt(12) - '0') && d2 == (cnpj.charAt(13) - '0');
        }
        private static int digit(String cnpj, int[] w) {
            int sum = 0;
            for (int i = 0; i < w.length; i++) sum += (cnpj.charAt(i) - '0') * w[i];
            int r = sum % 11;
            return (r < 2) ? 0 : 11 - r;
        }
    }
}
