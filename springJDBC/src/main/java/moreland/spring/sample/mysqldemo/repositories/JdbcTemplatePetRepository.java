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
package moreland.spring.sample.mysqldemo.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import moreland.spring.sample.mysqldemo.entities.Pet;
import moreland.spring.sample.mysqldemo.repositories.rowmappers.PetRowMapper;

public class JdbcTemplatePetRepository implements PetRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Pet createPet(Pet pet) {
        //jdbcTemplate.update("insert into pet (name) values (?, ?)", pet.getName());

        var keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((connection) -> {
            var preparedStatement = connection.prepareStatement("insert into pet (name) values (?)", new String[] { "id" });
            preparedStatement.setString(1, pet.getName());
            return preparedStatement;
        }, keyHolder); 
        final Number id = keyHolder.getKey();
        return findPetById(id.longValue());
    }

    @Override
    public Pet findPetById(Long id) {
        return jdbcTemplate.queryForObject("select * from pet where id = ?", new PetRowMapper(), id);
    }

    @Override
    public List<Pet> getPets() {
        var pets = jdbcTemplate.query("select  * from pet", new RowMapper<Pet>() {
            @Override
            public Pet mapRow(ResultSet rs, int rowNum) throws SQLException {
                var pet = new Pet();
                pet.setId(rs.getLong("id"));
                pet.setName(rs.getString("name"));
                return pet;
            }
        });

        return pets;
    }


    
}
