package org.example.gui;

import java.util.HashMap;
import java.util.Map;

public class AppResources {
    // Текущий язык (по умолчанию русский)
    private static Language currentLanguage = new RussianLanguage();

    public static Language getCurrentLanguage() {
        return currentLanguage;
    }

    public static final Map<String, String> RU_MAP = new HashMap<>();
    private static final Map<String, String> BE_MAP = new HashMap<>();
    private static final Map<String, String> EL_MAP = new HashMap<>();
    private static final Map<String, String> ES_NI_MAP = new HashMap<>();
    static {
        RU_MAP.put("error.userExists", "Пользователь уже существует");
        RU_MAP.put("error.userNotFound", "Пользователь не найден");
        RU_MAP.put("error.wrongPassword", "Неверный пароль");
        RU_MAP.put("error.emptyLogin", "Логин не может быть пустым");
        RU_MAP.put("error.emptyPassword", "Пароль не может быть пустым");
        RU_MAP.put("table.id", "ID");
        RU_MAP.put("table.login", "Логин");
        RU_MAP.put("table.name", "Имя");
        RU_MAP.put("table.creationDate", "Дата создания");
        RU_MAP.put("table.health", "Здоровье");
        RU_MAP.put("table.loyal", "Лоялен");
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
        // Белорусский
        BE_MAP.put("error.userExists", "Карыстальнік ужо існуе");
        BE_MAP.put("error.userNotFound", "Карыстальнік не знойдзены");
        BE_MAP.put("error.wrongPassword", "Няправільны пароль");
        BE_MAP.put("error.emptyLogin", "Лагін не можа быць пустым");
        BE_MAP.put("error.emptyPassword", "Пароль не можа быць пустым");
        //BE_MAP.putAll(RU_MAP);
        BE_MAP.put("table.login", "Лагін");
        BE_MAP.put("table.name", "Імя");
        BE_MAP.put("button.showField", "Паказаць поле");
        BE_MAP.put("table.creationDate", "Дата стварэння");
        BE_MAP.put("table.health", "Здароўе");
        BE_MAP.put("table.loyal", "Лаяльны");
        BE_MAP.put("table.weaponType", "Зброя");
        BE_MAP.put("table.meleeWeapon", "Блізкая зброя");
        BE_MAP.put("table.coordX", "Каардыната X");
        BE_MAP.put("table.coordY", "Каардыната Y");
        BE_MAP.put("table.chapterName", "Глава (імя)");
        BE_MAP.put("table.chapterWorld", "Глава (свет)");
        BE_MAP.put("button.execute", "Выканаць");
        BE_MAP.put("button.accept", "Дадаць");
        BE_MAP.put("button.cancel", "Адмена");
        BE_MAP.put("button.language", "Мова");
        BE_MAP.put("commandBox.prompt", "Абярыце каманду...");
        BE_MAP.put("marine.name", "Імя");
        BE_MAP.put("marine.coordX", "Каардыната X");
        BE_MAP.put("marine.coordY", "Каардыната Y");
        BE_MAP.put("marine.health", "Здароўе");
        BE_MAP.put("marine.loyal", "Лаяльны");
        BE_MAP.put("marine.weaponType", "Зброя");
        BE_MAP.put("marine.meleeWeapon", "Блізкая зброя");
        BE_MAP.put("marine.chapterName", "Глава (імя)");
        BE_MAP.put("marine.chapterWorld", "Глава (свет)");
        BE_MAP.put("edit", "Рэдагаваць");
        BE_MAP.put("remove", "Выдаліць");
        BE_MAP.put("not your marine", "Не ваш дэсантнік");
        // Греческий
        EL_MAP.put("error.userExists", "Ο χρήστης υπάρχει ήδη");
        EL_MAP.put("error.userNotFound", "Ο χρήστης δεν βρέθηκε");
        EL_MAP.put("error.wrongPassword", "Λάθος κωδικός");
        EL_MAP.put("error.emptyLogin", "Το όνομα χρήστη δεν μπορεί να είναι κενό");
        EL_MAP.put("error.emptyPassword", "Ο κωδικός δεν μπορεί να είναι κενός");
        //EL_MAP.putAll(RU_MAP);
        EL_MAP.put("table.login", "Σύνδεση");
        EL_MAP.put("table.name", "Όνομα");
        EL_MAP.put("button.showField", "Εμφάνιση πεδίου");
        EL_MAP.put("table.creationDate", "Ημερομηνία δημιουργίας");
        EL_MAP.put("table.health", "Υγεία");
        EL_MAP.put("table.loyal", "Πιστός");
        EL_MAP.put("table.weaponType", "Όπλο");
        EL_MAP.put("table.meleeWeapon", "Όπλο σώμα με σώμα");
        EL_MAP.put("table.coordX", "Συντεταγμένη X");
        EL_MAP.put("table.coordY", "Συντεταγμένη Y");
        EL_MAP.put("table.chapterName", "Κεφάλαιο (όνομα)");
        EL_MAP.put("table.chapterWorld", "Κεφάλαιο (κόσμος)");
        EL_MAP.put("button.execute", "Εκτέλεση");
        EL_MAP.put("button.accept", "Προσθήκη");
        EL_MAP.put("button.cancel", "Ακύρωση");
        EL_MAP.put("button.language", "Γλώσσα");
        EL_MAP.put("commandBox.prompt", "Επιλέξτε εντολή...");
        EL_MAP.put("marine.name", "Όνομα");
        EL_MAP.put("marine.coordX", "Συντεταγμένη X");
        EL_MAP.put("marine.coordY", "Συντεταγμένη Y");
        EL_MAP.put("marine.health", "Υγεία");
        EL_MAP.put("marine.loyal", "Πιστός");
        EL_MAP.put("marine.weaponType", "Όπλο");
        EL_MAP.put("marine.meleeWeapon", "Όπλο σώμα με σώμα");
        EL_MAP.put("marine.chapterName", "Κεφάλαιο (όνομα)");
        EL_MAP.put("marine.chapterWorld", "Κεφάλαιο (κόσμος)");
        EL_MAP.put("edit", "Επεξεργασία");
        EL_MAP.put("remove", "Διαγραφή");
        EL_MAP.put("not your marine", "Όχι δικός σας πεζοναύτης");
        // Испанский (Никарагуа)
        ES_NI_MAP.put("error.userExists", "El usuario ya existe");
        ES_NI_MAP.put("error.userNotFound", "Usuario no encontrado");
        ES_NI_MAP.put("error.wrongPassword", "Contraseña incorrecta");
        ES_NI_MAP.put("error.emptyLogin", "El usuario no puede estar vacío");
        ES_NI_MAP.put("error.emptyPassword", "La contraseña no puede estar vacía");
        //ES_NI_MAP.putAll(RU_MAP);
        ES_NI_MAP.put("table.login", "Usuario");
        ES_NI_MAP.put("table.name", "Nombre");
        ES_NI_MAP.put("button.showField", "Mostrar campo");
        ES_NI_MAP.put("table.creationDate", "Fecha de creación");
        ES_NI_MAP.put("table.health", "Salud");
        ES_NI_MAP.put("table.loyal", "Leal");
        ES_NI_MAP.put("table.weaponType", "Arma");
        ES_NI_MAP.put("table.meleeWeapon", "Arma cuerpo a cuerpo");
        ES_NI_MAP.put("table.coordX", "Coordenada X");
        ES_NI_MAP.put("table.coordY", "Coordenada Y");
        ES_NI_MAP.put("table.chapterName", "Capítulo (nombre)");
        ES_NI_MAP.put("table.chapterWorld", "Capítulo (mundo)");
        ES_NI_MAP.put("button.execute", "Ejecutar");
        ES_NI_MAP.put("button.accept", "Agregar");
        ES_NI_MAP.put("button.cancel", "Cancelar");
        ES_NI_MAP.put("button.language", "Idioma");
        ES_NI_MAP.put("commandBox.prompt", "Seleccione un comando...");
        ES_NI_MAP.put("marine.name", "Nombre");
        ES_NI_MAP.put("marine.coordX", "Coordenada X");
        ES_NI_MAP.put("marine.coordY", "Coordenada Y");
        ES_NI_MAP.put("marine.health", "Salud");
        ES_NI_MAP.put("marine.loyal", "Leal");
        ES_NI_MAP.put("marine.weaponType", "Arma");
        ES_NI_MAP.put("marine.meleeWeapon", "Arma cuerpo a cuerpo");
        ES_NI_MAP.put("marine.chapterName", "Capítulo (nombre)");
        ES_NI_MAP.put("marine.chapterWorld", "Capítulo (mundo)");
        ES_NI_MAP.put("edit", "Editar");
        ES_NI_MAP.put("remove", "Eliminar");
        ES_NI_MAP.put("not your marine", "No es tu marine");
    }

