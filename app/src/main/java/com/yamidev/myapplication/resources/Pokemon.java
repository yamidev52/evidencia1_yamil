package com.yamidev.myapplication.resources;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Pokemon {
    private int id;
    private String name;
    private int weight;
    private Sprites sprites;
    private List<TypeSlot> types;
    private List<AbilitySlot> abilities;

    public int getId() { return id; }
    public String getName() { return name; }
    public int getWeight() { return weight; }
    public Sprites getSprites() { return sprites; }
    public List<TypeSlot> getTypes() { return types; }
    public List<AbilitySlot> getAbilities() { return abilities; }


    public static class Sprites {
        @SerializedName("front_default") private String frontDefault;
        @SerializedName("back_default")  private String backDefault;
        @SerializedName("front_shiny")   private String frontShiny;
        @SerializedName("back_shiny")    private String backShiny;
        private OtherSprites other;

        public String getFrontDefault() { return frontDefault; }
        public String getBackDefault()  { return backDefault; }
        public String getFrontShiny()   { return frontShiny; }
        public String getBackShiny()    { return backShiny; }
        public OtherSprites getOther()  { return other; }
    }

    public static class OtherSprites {
        @SerializedName("official-artwork")
        private OfficialArtwork officialArtwork;

        public OfficialArtwork getOfficialArtwork() { return officialArtwork; }
    }

    public static class OfficialArtwork {
        @SerializedName("front_default")
        private String frontDefault;

        public String getFrontDefault() { return frontDefault; }
    }

    public static class TypeSlot {
        private Type type;
        public Type getType() { return type; }
    }

    public static class Type {
        private String name;
        public String getName() { return name; }
    }

    public static class AbilitySlot {
        private Ability ability;
        public Ability getAbility() { return ability; }
    }

    public static class Ability {
        private String name;
        public String getName() { return name; }
    }
}