package challenge.leandrini.domains.oportunidades.usecases.list;

import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class ListOpportunitiesUseCase implements IListOpportunitiesUseCase {
    
    private final IListOpportunitiesGateway listOpportunitiesGateway;
    
    @Override
    public ListOpportunitiesResult execute(ListOpportunitiesQuery query) {
        return listOpportunitiesGateway.execute(query);
    }
}