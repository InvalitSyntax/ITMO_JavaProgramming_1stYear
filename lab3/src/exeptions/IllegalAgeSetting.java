package exeptions;

public class IllegalAgeSetting extends RuntimeException {
    public IllegalAgeSetting(String message) {
        super(message);
    }
}
