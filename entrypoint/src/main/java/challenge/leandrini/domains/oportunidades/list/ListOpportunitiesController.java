package challenge.leandrini.domains.oportunidades.list;

import challenge.leandrini.domains.oportunidades.models.request.ListOpportunitiesRequest;
import challenge.leandrini.domains.oportunidades.models.response.ListOpportunitiesResponse;
import challenge.leandrini.domains.oportunidades.usecases.list.IListOpportunitiesUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/opportunities")
public class ListOpportunitiesController implements ListOpportunitiesApiMethod {

    private final ListOpportunitiesQueryFactory queryFactory;
    private final IListOpportunitiesUseCase listOpportunitiesUseCase;
    private final OpportunitiesPresenter presenter;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER', 'MANAGER', 'ASSISTANT')")
    public ListOpportunitiesResponse execute(
            @ParameterObject Pageable pageable,
            @Valid @ParameterObject @ModelAttribute ListOpportunitiesRequest request
    ) {
        var query  = queryFactory.from(pageable, request);
        var result = listOpportunitiesUseCase.execute(query);
        return presenter.present(result);
    }
}