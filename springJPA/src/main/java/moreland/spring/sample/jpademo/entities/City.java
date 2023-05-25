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

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

import static moreland.spring.sample.jpademo.internal.Guard.guardAgainstArgumentNull;
import static moreland.spring.sample.jpademo.internal.Guard.guardAgainstArgumentNullOrEmpty;

@Entity
@Table(name = "cities")
public class City {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @NotNull
    @JoinColumns({
        @JoinColumn(name="provinceId", referencedColumnName="id"),
        @JoinColumn(name="provinceName", referencedColumnName="name")
    })
    private Province province;
    @Column(insertable = false, updatable = false, nullable = true)
    private Long provinceId;
    @Column(insertable = false, updatable = false, nullable = true)
    private String provinceName;

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
    public Province getProvince() {
        return province;
    }
    public void setProvince(Province province) {
        this.province = province;
        if (province != null) {
            this.provinceId = this.province.getId();
            this.provinceName = this.province.getName();
        } else {
            this.provinceId = null;
            this.provinceName = null;
        }
    }
    public Long getProvinceId() {
        return provinceId;
    }
    public String getProvinceName() {
        return provinceName;
    }

    public static City create(String name, Province province) {
        guardAgainstArgumentNullOrEmpty(name, "name");
        guardAgainstArgumentNull(province, "province");

        var city = new City();
        city.setName(name);
        city.setProvince(province);

        return city;
    }
}
