package challenge.leandrini.domains.revenda.providers;

import challenge.leandrini.domains.revenda.mapper.DealershipMapper;
import challenge.leandrini.domains.revenda.mapper.IDealershipMapper;
import challenge.leandrini.domains.revenda.models.DealershipEntity;
import challenge.leandrini.domains.revenda.repositories.DealershipRepository;
import challenge.leandrini.domains.revendas.models.Dealership;
import challenge.leandrini.domains.revendas.usecases.listdealerships.IListDealershipsGateway;
import challenge.leandrini.domains.revendas.usecases.listdealerships.ListDealershipsPage;
import challenge.leandrini.domains.revendas.usecases.listdealerships.ListDealershipsQuery;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class ListDealershipsProvider implements IListDealershipsGateway {

    private final MongoTemplate mongoTemplate;
    private final IDealershipMapper dealershipMapper = new DealershipMapper();

    public ListDealershipsProvider(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public ListDealershipsPage execute(ListDealershipsQuery query) {
        List<Criteria> ands = new ArrayList<>();

        if (hasText(query.cnpj())) {
            ands.add(Criteria.where("cnpj").is(query.cnpj()));
        }
        if (hasText(query.socialName())) {
            String socialName = query.socialName().trim();
            String safeSocialName = Pattern.quote(socialName);
            ands.add(Criteria.where("socialName").regex(safeSocialName, "i"));
        }
        if (hasText(query.q())) {
            String q = query.q().trim();
            String safe = Pattern.quote(q);
            Criteria or = new Criteria().orOperator(
                    Criteria.where("socialName").regex(safe, "i"),
                    Criteria.where("cnpj").regex(safe, "i")
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

        long total = mongoTemplate.count(mongoQuery.skip(-1).limit(-1), DealershipEntity.class);
        List<DealershipEntity> entities = mongoTemplate.find(mongoQuery, DealershipEntity.class);

        List<Dealership> items = entities.stream().map(dealershipMapper::of).toList();

        return new ListDealershipsPage(items, total);
    }

    private static boolean hasText(String s) {
        return s != null && !s.trim().isEmpty();
    }
}
