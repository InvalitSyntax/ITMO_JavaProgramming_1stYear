package enums;

public enum Intelligence {
    LOW("глупый"), MEDIUM("не особо умный"), HIGH("умный");
    private final String intelligent;

    private Intelligence(String intelligent) {
        this.intelligent = intelligent;
    }

    @Override
    public String toString() {
        return this.intelligent;
    }
}
