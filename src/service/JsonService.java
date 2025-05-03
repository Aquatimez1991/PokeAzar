package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import model.Batalla;
import model.PokemonElegido;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonService {

    private static final String POKEMON_FILE = "pokemonElegidos.json";
    private static final String BATALLAS_FILE = "batallas.json";

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void savePokemonElegidos(List<PokemonElegido> pokemonElegidos) throws IOException {
        FileWriter writer = new FileWriter(POKEMON_FILE);
        gson.toJson(pokemonElegidos, writer);
        writer.close();
    }

    public static List<PokemonElegido> loadPokemonElegidos() throws IOException {
        File file = new File(POKEMON_FILE);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        FileReader reader = new FileReader(file);
        Type type = new TypeToken<List<PokemonElegido>>() {}.getType();
        List<PokemonElegido> lista = gson.fromJson(reader, type);

        return lista != null ? lista : new ArrayList<>();
    }

    public static void saveBatallas(List<Batalla> batallas) throws IOException {

        String json = gson.toJson(batallas);

        String jsonPrettified = gson.toJson(JsonParser.parseString(json));

        try (FileWriter writer = new FileWriter(BATALLAS_FILE)) {
            writer.write(jsonPrettified);
        }
    }

    public static List<Batalla> loadBatallas() throws IOException {
        File file = new File(BATALLAS_FILE);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        FileReader reader = new FileReader(BATALLAS_FILE);
        Type type = new TypeToken<List<Batalla>>() {}.getType();
        return gson.fromJson(reader, type);
    }

}
