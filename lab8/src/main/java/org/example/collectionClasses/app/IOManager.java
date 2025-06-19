package org.example.collectionClasses.app;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Менеджер ввода/вывода, управляющий взаимодействием с пользователем.
 *
 * @author ISyntax
 * @version 1.0
 */
public class IOManager {
    private boolean automatedInputNow;
    private final Stack<Pair<String, Scanner>> scannersFromExecute = new Stack<>();
    private String automatedOutput = "";
    private final ArrayList<String> executingScriptsName = new ArrayList<>();
    private StringBuilder writedMessagesStringBuilder = new StringBuilder();
    private boolean isCliet = false;
    private Deque<Scanner> scriptScanners = new ArrayDeque<>();

    /**
     * Конструктор менеджера ввода/вывода.
     */
    public IOManager() {
        scannersFromExecute.add(new Pair<>("Default", new Scanner(System.in)));
    }

    public void setIsClient(boolean isCliet) {
        this.isCliet = isCliet;
    }

    /**
     * Получает строку ввода от пользователя.
     *
     * @return строка ввода
     */
    public String getRawStringInput() {
        if (!scriptScanners.isEmpty()) {
            Scanner scanner = scriptScanners.peek();
            if (scanner.hasNextLine()) {
                return scanner.nextLine();
            } else {
                popScriptScanner();
                return getRawStringInput();
            }
        }
        String input = null;
        writeMessage("> ", false);

        do {
            Pair<String, Scanner> pair = scannersFromExecute.peek();
            String name = pair.first();
            Scanner scanner = pair.second();
            if (scanner.hasNextLine()) {
                input = scanner.nextLine();
                if (!name.equals("Default")) {
                    writeMessage(input + "\n", false);
                }
            } else {
                scannersFromExecute.pop();
                executingScriptsName.remove(name);
                scanner.close();
            }
        } while (input == null);

        return input.trim();
    }

    /**
     * Добавляет сканер для выполнения скрипта.
     *
     * @param name    имя скрипта
     * @param scanner сканер
     */
    public void addExecutingScanner(String name, Scanner scanner) {
        this.scannersFromExecute.push(new Pair<>(name, scanner));
    }

    public void pushScriptScanner(Scanner scanner) {
        scriptScanners.push(scanner);
    }

    public void popScriptScanner() {
        if (!scriptScanners.isEmpty()) scriptScanners.pop();
    }

    /**
     * Устанавливает режим автоматического ввода.
     *
     * @param automatedInputNow флаг автоматического ввода
     */
    public void setAutomatedInputNow(boolean automatedInputNow) {
        this.automatedInputNow = automatedInputNow;
    }

    /**
     * Получает валидную строку ввода.
     *
     * @param predicate условие валидации
     * @return валидная строка ввода
     */
    public String getValidStringInput(Predicate<String> predicate) {
        String input = getRawStringInput();
        while (!predicate.test(input)) {
            writeMessage("Некорректный ввод! попробуйте еще один раз\n", false);
            input = getRawStringInput();
        }
        return input;
    }

    /**
     * Получает числовой ввод.
     *
     * @param function функция преобразования строки в число
     * @param <T>      тип числа
     * @return число
     */
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

    /**
     * Получает валидный числовой ввод.
     *
     * @param function  функция преобразования строки в число
     * @param predicate условие валидации
     * @param <T>       тип числа
     * @return валидное число
     */
    public <T extends Number> T getValidNumberInput(Function<String, T> function, Predicate<T> predicate) {
        T out = getNumberInput(function);
        while (!predicate.test(out)) {
            writeMessage("Вы ввели неверное число! Повторите ввод\n", false);
            out = getNumberInput(function);
        }
        return out;
    }

    /**
     * Получает булевый ввод.
     *
     * @param function функция преобразования строки в булевое значение
     * @param <T>      тип булевого значения
     * @return булевое значение
     */
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

    /**
     * Получает ввод перечисления.
     *
     * @param enumClass класс перечисления
     * @param <T>       тип перечисления
     * @return значение перечисления
     */
    public <T extends Enum<T>> T getEnumInput(Class<T> enumClass) {
        Scanner scanner = new Scanner(System.in);
        T result = null;
        boolean isValid = false;

        while (!isValid) {
            String input = getRawStringInput().toUpperCase();

            try {
                result = Enum.valueOf(enumClass, input);
                isValid = true;
            } catch (IllegalArgumentException e) {
                writeMessage("Введенное значение не соответствует ни одному из допустимых значений.!\n", false);
            }
        }

        return result;
    }

    /**
     * Выводит сообщение.
     *
     * @param message сообщение
     * @param quiet   флаг тихого режима
     */
    public void writeMessage(String message, boolean quiet) {
        if (isCliet == true) {
            System.out.print(message);
        } else{
            if (!automatedInputNow && !quiet) {
                // System.out.print(message);
                this.writedMessagesStringBuilder.append(message);
            } else {
                automatedOutput += message;
            }
        }
    }

    public String popWritedMessages(){
        String out = this.writedMessagesStringBuilder.toString();
        this.writedMessagesStringBuilder = new StringBuilder();
        return out;
    }

    /**
     * Получает автоматизированный вывод.
     *
     * @return автоматизированный вывод
     */
    public String getAutomatedOutput() {
        this.automatedOutput = "";
        return automatedOutput;
    }

    /**
     * Добавляет имя выполняемого скрипта.
     *
     * @param script имя скрипта
     */
    public void addExecutingScriptName(String script) {
        executingScriptsName.add(script);
    }

    /**
     * Получает имена выполняемых скриптов.
     *
     * @return список имен скриптов
     */
    public ArrayList<String> getExecutingScriptsName() {
        return executingScriptsName;
    }
}