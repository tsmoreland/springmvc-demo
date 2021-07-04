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
package com.moreland.web.springdemo.controllers;

import com.moreland.web.springdemo.models.Gender;
import com.moreland.web.springdemo.models.Pet;
import com.moreland.web.springdemo.models.Species;

import org.owasp.encoder.Encode;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PetApiController {
    
    @GetMapping("/api/pet/{name}")
    public EntityModel<Pet> getPet(@PathVariable(value="name") String name) {
        return EntityModel.of(new Pet(Encode.forJavaScript(name), Species.RABBIT, Gender.Female),
            linkTo(methodOn(PetApiController.class).getPet("Bunny")).withSelfRel(),
            linkTo(methodOn(PetApiController.class).getPets()).withRel("pet"));
    }

    @GetMapping("/api/pet")
    public CollectionModel<EntityModel<Pet>> getPets() {
        List<EntityModel<Pet>> pets = new ArrayList<>();

        return CollectionModel.of(pets, 
            linkTo(methodOn(PetApiController.class).getPets()).withSelfRel());
    }

    @PostMapping("/api/pet")
    public Pet addPet(Pet pet) {
        return pet;
    }

    @PutMapping("/api/pet")
    public Pet updatePet(Pet pet) {
        return pet;
    }

    @DeleteMapping("/api/pet/{name}")
    public ResponseEntity<String> deletePet(@PathVariable("name") String name) {
        return ResponseEntity.ok("");
    }
}
