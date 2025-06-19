package org.example.gui;

import java.util.HashMap;
import java.util.Map;

public class AppResources {
    // Текущий язык (по умолчанию русский)
    private static Language currentLanguage = new RussianLanguage();

    public static void setLanguage(Language language) {
        currentLanguage = language;
    }

    public static Language getCurrentLanguage() {
        return currentLanguage;
    }

    public static String getUserExistsError() {
        return "Пользователь уже существует";
    }
    public static String getUserNotFoundError() {
        return "Пользователь не найден";
    }
    public static String getWrongPasswordError() {
        return "Неверный пароль";
    }
    public static String getEmptyLoginError() {
        return "Логин не может быть пустым";
    }
    public static String getEmptyPasswordError() {
        return "Пароль не может быть пустым";
    }
    public static String getCurrentUserLabel() {
        return getCurrentLanguage().getCurrentUserLabel();
    }

    private static final Map<String, String> RU_MAP = new HashMap<>();
    static {
        RU_MAP.put("table.id", "ID");
        RU_MAP.put("table.login", "Логин");
        RU_MAP.put("table.name", "Имя");
        RU_MAP.put("table.creationDate", "Дата создания");
        RU_MAP.put("table.health", "Здоровье");
        RU_MAP.put("table.loyal", "Лояльность");
        RU_MAP.put("table.weaponType", "Оружие");
        RU_MAP.put("table.meleeWeapon", "Оружие ближнего боя");
        RU_MAP.put("table.coordX", "Коорд. X");
        RU_MAP.put("table.coordY", "Коорд. Y");
        RU_MAP.put("table.chapterName", "Глава (имя)");
        RU_MAP.put("table.chapterWorld", "Глава (мир)");
        RU_MAP.put("button.showField", "Показать поле");
        RU_MAP.put("button.execute", "Выполнить");
        RU_MAP.put("button.accept", "Добавить");
        RU_MAP.put("button.cancel", "Отмена");
        RU_MAP.put("button.language", "Язык");
        RU_MAP.put("commandBox.prompt", "Выберите команду...");
        RU_MAP.put("marine.name", "Имя");
        RU_MAP.put("marine.coordX", "Коорд. X");
        RU_MAP.put("marine.coordY", "Коорд. Y");
        RU_MAP.put("marine.health", "Здоровье");
        RU_MAP.put("marine.loyal", "Лоялен");
        RU_MAP.put("marine.weaponType", "Оружие");
        RU_MAP.put("marine.meleeWeapon", "Оружие ближнего боя");
        RU_MAP.put("marine.chapterName", "Глава (имя)");
        RU_MAP.put("marine.chapterWorld", "Глава (мир)");
        RU_MAP.put("edit", "Редактировать");
        RU_MAP.put("remove", "Удалить");
        RU_MAP.put("not your marine", "Не ваш десантник");
    }

    public static String get(String key) {
        return RU_MAP.getOrDefault(key, key);
    }

    // Интерфейс для поддержки разных языков
    public interface Language {
        String getLoginInfo();
        String getLoginLabel();
        String getPasswordLabel();
        String getLoginButtonText();
        String getRegisterButtonText();
        String getWindowTitle();
        String getCurrentUserLabel();
    }

    // Реализация для русского языка
    public static class RussianLanguage implements Language {
        @Override public String getLoginInfo() { return "Страница входа"; }
        @Override public String getLoginLabel() { return "Логин"; }
        @Override public String getPasswordLabel() { return "Пароль"; }
        @Override public String getLoginButtonText() { return "Войти"; }
        @Override public String getRegisterButtonText() { return "Регистрация"; }
        @Override public String getWindowTitle() { return "Форма авторизации"; }
        @Override public String getCurrentUserLabel() { return "Пользователь:"; }
    }
    // В будущем можно добавить другие языки
}