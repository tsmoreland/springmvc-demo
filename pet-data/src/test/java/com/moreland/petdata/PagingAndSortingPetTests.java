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

import com.moreland.petdata.entities.Gender;
import com.moreland.petdata.entities.Species;
import com.moreland.petdata.repository.PetRepository;
import static com.moreland.petdata.entities.Pet.createPet;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;

@DataJpaTest
public class PagingAndSortingPetTests {
    
    @Autowired
    private PetRepository petRepository;

    @BeforeEach
    void setup() {
        petRepository.deleteAll();
    }

    @Test
    void findByNameReturnsSortedByNamesWhenSortingParameterGiven() {
        petRepository.save(createPet(1, "Storm",  Species.CAT, Gender.FEMALE));
        petRepository.save(createPet(2, "Mystique", Species.CAT, Gender.FEMALE));
        petRepository.save(createPet(3, "Launch", Species.RABBIT, Gender.FEMALE));
        petRepository.save(createPet(4, "ChiChi", Species.RABBIT, Gender.FEMALE));

        final var pets = petRepository.findAll(Sort.by("name", "gender"));
        Assertions.assertThat(pets).hasSize(4);

        final var iterator = pets.iterator();
        Assertions.assertThat(iterator.next().getName()).isEqualTo("ChiChi");
        Assertions.assertThat(iterator.next().getName()).isEqualTo("Launch");
        Assertions.assertThat(iterator.next().getName()).isEqualTo("Mystique");
        Assertions.assertThat(iterator.next().getName()).isEqualTo("Storm");
    }

}
