package challenge.leandrini.domains.oportunidades.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListOpportunitiesRequest {
    
    private Integer page = 0;
    private Integer size = 10;
    private String sortBy;
    private String sortDir;
    private String dealershipId;
    private String status;
    private String clientName;
    private String clientEmail;
    private String vehicleBrand;
    private String q;
    
}