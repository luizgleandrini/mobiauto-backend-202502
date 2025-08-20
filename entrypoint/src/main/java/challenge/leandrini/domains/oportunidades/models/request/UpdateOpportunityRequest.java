package challenge.leandrini.domains.oportunidades.models.request;

import challenge.leandrini.domains.oportunidades.models.OpportunityStatus;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOpportunityRequest {
    
    private OpportunityStatus status;
    private String conclusionReason;
    private String assignedUserId;
    private String clientName;
    
    @Email(message = "Client email must be valid")
    private String clientEmail;
    
    private String clientPhone;
    private String vehicleBrand;
    private String vehicleModel;
    private String vehicleVersion;
    private Integer vehicleYear;
    
}