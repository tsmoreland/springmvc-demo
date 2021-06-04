package moreland.spring.sample.mongodemo.queries;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import moreland.spring.sample.mongodemo.domain.Country;
import moreland.spring.sample.mongodemo.internal.Guard;
import moreland.spring.sample.mongodemo.specifications.Specification;

@Service
public class CountryQueries implements SpecificationQueries<Country> {

    private final MongoTemplate mongoTemplate;

    public CountryQueries(MongoTemplate mongoTemplate) {
        Guard.Against.argumentNull(mongoTemplate, "mongoTemplate");
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Country> findAll(Specification specification) {
        Guard.Against.argumentNull(specification, "specification");
        return mongoTemplate.find(specification.query(), Country.class);
    }

    @Override
    public Country findOne(Specification specification) {
        Guard.Against.argumentNull(specification, "specification");
        return mongoTemplate.findOne(specification.query(), Country.class);
    }

    @Override
    public Long count(Specification specification) {
        Guard.Against.argumentNull(specification, "specification");
        return mongoTemplate.count(specification.query(), Country.class);
    }
    
}
