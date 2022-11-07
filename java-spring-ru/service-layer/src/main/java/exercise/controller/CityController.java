package exercise.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import exercise.CityNotFoundException;
import exercise.model.City;
import exercise.model.CityWithTempurature;
import exercise.model.CityWithWeather;
import exercise.repository.CityRepository;
import exercise.service.WeatherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private WeatherService weatherService;


    @GetMapping("/cities/{id}")
    public CityWithWeather getWeather(@PathVariable Long id) throws JsonProcessingException {
        City city = cityRepository.findById(id)
                .orElseThrow(()-> new CityNotFoundException("City is not found"));
        return weatherService.getWeather(city.getName());
    }

    @GetMapping ("/search")
    public List<CityWithTempurature>  getCities(@RequestParam (required = false) String name) throws JsonProcessingException {
       return weatherService.getCities(name);
}
    
    // END
}

