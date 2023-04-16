package io.github.adainish.cobbleclearforge;

import io.github.adainish.cobbleclearforge.command.Command;
import io.github.adainish.cobbleclearforge.config.Config;
import io.github.adainish.cobbleclearforge.manager.WipeManager;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CobbleClearForge.MODID)
public class CobbleClearForge {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "cobbleclearforge";
    // Directly reference a slf4j logger

    public static CobbleClearForge instance;
    public static final String MOD_NAME = "CobblemonClear";
    public static final String VERSION = "1.0.0-Beta";
    public static final String AUTHORS = "Winglet";
    public static final String YEAR = "2023";
    private static final Logger log = LogManager.getLogger(MOD_NAME);
    private static File configDir;
    private static File storage;
    private static MinecraftServer server;
    public static Config config;

    public static WipeManager manager;
    public static Logger getLog() {
        return log;
    }

    public static MinecraftServer getServer() {
        return server;
    }

    public static void setServer(MinecraftServer server) {
        CobbleClearForge.server = server;
    }

    public static File getConfigDir() {
        return configDir;
    }

    public static void setConfigDir(File configDir) {
        CobbleClearForge.configDir = configDir;
    }

    public static File getStorage() {
        return storage;
    }

    public static void setStorage(File storage) {
        CobbleClearForge.storage = storage;
    }


    public CobbleClearForge() {
        instance = this;
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);


        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        log.info("Booting up %n by %authors %v %y"
                .replace("%n", MOD_NAME)
                .replace("%authors", AUTHORS)
                .replace("%v", VERSION)
                .replace("%y", YEAR)
        );
        initDirs();

    }

    @SubscribeEvent
    public void onCommandRegistry(RegisterCommandsEvent event) {

        //register commands
        event.getDispatcher().register(Command.getCommand());

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarted(ServerStartedEvent event) {
        setServer(ServerLifecycleHooks.getCurrentServer());
        initConfigs();
        reload();
    }


    public void initDirs() {
        setConfigDir(new File(FMLPaths.GAMEDIR.get().resolve(FMLConfig.defaultConfigPath()) + "/CobbleClear/"));
        getConfigDir().mkdir();
        setStorage(new File(getConfigDir(), "/storage/"));
        getStorage().mkdirs();
    }



    public void initConfigs() {
        Config.writeConfig();
        config = Config.getConfig();
    }

    public void reload() {
        initConfigs();
        if (manager == null) {
            manager = new WipeManager();
        }
        manager.init();
    }



}
