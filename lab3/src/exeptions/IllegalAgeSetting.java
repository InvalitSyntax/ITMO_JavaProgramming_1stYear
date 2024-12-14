package exeptions;

public class IllegalAgeSetting extends RuntimeException {
    public IllegalAgeSetting(String message) {
        super(message);
    }
    @Override
    public String getMessage() {
        return "Ошибка при установке возраста: " + super.getMessage();
    }
}
