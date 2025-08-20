package challenge.leandrini.domains.oportunidades.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOpportunityRequest {
    
    @NotBlank(message = "Dealership ID is required")
    private String dealershipId;
    
    @NotBlank(message = "Client name is required")
    private String clientName;
    
    @NotBlank(message = "Client email is required")
    @Email(message = "Client email must be valid")
    private String clientEmail;
    
    @NotBlank(message = "Client phone is required")
    private String clientPhone;
    
    @NotBlank(message = "Vehicle brand is required")
    private String vehicleBrand;
    
    @NotBlank(message = "Vehicle model is required")
    private String vehicleModel;
    
    @NotBlank(message = "Vehicle version is required")
    private String vehicleVersion;
    
    @NotNull(message = "Vehicle year is required")
    private Integer vehicleYear;
    
}