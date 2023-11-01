package Forecast.Models;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherService {

    private HttpURLConnection connection;
    private final String apiKey = "OhQeMbFNGA1LSeleUrvATkZ9TjkTSBpM";
    private final String language = "pl";

    private final String BASE_URL = "http://dataservice.accuweather.com";
    
    public void sendRequestToAPISearch(String endpoint, String location) throws IOException {
        String urlString = BASE_URL + endpoint + "?apikey=" + apiKey + "&q=" + location + "&language=" + language;
        connection = (HttpURLConnection) new URL(urlString).openConnection();
        connection.setRequestMethod("GET");
    }

    public void sendRequestToAPI1Hour(String endpoint, String key) throws IOException {
        String urlString = BASE_URL + endpoint + key + "?apikey=" + apiKey + "&language=" + language;
        connection = (HttpURLConnection) new URL(urlString).openConnection();
        connection.setRequestMethod("GET");
    }

    public void sendRequestToAPI1Day(String endpoint, String key) throws IOException {
        String urlString = BASE_URL + endpoint + key + "?apikey=" + apiKey + "&language=" + language;
        connection = (HttpURLConnection) new URL(urlString).openConnection();
        connection.setRequestMethod("GET");
    }

    public void sendRequestToAPI5Days(String endpoint, String key) throws IOException {
        String urlString = BASE_URL + endpoint + key + "?apikey=" + apiKey + "&language=" + language;
        connection = (HttpURLConnection) new URL(urlString).openConnection();
        connection.setRequestMethod("GET");
    }

    public void sendRequestToAPIAirQuality(String endpoint, String key) throws IOException {
        String urlString = BASE_URL + endpoint + key + "/-10" + "?apikey=" + apiKey + "&language=" + language;
        connection = (HttpURLConnection) new URL(urlString).openConnection();
        connection.setRequestMethod("GET");
    }

    public void sendRequestToAPINeighbors(String endpoint, String key) throws IOException {
        String urlString = BASE_URL + endpoint + key + "?apikey=" + apiKey + "&language=" + language;
        connection = (HttpURLConnection) new URL(urlString).openConnection();
        connection.setRequestMethod("GET");
    }

    public HttpURLConnection getResponseFromAPI() {
        return this.connection;
    }
}
