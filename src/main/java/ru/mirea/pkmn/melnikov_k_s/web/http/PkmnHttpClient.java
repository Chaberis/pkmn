package ru.mirea.pkmn.melnikov_k_s.web.http;

import com.fasterxml.jackson.databind.JsonNode;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

public class PkmnHttpClient {
    private Retrofit client;
    private PokemonTcgAPI tcgAPI;

    public PkmnHttpClient() {
        client = new Retrofit.Builder()
                .baseUrl("https://api.pokemontcg.io")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        tcgAPI = client.create(PokemonTcgAPI.class);
    }

    public JsonNode getPokemonCard(String name, String number) throws IOException {
        String requestQuery = "name:\"" + name + "\" number:" + number;

        Call<JsonNode> call = tcgAPI.getPokemon(requestQuery);
        Response<JsonNode> response = call.execute();

        if (!response.isSuccessful()) {
            throw new IOException("Ошибка при получении данных: " + response.message());
        }

        return response.body();
    }
}