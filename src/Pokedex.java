import model.*;
import service.PokeApiService;
import util.TypeMapper;
import util.ImageToAscii;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Pokedex {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PokeApiService apiService = new PokeApiService();

        boolean salir = false;

        while (!salir) {
            System.out.println("\n📜 MENÚ DE OPCIONES:");
            System.out.println("1. Elige un tipo de Pokémon");
            System.out.println("2. Salir");
            System.out.print("Opción: ");
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1":
                    System.out.println("\nTipos disponibles: planta, fuego, agua, electrico, psiquico, lucha, normal, metal, oscuro o dragon.");
                    System.out.print("Elige un tipo: ");
                    String tipoUsuario = scanner.nextLine().trim().toLowerCase();
                    String apiType = TypeMapper.toApiType(tipoUsuario);

                    if (apiType == null) {
                        System.out.println("⚠️ Tipo no válido.");
                        break;
                    }

                    try {
                        Pokemon pokemon = apiService.getRandomPokemonByType(apiType);
                        System.out.println("\n🔎 Pokémon encontrado:");
                        System.out.println("Nombre: " + pokemon.getName());

                        System.out.print("Tipo: ");
                        for (PokemonTypeSlot slot : pokemon.getTypes()) {
                            System.out.print(slot.getType().getName() + " ");
                        }

                        System.out.println("\nImagen: " + pokemon.getSprites().getFront_default());

                        System.out.println("\nImagen del Pokémon en ASCII:");
                        ImageToAscii.printImageAsAscii(pokemon.getSprites().getFront_default(), 30, 10);

                        List<PokemonAbilitySlot> abilities = pokemon.getAbilities();
                        System.out.println("\nHabilidades disponibles:");
                        for (int i = 0; i < abilities.size(); i++) {
                            System.out.println((i + 1) + ". " + abilities.get(i).getAbility().getName());
                        }
                        int indexHabilidad = -1;
                        while (indexHabilidad < 0 || indexHabilidad >= abilities.size()) {
                            System.out.print("Elige el número de una habilidad: ");
                            try {
                                indexHabilidad = Integer.parseInt(scanner.nextLine().trim()) - 1;
                                if (indexHabilidad < 0 || indexHabilidad >= abilities.size()) {
                                    System.out.println("❌ Número inválido. Intenta nuevamente.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("❌ Entrada inválida. Ingresa un número.");
                            }
                        }
                        String habilidadElegida = abilities.get(indexHabilidad).getAbility().getName();

                        List<PokemonMoveSlot> moves = pokemon.getMoves();
                        int cantidadMostrar = Math.min(5, moves.size());
                        System.out.println("\nMovimientos disponibles:");
                        for (int i = 0; i < cantidadMostrar; i++) {
                            System.out.println((i + 1) + ". " + moves.get(i).getMove().getName());
                        }
                        int indexMovimiento = -1;
                        while (indexMovimiento < 0 || indexMovimiento >= cantidadMostrar) {
                            System.out.print("Elige el número de un movimiento: ");
                            try {
                                indexMovimiento = Integer.parseInt(scanner.nextLine().trim()) - 1;
                                if (indexMovimiento < 0 || indexMovimiento >= cantidadMostrar) {
                                    System.out.println("❌ Número inválido. Intenta nuevamente.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("❌ Entrada inválida. Ingresa un número.");
                            }
                        }
                        String movimientoElegido = moves.get(indexMovimiento).getMove().getName();

                        System.out.println("\n📌 RESUMEN DEL POKÉMON:");
                        System.out.println("Nombre: " + pokemon.getName());
                        System.out.print("Tipo: ");
                        for (PokemonTypeSlot slot : pokemon.getTypes()) {
                            System.out.print(slot.getType().getName() + " ");
                        }
                        System.out.println("\nHabilidad elegida: " + habilidadElegida);
                        System.out.println("Movimiento elegido: " + movimientoElegido);

                        System.out.println("\nImagen del Pokémon en ASCII:");
                        ImageToAscii.printImageAsAscii(pokemon.getSprites().getFront_default(), 30, 10);

                    } catch (IOException e) {
                        System.out.println("⚠️ Error al conectar con la PokéAPI: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("⚠️ Ocurrió un error: " + e.getMessage());
                    }
                    break;

                case "2":
                    salir = true;
                    System.out.println("👋 ¡Hasta la próxima, entrenador Pokémon!");
                    break;

                default:
                    System.out.println("❌ Opción no válida. Intenta nuevamente.");
            }
        }
    }
}
