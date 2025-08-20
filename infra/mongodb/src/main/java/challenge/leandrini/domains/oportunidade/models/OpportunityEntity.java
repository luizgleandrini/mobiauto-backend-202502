package challenge.leandrini.domains.oportunidade.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "opportunities")
public class OpportunityEntity {

    @Id
    private String id;

    private String code;

    private String dealershipId;

    private String status;

    private String conclusionReason;

    private String assignedUserId;

    private String clientName;

    private String clientEmail;

    private String clientPhone;

    private String vehicleBrand;

    private String vehicleModel;

    private String vehicleVersion;

    private Integer vehicleYear;

    @Indexed(direction = IndexDirection.DESCENDING)
    private Date createdAt;

    @Indexed(direction = IndexDirection.DESCENDING)
    private Date updatedAt;

    private Date assignedAt;

    private Date concludedAt;

}