package Fire.MonthlyCrate.Animation;

import Fire.MonthlyCrate.Animation.ItemChangers.RowAnimator;
import Fire.MonthlyCrate.MysteryItemTimer;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class MysteryCrateTimer implements Runnable{

    public List<MysteryItemTimer> mysteryItems;
    private Inventory inv;
    public RowAnimator rowAnimator;

    MysteryCrateFinalItemAnimatitor finalItemAnimatitor;

    public MysteryCrateTimer(Inventory Inv, List<MysteryItemTimer> MysteryItems, RowAnimator RowAnimator, MysteryCrateFinalItemAnimatitor FinalItemAnimatitor){
        inv = Inv;
        mysteryItems = MysteryItems;
        rowAnimator = RowAnimator;
        finalItemAnimatitor = FinalItemAnimatitor;
    }

    @Override
    public void run() {
        if (allMysteryItemsOpened()){
            finalItemAnimatitor.onTick();
        }
        else{
            mysteryItems.stream().forEach(MysteryItemTimer::onTick);
            rowAnimator.animateRainbowPanes(mysteryItems);
        }

    }

    public void onClick(int slotIndex){

        //if (slotIndex == 49)//Reveal Item

        Optional<MysteryItemTimer> mysteryItemTimer = mysteryItems.stream().filter(mysteryItemTimer1 -> mysteryItemTimer1.inventorySlot == slotIndex).findFirst();
        if (mysteryItemTimer.isPresent())
            mysteryItemTimer.get().started = true;
    }

    private boolean allMysteryItemsOpened(){
        return mysteryItems.stream().allMatch(MysteryItemTimer::timerFinished);
    }



}
