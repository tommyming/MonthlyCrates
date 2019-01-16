package Fire.MonthlyCrate.Animation.ItemChangers;

import Fire.MonthlyCrate.MysteryItemTimer;
import Fire.Util.InventoryUtil;
import org.bukkit.inventory.Inventory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RowAnimator {

    IItemStackRandomizer itemStackRandomizer;
    IItemStackRandomizer itemStackRestorer;
    Inventory inv;

    Set<Integer> rows = new HashSet<>();
    Set<Integer> columns = new HashSet<>();;

    private static int[] rowSlots  = new int[] {0, 1, 2, 6, 7, 8 };
    private static int[] normalColumnSlots = new int[] {0, 4, 5}; //Not 5 for the middle row
    private static int[] centredColumnSlots = new int[] {0, 4}; //Not 5 for the middle row

    public RowAnimator(Inventory Inv, IItemStackRandomizer ItemStackRandomizer, IItemStackRandomizer ItemStackRestorer){
        itemStackRandomizer = ItemStackRandomizer;
        inv = Inv;
        itemStackRestorer = ItemStackRestorer;
    }

    public void animateRainbowPanes(List<MysteryItemTimer> mysteryItems){
        updateRowsAndColumns(mysteryItems);
        animatePanes(rows, columns);
    }

    private void updateRowsAndColumns(List<MysteryItemTimer> mysteryItems){

        rows = new HashSet<>();
        columns = new HashSet<>();

        for (MysteryItemTimer mysteryItem : mysteryItems){
            if (mysteryItem.started && !mysteryItem.timerFinished()){

                int itemSlot = mysteryItem.inventorySlot;
                rows.add(InventoryUtil.rowOf(itemSlot));
                columns.add(InventoryUtil.columnOf(itemSlot));
            }
        }
    }

    private void animatePanes(Set<Integer> rows, Set<Integer> columns){
        for (int row : rows){
            for (int column : rowSlots){
                int slot = InventoryUtil.getSlot(row, column);
                inv.setItem(slot, itemStackRandomizer.nextStack());
            }
        }

        for (int column : columns){
            int[] columnSlots = getColumnSlots(column);
            for (int row : columnSlots){
                int slot = InventoryUtil.getSlot(row, column);
                inv.setItem(slot, itemStackRandomizer.nextStack());
            }
        }
    }

    private int[] getColumnSlots(int column){
        if (column != 4)
            return normalColumnSlots;
        else
            return centredColumnSlots;
    }

    public void restoreSlot(int slotIndex){

        int row1 = InventoryUtil.rowOf(slotIndex);
        if (!rows.contains(row1)){
            for (int column : rowSlots){
                int slot = InventoryUtil.getSlot(row1, column);
                inv.setItem(slot, itemStackRestorer.nextStack());
            }
        }

        int column = InventoryUtil.columnOf(slotIndex);
        if (!columns.contains(column)){
            int[] columnSlots = getColumnSlots(column);
            for (int row : columnSlots){
                int slot = InventoryUtil.getSlot(row, column);
                inv.setItem(slot, itemStackRestorer.nextStack());
            }
        }
    }
}
