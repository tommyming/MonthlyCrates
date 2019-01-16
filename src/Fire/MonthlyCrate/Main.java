package Fire.MonthlyCrate;

import Fire.InvClickEvent;
import Fire.OpenCrateCommand;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable(){
        Bukkit.getLogger().info("Yay!");

        InvClickEvent invClickEvent = new InvClickEvent();
        Bukkit.getPluginManager().registerEvents(invClickEvent, this);

        FileConfiguration config = getConfig();

        MysteryCrateFactory mysteryCrateFactory = new MysteryCrateFactory(invClickEvent, config);
        this.getCommand("MysterCrate").setExecutor(new OpenCrateCommand(this, mysteryCrateFactory, config.getInt("updateSpeed")));

    }

    @Override
    public void onDisable(){

    }

}
