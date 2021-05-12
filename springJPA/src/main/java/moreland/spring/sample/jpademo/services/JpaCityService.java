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

import moreland.spring.sample.jpademo.entities.City;
import moreland.spring.sample.jpademo.repositories.CityRepository;

public class JpaCityService implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Override
    public Optional<City> find(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        try {
            return cityRepository.findById(id);

        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<City> findByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name cannot be null or empty");
        }
        try {
            return cityRepository.findFirstByName(name);

        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }

    @Override
    public void delete(City city) {
        if (city == null) {
            throw new IllegalArgumentException("city cannot be null");
        }

        try {
            cityRepository.delete(city);

        } catch (RuntimeException e) {

        }
    }
    
}
