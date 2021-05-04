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
package moreland.spring.sample.mysqldemo.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import moreland.spring.sample.mysqldemo.entities.Pet;
import moreland.spring.sample.mysqldemo.model.Problem;
import moreland.spring.sample.mysqldemo.model.ProblemBuilder;
import moreland.spring.sample.mysqldemo.services.PetService;

@RestController
public class PetController {
    
    private PetService petService;

    public PetController(PetService petService) {
        super();

        if (petService == null) {
            throw new IllegalArgumentException("petService cannot be null");
        }
        this.petService = petService;
    }

    @RequestMapping(value = "/api/pet", method = RequestMethod.POST)
    public @ResponseBody Pet createPet(@RequestBody Pet pet) {
        return petService.createPet(pet);
    }

    @RequestMapping(value = "/api/pet/{id}", method = RequestMethod.GET)
    public @ResponseBody Pet getById(@PathVariable("id") Long id) {
        return petService.findById(id);
    }

    @RequestMapping(value = "/api/pet", method = RequestMethod.GET)
    public @ResponseBody List<Pet>  getAll() {
        return petService.getAll();
    }

    @ExceptionHandler(RuntimeException.class)
    public @ResponseBody Problem handle(HttpServletRequest request, HttpServletResponse response, RuntimeException exception) {
        return (new ProblemBuilder())
            .withTitle("Unexpected error occurred")
            .withStatus(500)
            .withDetail("Exception of type " + exception.getClass().toString() + " occurred")
            .withInstance("about:blank")
            .build();
    }
}
