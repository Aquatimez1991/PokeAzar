package util;

import java.util.Map;

public class TypeMapper {
    private static final Map<String, String> TYPE_MAP = Map.of(
            "planta", "grass",
            "fuego", "fire",
            "agua", "water",
            "electrico", "electric",
            "psiquico", "psychic",
            "lucha", "fighting",
            "normal", "normal",
            "metal", "steel",
            "oscuro", "dark",
            "dragon", "dragon"
    );

    public static String toApiType(String input) {
        return TYPE_MAP.getOrDefault(input.toLowerCase(), null);
    }
}
