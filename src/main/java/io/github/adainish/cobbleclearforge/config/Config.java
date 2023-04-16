package io.github.adainish.cobbleclearforge.config;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import io.github.adainish.cobbleclearforge.CobbleClearForge;
import io.github.adainish.cobbleclearforge.obj.ItemWhitelist;
import io.github.adainish.cobbleclearforge.obj.PokemonWhitelist;
import io.github.adainish.cobbleclearforge.util.Adapters;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Config
{
    public ItemWhitelist itemWhitelist = new ItemWhitelist();
    public String itemWarningMessage = "&c&lA Item Wipe will be occurring in %time%";
    public List<Integer> warningIntervalsSecondsItems = new ArrayList<>(Arrays.asList(10, 20, 30));
    public String itemsWipedMessage = "&4&lWiped %amount% items";
    public int itemWipeTimerMinutes = 10;
    public PokemonWhitelist pokemonWhitelist = new PokemonWhitelist();

    public String pokemonWarningMessage = "&c&lA Pokemon Wipe will be occurring in %time%";
    public String pokemonsWipedMessage = "&4&lWiped %amount% Pokemon";

    public List<Integer> warningIntervalsSecondsPokemon = new ArrayList<>(Arrays.asList(10, 20, 30));
    public int pokemonWipeTimerMinutes = 10;


    public Config()
    {

    }

    public static void writeConfig()
    {
        File dir = CobbleClearForge.getConfigDir();
        dir.mkdirs();
        Gson gson  = Adapters.PRETTY_MAIN_GSON;
        Config config = new Config();
        try {
            File file = new File(dir, "config.json");
            if (file.exists())
                return;
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            String json = gson.toJson(config);
            writer.write(json);
            writer.close();
        } catch (IOException e)
        {
            CobbleClearForge.getLog().warn(e);
        }
    }

    public static Config getConfig()
    {
        File dir = CobbleClearForge.getConfigDir();
        dir.mkdirs();
        Gson gson  = Adapters.PRETTY_MAIN_GSON;
        File file = new File(dir, "config.json");
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            CobbleClearForge.getLog().error("Something went wrong attempting to read the Config");
            return null;
        }

        return gson.fromJson(reader, Config.class);
    }

}
