//
// Copyright © 2021 Terry Moreland
// Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), 
// to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
// and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
// WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
//
package moreland.spring.sample.mongodemo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import moreland.spring.sample.mongodemo.domain.Country;
import moreland.spring.sample.mongodemo.repositories.CountryRepository;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@Import(DatabaseTestConfiguration.class)
public class CountryRepositoryTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CountryRepository countryRepository;

    @BeforeEach
    public void beforeEach() {
        var canada = new Country("1576624e-8340-4f7e-8b37-ccc6949dfcda", "Dominion of Canada");
        var uk = new Country("c7f244cd-11d9-4535-8be7-fa85723ee4a4", "United Kingdom");
        countryRepository.insert(Arrays.asList(canada, uk));
    }
    @AfterEach
    public void afterEach() {
        countryRepository.deleteAll();
    }

    /**
     * mostly a santity test to verify that the read/write operations are working
     */
    @Test
    public void findAllReturnsTwoCountries() {
        var countries = countryRepository.findAll();
        assertEquals(2, countries.size());
    }


}
