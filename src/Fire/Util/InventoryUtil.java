package Fire.Util;

public class InventoryUtil {

    public static int rowOf(int slot){
            return slot / 9;
    }

    public static int columnOf(int slot){
        return slot % 9;
    }

    public static int getSlot(int row, int collumn){
        return row * 9 + collumn;
    }
}
