package Fire.MonthlyCrate;

import Fire.InvClickEvent;
import Fire.MonthlyCrate.Animation.ItemChangers.IItemStackRandomizer;
import Fire.MonthlyCrate.Animation.ItemChangers.RainbowItemStackRandomizer;
import Fire.MonthlyCrate.Animation.ItemChangers.RestoringRandomizer;
import Fire.MonthlyCrate.Animation.MysteryCrateFinalItemAnimatitor;
import Fire.MonthlyCrate.Animation.MysteryCrateTimer;
import Fire.MonthlyCrate.Animation.ItemChangers.RowAnimator;
import Fire.Util.InventoryUtil;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MysteryCrateFactory {

    InvClickEvent invClickEvent;
    List<ItemStack> rewards = new LinkedList<>();
    List<Integer> colors = new ArrayList<>();
    List<Integer> finalColors = new ArrayList<>();

    List<ItemStack> slidingAnimation = new LinkedList<>();

    public MysteryCrateFactory(InvClickEvent InvClickEvent, ConfigurationSection config){
        invClickEvent = InvClickEvent;
        rewards.add(new ItemStack(Material.GRASS));
        rewards.add(new ItemStack(Material.STONE));
        rewards.add(new ItemStack(Material.WOOD));
        rewards.add(new ItemStack(Material.DIAMOND));

        colors = config.getIntegerList("spinningPaneColors");
        finalColors = config.getIntegerList("finalBackgroundColor");

        List<Integer> slidingAnimationColors = config.getIntegerList("slidingPaneColors");
        for (int color : slidingAnimationColors) --
            slidingAnimation.add(RainbowItemStackRandomizer.getPane(color));
    }

    public MysteryCrateTimer nextMysteryCrateTimer(Player player, Inventory inventory){

        IItemStackRandomizer randomizer = new RainbowItemStackRandomizer(colors, new Random());
        IItemStackRandomizer restorer = new RestoringRandomizer(RainbowItemStackRandomizer.getPane(11));
        RowAnimator rowAnimator = new RowAnimator(inventory, randomizer, restorer);

        List<MysteryItemTimer> mysteryItems = getMysterItemTimers(inventory, rowAnimator);


        IItemStackRandomizer finalBackgroundRandomizer = new RainbowItemStackRandomizer(finalColors, new Random());
        MysteryCrateFinalItemAnimatitor finalItemAnimatitor = new MysteryCrateFinalItemAnimatitor(inventory, slidingAnimation,7, finalBackgroundRandomizer);

        MysteryCrateTimer mysteryCrateTimer = new MysteryCrateTimer(inventory, mysteryItems, rowAnimator, finalItemAnimatitor);
        invClickEvent.mysteryCrateTimers.put(player, mysteryCrateTimer);
        return mysteryCrateTimer;
    }

    private List<MysteryItemTimer> getMysterItemTimers(Inventory inv, RowAnimator rowAnimator){

        LootPicker lootPicker = new LootPicker(new Random(), rewards);

        List<MysteryItemTimer> mysteryItems = new LinkedList<>();
        for (int row = 0; row < 3; row++)
            for (int column = 0; column < 3; column++){
                int slot = InventoryUtil.getSlot(row + 1, column + 3);
                MysteryItemTimer mysteryItemTimer = new MysteryItemTimer(10, inv, slot, rowAnimator, lootPicker.nextReward(), rewards);
                mysteryItems.add(mysteryItemTimer);
            }
        return mysteryItems;
    }


}
