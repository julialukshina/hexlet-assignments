package exercise.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

    @Component
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class CityWithTempurature  {
        String name;
        String temperature;
    }

