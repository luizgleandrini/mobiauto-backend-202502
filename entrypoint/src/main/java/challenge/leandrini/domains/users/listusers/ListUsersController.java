package challenge.leandrini.domains.users.listusers;

import challenge.leandrini.domains.users.models.request.ListUsersRequest;
import challenge.leandrini.domains.users.models.response.ListUsersResponse;
import challenge.leandrini.domains.users.usecases.listusers.IListUsersUseCase;
import challenge.leandrini.domains.users.usecases.listusers.ListUsersQuery;
import challenge.leandrini.domains.users.usecases.listusers.ListUsersResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v1/users")
@RestController
public class ListUsersController implements ListUsersApiMethod {

    private final IListUsersUseCase listUsersUseCase;
    private final ListUsersQueryFactory queryFactory;
    private final UsersPresenter presenter;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@userAuth.canEditUserProfiles(authentication)")
    public ListUsersResponse execute(
            @ParameterObject Pageable pageable,
            @Valid @ParameterObject @ModelAttribute ListUsersRequest request
    ) {
        ListUsersQuery query = queryFactory.from(pageable, request);
        ListUsersResult result = listUsersUseCase.execute(query);
        return presenter.present(result);
    }
}
