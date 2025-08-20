package challenge.leandrini.domains.oportunidades.usecases.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOpportunityParameters {
    
    private String dealershipId;
    private String clientName;
    private String clientEmail;
    private String clientPhone;
    private String vehicleBrand;
    private String vehicleModel;
    private String vehicleVersion;
    private Integer vehicleYear;
    
}