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

import com.moreland.petdata.repository.PetRepository;
import com.moreland.petdata.service.PetService;
import static com.moreland.petdata.entities.Pet.createPet;

import com.moreland.petdata.entities.Gender;
import com.moreland.petdata.entities.Species;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PetServiceTests {
    
    @Autowired 
    private PetRepository petRepository;
    @Autowired
    private PetService petService;

    @BeforeEach
    void setup() {
        petRepository.deleteAll();
    }

    @Test
    public void AddPetsShouldNotRollbackWhenMoreThanFivePetsAndNotTransactional() {
        try {
            petService.AddPets(
                createPet(1, "Storm", Species.CAT, Gender.FEMALE),
                createPet(2, "Mystique", Species.CAT, Gender.FEMALE),
                createPet(3, "Launch", Species.RABBIT, Gender.FEMALE),
                createPet(4, "ChiChi", Species.RABBIT, Gender.FEMALE),
                createPet(5, "Penelope", Species.CAT, Gender.FEMALE),
                createPet(5, "Homer", Species.CAT, Gender.MALE)
            );
        } catch (RuntimeException e) {
            // extpected error, do nothing
        } finally {
            Assertions.assertThat(petRepository.findAll()).isNotEmpty();
        }
    }

    @Test
    public void AddPetsShouldRollbackWhenMoreThanFivePetsAndTransactional() {
        try {
            petService.AddPetsTransaction(
                createPet(1, "Storm", Species.CAT, Gender.FEMALE),
                createPet(2, "Mystique", Species.CAT, Gender.FEMALE),
                createPet(3, "Launch", Species.RABBIT, Gender.FEMALE),
                createPet(4, "ChiChi", Species.RABBIT, Gender.FEMALE),
                createPet(5, "Penelope", Species.CAT, Gender.FEMALE),
                createPet(5, "Homer", Species.CAT, Gender.MALE)
            );
        } catch (RuntimeException e) {
            // extpected error, do nothing
        } finally {
            Assertions.assertThat(petRepository.findAll()).isEmpty();
        }
    }
}
