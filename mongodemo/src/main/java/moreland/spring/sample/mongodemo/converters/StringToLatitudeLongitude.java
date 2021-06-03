package moreland.spring.sample.mongodemo.converters;

import org.springframework.core.convert.converter.Converter;

import moreland.spring.sample.mongodemo.domain.LatitudeLongitude;
import moreland.spring.sample.mongodemo.internal.Guard;

public class StringToLatitudeLongitude implements Converter<String, LatitudeLongitude> {

    @Override
    public LatitudeLongitude convert(String source) {
        Guard.Against.argumentNull(source, "source");
        var latitudeLongitude = new LatitudeLongitude();

        var parts = source.split(",");
        if (parts.length == 2) {
            try {
                latitudeLongitude.setLatitude(Float.parseFloat(parts[0]));
                latitudeLongitude.setLongitude(Float.parseFloat(parts[1]));
            } catch (NumberFormatException e) {
                latitudeLongitude.setLatitude(Float.valueOf(0.0f));
                latitudeLongitude.setLongitude(Float.valueOf(0.0f));
            }
        }

        return latitudeLongitude;
    }
    
}
