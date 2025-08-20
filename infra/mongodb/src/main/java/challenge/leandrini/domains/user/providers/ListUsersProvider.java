package challenge.leandrini.domains.user.providers;

import challenge.leandrini.domains.user.mapper.IUserMapper;
import challenge.leandrini.domains.user.mapper.UserMapper;
import challenge.leandrini.domains.user.models.UserEntity;
import challenge.leandrini.domains.users.models.User;
import challenge.leandrini.domains.users.usecases.listusers.IListUsersGateway;
import challenge.leandrini.domains.users.usecases.listusers.ListUsersPage;
import challenge.leandrini.domains.users.usecases.listusers.ListUsersQuery;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class ListUsersProvider implements IListUsersGateway {

    private final MongoTemplate mongoTemplate;
    private final IUserMapper userMapper = new UserMapper();

    public ListUsersProvider(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public ListUsersPage execute(ListUsersQuery query) {
        List<Criteria> ands = new ArrayList<>();

        if (hasText(query.storeId())) {
            ands.add(Criteria.where("storeId").is(query.storeId()));
        }
        if (hasText(query.role())) {
            ands.add(Criteria.where("role").is(query.role()));
        }
        if (hasText(query.email())) {
            ands.add(Criteria.where("email").regex(Pattern.quote(query.email().trim()), "i"));
        }
        if (hasText(query.q())) {
            String q = query.q().trim();
            String safe = Pattern.quote(q);
            Criteria or = new Criteria().orOperator(
                    Criteria.where("name").regex(safe, "i"),
                    Criteria.where("email").regex(safe, "i"),
                    Criteria.where("phone").regex(safe, "i")
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

        long total = mongoTemplate.count(mongoQuery.skip(-1).limit(-1), UserEntity.class);
        List<UserEntity> entities = mongoTemplate.find(mongoQuery, UserEntity.class);

        List<User> items = entities.stream().map(userMapper::of).toList();

        return new ListUsersPage(items, total);
    }

    private static boolean hasText(String s) {
        return s != null && !s.trim().isEmpty();
    }
}
