package Fire.MonthlyCrate.Animation.ItemChangers;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RainbowItemStackRandomizer implements IItemStackRandomizer{

    private List<ItemStack> panes = new ArrayList<>();
    Random r;

    public RainbowItemStackRandomizer(List<Integer> colors, Random random){
        r = random;
        for (int color : colors)
            panes.add((getPane(color)));
    }

    public static ItemStack getPane(int color){
        return new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)color);
    }

    @Override
    public ItemStack nextStack() {
        int nextItem = r.nextInt(panes.size());
        return panes.get(nextItem);
    }
}
