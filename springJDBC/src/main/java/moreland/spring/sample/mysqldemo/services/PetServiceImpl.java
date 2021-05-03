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
package moreland.spring.sample.mysqldemo.services;

import java.util.List;
import java.util.stream.Collectors;

import moreland.spring.sample.mysqldemo.entities.Pet;
import moreland.spring.sample.mysqldemo.repositories.PetRepository;

public class PetServiceImpl implements PetService {

    private PetRepository petRepository;

    public PetServiceImpl(PetRepository petRepository) {
        super();
        this.petRepository = petRepository;
    }

    @Override
    public Pet createPet(Pet pet) {
        return petRepository.createPet(pet);
    }

    @Override
    public Pet findPetById(Long id) {
        return petRepository.findPetById(id);
    }

    @Override
    public List<Pet> getPets() {
        return petRepository.getPets();
    }

    @Override
    public Pet updatePet(Pet pet) {
        return petRepository.updatePet(pet);
    }

    @Override
    public void batchUpdateName(List<Pet> updatedPets) {
        var pairs = updatedPets.stream()
            .map(p -> new Object[] { p.getName(), p.getId()})
            .collect(Collectors.toList());
       petRepository.batchUpdateName(pairs); 
    }
    
}
