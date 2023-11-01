package Forecast.ViewModels;

import java.io.IOException;
import java.util.Scanner;
import java.net.HttpURLConnection;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import Forecast.Models.WeatherService;      // do Model
import Forecast.View.GUI;

public class ForecastViewModel {

    private final WeatherService weatherService;    // do Model
    private final GUI gui;

    private StringProperty location1Hour = new SimpleStringProperty();
    private StringProperty location1Day = new SimpleStringProperty();
    private StringProperty location5Days = new SimpleStringProperty();
    private StringProperty locationAirQuality = new SimpleStringProperty();
    private StringProperty locationNeighbors = new SimpleStringProperty();

    public ForecastViewModel(GUI gui) {
        this.gui = gui;
        this.weatherService = new WeatherService();     // do Model
    }

    public void search1HourForecast() {
        try {
            weatherService.sendRequestToAPISearch("/locations/v1/search", location1Hour.get());
            HttpURLConnection response = weatherService.getResponseFromAPI();

            if (response.getResponseCode() == 200) {
                Scanner scanner = new Scanner(response.getInputStream());
                String responseString = scanner.useDelimiter("\\A").next();
                scanner.close();
                response.disconnect();

                // Przetwarzamy JSON za pomocą Jackson
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseString);

                // Pobieramy wartość "Key" z JSON i zapisujemy do zmiennej
                String key = jsonNode.get(0).get("Key").asText();

                // *** ODPOWIEDŹ DO GUI ***
                sendResponseToView("★★★ Key: " + key + " ★★★");

                // Teraz pobieramy dane z drugiego endpointa
                weatherService.sendRequestToAPI1Hour("/forecasts/v1/hourly/1hour/", key);
                response = weatherService.getResponseFromAPI();

                if (response.getResponseCode() == 200) {
                    scanner = new Scanner(response.getInputStream());
                    responseString = scanner.useDelimiter("\\A").next();
                    scanner.close();
                    response.disconnect();

                    // Przetwarzamy JSON za pomocą Jackson
                    jsonNode = objectMapper.readTree(responseString);

                    // Pobieramy wartość "dailyForecasts z JSON i zapisujemy do zmiennej
                    JsonNode dailyForecasts = jsonNode.get(0);

                    String date = dailyForecasts.get("DateTime").asText();
                    JsonNode temperature = dailyForecasts.get("Temperature");
                    double temperatureF = temperature.get("Value").asDouble();

                    // Przeliczamy temperature z F na C
                    double temperatureC = Math.round(((temperatureF - 32) * 5/9) * 10.0) / 10.0;

                    String iconPhrase = dailyForecasts.get("IconPhrase").asText();

                    // *** ODPOWIEDŹ DO GUI ***
                    sendResponseToView("DateTime: " + date);
                    sendResponseToView("Temperature: " + temperatureC + "°C");
                    sendResponseToView("IconPhrase: " + iconPhrase + "\n");
                } else {
                    // *** ODPOWIEDŹ DO GUI ***
                    sendResponseToView("Błąd: Nie można pobrać prognozy na 1h.");
                }
            } else {
                // *** ODPOWIEDŹ DO GUI ***
                sendResponseToView("Błąd: Nie można znaleźć lokalizacji.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void search1DayForecast() {
        try {
            weatherService.sendRequestToAPISearch("/locations/v1/search", location1Day.get());
            HttpURLConnection response = weatherService.getResponseFromAPI();

            if (response.getResponseCode() == 200) {
                Scanner scanner = new Scanner(response.getInputStream());
                String responseString = scanner.useDelimiter("\\A").next();
                scanner.close();
                response.disconnect();

                // Przetwarzamy JSON za pomocą Jackson
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseString);

                // Pobieramy wartość "Key" z JSON i zapisujemy do zmiennej
                String key = jsonNode.get(0).get("Key").asText();

                // *** ODPOWIEDŹ DO GUI ***
                sendResponseToView("★★★ Key: " + key + " ★★★");

                // Teraz pobieramy dane z drugiego endpointa
                weatherService.sendRequestToAPI1Day("/forecasts/v1/daily/1day/", key);
                response = weatherService.getResponseFromAPI();

                if (response.getResponseCode() == 200) {
                    scanner = new Scanner(response.getInputStream());
                    responseString = scanner.useDelimiter("\\A").next();
                    scanner.close();
                    response.disconnect();

                    // Przetwarzamy JSON za pomocą Jackson
                    jsonNode = objectMapper.readTree(responseString);

                    // Pobieramy wartość "dailyForecasts z JSON i zapisujemy do zmiennej
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

                        // *** ODPOWIEDŹ DO GUI ***
                        sendResponseToView("Date: " + date);
                        sendResponseToView("Temperature - Min: " + minTemperatureC + "°C, Max: " + maxTemperatureC + "°C");
                        sendResponseToView("Day IconPhrase: " + dayIconPhrase);
                        sendResponseToView("Night IconPhrase: " + nightIconPhrase + "\n");
                    }
                } else {
                    // *** ODPOWIEDŹ DO GUI ***
                    sendResponseToView("Błąd: Nie można pobrać prognozy na 1 dzień.");
                }
            } else {
                // *** ODPOWIEDŹ DO GUI ***
                sendResponseToView("Błąd: Nie można znaleźć lokalizacji.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void search5DaysForecast() {
        try {
            weatherService.sendRequestToAPISearch("/locations/v1/search", location5Days.get());
            HttpURLConnection response = weatherService.getResponseFromAPI();

            if (response.getResponseCode() == 200) {
                Scanner scanner = new Scanner(response.getInputStream());
                String responseString = scanner.useDelimiter("\\A").next();
                scanner.close();
                response.disconnect();

                // Przetwarzamy JSON za pomocą Jackson
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseString);

                // Pobieramy wartość "Key" z JSON i zapisujemy do zmiennej
                String key = jsonNode.get(0).get("Key").asText();

                // *** ODPOWIEDŹ DO GUI ***
                sendResponseToView("★★★ Key: " + key + " ★★★");

                // Teraz pobieramy dane z drugiego endpointa
                weatherService.sendRequestToAPI5Days("/forecasts/v1/daily/5day/", key);
                response = weatherService.getResponseFromAPI();

                if (response.getResponseCode() == 200) {
                    scanner = new Scanner(response.getInputStream());
                    responseString = scanner.useDelimiter("\\A").next();
                    scanner.close();
                    response.disconnect();

                    // Przetwarzamy JSON za pomocą Jackson
                    jsonNode = objectMapper.readTree(responseString);

                    // Pobieramy wartość "dailyForecasts z JSON i zapisujemy do zmiennej
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

                        sendResponseToView("Date: " + date);
                        sendResponseToView("Temperature - Min: " + minTemperatureC + "°C, Max: " + maxTemperatureC + "°C");
                        sendResponseToView("Day IconPhrase: " + dayIconPhrase);
                        sendResponseToView("Night IconPhrase: " + nightIconPhrase + "\n");
                    }
                } else {
                    // *** ODPOWIEDŹ DO GUI ***
                    sendResponseToView("Błąd: Nie można pobrać prognozy na 5 dni.");
                }
            } else {
                // *** ODPOWIEDŹ DO GUI ***
                System.out.println("Błąd: Nie można znaleźć lokalizacji.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchAirQuality() {
        try {
            weatherService.sendRequestToAPISearch("/locations/v1/search", locationAirQuality.get());
            HttpURLConnection response = weatherService.getResponseFromAPI();

            if (response.getResponseCode() == 200) {
                Scanner scanner = new Scanner(response.getInputStream());
                String responseString = scanner.useDelimiter("\\A").next();
                scanner.close();
                response.disconnect();

                // Przetwarzamy JSON za pomocą Jackson
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseString);

                // Pobieramy wartość "Key" z JSON i zapisujemy do zmiennej
                String key = jsonNode.get(0).get("Key").asText();

                // *** ODPOWIEDŹ DO GUI ***
                sendResponseToView("★★★ Key: " + key + " ★★★");

                // Teraz pobieramy dane z drugiego endpointa
                weatherService.sendRequestToAPIAirQuality("/indices/v1/daily/1day/", key);
                response = weatherService.getResponseFromAPI();

                if (response.getResponseCode() == 200) {
                    scanner = new Scanner(response.getInputStream());
                    responseString = scanner.useDelimiter("\\A").next();
                    scanner.close();
                    response.disconnect();

                    // Przetwarzamy JSON za pomocą Jackson
                    jsonNode = objectMapper.readTree(responseString);

                    // Pobieramy wartość "dailyForecasts z JSON i zapisujemy do zmiennej
                    JsonNode firstElement = jsonNode.get(0);

                    String name = firstElement.get("Name").asText();
                    String localDateTime = firstElement.get("LocalDateTime").asText();
                    String category = firstElement.get("Category").asText();

                    // *** ODPOWIEDŹ DO GUI ***
                    sendResponseToView("Name: " + name);
                    sendResponseToView("LocalDateTime: " + localDateTime);
                    sendResponseToView("Category: " + category + "\n");
                } else {
                    // *** ODPOWIEDŹ DO GUI ***
                    sendResponseToView("Błąd: Nie można pobrać informacji o jakosci powietrza.");
                }
            } else {
                // *** ODPOWIEDŹ DO GUI ***
                sendResponseToView("Błąd: Nie można znaleźć lokalizacji.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchNeighbors() {
        try {
            weatherService.sendRequestToAPISearch("/locations/v1/search", locationNeighbors.get());
            HttpURLConnection response = weatherService.getResponseFromAPI();

            if (response.getResponseCode() == 200) {
                Scanner scanner = new Scanner(response.getInputStream());
                String responseString = scanner.useDelimiter("\\A").next();
                scanner.close();
                response.disconnect();

                //Przetwarzamy JSON za pomocą Jackson
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseString);

                //Pobieramy wartość "Key" z JSON i zapisujemy do zmiennej
                String key = jsonNode.get(0).get("Key").asText();

                // *** ODPOWIEDŹ DO GUI ***
                sendResponseToView("★★★ Key: " + key + " ★★★");

                //Teraz pobieramy dane z drugiego endpointa
                weatherService.sendRequestToAPINeighbors("/locations/v1/cities/neighbors/", key);
                response = weatherService.getResponseFromAPI();

                if (response.getResponseCode() == 200) {
                    scanner = new Scanner(response.getInputStream());
                    responseString = scanner.useDelimiter("\\A").next();
                    scanner.close();
                    response.disconnect();

                    // Przetwarzamy JSON za pomocą Jackson
                    jsonNode = objectMapper.readTree(responseString);

                    for (JsonNode locationNode : jsonNode) {
                        String localizedName = locationNode.get("EnglishName").asText();

                        // *** ODPOWIEDŹ DO GUI ***
                        sendResponseToView("LocalizedName: " + localizedName);
                    }
                    // *** ODPOWIEDŹ DO GUI ***
                    sendResponseToView("\n");
                } else {
                    // *** ODPOWIEDŹ DO GUI ***
                    sendResponseToView("Błąd: Nie można pobrać informacji o sasiadach.");
                }
            } else {
                // *** ODPOWIEDŹ DO GUI ***
                sendResponseToView("Błąd: Nie można znaleźć lokalizacji.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public StringProperty getLocation1Hour() {
        return this.location1Hour;
    }

    public StringProperty getLocation1Day() {
        return this.location1Day;
    }

    public StringProperty getLocation5Days() {
        return this.location5Days;
    }

    public StringProperty getLocationAirQuality() {
        return this.locationAirQuality;
    }

    public StringProperty getLocationNeighbors() {
        return this.locationNeighbors;
    }

    private void sendResponseToView(String response) {
        gui.displayResponse(response);
    }
}
