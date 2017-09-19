package com.example.ray.pokedb;

import android.app.Activity;
import android.content.Context;
import java.util.HashMap;

/**
 * Created by Rayaan Fakier on 2016/10/21.
 * Contains methods for retrieving all constants needed across application
 */

public class Constants extends Activity {
    private Context context;

    public Constants(Context c){
        context = c;
    }

    public String[] getAllPokemon(){
        return context.getResources().getStringArray(R.array.pokemon_candy_keys);
    }

    public int[] getAllCandiesToEvo(){
        return context.getResources().getIntArray(R.array.pokemon_candy_vals);
    }

    public HashMap<String, Integer> getPokeToCandyForEvoMap(){
        HashMap<String, Integer> map = new HashMap<>();
        String[] allPokemon = getAllPokemon();
        int[] allCandiesToEvo = getAllCandiesToEvo();

        for (int i = 0; i < 151; i++){
            map.put(allPokemon[i], allCandiesToEvo[i]);
        }

        return map;
    }

    public int getPokemonId(String pokemonName){

        String[] pokemon = getAllPokemon();

        try{

            int id = 1;
            while (!pokemon[id-1].equals(pokemonName)){
                id++;
            }

            return id;
        }
        catch (ArrayIndexOutOfBoundsException e){
            return -1;
        }
    }

    public HashMap<String, String[]> getEvoMap(){
        HashMap<String, String[]> map = new HashMap<>();
        map.put("Bulbasaur", new String[]{"Ivysaur", "Venusaur"});
        map.put("Squirtle", new String[]{"Wartortle", "Blastoise"});
        map.put("Charmnder", new String[]{"Charmander", "Charizard"});
        map.put("Weedle", new String[]{"Kakuna", "Beedrill"});
        map.put("Caterpie", new String[]{"Metapod", "Butterfree"});
        map.put("Pidgey", new String[]{"Pidgeotto", "Pidgeot"});
        map.put("Rattata", new String[]{"Raticate"});
        map.put("Spearow", new String[]{"Fearow"});
        map.put("Ekans", new String[]{"Arbok"});
        map.put("Pikachu", new String[]{"Raichu"});
        map.put("Sandshrew", new String[]{"Sandslash"});
        map.put("Nidoran♀", new String[]{"Nidorina", "Nidoqueen"});
        map.put("Nidoran♂", new String[]{"Nidorino", "Nidoking"});
        map.put("Clefairy", new String[]{"Clefable"});
        map.put("Vulpix", new String[]{"Ninetales"});
        map.put("Jigglypuff", new String[]{"Wigglytuff"});
        map.put("Zubat", new String[]{"Golbat"});
        map.put("Oddish", new String[]{"Gloom", "Vileplume"});
        map.put("Paras", new String[]{"Parasect"});
        map.put("Venomoth", new String[]{"Venomoth"});
        map.put("Diglett", new String[]{"Dugtrio"});
        map.put("Meowth", new String[]{"Persian"});
        map.put("Psyduck", new String[]{"Golduck"});
        map.put("Mankey", new String[]{"Primeape"});
        map.put("Growlithe", new String[]{"Arcanine"});
        map.put("Poliwag", new String[]{"Poliwhirl", "Poliwrath"});
        map.put("Abra", new String[]{"Kadabra", "Alakazam"});
        map.put("Machop", new String[]{"Machoke", "Machamp"});
        map.put("Bellsprout", new String[]{"Weepinbell", "Victreebel"});
        map.put("Tentacool", new String[]{"Tentacruel"});
        map.put("Geodude", new String[]{"Graveler", "Golem"});
        map.put("Ponyta", new String[]{"Rapidash"});
        map.put("Slowpoke", new String[]{"Slowbro"});
        map.put("Magnemite", new String[]{"Magneton"});
        map.put("Doduo", new String[]{"Dodrio"});
        map.put("Seel", new String[]{"Dewgong"});
        map.put("Grimer", new String[]{"Muk"});
        map.put("Shellder", new String[]{"Cloyster"});
        map.put("Gastly", new String[]{"Haunter", "Gengar"});
        map.put("Drowzee", new String[]{"Hypno"});
        map.put("Krabby", new String[]{"Kingler"});
        map.put("Exeggcute", new String[]{"Exeggutor"});
        map.put("Cubone", new String[]{"Marowak"});
        map.put("Koffing", new String[]{"Weezing"});
        map.put("Rhyhorn", new String[]{"Rhydon"});
        map.put("Horsea", new String[]{"Seadra"});
        map.put("Goldeen", new String[]{"Seaking"});
        map.put("Staryu", new String[]{"Starmie"});
        map.put("Magikarp", new String[]{"Gyarados"});
        map.put("Eevee", new String[]{"Vaporeon", "Jolteon", "Flareon"});
        map.put("Omanyte", new String[]{"Omastar"});
        map.put("Kabuto", new String[]{"Kabutops"});
        map.put("Dratini", new String[]{"Dragonair", "Dragonite"});

        return map;
    }


}
