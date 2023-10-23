package Forecast.ViewModels;

import Forecast.Models.WeatherService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.Scanner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ForecastViewModel {

    private final WeatherService weatherService;
    private StringProperty location = new SimpleStringProperty();
    private StringProperty forecast = new SimpleStringProperty();

    public ForecastViewModel() {
        this.weatherService = new WeatherService();
    }

    public void search1HourForecast() {

    }

    public void clicked1HourButton(Button button, TextField textField) {
        button.setOnAction(e -> {
            try {
                String location = textField.getText(); //Pobieramy aplikacje z pola tekstowego
                String urlTextSearch = "http://dataservice.accuweather.com/locations/v1/search" + "?apikey=" + apiKey + "&q=" + location + "&language=" + language;

                //Wysylamy zapytanie do API i odczytujemy odpowiedz
                HttpURLConnection connection = (HttpURLConnection) new URL(urlTextSearch).openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    Scanner scanner = new Scanner(connection.getInputStream());
                    String response = scanner.useDelimiter("\\A").next();
                    scanner.close();
                    connection.disconnect();

                    //Przetwarzamy JSON za pomocą Jackson
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(response);

                    //Pobieramy wartość "Key" z JSON i zapisujemy do zmiennej
                    String key = jsonNode.get(0).get("Key").asText();

                    //Wyświetlamy klucz
                    System.out.println("★★★ Key: " + key + " ★★★");

                    //Teraz pobieramy dane z drugiego endpointa
                    String urlDailyForecasts = "http://dataservice.accuweather.com/forecasts/v1/hourly/1hour/" + key + "?apikey=" + apiKey + "&language=" + language;

                    connection = (HttpURLConnection) new URL(urlDailyForecasts).openConnection();
                    connection.setRequestMethod("GET");
                    responseCode = connection.getResponseCode();

                    if (responseCode == 200) {
                        scanner = new Scanner(connection.getInputStream());
                        response = scanner.useDelimiter("\\A").next();
                        scanner.close();
                        connection.disconnect();

                        //Przetwarzamy JSON za pomocą Jackson
                        jsonNode = objectMapper.readTree(response);

                        JsonNode dailyForecasts = jsonNode.get(0);

                        String date = dailyForecasts.get("DateTime").asText();
                        JsonNode temperature = dailyForecasts.get("Temperature");
                        double temperatureF = temperature.get("Value").asDouble();


                        //Przeliczamy temperature z F na C
                        double temperatureC = Math.round(((temperatureF - 32) * 5/9) * 10.0) / 10.0;

                        String iconPhrase = dailyForecasts.get("IconPhrase").asText();

                        System.out.println("DateTime: " + date);
                        System.out.println("Temperature: " + temperatureC + "°C");
                        System.out.println("IconPhrase: " + iconPhrase);
                        System.out.println();

                    } else {
                        System.out.println("Błąd: Nie można pobrać prognozy na 1h.");
                    }
                } else {
                    System.out.println("Błąd: Nie można znaleźć lokalizacji.");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }


    public void clicked1DayButton(Button button, TextField textField) {
        button.setOnAction(e -> {
            try {
                String location = textField.getText();
                String urlTextSearch = "http://dataservice.accuweather.com/locations/v1/search" + "?apikey=" + apiKey + "&q=" + location + "&language=" + language;

                HttpURLConnection connection = (HttpURLConnection) new URL(urlTextSearch).openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    Scanner scanner = new Scanner(connection.getInputStream());
                    String response = scanner.useDelimiter("\\A").next();
                    scanner.close();
                    connection.disconnect();

                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(response);

                    String key = jsonNode.get(0).get("Key").asText();

                    System.out.println("★★★ Key: " + key + " ★★★");

                    String urlDailyForecasts = "http://dataservice.accuweather.com/forecasts/v1/daily/1day/" + key + "?apikey=" + apiKey + "&language=" + language;

                    connection = (HttpURLConnection) new URL(urlDailyForecasts).openConnection();
                    connection.setRequestMethod("GET");
                    responseCode = connection.getResponseCode();

                    if (responseCode == 200) {
                        scanner = new Scanner(connection.getInputStream());
                        response = scanner.useDelimiter("\\A").next();
                        scanner.close();
                        connection.disconnect();

                        jsonNode = objectMapper.readTree(response);

                        JsonNode dailyForecasts = jsonNode.get("DailyForecasts");
                        for (JsonNode forecast : dailyForecasts) {
                            String date = forecast.get("Date").asText();
                            JsonNode temperature = forecast.get("Temperature");
                            double minTemperatureF = temperature.get("Minimum").get("Value").asDouble();
                            double maxTemperatureF = temperature.get("Maximum").get("Value").asDouble();

                            double minTemperatureC = Math.round(((minTemperatureF - 32) * 5/9) * 10.0) / 10.0;
                            double maxTemperatureC = Math.round(((maxTemperatureF - 32) * 5/9) * 10.0) / 10.0;
                            JsonNode day = forecast.get("Day");
                            String dayIconPhrase = day.get("IconPhrase").asText();
                            JsonNode night = forecast.get("Night");
                            String nightIconPhrase = night.get("IconPhrase").asText();

                            System.out.println("Date: " + date);
                            System.out.println("Temperature - Min: " + minTemperatureC + "°C, Max: " + maxTemperatureC + "°C");
                            System.out.println("Day IconPhrase: " + dayIconPhrase);
                            System.out.println("Night IconPhrase: " + nightIconPhrase);
                            System.out.println();
                        }
                    } else {
                        System.out.println("Błąd: Nie można pobrać prognozy na 1 dzien.");
                    }
                } else {
                    System.out.println("Błąd: Nie można znaleźć lokalizacji.");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }


    public void clicked5DaysButton(Button button, TextField textField) {
        button.setOnAction(e -> {
            try {
                String location = textField.getText();
                String urlTextSearch = "http://dataservice.accuweather.com/locations/v1/search" + "?apikey=" + apiKey + "&q=" + location + "&language=" + language;

                HttpURLConnection connection = (HttpURLConnection) new URL(urlTextSearch).openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    Scanner scanner = new Scanner(connection.getInputStream());
                    String response = scanner.useDelimiter("\\A").next();
                    scanner.close();
                    connection.disconnect();

                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(response);

                    String key = jsonNode.get(0).get("Key").asText();

                    System.out.println("★★★ Key: " + key + " ★★★");

                    String urlDailyForecasts = "http://dataservice.accuweather.com/forecasts/v1/daily/5day/" + key + "?apikey=" + apiKey + "&language=" + language;

                    connection = (HttpURLConnection) new URL(urlDailyForecasts).openConnection();
                    connection.setRequestMethod("GET");
                    responseCode = connection.getResponseCode();

                    if (responseCode == 200) {
                        scanner = new Scanner(connection.getInputStream());
                        response = scanner.useDelimiter("\\A").next();
                        scanner.close();
                        connection.disconnect();

                        jsonNode = objectMapper.readTree(response);

                        JsonNode dailyForecasts = jsonNode.get("DailyForecasts");
                        for (JsonNode forecast : dailyForecasts) {
                            String date = forecast.get("Date").asText();
                            JsonNode temperature = forecast.get("Temperature");
                            double minTemperatureF = temperature.get("Minimum").get("Value").asDouble();
                            double maxTemperatureF = temperature.get("Maximum").get("Value").asDouble();

                            double minTemperatureC = Math.round(((minTemperatureF - 32) * 5/9) * 10.0) / 10.0;
                            double maxTemperatureC = Math.round(((maxTemperatureF - 32) * 5/9) * 10.0) / 10.0;
                            JsonNode day = forecast.get("Day");
                            String dayIconPhrase = day.get("IconPhrase").asText();
                            JsonNode night = forecast.get("Night");
                            String nightIconPhrase = night.get("IconPhrase").asText();

                            System.out.println("Date: " + date);
                            System.out.println("Temperature - Min: " + minTemperatureC + "°C, Max: " + maxTemperatureC + "°C");
                            System.out.println("Day IconPhrase: " + dayIconPhrase);
                            System.out.println("Night IconPhrase: " + nightIconPhrase);
                            System.out.println();
                        }
                    } else {
                        System.out.println("Błąd: Nie można pobrać prognozy na 5 dni.");
                    }
                } else {
                    System.out.println("Błąd: Nie można znaleźć lokalizacji.");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void clickedAirQualityButton(Button button, TextField textField) {
        button.setOnAction(e -> {
            try {
                String location = textField.getText();
                String urlTextSearch = "http://dataservice.accuweather.com/locations/v1/search" + "?apikey=" + apiKey + "&q=" + location + "&language=" + language;

                HttpURLConnection connection = (HttpURLConnection) new URL(urlTextSearch).openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    Scanner scanner = new Scanner(connection.getInputStream());
                    String response = scanner.useDelimiter("\\A").next();
                    scanner.close();
                    connection.disconnect();

                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(response);

                    String key = jsonNode.get(0).get("Key").asText();

                    System.out.println("★★★ Key: " + key + " ★★★");

                    String urlDailyForecasts = "http://dataservice.accuweather.com/indices/v1/daily/1day/" + key + "/-10" + "?apikey=" + apiKey + "&language=" + language;

                    connection = (HttpURLConnection) new URL(urlDailyForecasts).openConnection();
                    connection.setRequestMethod("GET");
                    responseCode = connection.getResponseCode();

                    if (responseCode == 200) {
                        scanner = new Scanner(connection.getInputStream());
                        response = scanner.useDelimiter("\\A").next();
                        scanner.close();
                        connection.disconnect();

                        jsonNode = objectMapper.readTree(response);

                        JsonNode firstElement = jsonNode.get(0);

                        String name = firstElement.get("Name").asText();
                        String localDateTime = firstElement.get("LocalDateTime").asText();
                        String category = firstElement.get("Category").asText();

                        System.out.println("Name: " + name);
                        System.out.println("LocalDateTime: " + localDateTime);
                        System.out.println("Category: " + category);
                        System.out.println();
                    } else {
                        System.out.println("Błąd: Nie można pobrać informacji o jakosci powietrza");
                    }
                } else {
                    System.out.println("Błąd: Nie można znaleźć lokalizacji.");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void clickedNeighborsButton(Button button, TextField textField) {
        button.setOnAction(e -> {
            try {
                String location = textField.getText();
                String urlTextSearch = "http://dataservice.accuweather.com/locations/v1/search" + "?apikey=" + apiKey + "&q=" + location + "&language=" + language;

                HttpURLConnection connection = (HttpURLConnection) new URL(urlTextSearch).openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    Scanner scanner = new Scanner(connection.getInputStream());
                    String response = scanner.useDelimiter("\\A").next();
                    scanner.close();
                    connection.disconnect();

                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(response);

                    String key = jsonNode.get(0).get("Key").asText();

                    System.out.println("★★★ Key: " + key + " ★★★");

                    String urlDailyForecasts = "http://dataservice.accuweather.com/locations/v1/cities/neighbors/" + key + "?apikey=" + apiKey + "&language=" + language;

                    connection = (HttpURLConnection) new URL(urlDailyForecasts).openConnection();
                    connection.setRequestMethod("GET");
                    responseCode = connection.getResponseCode();

                    if (responseCode == 200) {
                        scanner = new Scanner(connection.getInputStream());
                        response = scanner.useDelimiter("\\A").next();
                        scanner.close();
                        connection.disconnect();

                        objectMapper = new ObjectMapper();
                        jsonNode = objectMapper.readTree(response);

                        for (JsonNode locationNode : jsonNode) {
                            String localizedName = locationNode.get("EnglishName").asText();
                            System.out.println("LocalizedName: " + localizedName);
                        }
                        System.out.println();
                    } else {
                        System.out.println("Błąd: Nie można pobrać informacji o sasiadach.");
                    }
                } else {
                    System.out.println("Błąd: Nie można znaleźć lokalizacji.");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public StringProperty getLocation() {
        return this.location;
    }

    public void setLocation(StringProperty location) {
        this.location = location;
    }

    public StringProperty getForecast() {
        return this.forecast;
    }

    public void setForecast(StringProperty forecast) {
        this.forecast = forecast;
    }
}
