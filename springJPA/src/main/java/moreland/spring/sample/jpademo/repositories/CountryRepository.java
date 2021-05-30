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

package moreland.spring.sample.jpademo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import moreland.spring.sample.jpademo.entities.Country;
import moreland.spring.sample.jpademo.projections.CountrySummary;

public interface CountryRepository extends JpaRepository<Country, Long> {
    
    Optional<Country> findFirstByName(String name);
    Page<Country> findByNameContains(String name, Pageable pageable);

    List<CountrySummary> getAll();

    @Query("""
        select c from Country c
        join fetch c.provinces 
        where c.id = :id 
    """)
    Optional<Country> getWithProvincesById(@Param("id") Long id);
}
