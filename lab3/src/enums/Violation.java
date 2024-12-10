package enums;

public enum Violation {
    STEAL("красть"), FIGHT("драться"), HIT("ударить"), OFFEND("взять чужое");

    private final String violation;

    Violation(String violation) {
        this.violation = violation;
    }

    @Override
    public String toString() {
        return violation;
    }
}