    public enum Lang { RU, BE, EL, ES_NI }
    private static Lang currentLang = Lang.RU;

    public static void setLanguage(Lang lang) {
        currentLang = lang;
        // Устанавливаем соответствующий Language в зависимости от Lang
        switch (lang) {
            case BE:
                currentLanguage = new BelarusianLanguage();
                break;
            case EL:
                currentLanguage = new GreekLanguage();
                break;
            case ES_NI:
                currentLanguage = new SpanishNILanguage();
                break;
            default: // RU
                currentLanguage = new RussianLanguage();
        }
    }

    public static Lang getCurrentLang() {
        return currentLang;
    }

    public static String get(String key) {
        switch (currentLang) {
            case BE: return BE_MAP.getOrDefault(key, key);
            case EL: return EL_MAP.getOrDefault(key, key);
            case ES_NI: return ES_NI_MAP.getOrDefault(key, key);
            default: return RU_MAP.getOrDefault(key, key);
        }
    }

    public static String getEmptyLoginError() { return get("error.emptyLogin"); }
    public static String getEmptyPasswordError() { return get("error.emptyPassword"); }
    public static String getUserNotFoundError() { return get("error.userNotFound"); }
    public static String getWrongPasswordError() { return get("error.wrongPassword"); }
    public static String getUserExistsError() { return get("error.userExists"); }

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
    // Реализация для белорусского языка
    public static class BelarusianLanguage implements Language {
        @Override public String getLoginInfo() { return "Старонка ўваходу"; }
        @Override public String getLoginLabel() { return "Лагін"; }
        @Override public String getPasswordLabel() { return "Пароль"; }
        @Override public String getLoginButtonText() { return "Увайсці"; }
        @Override public String getRegisterButtonText() { return "Рэгістрацыя"; }
        @Override public String getWindowTitle() { return "Форма аўтарызацыі"; }
        @Override public String getCurrentUserLabel() { return "Карыстальнік:"; }
    }
    // Реализация для греческого языка
    public static class GreekLanguage implements Language {
        @Override public String getLoginInfo() { return "Σελίδα εισόδου"; }
        @Override public String getLoginLabel() { return "Σύνδεση"; }
        @Override public String getPasswordLabel() { return "Κωδικός"; }
        @Override public String getLoginButtonText() { return "Είσοδος"; }
        @Override public String getRegisterButtonText() { return "Εγγραφή"; }
        @Override public String getWindowTitle() { return "Φόρμα σύνδεσης"; }
        @Override public String getCurrentUserLabel() { return "Χρήστης:"; }
    }
    // Реализация для испанского (Никарагуа)
    public static class SpanishNILanguage implements Language {
        @Override public String getLoginInfo() { return "Página de inicio de sesión"; }
        @Override public String getLoginLabel() { return "Usuario"; }
        @Override public String getPasswordLabel() { return "Contraseña"; }
        @Override public String getLoginButtonText() { return "Iniciar sesión"; }
        @Override public String getRegisterButtonText() { return "Registrarse"; }
        @Override public String getWindowTitle() { return "Formulario de inicio de sesión"; }
        @Override public String getCurrentUserLabel() { return "Usuario:"; }
    }
}