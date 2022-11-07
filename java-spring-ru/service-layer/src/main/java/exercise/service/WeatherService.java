package exercise.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import exercise.HttpClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import exercise.model.CityWithTempurature;
import exercise.model.CityWithWeather;
import liquibase.pro.packaged.S;
import org.springframework.stereotype.Service;
import exercise.CityNotFoundException;
import exercise.repository.CityRepository;
import exercise.model.City;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class WeatherService {

    @Autowired
    CityRepository cityRepository;
    @Autowired
    ObjectMapper objectMapper;
    // Клиент
    HttpClient client;

    // При создании класса сервиса клиент передаётся снаружи
    // В теории это позволит заменить клиент без изменения самого сервиса
    WeatherService(HttpClient client) {
        this.client = client;
    }

    // BEGIN
    public CityWithWeather getWeather(String name) throws JsonProcessingException {
        String url = "http://weather/api/v2/cities/"+name;
        return objectMapper.readValue(client.get(url), CityWithWeather.class);
    }

public List<CityWithTempurature> getCities(String name) throws JsonProcessingException {
    List<City> cities;
    List<CityWithTempurature> temps = new ArrayList<>();
        if(name !=null){
            cities = cityRepository.findByNameIgnoreCaseStartingWith(name);
                  } else{
            cities = cityRepository.findAllByOrderByNameAsc();
        }
        if(cities.size()>0){
            for (City city:
                    cities) {
                CityWithTempurature temp = new CityWithTempurature(city.getName(), getWeather(city.getName()).getTemperature());
                temps.add(temp);
            }
        }
    return temps;
    }

    // END
}
