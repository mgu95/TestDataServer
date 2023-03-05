package pl.mgu95.testDataServer.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.mgu95.testDataServer.weather.WeatherDataGenerator;

@RestController
public class WeatherDataController {

    WeatherDataGenerator dataGenerator = new WeatherDataGenerator();

    @RequestMapping(value = "/getWeatherData", method = RequestMethod.GET)
    public String getWeatherData() {
        return dataGenerator.generateWeatherData();
    }

    @RequestMapping(value = "/searchCity={city}", method = RequestMethod.GET)
    public String searchCity(@PathVariable String city) {
        if (city.equals("Wroclaw")) {
            return "[{\"id\":1991652,\"name\":\"Wroclaw\",\"region\":\"\",\"country\":\"Poland\",\"lat\":51.1,\"lon\":17.03,\"url\":\"wroclaw-poland\"}]";
        } else {
            return "[]";
        }
    }
}
