package Fire;

import Fire.MonthlyCrate.Animation.MysteryCrateTimer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;

public class InvClickEvent implements Listener {

    public HashMap<Player, MysteryCrateTimer> mysteryCrateTimers = new HashMap<>();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        MysteryCrateTimer mysteryCrateTimer = mysteryCrateTimers.get(event.getWhoClicked());
        if (mysteryCrateTimer != null){
            event.setCancelled(true);
            mysteryCrateTimer.onClick(event.getSlot());
        }
    }
}
