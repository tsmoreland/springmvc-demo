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

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static moreland.spring.sample.jpademo.internal.Guard.guardAgainstArgumentNull;
import static moreland.spring.sample.jpademo.internal.Guard.guardAgainstArgumentNullOrEmpty;
import moreland.spring.sample.jpademo.entities.Country;
import moreland.spring.sample.jpademo.entities.Province;
import moreland.spring.sample.jpademo.repositories.CountryRepository;
import moreland.spring.sample.jpademo.repositories.ProvinceRepository;
import moreland.spring.sample.jpademo.shared.AddFailureException;

@Service
public class JpaCountryService implements CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Override
    public Optional<Country> find(Long id) {
        guardAgainstArgumentNull(id, "id");
        try {
            return countryRepository.findById(id);
        } catch (RuntimeException e) {
            return Optional.empty();
        }

    }

    @Override
    public Optional<Country> findByName(String name) {
        guardAgainstArgumentNullOrEmpty(name, "name");
        try {
            return countryRepository.findFirstByName(name);

        } catch (RuntimeException e) {
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
            var country = countryRepository.getOne(countryId);
            var newProvince = Province.create(name, country);

            return Optional.of(provinceRepository.save(newProvince));

        } catch (RuntimeException e) {
            throw new AddFailureException(e);
        }
        
    }

    @Override
    @Transactional
    public void delete(Country country) {
        guardAgainstArgumentNull(country, "country");
        try {
            countryRepository.delete(country);
        } catch (RuntimeException e) {
            // .. log eventually ..yy
        }
    }
}
