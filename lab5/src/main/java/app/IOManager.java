package app;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;

public class IOManager {
    private boolean automatedInputNow;
    private final Scanner scanner = new Scanner(System.in);
    private String automatedOutput = "";
    private ArrayList<String> executingScripts = new ArrayList<>();

    public String getRawStringInput() {
        String input;
        automatedInputNow = false;
        writeMessage("> ", false);

        input = scanner.nextLine();

        return input.trim();
    }

    public void setAutomatedInputNow(boolean automatedInputNow) {
        this.automatedInputNow = automatedInputNow;
    }

    public String getValidStringInput(Predicate<String> predicate) {
        String input = getRawStringInput();
        while (!predicate.test(input)) {
            writeMessage("Некорректный ввод! попробуйте еще один раз\n", false);
            input = getRawStringInput();
        }
        return input;
    }

    public <T extends Number> T getNumberInput(Function<String, T> function) {
        T out = null;
        while (out == null) {
            try {
                out = function.apply(getRawStringInput());
            } catch (NumberFormatException | NullPointerException e) {
                writeMessage("Вы ввели не число! Повторите ввод\n", false);
            }
        }
        return out;
    }

    public <T extends Number> T getValidNumberInput(Function<String, T> function, Predicate<T> predicate) {
        T out = getNumberInput(function);
        while (!predicate.test(out)) {
            writeMessage("Вы ввели неверное число! Повторите ввод\n", false);
            out = getNumberInput(function);
        }
        return out;
    }

    public <T extends Boolean> T getBooleanInput(Function<String, T> function) {
        T out = null;
        while (out == null) {
            try {
                out = function.apply(getRawStringInput());
            } catch (Exception e) {
                writeMessage(e.getMessage(), false);
            }
        }
        return out;
    }

    public <T extends Enum<T>> T getEnumInput(Class<T> enumClass) {
        Scanner scanner = new Scanner(System.in);
        T result = null;
        boolean isValid = false;

        while (!isValid) {
            String input = getRawStringInput().toUpperCase();

            try {
                // Пытаемся преобразовать ввод в значение enum
                result = Enum.valueOf(enumClass, input);
                isValid = true; // Ввод корректен, выходим из цикла
            } catch (IllegalArgumentException e) {
                // Если ввод некорректен, выводим сообщение об ошибке
                writeMessage("Введенное значение не соответствует ни одному из допустимых значений.!\n", false);
            }
        }

        return result;
    }

    public void writeMessage(String message, boolean quiet) {
        if (!automatedInputNow && !quiet) {
            System.out.print(message);
        } else {
            automatedOutput += message;
        }
    }

    public String getAutomatedOutput() {
        this.automatedOutput = "";
        return automatedOutput;
    }

    public void addExecutingScript(String script) {
        executingScripts.add(script);
    }

    public ArrayList<String> getExecutingScripts() {
        return executingScripts;
    }
}
