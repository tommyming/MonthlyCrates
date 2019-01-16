package Fire.MonthlyCrate.Animation.ItemChangers;

import org.bukkit.inventory.ItemStack;

public class RestoringRandomizer implements IItemStackRandomizer{

    ItemStack restoreItem;

    public RestoringRandomizer(ItemStack stack){
        restoreItem = stack;
    }

    @Override
    public ItemStack nextStack() {
        return restoreItem;
    }
}
