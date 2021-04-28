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
package com.moreland.petdata;

import com.moreland.petdata.entities.Animal;
import com.moreland.petdata.entities.AnimalType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PagingAndSortingAnimalTests {
    
    @Autowired
    AnimalRepository animalRepository;

    @BeforeEach
    void setup() {
        animalRepository.deleteAll();
    }

    @Test
    void findByNameReturnsSortedByNamesWhenSortingParameterGiven() {
        animalRepository.save(createAnimal(1,  "cat", AnimalType.MAMMAL));
        animalRepository.save(createAnimal(2,  "goldfish", AnimalType.FISH));
        animalRepository.save(createAnimal(3,  "rabbit", AnimalType.MAMMAL));

        final var animals = animalRepository.findAll(Sort.by("name"));
        
        assertThat(animals).hasSize(3);
    }

    private static Animal createAnimal(long id, String name, AnimalType type) {
        var animal = new Animal();
        animal.setId(Long.valueOf(id));
        animal.setName(name);
        animal.setType(type);

        return animal;
    }
    
}
