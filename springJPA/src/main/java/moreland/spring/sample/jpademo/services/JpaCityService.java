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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static moreland.spring.sample.jpademo.internal.Guard.guardAgainstArgumentNull;
import static moreland.spring.sample.jpademo.internal.Guard.guardAgainstArgumentNullOrEmpty;
import moreland.spring.sample.jpademo.entities.City;
import moreland.spring.sample.jpademo.repositories.CityRepository;

@Service
public class JpaCityService implements CityService {

    /*
     * TODO: 
     * - add models folder and maybe model/request, model/response
     * - add city, province and country to each representing the specific request/response
     *   we expect to see for each - in other words don't resue the entity in the rest
     *   controller, be specific 
     */

    @Autowired
    private CityRepository cityRepository;

    @Override
    public List<City> findAll() {
        return cityRepository.findAll();
    }
    
    @Override
    public City addCity(City city) {
        guardAgainstArgumentNull(city, "city");
        return cityRepository.save(city);
    }


    @Override
    public Optional<City> find(Long id) {
        guardAgainstArgumentNull(id, "id");
        try {
            return cityRepository.findById(id);

        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<City> findByName(String name) {
        guardAgainstArgumentNullOrEmpty(name, "name");
        try {
            return cityRepository.findFirstByName(name);

        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }

    @Override
    public void delete(City city) {
        guardAgainstArgumentNull(city, "city");

        try {
            cityRepository.delete(city);

        } catch (RuntimeException e) {
            // .. log eventually ..
        }
    }

}
