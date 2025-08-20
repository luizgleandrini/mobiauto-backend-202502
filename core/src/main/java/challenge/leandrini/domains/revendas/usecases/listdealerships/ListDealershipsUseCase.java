package challenge.leandrini.domains.revendas.usecases.listdealerships;

import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class ListDealershipsUseCase implements IListDealershipsUseCase{

    private final IListDealershipsGateway listDealershipsGateway;

    @Override
    public ListDealershipsResult execute(ListDealershipsQuery query) {

        ListDealershipsQuery criteria = new ListDealershipsQuery(
                query.page(),
                query.size(),
                query.sortBy(),
                query.sortDir(),
                query.cnpj(),
                query.socialName(),
                query.q()
        );

        ListDealershipsPage page = listDealershipsGateway.execute(criteria);

        long total = page.totalElements();
        int size = Math.max(1, query.size());
        int totalPages = (int) Math.ceil((double) total / size);
        totalPages = Math.max(totalPages, 1);

        int currentPage = query.page();
        boolean hasPrevious = currentPage > 0;
        boolean hasNext = (currentPage + 1) < totalPages;

        return new ListDealershipsResult(
                page.items(),
                currentPage,
                size,
                total,
                totalPages,
                hasNext,
                hasPrevious
        );
    }
}