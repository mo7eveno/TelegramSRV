package me.mo7eveno;

import me.mo7eveno.minecraft.MinecraftPlayerChat;
import org.bukkit.plugin.java.JavaPlugin;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;
import java.io.IOException;
public final class TelegramSRV extends JavaPlugin {
    public static TelegramBot bot;
    public static final long PluginStartTime = System.currentTimeMillis();
    private static TelegramSRV instance;
    @Override
    public void onEnable() {
        instance = this;
        preLoader();
    }
    void preLoader() {
        configLoader();
        if (!getConfig().getString("token").equalsIgnoreCase("not selected") || !getConfig().getString("chat_id").equalsIgnoreCase("not selected")) {
            eventLoader();
            commandLoader();
            botLoader();
            getLogger().info("Events; Commands; Bot are loaded successfully");
        } else {
            getLogger().warning("Token or chatid of telegram chat isnt selected! Put in it in config.yml please! Plugin is disabling");
            getInstance().onDisable();
        }
    }
    void botLoader() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new TelegramBot(this));
            bot = new TelegramBot(this);
            /*bot = new TelegramBot(this);*/
        } catch (TelegramApiException e) {
            e.printStackTrace();
            getLogger().warning("TelegramApiException! Plugin is disabling");
            onDisable();
        }
    }
    void commandLoader() {
        getCommand("telegramsrv").setExecutor(new TelegramCommand());
    }
    void configLoader() {
        loadConfigPlayerDatabase();
        loadConfig();
    }
    void eventLoader() {
        getServer().getPluginManager().registerEvents(new MinecraftPlayerChat(), this);
    }
    void loadConfigPlayerDatabase() {
        File folder = getInstance().getDataFolder();
        folder.mkdirs(); // create folder if it is not created
        File file = new File(folder, "playerdatabase.yml");
        try {
            file.createNewFile();
        } catch (IOException e) {
            this.getServer().getLogger().info("Config already created (THAT'S NOT A ERROR)");
        }
    }
    void loadConfig() {
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        getServer().getLogger().info("TelegramSRV is disabled!");
    }
    public static TelegramSRV getInstance() {
        return instance;
    }
}