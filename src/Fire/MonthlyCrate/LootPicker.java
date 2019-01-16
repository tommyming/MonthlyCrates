package Fire.MonthlyCrate;

import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class LootPicker {
    private List<ItemStack> loot;
    private Random r;

    public LootPicker(Random R, List<ItemStack> LootTable){
        loot = LootTable;
        r = R;
    }

    public ItemStack nextReward(){
        return loot.get(r.nextInt(loot.size()));
    }
}
