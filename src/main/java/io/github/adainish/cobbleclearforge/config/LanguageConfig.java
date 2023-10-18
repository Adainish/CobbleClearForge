package io.github.adainish.cobbleclearforge.config;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import io.github.adainish.cobbleclearforge.CobbleClearForge;
import io.github.adainish.cobbleclearforge.util.Adapters;

import java.io.*;

public class LanguageConfig
{
    public String prefix = "&c&l[&b&lCobbleClear&c&l]";
    public String splitter = " » ";

    public static void writeConfig()
    {
        File dir = CobbleClearForge.getConfigDir();
        dir.mkdirs();
        Gson gson  = Adapters.PRETTY_MAIN_GSON;
        LanguageConfig config = new LanguageConfig();
        try {
            File file = new File(dir, "language.json");
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

    public static LanguageConfig getConfig()
    {
        File dir = CobbleClearForge.getConfigDir();
        dir.mkdirs();
        Gson gson  = Adapters.PRETTY_MAIN_GSON;
        File file = new File(dir, "language.json");
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            CobbleClearForge.getLog().error("Something went wrong attempting to read the Config");
            return null;
        }

        return gson.fromJson(reader, LanguageConfig.class);
    }

}
