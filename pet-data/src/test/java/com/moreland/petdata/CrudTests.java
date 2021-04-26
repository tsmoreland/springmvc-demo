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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import javax.persistence.EntityManager;

import com.moreland.petdata.entities.Gender;
import com.moreland.petdata.entities.Pet;
import com.moreland.petdata.entities.Species;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CrudTests {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private PetRepository petRepository;

	@BeforeEach
	void setup() {
		petRepository.deleteAll();
	}

	@Test
	void entityManagerPersitDoesNotThrowWhenNewPetIsValid() {
		assertDoesNotThrow(() -> {
			final var pet = new Pet();
			pet.setName("Storm");
			pet.setSpecies(Species.CAT);
			pet.setGender(Gender.Female);
			entityManager.persist(pet);
		});
	}

	@Test
	void entityManagerGetResultListReturnsPersistedPet() {
		final var pet = new Pet();
		pet.setName("Storm");
		pet.setSpecies(Species.CAT);
		pet.setGender(Gender.Female);
		entityManager.persist(pet);

		final var query = entityManager.createQuery("select p from Pet p", Pet.class);
		var results = query.getResultList();
		Assertions.assertThat(results)
			.hasSize(1)
			.first()
			.isEqualTo(pet);
	}

	@Test
	void petRepositorySaveDoesNotThrowWhenPetIsValid() {
		assertDoesNotThrow(() -> {
			final var pet = new Pet();
			pet.setName("Storm");
			pet.setSpecies(Species.CAT);
			pet.setGender(Gender.Female);
			petRepository.save(pet);
		});
	}

	@Test
	void petRepositoryFindAllReturnsSavedItem() {
		assertDoesNotThrow(() -> {
			final var pet = new Pet();
			pet.setName("Storm");
			pet.setSpecies(Species.CAT);
			pet.setGender(Gender.Female);
			petRepository.save(pet);

			Assertions.assertThat(petRepository.findAll())
				.hasSize(1)
				.first()
				.isEqualTo(pet);
		});
	}

	@Test
	void petRepositoryDeleteShouldRemoveItem() {
		assertDoesNotThrow(() -> {
			final var pet = new Pet();
			pet.setName("Storm");
			pet.setSpecies(Species.CAT);
			pet.setGender(Gender.Female);
			final var savedPet = petRepository.save(pet);

			petRepository.deleteById(savedPet.getId());
			Assertions.assertThat(petRepository.count()).isZero();
		});
	}
}
