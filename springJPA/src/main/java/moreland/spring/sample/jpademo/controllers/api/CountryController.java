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
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import moreland.spring.sample.jpademo.model.request.CountryCreateModel;
import moreland.spring.sample.jpademo.model.response.Country;
import moreland.spring.sample.jpademo.model.response.CountrySummary;
import moreland.spring.sample.jpademo.services.CountryMapper;
import moreland.spring.sample.jpademo.services.CountryService;
import moreland.spring.sample.jpademo.shared.AddFailureException;

@RestController
public class CountryController {

    @Autowired
    private CountryService countryService;

    @Autowired
    private CountryMapper countryMapper;

    @GetMapping(
        value = "api/countries",
        produces = { "application/json", "application/xml" },
        consumes = { "application/json", "application/xml" }
    )
    public List<CountrySummary> getCountries() {
        return countryService.getAll().stream()
            .map(countryMapper::toCountrySummary)
            .collect(Collectors.toList());
    }
    
    @GetMapping(
        value = "api/countries/{id}", 
        produces = { "application/json", "application/xml" },
        consumes = { "application/json", "application/xml" }
    )
    public Country getCountryById(@PathVariable Long id) {
        return countryMapper.toCountry(countryService.find(id).get());
    }

    @PostMapping(
        value = "api/countries", 
        produces = { "application/json", "application/xml" },
        consumes = { "application/json", "application/xml" }
    )
    public Country createCountry(@Valid @ModelAttribute @RequestBody CountryCreateModel country, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(); // add new exception so we can include these in error response
        }
        return countryService.createCountry(country.name())
            .map(countryMapper::toCountry)
            .orElseThrow(() -> new AddFailureException());
    }
}
