package enums;

public enum Time {
    SLOW("медленно"), FAST("быстро"), NORMAL("обычно");

    private final String time;

    Time(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return this.time;
    }
}
