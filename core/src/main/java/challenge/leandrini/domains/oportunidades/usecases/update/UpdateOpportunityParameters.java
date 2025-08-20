package challenge.leandrini.domains.oportunidades.usecases.update;

import challenge.leandrini.domains.oportunidades.models.OpportunityStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOpportunityParameters {
    
    private String opportunityId;
    private OpportunityStatus status;
    private String conclusionReason;
    private String assignedUserId;
    private String clientName;
    private String clientEmail;
    private String clientPhone;
    private String vehicleBrand;
    private String vehicleModel;
    private String vehicleVersion;
    private Integer vehicleYear;
    
}