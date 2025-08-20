package challenge.leandrini.domains.oportunidade.mapper;

import challenge.leandrini.domains.oportunidade.models.OpportunityEntity;
import challenge.leandrini.domains.oportunidades.models.Opportunity;
import challenge.leandrini.domains.oportunidades.models.OpportunityStatus;

import java.util.Date;

public class OpportunityMapper implements IOpportunityMapper {
    @Override
    public Opportunity of(OpportunityEntity opportunityEntity) {
        String id = opportunityEntity.getId();
        String code = opportunityEntity.getCode();
        String dealershipId = opportunityEntity.getDealershipId();
        OpportunityStatus status = OpportunityStatus.valueOf(opportunityEntity.getStatus());
        String conclusionReason = opportunityEntity.getConclusionReason();
        String assignedUserId = opportunityEntity.getAssignedUserId();
        String clientName = opportunityEntity.getClientName();
        String clientEmail = opportunityEntity.getClientEmail();
        String clientPhone = opportunityEntity.getClientPhone();
        String vehicleBrand = opportunityEntity.getVehicleBrand();
        String vehicleModel = opportunityEntity.getVehicleModel();
        String vehicleVersion = opportunityEntity.getVehicleVersion();
        Integer vehicleYear = opportunityEntity.getVehicleYear();
        Date createdAt = opportunityEntity.getCreatedAt();
        Date updatedAt = opportunityEntity.getUpdatedAt();
        Date assignedAt = opportunityEntity.getAssignedAt();
        Date concludedAt = opportunityEntity.getConcludedAt();

        Opportunity opportunity = new Opportunity(
                id,
                code,
                dealershipId,
                status,
                conclusionReason,
                assignedUserId,
                clientName,
                clientEmail,
                clientPhone,
                vehicleBrand,
                vehicleModel,
                vehicleVersion,
                vehicleYear,
                createdAt,
                updatedAt,
                assignedAt,
                concludedAt
        );
        return opportunity;
    }

    @Override
    public OpportunityEntity of(Opportunity opportunity) {
        String id = opportunity.getId();
        String code = opportunity.getCode();
        String dealershipId = opportunity.getDealershipId();
        String status = opportunity.getStatus().name();
        String conclusionReason = opportunity.getConclusionReason();
        String assignedUserId = opportunity.getAssignedUserId();
        String clientName = opportunity.getClientName();
        String clientEmail = opportunity.getClientEmail();
        String clientPhone = opportunity.getClientPhone();
        String vehicleBrand = opportunity.getVehicleBrand();
        String vehicleModel = opportunity.getVehicleModel();
        String vehicleVersion = opportunity.getVehicleVersion();
        Integer vehicleYear = opportunity.getVehicleYear();
        Date createdAt = opportunity.getCreatedAt();
        Date updatedAt = opportunity.getUpdatedAt();
        Date assignedAt = opportunity.getAssignedAt();
        Date concludedAt = opportunity.getConcludedAt();

        OpportunityEntity opportunityEntity = new OpportunityEntity(
                id,
                code,
                dealershipId,
                status,
                conclusionReason,
                assignedUserId,
                clientName,
                clientEmail,
                clientPhone,
                vehicleBrand,
                vehicleModel,
                vehicleVersion,
                vehicleYear,
                createdAt,
                updatedAt,
                assignedAt,
                concludedAt
        );

        return opportunityEntity;
    }
}