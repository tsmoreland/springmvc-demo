package moreland.spring.sample.jpademo.services;

import moreland.spring.sample.jpademo.entities.City;
import moreland.spring.sample.jpademo.model.response.CityModel;

public interface CityMapper {
    
    CityModel mapCityToCityModel(City city);
}
