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
package moreland.spring.sample.mongodemo.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import moreland.spring.sample.mongodemo.domain.Country;
import moreland.spring.sample.mongodemo.model.request.CountryCreateModel;
import moreland.spring.sample.mongodemo.queries.CountryQueries;
import moreland.spring.sample.mongodemo.repositories.CountryRepository;
import moreland.spring.sample.mongodemo.specifications.Specification;

@Service
public class CountryServiceImpl implements CountryService {
    
    private CountryRepository countryRepository;
    private CountryQueries countryQueries;


    public CountryServiceImpl(CountryRepository countryRepository, CountryQueries countryQueries) {
        this.countryRepository = countryRepository;
        this.countryQueries = countryQueries;
    }

    public Country create(CountryCreateModel countryModel) {
        var country = new Country(
            UUID.randomUUID().toString(),
            countryModel.getName(),
            countryModel.getPopulation());
        return countryRepository.insert(country);
    }

    public Optional<Country> findOneBySpec(Specification specification) {
        return Optional.of(countryQueries.findOne(specification));
    }

    @Override
    public Optional<Country> findById(String id) {
        return countryRepository.findById(id);
    }

    @Override
    public Optional<Country> findByName(String name) {
        return countryRepository.findByName(name);
    }
}
