package challenge.leandrini.domains.oportunidades.usecases.create;

import challenge.leandrini.IGetDate;
import challenge.leandrini.domains.oportunidades.models.Opportunity;
import challenge.leandrini.domains.oportunidades.models.OpportunityStatus;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Named
@RequiredArgsConstructor
public class CreateOpportunityUseCase implements ICreateOpportunityUseCase {
    
    private final ICreateOpportunityGateway createOpportunityGateway;
    private final IGetDate getDate;
    
    @Override
    public void execute(CreateOpportunityParameters parameters) {
        var code = generateOpportunityCode();
        
        var opportunity = new Opportunity(
            null,
            code,
            parameters.getDealershipId(),
            OpportunityStatus.NOVO,
            null,
            null,
            parameters.getClientName(),
            parameters.getClientEmail(),
            parameters.getClientPhone(),
            parameters.getVehicleBrand(),
            parameters.getVehicleModel(),
            parameters.getVehicleVersion(),
            parameters.getVehicleYear(),
            getDate.now(),
            getDate.now(),
            null,
            null
        );
        
        createOpportunityGateway.execute(opportunity);
    }
    
    private String generateOpportunityCode() {
        return "OPP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}