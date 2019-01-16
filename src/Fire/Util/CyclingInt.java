package Fire.Util;

public class CyclingInt {
    private int exclusiveMaxValue;
    private int value;

    public CyclingInt(int ExclusiveMaxValue){
        exclusiveMaxValue = ExclusiveMaxValue;
    }

    public void setValue(int Value){
        value = Value % exclusiveMaxValue;
    }

    public int getValue(){
        return value;
    }

    public void increment(){
        setValue(value + 1);
    }

}
