package challenge.leandrini.domains.oportunidades.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOpportunityResponse {
    
    private String id;
    private String code;
    private String dealershipId;
    private String status;
    private String conclusionReason;
    private String assignedUserId;
    private Date assignedAt;
    private Date concludedAt;
    private String clientName;
    private String clientEmail;
    private String clientPhone;
    private String vehicleBrand;
    private String vehicleModel;
    private String vehicleVersion;
    private Integer vehicleYear;
    private Date createdAt;
    private Date updatedAt;
    
}