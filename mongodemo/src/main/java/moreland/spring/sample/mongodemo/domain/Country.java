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

package moreland.spring.sample.mongodemo.domain;

import java.util.ArrayList;
import java.util.List;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "countries")
public class Country {

    @Id
    private String id;

    @Field(name="name") // redundant but here to how how it could be used
    @Indexed(direction = IndexDirection.ASCENDING, unique = true)
    private String name;

    @Field(name="population")
    private Long population;

    @Transient
    private String loweredName;
    
    //@DbRef for joins, not yet sure if it works for list

    @DBRef(lazy = true)
    private List<Province> provinces;

    @DBRef(lazy = true)
    private List<State> states;

    public Country() {
        this("", "");
    }
    public Country(String id, String name) {
        this.id = id;
        this.name = name;
        this.provinces = new ArrayList<>();
        this.states = new ArrayList<>();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getPopulation() {
        return this.population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public String getLoweredName() {
        return this.loweredName;
    }

    public void setLoweredName(String loweredName) {
        this.loweredName = loweredName;
    }

    public List<Province> getProvinces() {
        return this.provinces;
    }
    public void setProvinces(List<Province> provinces) {
        this.provinces = provinces;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }
  
}