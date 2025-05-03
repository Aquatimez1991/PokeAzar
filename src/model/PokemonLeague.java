package model;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class PokemonLeague {

    // Ventajas de los tipos de Pokémon (puedes expandirla según las reglas)
    private static final Map<String, List<String>> typeAdvantages = Map.of(
            "grass", List.of("water"),
            "fire", List.of("grass", "steel"),
            "water", List.of("fire"),
            "electric", List.of("water"),
            "psychic", List.of("fighting"),
            "fighting", List.of("normal", "steel"),
            "normal", List.of(),
            "steel", List.of(),
            "dark", List.of("psychic"),
            "dragon", List.of("dragon")
    );

    // Lista de tipos válidos
    private static final List<String> validTypes = List.of(
            "grass", "fire", "water", "electric", "psychic",
            "fighting", "normal", "steel", "dark", "dragon"
    );

    // Método para generar un Pokémon aleatorio de un tipo válido
    public static Pokemon generateRandomOpponent(service.PokeApiService apiService) {
        Random random = new Random();
        String randomType;
        Pokemon opponentPokemon = null;

        // Intentar generar un Pokémon hasta que tenga un tipo válido
        do {
            randomType = validTypes.get(random.nextInt(validTypes.size()));
            try {
                opponentPokemon = apiService.getRandomPokemonByType(randomType);
            } catch (Exception e) {
                System.out.println("Error al generar Pokémon oponente: " + e.getMessage());
                return null; // En caso de error, retornamos null
            }
        } while (opponentPokemon != null && !isValidSingleType(opponentPokemon));

        return opponentPokemon;
    }

    // Validar si el Pokémon tiene solo un tipo válido
    private static boolean isValidSingleType(Pokemon pokemon) {
        // Verificar si el Pokémon tiene solo un tipo y ese tipo es válido
        if (pokemon.getTypes().size() == 1) {
            String pokemonType = pokemon.getTypes().get(0).getType().getName().toLowerCase();
            return validTypes.contains(pokemonType); // Comprobar si el tipo está en la lista de tipos válidos
        }
        return false; // Si tiene más de un tipo, no es válido
    }

    // Método para determinar el ganador de una batalla
    public static String determineWinner(Pokemon userPokemon, Pokemon opponentPokemon) {
        String userType = userPokemon.getTypes().get(0).getType().getName().toLowerCase();
        String opponentType = opponentPokemon.getTypes().get(0).getType().getName().toLowerCase();

        boolean userWins = typeAdvantages.getOrDefault(userType, List.of()).contains(opponentType);
        boolean opponentWins = typeAdvantages.getOrDefault(opponentType, List.of()).contains(userType);

        if (userWins && !opponentWins) return "¡Ganaste la batalla!";
        if (!userWins && opponentWins) return "Perdiste la batalla...";
        return "Empate. Ambos Pokémon están equilibrados.";
    }
}
