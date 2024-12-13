package enums;

public enum TimeSpeed {
    SLOW("медленно"), FAST("быстро"), NORMAL("обычно");

    private final String time;

    TimeSpeed(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return this.time;
    }
}