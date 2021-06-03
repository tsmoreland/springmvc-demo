package moreland.spring.sample.mongodemo.commands;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import moreland.spring.sample.mongodemo.domain.Country;
import moreland.spring.sample.mongodemo.internal.Guard;

@Service
public class CountryCommands {
    
    private MongoTemplate mongoTemplate;

    public CountryCommands(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Country Create(Country item) {
        Guard.Against.ArgumentNull(item, "item");
        return mongoTemplate.insert(item);
    }

    public List<Country> Create(Country... items) {
        // TODO: validate that no item in items is null
        var asList = Arrays.asList(items);
        return mongoTemplate.insert(asList);
    }

    public void Upsert(Country item) {
        mongoTemplate.save(item);
    }

}
