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

import org.springframework.beans.factory.annotation.Autowired;

import static moreland.spring.sample.jpademo.internal.Guard.guardAgainstArgumentNull;
import static moreland.spring.sample.jpademo.internal.Guard.guardAgainstArgumentNullOrEmpty;

import moreland.spring.sample.jpademo.entities.City;
import moreland.spring.sample.jpademo.entities.Province;
import moreland.spring.sample.jpademo.repositories.CityRepository;
import moreland.spring.sample.jpademo.repositories.ProvinceRepository;
import moreland.spring.sample.jpademo.shared.AddFailureException;

public class JpaProvinceService implements ProvinceService {

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private CityRepository cityRepository;

    @Override
    public Optional<Province> find(Long id) {
        guardAgainstArgumentNull(id, "id");

        try {
            return provinceRepository.findById(id);
        } catch (RuntimeException e) {
            return Optional.empty();
        }

    }

    @Override
    public Optional<Province> findByName(String name) {
        guardAgainstArgumentNullOrEmpty(name, "name");
        try {
            return provinceRepository.findFirstByName(name);

        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }

    @Override
    public void delete(Province province) {
        guardAgainstArgumentNull(province, "province");
        try {
            provinceRepository.delete(province);

        } catch (RuntimeException e) {
            // .. log eventually ..
        }
    }

    @Override
    public Optional<City> addCity(Long provinceId, String name) {
        guardAgainstArgumentNull(provinceId, "provinceId");
        guardAgainstArgumentNullOrEmpty(name, "name");
        try {

            var city = cityRepository.findByNameAndProvinceId(name, provinceId);
            if (city.isPresent()) {
                return city;
            }

            var province = provinceRepository.getOne(provinceId);
            var newCity = City.create(name, province);
            return Optional.of(cityRepository.save(newCity));

        } catch (RuntimeException e) {

            throw new AddFailureException(e);
        }
    }

    @Override
    public Optional<City> addCity(Province province, String name) {
        guardAgainstArgumentNull(province, "province");
        return addCity(province.getId(), name);
    }
}
