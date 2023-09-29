package logic.terminateCondition;

public abstract class TerminateCondition {
    private int count;

    public TerminateCondition(int time){
        count = time;
    }

    public int getCount() {
        return count;
    }

    public TerminateCondition() {
    }
}
