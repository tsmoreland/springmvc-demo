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
package moreland.spring.sample.jpademo.controllers.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import moreland.spring.sample.jpademo.entities.City;
import moreland.spring.sample.jpademo.model.request.CityCreateModel;
import moreland.spring.sample.jpademo.model.response.CityModel;
import moreland.spring.sample.jpademo.services.CityMapper;
import moreland.spring.sample.jpademo.services.CityService;
import moreland.spring.sample.jpademo.services.ProvinceService;

import static moreland.spring.sample.jpademo.internal.Guard.guardAgainstArgumentNullOrEmpty;
import static moreland.spring.sample.jpademo.internal.Guard.guardAgainstArgumentNegative;

@RestController
public class CityController {
    
    @Autowired
    private CityService cityService;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CityMapper cityMapper;

    @GetMapping(
        value = "api/cities",
        produces = { "application/json", "application/xml" },
        consumes = { "application/json", "application/xml" }
    )
    public List<City> getCities() {
        return cityService.findAll();
    }

    @GetMapping(
        value = "api/cities/{id}", 
        produces = { "application/json", "application/xml" },
        consumes = { "application/json", "application/xml" }
    )
    public CityModel getCity(@PathVariable Long id) {
        return cityMapper.mapCityToCityModel(cityService.find(id).get());
    }

    @PostMapping(
        value = "api/provinces/{provinceId}/cities", 
        produces = { "application/json", "application/xml" },
        consumes = { "application/json", "application/xml" }
    )
    public CityModel createCityWithProvince(@PathVariable Long provinceId, @Valid @ModelAttribute @RequestBody CityCreateModel city, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(); // add new exception so we can include these in error response
        }

        guardAgainstArgumentNegative(provinceId, "provinceId");
        guardAgainstArgumentNullOrEmpty(city.name(), "city");

        return cityMapper.mapCityToCityModel(provinceService.addCity(provinceId, city.name()).get());
    }
}
