package Fire.MonthlyCrate.Animation;

import Fire.MonthlyCrate.Animation.ItemChangers.IItemStackRandomizer;
import Fire.Util.InventoryUtil;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MysteryCrateFinalItemAnimatitor extends TickingTimer {

    private List<ItemStack> slidingAnimationItems;
    private IItemStackRandomizer finalBackgroundRandomizer;
    int currentSlidingAnimationItem;
    Inventory inv;

    int curentSlotsMoved = 0;
    private boolean finished = false;

    public MysteryCrateFinalItemAnimatitor(Inventory Inv, List<ItemStack> SlidingAnimationItems, int ticksPerPaneTransition, IItemStackRandomizer FinalBackgroundRandomizer){
        super(ticksPerPaneTransition);
        inv = Inv;
        slidingAnimationItems = SlidingAnimationItems;
        finalBackgroundRandomizer = FinalBackgroundRandomizer;
    }

    @Override
    public void onTick() {

        if (finished)
            return;

        super.onTick();
        if (curentSlotsMoved > 4){
            if (currentSlidingAnimationItem < slidingAnimationItems.size() - 1)
                startNextSlide();
            else{
                finalizeBackground();
                revealSpecialItem();
                finished = true;
            }
        }
        else{
            slide();
        }
    }

    private void slide(){

        ItemStack item = slidingAnimationItems.get(currentSlidingAnimationItem);
        fillColumn(curentSlotsMoved, item);
        fillColumn(8 - curentSlotsMoved, item);

        curentSlotsMoved++;
    }

    private void fillColumn(int columnIndex, ItemStack item){
        switch (columnIndex){
            case 4:
                fillCenterColumn(columnIndex, item);
                break;
            case 3:
            case 5:
                fillLootColumn(columnIndex, item);
                break;
            default:
                fillOuterColumn(columnIndex, item);
        }
    }

    private void fillCenterColumn(int columnIndex, ItemStack item){
        inv.setItem(InventoryUtil.getSlot(0, columnIndex), item);
        inv.setItem(InventoryUtil.getSlot(4, columnIndex), item);
    }

    private void fillLootColumn(int columnIndex, ItemStack item){
        inv.setItem(InventoryUtil.getSlot(0, columnIndex), item);
        inv.setItem(InventoryUtil.getSlot(4, columnIndex), item);
        inv.setItem(InventoryUtil.getSlot(5, columnIndex), item);
    }

    private void fillOuterColumn(int columnIndex, ItemStack item){
        for (int i = 0; i < 6; i++){
            inv.setItem(InventoryUtil.getSlot(i, columnIndex), item);
        }
    }

    private void revealSpecialItem(){

    }

    private void finalizeBackground(){
        for (int i = 0; i < inv.getContents().length; i++){
            if (inv.getItem(i).getType() == Material.STAINED_GLASS_PANE && i != 49){
                inv.setItem(i, finalBackgroundRandomizer.nextStack());
            }
        }
    }

    private void startNextSlide(){
        currentSlidingAnimationItem++;
        curentSlotsMoved = 0;
        reset();
    }

}
