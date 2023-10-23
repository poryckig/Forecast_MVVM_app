package Forecast.Models;

import com.fasterxml.jackson.databind.JsonNode;

public class WeatherService {

    private final String apiKey = "xdnYM67j2fBPsykUdeADO8rwwNm7E6Lb";
    private final String language = "pl";

    public JsonNode searchLocation(String location) throws IOException {
        // Implementacja wyszukiwania lokalizacji i zwrócenie odpowiedzi w formie JsonNode
    }

    public JsonNode get1HourForecast(String key) throws IOException {
        // Implementacja pobrania prognozy na 1h i zwrócenie odpowiedzi w formie JsonNode
    }
}
