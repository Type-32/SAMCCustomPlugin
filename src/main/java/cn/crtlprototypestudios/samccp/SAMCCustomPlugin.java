package cn.crtlprototypestudios.samccp;

import cn.crtlprototypestudios.samccp.core.commands.SetCommand;
import cn.crtlprototypestudios.samccp.core.scheduling.SchedulerWrapper;
import cn.crtlprototypestudios.samccp.core.tasks.DateCheckTask;
import cn.crtlprototypestudios.samccp.core.utility.Reference;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;
import java.util.Objects;

public final class SAMCCustomPlugin extends JavaPlugin {

    private static SAMCCustomPlugin instance;

    private static FileConfiguration customConfig;
    private static File configFile;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        SetCommand setCommand = new SetCommand();
        Objects.requireNonNull(getCommand(SetCommand.KEYWORD)).setExecutor(setCommand);
        Objects.requireNonNull(getCommand(SetCommand.KEYWORD)).setTabCompleter(setCommand);

        // Plugin startup logic
        BukkitScheduler scheduler = this.getServer().getScheduler();
        SchedulerWrapper.setInstance(scheduler);

        long period = 20L * 60 * getConfig().getLong("dateCheckEveryNMins");
        scheduler.runTaskTimer(this, new DateCheckTask(), 0, period);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static SAMCCustomPlugin getInstance() {
        return instance;
    }
}
