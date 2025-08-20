package challenge.leandrini.domains.oportunidades.usecases.update;

import challenge.leandrini.IGetDate;
import challenge.leandrini.domains.oportunidades.models.Opportunity;
import challenge.leandrini.domains.oportunidades.models.OpportunityStatus;
import challenge.leandrini.exceptions.NotFoundException;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class UpdateOpportunityUseCase implements IUpdateOpportunityUseCase {
    
    private final IGetOpportunityByIdGateway getOpportunityByIdGateway;
    private final IUpdateOpportunityGateway updateOpportunityGateway;
    private final IGetDate getDate;
    
    @Override
    public Opportunity execute(UpdateOpportunityParameters parameters) {
        var opportunity = getOpportunityByIdGateway.execute(parameters.getOpportunityId());
        
        if (opportunity == null) {
            throw new NotFoundException("Opportunity not found with id: " + parameters.getOpportunityId());
        }
        
        if (parameters.getClientName() != null) {
            opportunity.setClientName(parameters.getClientName());
        }
        if (parameters.getClientEmail() != null) {
            opportunity.setClientEmail(parameters.getClientEmail());
        }
        if (parameters.getClientPhone() != null) {
            opportunity.setClientPhone(parameters.getClientPhone());
        }
        if (parameters.getVehicleBrand() != null) {
            opportunity.setVehicleBrand(parameters.getVehicleBrand());
        }
        if (parameters.getVehicleModel() != null) {
            opportunity.setVehicleModel(parameters.getVehicleModel());
        }
        if (parameters.getVehicleVersion() != null) {
            opportunity.setVehicleVersion(parameters.getVehicleVersion());
        }
        if (parameters.getVehicleYear() != null) {
            opportunity.setVehicleYear(parameters.getVehicleYear());
        }
        
        if (parameters.getStatus() != null) {
            updateStatus(opportunity, parameters);
        }
        
        if (parameters.getAssignedUserId() != null && !parameters.getAssignedUserId().equals(opportunity.getAssignedUserId())) {
            opportunity.setAssignedUserId(parameters.getAssignedUserId());
            opportunity.setAssignedAt(getDate.now());
            
            if (opportunity.getStatus() == OpportunityStatus.NOVO) {
                opportunity.setStatus(OpportunityStatus.EM_ATENDIMENTO);
            }
        }
        
        opportunity.setUpdatedAt(getDate.now());
        
        updateOpportunityGateway.execute(opportunity);
        
        return opportunity;
    }
    
    private void updateStatus(Opportunity opportunity, UpdateOpportunityParameters parameters) {
        var newStatus = parameters.getStatus();
        var currentStatus = opportunity.getStatus();
        
        if (newStatus == OpportunityStatus.EM_ATENDIMENTO && currentStatus == OpportunityStatus.NOVO) {
            opportunity.setStatus(newStatus);
        } else if (newStatus == OpportunityStatus.CONCLUIDO) {
            opportunity.setStatus(newStatus);
            opportunity.setConcludedAt(getDate.now());
            
            if (parameters.getConclusionReason() != null) {
                opportunity.setConclusionReason(parameters.getConclusionReason());
            }
        } else if (newStatus != currentStatus) {
            opportunity.setStatus(newStatus);
        }
    }
}