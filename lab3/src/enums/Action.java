package enums;

public enum Action {
    THINK("думать"), DO("делать"), STEAL("красть"), FIGHT("драться"), HIT("ударить"), OFFEND("взять чужое");

    private final String act;

    Action(String act) {
        this.act = act;
    }

    @Override
    public String toString() {
        return act;
    }
}
