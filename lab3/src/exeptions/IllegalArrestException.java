package exeptions;

public class IllegalArrestException extends Exception {
    public IllegalArrestException(String message) {
        super(message);
    }
    @Override
    public String getMessage() {
        return "Ошибка ареста: " + super.getMessage();
    }
}