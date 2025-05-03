package service;

import com.google.gson.*;
import model.Pokemon;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;

public class PokeApiService {
    private static final String BASE_URL = "https://pokeapi.co/api/v2/type/";

    public Pokemon getRandomPokemonByType(String type) throws IOException {
        URL url = new URL(BASE_URL + type.toLowerCase());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        Scanner sc = new Scanner(conn.getInputStream());
        StringBuilder jsonBuilder = new StringBuilder();
        while (sc.hasNext()) jsonBuilder.append(sc.nextLine());
        sc.close();

        JsonObject jsonObject = JsonParser.parseString(jsonBuilder.toString()).getAsJsonObject();
        JsonArray pokemonArray = jsonObject.getAsJsonArray("pokemon");

        int randomIndex = new Random().nextInt(pokemonArray.size());
        String pokemonUrl = pokemonArray.get(randomIndex).getAsJsonObject()
                .get("pokemon").getAsJsonObject()
                .get("url").getAsString();

        return fetchPokemonDetails(pokemonUrl);
    }

    private Pokemon fetchPokemonDetails(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        Scanner sc = new Scanner(conn.getInputStream());
        StringBuilder jsonBuilder = new StringBuilder();
        while (sc.hasNext()) jsonBuilder.append(sc.nextLine());
        sc.close();

        Gson gson = new Gson();
        return gson.fromJson(jsonBuilder.toString(), Pokemon.class);
    }
}
