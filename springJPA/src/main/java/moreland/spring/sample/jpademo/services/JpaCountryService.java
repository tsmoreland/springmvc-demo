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

package moreland.spring.sample.jpademo.services;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static moreland.spring.sample.jpademo.internal.Guard.guardAgainstArgumentNull;
import static moreland.spring.sample.jpademo.internal.Guard.guardAgainstArgumentNullOrEmpty;
import moreland.spring.sample.jpademo.entities.Country;
import moreland.spring.sample.jpademo.entities.Province;
import moreland.spring.sample.jpademo.projections.CountrySummary;
import moreland.spring.sample.jpademo.repositories.CountryRepository;
import moreland.spring.sample.jpademo.repositories.ProvinceRepository;

@Service
public class JpaCountryService implements CountryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JpaCountryService.class);

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Override
    public List<CountrySummary> getAll() {
        try {
            return countryRepository.getAll();
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<Country> find(Long id) {
        guardAgainstArgumentNull(id, "id");
        try {
            return countryRepository.findById(id);
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage(), e);
            return Optional.empty();
        }

    }

    @Override
    public Optional<Country> findByName(String name) {
        guardAgainstArgumentNullOrEmpty(name, "name");
        try {
            return countryRepository.findFirstByName(name);

        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage(), e);
            return Optional.empty();
        }
    }


    public Optional<Province> addProvince(Country country, String name) {
        guardAgainstArgumentNull(country, "country");
        return addProvince(country.getId(), name);
    }
    public Optional<Province> addProvince(Long countryId, String name) {
        guardAgainstArgumentNull(countryId, "countryId");
        guardAgainstArgumentNullOrEmpty(name, "name");

        try {
            var province = provinceRepository.findByNameAndCountryId(name, countryId);
            if (province.isPresent()) {
                return province;
            }
            var country = countryRepository.getById(countryId);
            var newProvince = Province.create(name, country);

            return Optional.of(provinceRepository.save(newProvince));

        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
        
    }

    @Override
    @Transactional
    public void delete(Country country) {
        guardAgainstArgumentNull(country, "country");
        try {
            countryRepository.delete(country);
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    public Optional<Country> createCountry(String name) {
        guardAgainstArgumentNullOrEmpty(name, "name");
        try {
            var country = new Country();
            country.setName(name);
            return Optional.of(countryRepository.save(country));

        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage(), e);
            return Optional.empty();
        }

    }

}
