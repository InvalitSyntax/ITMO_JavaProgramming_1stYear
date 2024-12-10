package enums;

public enum Intelligence {
    LOW("глупый"), MEDIUM("умнее"), HIGH("умный");
    private final String intelligent;

    Intelligence(String intelligent) {
        this.intelligent = intelligent;
    }

    @Override
    public String toString() {
        return this.intelligent;
    }
}
