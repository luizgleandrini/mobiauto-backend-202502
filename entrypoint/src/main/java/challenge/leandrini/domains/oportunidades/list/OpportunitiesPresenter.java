package challenge.leandrini.domains.oportunidades.list;

import challenge.leandrini.domains.oportunidades.models.response.ListOpportunitiesResponse;
import challenge.leandrini.domains.oportunidades.models.response.OpportunityItem;
import challenge.leandrini.domains.oportunidades.usecases.list.ListOpportunitiesResult;
import org.springframework.stereotype.Component;

@Component
public class OpportunitiesPresenter {
    
    public ListOpportunitiesResponse present(ListOpportunitiesResult result) {
        var items = result.items().stream()
                .map(opportunity -> new OpportunityItem(
                        opportunity.getId(),
                        opportunity.getCode(),
                        opportunity.getDealershipId(),
                        opportunity.getStatus().getValue(),
                        opportunity.getConclusionReason(),
                        opportunity.getAssignedUserId(),
                        opportunity.getClientName(),
                        opportunity.getClientEmail(),
                        opportunity.getClientPhone(),
                        opportunity.getVehicleBrand(),
                        opportunity.getVehicleModel(),
                        opportunity.getVehicleVersion(),
                        opportunity.getVehicleYear(),
                        opportunity.getCreatedAt(),
                        opportunity.getUpdatedAt(),
                        opportunity.getAssignedAt(),
                        opportunity.getConcludedAt()
                ))
                .toList();
        
        return new ListOpportunitiesResponse(
                items,
                result.page(),
                result.size(),
                result.totalElements(),
                result.totalPages(),
                result.hasNext(),
                result.hasPrevious()
        );
    }
}