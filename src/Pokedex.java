import model.*;
import service.PokeApiService;
import service.JsonService;
import util.ImageToAscii;
import util.TypeMapper;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Pokedex {
    private static Pokemon resumenPokemon = null;

    private static String habilidadElegida = "";
    private static String movimientoElegido = "";

    private static final Scanner scanner = new Scanner(System.in);
    private static final PokeApiService apiService = new PokeApiService();
    private Sprites sprites;

    public Sprites getSprites() { return sprites; }
    public void setSprites(Sprites sprites) { this.sprites = sprites; }

    public static void main(String[] args) {
        int opcion;

        do {

            System.out.println("**********************************");

            System.out.println("📋 Menú Principal");
            System.out.println("1. Elegir un tipo de Pokémon");
            System.out.println("2. Liga Pokémon");
            System.out.println("3. Ver Pokémon elegidos");
            System.out.println("4. Ver batallas");
            System.out.println("5. Guardar Pokémon y batallas");
            System.out.println("6. Cargar Pokémon y batallas");
            System.out.println("7. Salir");

            System.out.println("**********************************");

            System.out.print("Selecciona una opción: ");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1 -> elegirPokemon();
                case 2 -> {
                    if (resumenPokemon == null) {
                        System.out.println("⚠️ Primero debes elegir un Pokémon.");
                    } else {
                        iniciarLiga();
                    }
                }
                case 3 -> mostrarPokemonElegidos();
                case 4 -> mostrarBatallas();
                case 5 -> guardarDatos();
                case 6 -> cargarDatos();
                case 7 -> System.out.println("¡Hasta la próxima, entrenador Pokémon!");
                default -> System.out.println("Opción no válida. Intenta nuevamente.");
            }
        } while (opcion != 7);
    }

    private static void elegirPokemon() {
        System.out.println("Elige un tipo: planta, fuego, agua, eléctrico, psíquico, lucha, normal, metal, oscuro o dragón:");
        String tipoUsuario = scanner.nextLine().trim().toLowerCase();
        String apiType = TypeMapper.toApiType(tipoUsuario);

        if (apiType == null) {
            System.out.println("Tipo no válido.");
            return;
        }

        try {
            Pokemon pokemon = apiService.getRandomPokemonByType(apiType);
            System.out.println("**********************************");
            System.out.println("🔎 Pokémon encontrado:");
            System.out.println("Nombre: " + pokemon.getName());
            System.out.print("Tipo: ");
            for (PokemonTypeSlot slot : pokemon.getTypes()) {
                System.out.print(slot.getType().getName() + " ");
            }

            System.out.println("\nImagen: " + pokemon.getSprites().getFront_default());

            System.out.println("**********************************");

            System.out.println("\nHabilidades:");
            for (int i = 0; i < pokemon.getAbilities().size(); i++) {
                System.out.println((i + 1) + ". " + pokemon.getAbilities().get(i).getAbility().getName());
            }

            System.out.println("Movimientos:");
            for (int i = 0; i < Math.min(pokemon.getMoves().size(), 10); i++) {
                System.out.println((i + 1) + ". " + pokemon.getMoves().get(i).getMove().getName());
            }
            System.out.println("**********************************");
            System.out.println("\nImagen del Pokémon en ASCII:");
            ImageToAscii.printImageAsAscii(pokemon.getSprites().getFront_default(), 30, 10);

            System.out.print("Elige una habilidad por número: ");
            int habIndex = Integer.parseInt(scanner.nextLine()) - 1;
            habilidadElegida = pokemon.getAbilities().get(habIndex).getAbility().getName();

            System.out.print("Elige un movimiento por número: ");
            int movIndex = Integer.parseInt(scanner.nextLine()) - 1;
            movimientoElegido = pokemon.getMoves().get(movIndex).getMove().getName();

            resumenPokemon = pokemon;
            System.out.println("\n📌 RESUMEN DEL POKÉMON:");
            System.out.println("Nombre: " + pokemon.getName());
            System.out.print("Tipo: ");
            for (PokemonTypeSlot slot : pokemon.getTypes()) {
                System.out.print(slot.getType().getName() + " ");
            }
            System.out.println("\nHabilidad elegida: " + habilidadElegida);
            System.out.println("Movimiento elegido: " + movimientoElegido);
            System.out.println("Imagen:");
            ImageToAscii.printImageAsAscii(pokemon.getSprites().getFront_default(), 30, 10);

        } catch (IOException e) {
            System.out.println("Error al conectar con la PokéAPI: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void iniciarLiga() {
        System.out.println("\n⚔️ ¡Iniciando Liga Pokémon!");
        Pokemon rival = PokemonLeague.generateRandomOpponent(apiService);

        if (rival == null) return;

        System.out.println("\n👤 Tu Pokémon:");
        System.out.println("Nombre: " + resumenPokemon.getName());
        System.out.print("Tipo: ");
        for (PokemonTypeSlot slot : resumenPokemon.getTypes()) {
            System.out.print(slot.getType().getName() + " ");
        }

        System.out.println("\n\n🤖 Pokémon rival:");
        System.out.println("Nombre: " + rival.getName());
        System.out.print("Tipo: ");
        for (PokemonTypeSlot slot : rival.getTypes()) {
            System.out.print(slot.getType().getName() + " ");
        }

        System.out.println("\nResultado de la batalla:");
        String resultado = PokemonLeague.determineWinner(resumenPokemon, rival);
        System.out.println(resultado);

        guardarBatalla(resumenPokemon, rival, resultado);
    }

    private static void mostrarPokemonElegidos() {
        try {
            List<PokemonElegido> pokemonElegidos = JsonService.loadPokemonElegidos();
            System.out.println("\nPokémon elegidos:");
            for (PokemonElegido pokemon : pokemonElegidos) {
                System.out.println("Nombre: " + pokemon.getNombre());
                System.out.println("Tipo: " + String.join(", ", pokemon.getTipo()));
                System.out.println("Habilidad: " + pokemon.getHabilidad());
                System.out.println("Movimiento: " + pokemon.getMovimiento());
                System.out.println();
            }
        } catch (IOException e) {
            System.out.println("Error al cargar los Pokémon elegidos: " + e.getMessage());
        }
    }

    private static void mostrarBatallas() {
        try {
            List<Batalla> batallas = JsonService.loadBatallas();
            System.out.println("\nBatallas:");
            for (Batalla batalla : batallas) {
                System.out.println("Tu Pokémon: " + batalla.getPokemonUsuario());
                System.out.println("Pokémon Oponente: " + batalla.getPokemonOponente());
                System.out.println("Resultado: " + batalla.getResultado());
                System.out.println("Fecha: " + batalla.getFecha());
                System.out.println();
            }
        } catch (IOException e) {
            System.out.println("Error al cargar las batallas: " + e.getMessage());
        }
    }

    private static void guardarDatos() {
        try {
            List<PokemonElegido> pokemonElegidos = JsonService.loadPokemonElegidos();
            if (resumenPokemon != null) {
                pokemonElegidos.add(new PokemonElegido(
                        resumenPokemon.getName(),
                        List.of(resumenPokemon.getTypes().get(0).getType().getName()),
                        habilidadElegida,
                        movimientoElegido
                ));
                JsonService.savePokemonElegidos(pokemonElegidos);
            }

            List<Batalla> batallas = JsonService.loadBatallas();
            batallas.add(new Batalla(resumenPokemon.getName(), "Rival", "Ganaste", new java.util.Date()));
            JsonService.saveBatallas(batallas);

            System.out.println("Datos guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar los datos: " + e.getMessage());
        }
    }

    private static void cargarDatos() {
        try {
            List<PokemonElegido> pokemonElegidos = JsonService.loadPokemonElegidos();
            List<Batalla> batallas = JsonService.loadBatallas();
            System.out.println("Datos cargados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al cargar los datos: " + e.getMessage());
        }
    }

    private static void guardarBatalla(Pokemon pokemonUsuario, Pokemon pokemonOponente, String resultado) {
        try {
            List<Batalla> batallas = JsonService.loadBatallas();
            batallas.add(new Batalla(pokemonUsuario.getName(), pokemonOponente.getName(), resultado, new java.util.Date()));
            JsonService.saveBatallas(batallas);
        } catch (IOException e) {
            System.out.println("Error al guardar la batalla: " + e.getMessage());
        }
    }
}
