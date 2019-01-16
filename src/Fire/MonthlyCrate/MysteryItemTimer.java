package Fire.MonthlyCrate;

import Fire.MonthlyCrate.Animation.ItemChangers.RowAnimator;
import Fire.MonthlyCrate.Animation.TickingTimer;
import Fire.Util.CyclingInt;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MysteryItemTimer extends TickingTimer {

    ItemStack finalItem;
    List<ItemStack> lootItems;
    CyclingInt currentLootItem;

    public int inventorySlot;
    public boolean started;

    private Inventory inventory;
    private RowAnimator rowAnimator;

    public MysteryItemTimer(int ticks, Inventory Inventory, int InventorySlot, RowAnimator RowAnimator, ItemStack FinalItem, List<ItemStack> items){
        super(ticks);
        started = false;
        inventory = Inventory;
        inventorySlot = InventorySlot;
        finalItem = FinalItem;
        lootItems = items;
        rowAnimator = RowAnimator;
        currentLootItem = new CyclingInt(lootItems.size());
    }

    @Override
    public void onTick() {
        if (!started)
            return;

        if (timerFinished()){
            inventory.setItem(inventorySlot, finalItem);
            rowAnimator.restoreSlot(inventorySlot);
            return;
        }

        super.onTick();
        updateShownLootItem();
    }

    private void updateShownLootItem(){
        inventory.setItem(inventorySlot, lootItems.get(currentLootItem.getValue()));
        currentLootItem.increment();
    }
}
