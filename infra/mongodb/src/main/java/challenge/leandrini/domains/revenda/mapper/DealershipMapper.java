package challenge.leandrini.domains.revenda.mapper;

import challenge.leandrini.domains.revenda.models.DealershipEntity;
import challenge.leandrini.domains.revendas.models.Dealership;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;

public class DealershipMapper implements IDealershipMapper{

    @Override
    public Dealership of(DealershipEntity dealershipEntity) {
        String id = dealershipEntity.getId();
        String cnpj = dealershipEntity.getCnpj();
        String socialName = dealershipEntity.getSocialName();
        Date createdAt = dealershipEntity.getCreatedAt();
        Date updatedAt = dealershipEntity.getUpdatedAt();

        Dealership dealership = new Dealership(
                id,
                cnpj,
                socialName,
                createdAt,
                updatedAt
        );

        return dealership;
    }

    @Override
    public DealershipEntity of(Dealership dealership) {
        String id = dealership.getId();
        String cnpj = dealership.getCnpj();
        String socialName = dealership.getSocialName();
        Date createdAt = dealership.getCreatedAt();
        Date updatedAt = dealership.getUpdatedAt();

        DealershipEntity dealershipEntity = new DealershipEntity(
                id,
                cnpj,
                socialName,
                createdAt,
                updatedAt
        );

        return dealershipEntity;
    }
}
