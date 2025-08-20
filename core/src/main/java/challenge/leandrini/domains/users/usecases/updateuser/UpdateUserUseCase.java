package challenge.leandrini.domains.users.usecases.updateuser;

import challenge.leandrini.IGetDate;
import challenge.leandrini.domains.users.common.ICurrentUserGateway;
import challenge.leandrini.domains.users.models.User;
import challenge.leandrini.domains.users.services.UserAuthorizationService;
import challenge.leandrini.domains.users.usecases.common.IGetUserByIdGateway;
import challenge.leandrini.domains.users.usecases.create.IGetUserByEmailGateway;
import challenge.leandrini.exceptions.NotFoundException;
import challenge.leandrini.exceptions.UniqueConstraintException;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Named
@RequiredArgsConstructor
public class UpdateUserUseCase implements IUpdateUserUseCase{

    private final IGetUserByIdGateway getUserByIdGateway;
    private final IUpdateUserGateway updateUserGateway;
    private final ICurrentUserGateway currentUserGateway;
    private final UserAuthorizationService userAuthorizationService;
    private final IGetDate getDate;

    @Override
    public User execute(UpdateUserParameters parameters) {
        var current = currentUserGateway.currentUser();
        var target  = getUserByIdGateway.execute(parameters.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found" + parameters.getUserId()));

        userAuthorizationService.validateUserEditPermission(current, target);

        if (parameters.getName() != null)
            target.setName(parameters.getName());

        if (parameters.getPhone() != null)
            target.setPhone(parameters.getPhone());

        if (parameters.getRole() != null) {
            target.setRole(parameters.getRole());
        }
        if (parameters.getStoreId() != null) {
            target.setStoreId(parameters.getStoreId());
        }

        target.setUpdatedAt(getDate.now());

        return updateUserGateway.execute(target);
    }
}
