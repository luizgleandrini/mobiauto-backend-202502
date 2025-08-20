package challenge.leandrini.domains.revendas.list;

import challenge.leandrini.domains.revendas.listdealerships.DealershipsPresenter;
import challenge.leandrini.domains.revendas.listdealerships.ListDealershipsApiMethod;
import challenge.leandrini.domains.revendas.listdealerships.ListDealershipsQueryFactory;
import challenge.leandrini.domains.revendas.models.request.ListDealershipsRequest;
import challenge.leandrini.domains.revendas.models.response.ListDealershipsResponse;
import challenge.leandrini.domains.revendas.usecases.listdealerships.IListDealershipsUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/dealerships")
public class ListDealershipsController implements ListDealershipsApiMethod {

    private final ListDealershipsQueryFactory queryFactory;
    private final IListDealershipsUseCase listDealershipsUseCase;
    private final DealershipsPresenter presenter;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER', 'MANAGER')")
    public ListDealershipsResponse execute(
            @ParameterObject Pageable pageable,
            @Valid @ParameterObject @ModelAttribute ListDealershipsRequest request
    ) {
        var query  = queryFactory.from(pageable, request);
        var result = listDealershipsUseCase.execute(query);
        return presenter.present(result);
    }
}
