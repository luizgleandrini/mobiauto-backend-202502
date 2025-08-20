package challenge.leandrini.domains.oportunidade.providers;

import challenge.leandrini.domains.oportunidade.mapper.IOpportunityMapper;
import challenge.leandrini.domains.oportunidade.mapper.OpportunityMapper;
import challenge.leandrini.domains.oportunidade.models.OpportunityEntity;
import challenge.leandrini.domains.oportunidades.models.Opportunity;
import challenge.leandrini.domains.oportunidades.usecases.list.IListOpportunitiesGateway;
import challenge.leandrini.domains.oportunidades.usecases.list.ListOpportunitiesQuery;
import challenge.leandrini.domains.oportunidades.usecases.list.ListOpportunitiesResult;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class ListOpportunitiesProvider implements IListOpportunitiesGateway {

    private final MongoTemplate mongoTemplate;
    private final IOpportunityMapper opportunityMapper = new OpportunityMapper();

    public ListOpportunitiesProvider(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public ListOpportunitiesResult execute(ListOpportunitiesQuery query) {
        List<Criteria> ands = new ArrayList<>();

        if (hasText(query.dealershipId())) {
            ands.add(Criteria.where("dealershipId").is(query.dealershipId()));
        }
        if (hasText(query.status())) {
            ands.add(Criteria.where("status").is(query.status()));
        }
        if (hasText(query.clientName())) {
            String clientName = query.clientName().trim();
            String safeClientName = Pattern.quote(clientName);
            ands.add(Criteria.where("clientName").regex(safeClientName, "i"));
        }
        if (hasText(query.clientEmail())) {
            String clientEmail = query.clientEmail().trim();
            String safeClientEmail = Pattern.quote(clientEmail);
            ands.add(Criteria.where("clientEmail").regex(safeClientEmail, "i"));
        }
        if (hasText(query.vehicleBrand())) {
            String vehicleBrand = query.vehicleBrand().trim();
            String safeVehicleBrand = Pattern.quote(vehicleBrand);
            ands.add(Criteria.where("vehicleBrand").regex(safeVehicleBrand, "i"));
        }
        if (hasText(query.q())) {
            String q = query.q().trim();
            String safe = Pattern.quote(q);
            Criteria or = new Criteria().orOperator(
                    Criteria.where("code").regex(safe, "i"),
                    Criteria.where("clientName").regex(safe, "i"),
                    Criteria.where("clientEmail").regex(safe, "i"),
                    Criteria.where("vehicleBrand").regex(safe, "i"),
                    Criteria.where("vehicleModel").regex(safe, "i")
            );
            ands.add(or);
        }

        Query mongoQuery = new Query();
        if (!ands.isEmpty()) {
            mongoQuery.addCriteria(new Criteria().andOperator(ands.toArray(new Criteria[0])));
        }

        String sortBy = hasText(query.sortBy()) ? query.sortBy() : "createdAt";
        Sort.Direction dir = "ASC".equalsIgnoreCase(query.sortDir()) ? Sort.Direction.ASC : Sort.Direction.DESC;
        mongoQuery.with(Sort.by(dir, sortBy));

        int page = Math.max(0, query.page());
        int size = Math.max(1, query.size());
        mongoQuery.skip((long) page * size).limit(size);

        long total = mongoTemplate.count(mongoQuery.skip(-1).limit(-1), OpportunityEntity.class);
        List<OpportunityEntity> entities = mongoTemplate.find(mongoQuery, OpportunityEntity.class);

        List<Opportunity> items = entities.stream().map(opportunityMapper::of).toList();

        int totalPages = (int) Math.ceil((double) total / size);
        boolean hasNext = page < totalPages - 1;
        boolean hasPrevious = page > 0;

        return new ListOpportunitiesResult(items, page, size, total, totalPages, hasNext, hasPrevious);
    }

    private static boolean hasText(String s) {
        return s != null && !s.trim().isEmpty();
    }
}