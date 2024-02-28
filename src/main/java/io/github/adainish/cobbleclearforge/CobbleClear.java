package io.github.adainish.cobbleclearforge;

import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.platform.events.PlatformEvents;
import io.github.adainish.cobbleclearforge.command.Command;
import io.github.adainish.cobbleclearforge.config.Config;
import io.github.adainish.cobbleclearforge.config.LanguageConfig;
import io.github.adainish.cobbleclearforge.manager.WipeManager;
import kotlin.Unit;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class CobbleClear implements ModInitializer {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "cobbleclear";
    // Directly reference a slf4j logger

    public static CobbleClear instance;
    public static final String MOD_NAME = "CobblemonClear";
    public static final String VERSION = "1.1.0-Beta";
    public static final String AUTHORS = "Winglet";
    public static final String YEAR = "2023";
    private static final Logger log = LogManager.getLogger(MOD_NAME);
    private static File configDir;
    private static File storage;
    private static MinecraftServer server;
    public static Config config;
    public static LanguageConfig languageConfig;

    public static WipeManager manager;
    public static Logger getLog() {
        return log;
    }

    public static MinecraftServer getServer() {
        return server;
    }

    public static void setServer(MinecraftServer server) {
        CobbleClear.server = server;
    }

    public static File getConfigDir() {
        return configDir;
    }

    public static void setConfigDir(File configDir) {
        CobbleClear.configDir = configDir;
    }

    public static File getStorage() {
        return storage;
    }

    public static void setStorage(File storage) {
        CobbleClear.storage = storage;
    }


    public CobbleClear() {
        instance = this;
    }

    @Override
    public void onInitialize() {
        commonSetup();
    }

    private void commonSetup() {
        log.info("Booting up %n by %authors %v %y"
                .replace("%n", MOD_NAME)
                .replace("%authors", AUTHORS)
                .replace("%v", VERSION)
                .replace("%y", YEAR)
        );
        initDirs();

        PlatformEvents.SERVER_STARTED.subscribe(Priority.NORMAL, event -> {
            server = event.getServer();
            initConfigs();
            reload();
            return Unit.INSTANCE;
        });

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(Command.getCommand());
        });
    }

    public void initDirs() {
        setConfigDir(new File(FabricLoader.getInstance().getConfigDir() + "/CobbleClear/"));
        getConfigDir().mkdir();
        setStorage(new File(getConfigDir(), "/storage/"));
        getStorage().mkdirs();
    }



    public void initConfigs() {
        Config.writeConfig();
        config = Config.getConfig();
        LanguageConfig.writeConfig();
        languageConfig = LanguageConfig.getConfig();
    }

    public void reload() {
        initConfigs();
        if (manager == null) {
            manager = new WipeManager();
        }
        manager.init();
    }


}
