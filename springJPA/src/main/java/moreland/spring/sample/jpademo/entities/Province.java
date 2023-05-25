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

package moreland.spring.sample.jpademo.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import static moreland.spring.sample.jpademo.internal.Guard.guardAgainstArgumentNull;
import static moreland.spring.sample.jpademo.internal.Guard.guardAgainstArgumentNullOrEmpty;

@Entity
@Table(name = "provinces")
@NamedQueries({
    @NamedQuery(name = Province.FULL_NAMES, query = Province.FULL_NAMES_JPQL),
    @NamedQuery(name = Province.FULL_NAME, query = Province.FULL_NAME_JPQL)
})
public class Province {
    
    public static final String FULL_NAMES = "Province.getFullnames";
    public static final String FULL_NAMES_JPQL = """
        select new moreland.spring.sample.jpademo.projections.ProvinceFullname(p.name, c.name)
        from Province p, Country c
        where p.country.id = c.id
        """; // don't need the join but doing anyway to have a working example of one

    public static final String FULL_NAME = "Province.getFullNameById";
    public static final String FULL_NAME_JPQL = """
        select new moreland.spring.sample.jpademo.projections.ProvinceFullname(p.name, p.countryName)
        from Province p
        where p.id = :id
        """;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumns({
        @JoinColumn(name="countryId", referencedColumnName="id"),
        @JoinColumn(name="countryName", referencedColumnName="name")
    })
    private Country country;

    @Column(insertable = false, updatable = false)
    private Long countryId;

    @Column(insertable = false, updatable = false)
    private String countryName;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY,  mappedBy = "province", cascade = CascadeType.ALL)
    private List<City> cities = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
        if (this.country != null) {
            this.countryId = this.country.getId();
            this.countryName = this.country.getName();
        } else {
            this.countryId = null;
            this.countryName = null;
        }
    }

    public Long getCountryId() {
        return countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public Optional<City> getCityByName(String name) {
        guardAgainstArgumentNullOrEmpty(name, "name");
        final String lowerName = name.toLowerCase();

        return getCities().stream()
            .filter(p -> p.getName().toLowerCase() == lowerName)
            .findFirst();
    }

    public static Province create(String name, Country country) {
        guardAgainstArgumentNullOrEmpty(name, "name");
        guardAgainstArgumentNull(country, "country");

        var province = new Province();
        province.setName(name);
        province.setCountry(country);

        return province;
    }
}
