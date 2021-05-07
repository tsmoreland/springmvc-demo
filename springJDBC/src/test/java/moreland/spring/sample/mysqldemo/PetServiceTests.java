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
package moreland.spring.sample.mysqldemo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import moreland.spring.sample.mysqldemo.configuration.MysqldemoConfiguration;
import moreland.spring.sample.mysqldemo.configuration.SecurityConfiguration;
import moreland.spring.sample.mysqldemo.entities.Pet;
import moreland.spring.sample.mysqldemo.repositories.PetRepository;
import moreland.spring.sample.mysqldemo.services.PetService;
import moreland.spring.sample.mysqldemo.services.PetServiceImpl;

@SpringBootTest
@ContextConfiguration(classes = {SecurityConfiguration.class, MysqldemoConfiguration.class})
public class PetServiceTests {
    
    @Mock
    private PetRepository petRepository;
    private PetService petService;
    
    @BeforeEach
    void beforeEach() {
        petService = new PetServiceImpl(petRepository);

    }

    @Test
    void constructorThrowsIllegalArgumentExceptionWhenPetRepositoryIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new PetServiceImpl(null));
    }

    @Test
    void createReturnsPetWhenPetRepositoryReturnsResult() {
        var newPet = buildPet("new-pet");
        var expected = buildPet("expected");
        expected.setId(Long.valueOf(42));
        when(petRepository.create(newPet)).thenReturn(expected);

        var actual = petService.create(newPet);

        assertEquals(expected, actual);
    }

    @Test
    void getAllReturnsAllWhenPetRepositoryReturnsAll() {
        var expected = List.of(buildPet("alpha"), buildPet("bravo"));
        when(petRepository.getAll()).thenReturn(expected);

        var actual = petService.getAll();

        assertEquals(expected, actual);
    }

    Pet buildPet(String name) {
        var pet = new Pet();
        pet.setName(name);
        return pet;
    }

}
