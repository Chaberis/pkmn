package ru.mirea.pkmn.melnikov_k_s;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PokemonAPI {

    private static final String BASE_URL = "https://api.pokemontcg.io/v2/cards";

    public static JsonNode getCardFromAPI(String name, String number) throws Exception {
        String query = String.format("?q=name:\"%s\" number:%s", name, number);
        URL url = new URL(BASE_URL + query);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json");

        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(response.toString());
        } else {
            throw new Exception("Failed to get data from API: " + responseCode);
        }
    }
}