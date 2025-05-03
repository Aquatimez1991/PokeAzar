import model.*;
import service.PokeApiService;
import util.TypeMapper;
import util.ImageToAscii;
import java.io.IOException;
import java.util.Scanner;

public class Pokedex {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PokeApiService apiService = new PokeApiService();

        System.out.println("Elige un tipo: planta, fuego, agua, electrico, psiquico, lucha, normal, metal, oscuro o dragon:");
        String tipoUsuario = scanner.nextLine().trim().toLowerCase();
        String apiType = TypeMapper.toApiType(tipoUsuario);

        if (apiType == null) {
            System.out.println("Tipo no válido.");
            return;
        }

        try {
            Pokemon pokemon = apiService.getRandomPokemonByType(apiType);
            System.out.println("\n🔎 Pokémon encontrado:");
            System.out.println("Nombre: " + pokemon.getName());

            // TIPOS
            System.out.print("Tipo: ");
            for (PokemonTypeSlot slot : pokemon.getTypes()) {
                System.out.print(slot.getType().getName() + " ");
            }

            // URL DE IMAGEN
            System.out.println("\nImagen: " + pokemon.getSprites().getFront_default());

            // ASCII
            System.out.println("\nImagen del Pokémon en ASCII:");
            ImageToAscii.printImageAsAscii(pokemon.getSprites().getFront_default(), 30, 10);

            // HABILIDADES
            System.out.println("\nHabilidades:");
            for (PokemonAbilitySlot abilitySlot : pokemon.getAbilities()) {
                System.out.println("- " + abilitySlot.getAbility().getName());
            }

            // MOVIMIENTOS (mostrar solo 5 primeros)
            System.out.println("\nMovimientos:");
            int count = 0;
            for (PokemonMoveSlot moveSlot : pokemon.getMoves()) {
                System.out.println("- " + moveSlot.getMove().getName());
                if (++count == 5) break;
            }

        } catch (IOException e) {
            System.out.println("Error al conectar con la PokéAPI: " + e.getMessage());
        }
    }
}
