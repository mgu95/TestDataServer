package pl.mgu95.testDataServer.weather;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class WeatherDataGenerator {

    JSONObject json = new JSONObject(getMyString());

    public String generateWeatherData() {
        updateUpdateTime();
        updateDates();
        return json.toString();
    }

    private void updateDates() {
        JSONObject forecast = json.getJSONObject("forecast");
        JSONArray forecastDay = forecast.getJSONArray("forecastday");
        forecastDay.put(0, changeToDay(forecastDay.getJSONObject(0), 0));
        forecastDay.put(1, changeToDay(forecastDay.getJSONObject(1), 1));
        forecastDay.put(2, changeToDay(forecastDay.getJSONObject(2), 2));
    }

    private JSONObject changeToDay(JSONObject object, int day) {
        object.put("date", makeDay(day));
        JSONArray hours = object.getJSONArray("hour");
        for (int i = 0; i < hours.length(); i++) {
            JSONObject hour = hours.getJSONObject(i);
            String time = hour.getString("time").substring(11);
            hour.put("time", makeDay(day) + " " + time);
        }
        return object;
    }

    private String makeDay(int day) {
        LocalDate date = LocalDate.now();
        if (day == 0) {
            return date.toString();
        } else if (day == 1) {
            return date.plusDays(1).toString();
        } else {
            return date.plusDays(2).toString();
        }
    }

    private void updateUpdateTime() {
        JSONObject current = json.getJSONObject("current");

        LocalTime time = LocalTime.now();
        StringBuilder builder = new StringBuilder(LocalDate.now() + " " + time.getHour() + ":");
        int minutes = time.getMinute();
        if (minutes <= 14) {
            builder.append("00");
        } else if (minutes >= 15 && minutes <= 29) {
            builder.append("15");
        } else if (minutes >= 30 && minutes <= 44) {
            builder.append("30");
        } else {
            builder.append("45");
        }
        current.put("last_updated", builder.toString());
    }

    private String getMyString() {
        try {
            File myObj = new File("data.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                return data;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            File myObj = new File("data.txt");
            try {
                myObj.createNewFile();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }
}
