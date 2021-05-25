package me.tulio.clan;

import lombok.Getter;
import me.tulio.clan.commands.ClanCommand;
import me.tulio.clan.leaderboards.Leaderboard;
import me.tulio.clan.listeners.ClanListener;
import me.tulio.clan.util.FileConfig;
import me.tulio.clan.util.PlaceholderAPI;
import me.tulio.clan.util.commands.CommandManager;
import me.tulio.clan.util.menu.ButtonListener;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Clan extends JavaPlugin {

    private CommandManager commandManager;
    private FileConfig clansConfig, mainConfig;

    @Override
    public void onEnable() {
        loadConfigs();
        initManagers();
        initListeners();
        initCommands();
    }

    @Override
    public void onDisable() {
        ClanHandler.saveInventorys();
        ClanHandler.saveClans();
    }

    private void initManagers() {
        this.commandManager = new CommandManager(this);
        ClanHandler.init();
        Leaderboard.init();
        new PlaceholderAPI().register();
    }

    private void initCommands() {
        new ClanCommand();
    }

    private void initListeners() {
        getServer().getPluginManager().registerEvents(new ClanListener(), this);
        getServer().getPluginManager().registerEvents(new ButtonListener(), this);
    }

    private void loadConfigs() {
        this.mainConfig = new FileConfig(this, "config.yml");
        this.clansConfig = new FileConfig(this, "clans.yml");
    }

    public static Clan get() {
        return getPlugin(Clan.class);
    }
}
