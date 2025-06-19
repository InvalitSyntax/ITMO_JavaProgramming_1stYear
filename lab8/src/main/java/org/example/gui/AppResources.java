package org.example.gui;

public class AppResources {
    // Текущий язык (по умолчанию русский)
    private static Language currentLanguage = new RussianLanguage();

    public static void setLanguage(Language language) {
        currentLanguage = language;
    }

    public static Language getCurrentLanguage() {
        return currentLanguage;
    }

    // Интерфейс для поддержки разных языков
    public interface Language {
        String getLoginInfo();
        String getLoginLabel();
        String getPasswordLabel();
        String getLoginButtonText();
        String getRegisterButtonText();
        String getWindowTitle();
    }

    // Реализация для русского языка
    public static class RussianLanguage implements Language {
        @Override public String getLoginInfo() { return "Страница входа"; }
        @Override public String getLoginLabel() { return "Логин"; }
        @Override public String getPasswordLabel() { return "Пароль"; }
        @Override public String getLoginButtonText() { return "Войти"; }
        @Override public String getRegisterButtonText() { return "Регистрация"; }
        @Override public String getWindowTitle() { return "Форма авторизации"; }
    }

    // В будущем можно добавить:
    // public static class EnglishLanguage implements Language { ... }
}