package model;

import java.util.List;

public class Pokemon {
    private int id;
    private String name;
    private List<PokemonTypeSlot> types;
    private List<PokemonAbilitySlot> abilities;
    private List<PokemonMoveSlot> moves;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<PokemonTypeSlot> getTypes() { return types; }
    public void setTypes(List<PokemonTypeSlot> types) { this.types = types; }

    private Sprites sprites;

    public Sprites getSprites() { return sprites; }
    public void setSprites(Sprites sprites) { this.sprites = sprites; }
    public List<PokemonAbilitySlot> getAbilities() {
        return abilities;
    }

    public List<PokemonMoveSlot> getMoves() {
        return moves;
    }
}
