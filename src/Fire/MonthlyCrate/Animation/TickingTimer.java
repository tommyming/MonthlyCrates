package Fire.MonthlyCrate.Animation;

public class TickingTimer {
    private int remainingTicks;
    private int startingTicks;

    public TickingTimer(int Ticks){
        remainingTicks = Ticks;
        startingTicks = Ticks;
    }

    public void reset(){
        remainingTicks = startingTicks;
    }

    public void onTick(){
            decrementTicks();
    }

    private void decrementTicks(){
        remainingTicks--;
    }

    public boolean timerFinished(){
        return remainingTicks <= 0;
    }
}
