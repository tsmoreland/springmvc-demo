package moreland.spring.sample.mongodemo.model.response;

import java.util.List;

public class ProvinceModel {
    
    private final String id;
    private final String name;
    private final List<CityModel> cities;

    public ProvinceModel(String id, String name, List<CityModel> cities) {
        this.id = id;
        this.name = name;
        this.cities = cities;
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public List<CityModel> getCities() {
        return cities;
    }
}
