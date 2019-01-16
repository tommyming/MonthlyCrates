package Fire;

import Fire.MonthlyCrate.Animation.ItemChangers.RainbowItemStackRandomizer;
import Fire.MonthlyCrate.Animation.ItemChangers.RowAnimator;
import Fire.MonthlyCrate.Main;
import Fire.MonthlyCrate.MysteryCrateFactory;
import Fire.MonthlyCrate.Animation.MysteryCrateTimer;
import Fire.Util.InventoryUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class OpenCrateCommand implements CommandExecutor {

    MysteryCrateFactory mcrateFactory;
    Main main;
    int updateSpeed;

    public OpenCrateCommand(Main Main, MysteryCrateFactory McrateFactory, int UpdateSpeed){
        mcrateFactory = McrateFactory;
        main = Main;
        updateSpeed = UpdateSpeed;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        Inventory inv = nextInventory();
        Player player = (Player)commandSender;
        player.openInventory(inv);

        MysteryCrateTimer mcrateTimer = mcrateFactory.nextMysteryCrateTimer(player, inv);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, mcrateTimer, 0L, updateSpeed);//Runs every tick, starting in 0 ticks

        return false;
    }

    private Inventory nextInventory(){
        Inventory inv = Bukkit.createInventory(null, 54, "Crate");

        ItemStack backgroundPanes = RainbowItemStackRandomizer.getPane(11);
        for (int i = 0; i < inv.getContents().length; i++){
            inv.setItem(i, backgroundPanes);
        }


        for (int row = 0; row < 3; row++)
            for (int column = 0; column < 3; column++){
                int slot = InventoryUtil.getSlot(row + 1, column + 3);
                inv.setItem(slot, new ItemStack(Material.ENDER_CHEST));
            }

        int lootItemSlot = InventoryUtil.getSlot(5, 4);
        inv.setItem(lootItemSlot, RainbowItemStackRandomizer.getPane(14));

        return inv;
    }
}
