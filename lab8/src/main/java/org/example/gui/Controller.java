package org.example.gui;

import java.net.URL;
import java.util.ResourceBundle;

import org.example.client.ClientApp;
import org.example.collectionClasses.app.IOManager;
import org.example.collectionClasses.commands.Answer;
import org.example.gui.AppResources.Language;
import org.example.collectionClasses.app.CommandManager;
import org.example.client.ClientNetworkManager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.control.MenuItem;

public class Controller implements Initializable {
    @FXML
    private MenuButton login_language;
    @FXML private Text login_login_text;
    @FXML private TextField login_login_field;
    @FXML private Text login_password_text;
    @FXML private PasswordField login_password_field;
    @FXML private Button login_login_button;
    @FXML private Button login_register_button;
    @FXML private Text login_info_text;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateTexts();
        // --- добавляем обработку смены языка ---
        login_language.getItems().clear();
        MenuItem ru = new MenuItem("Русский");
        MenuItem be = new MenuItem("Беларуская");
        MenuItem el = new MenuItem("Ελληνικά");
        MenuItem es = new MenuItem("Español (NI)");
        ru.setOnAction(e -> { AppResources.setLanguage(AppResources.Lang.RU); updateTexts(); });
        be.setOnAction(e -> { AppResources.setLanguage(AppResources.Lang.BE); updateTexts(); });
        el.setOnAction(e -> { AppResources.setLanguage(AppResources.Lang.EL); updateTexts(); });
        es.setOnAction(e -> { AppResources.setLanguage(AppResources.Lang.ES_NI); updateTexts(); });
        login_language.getItems().addAll(ru, be, el, es);
        // --- конец блока смены языка ---
        // Инициализация клиента для GUI (один раз на приложение)
        try {
            IOManager ioManager = new org.example.collectionClasses.app.IOManager();
            ioManager.setIsClient(true);
            org.example.collectionClasses.app.CommandManager commandManager = new org.example.collectionClasses.app.CommandManager();
            org.example.client.ClientNetworkManager networkManager = new org.example.client.ClientNetworkManager("localhost", 57486);
            org.example.client.ClientApp.initForGUI(ioManager, commandManager, networkManager);
        } catch (Exception e) {
            login_info_text.setText(AppResources.get("error.initClient") + ": " + e.getMessage());
        }
        login_login_button.setOnAction(event -> handleLogin());
        login_register_button.setOnAction(event -> handleRegister());
    }

    private void updateTexts() {
        Language lang = AppResources.getCurrentLanguage(); // Теперь будет возвращать правильный язык
        
        login_login_text.setText(lang.getLoginLabel());
        login_password_text.setText(lang.getPasswordLabel());
        login_login_button.setText(lang.getLoginButtonText());
        login_register_button.setText(lang.getRegisterButtonText());
        login_login_field.setPromptText(lang.getLoginLabel()); // Теперь prompt тоже обновится
        login_password_field.setPromptText(lang.getPasswordLabel());
        login_language.setText(AppResources.get("button.language")); // Использует currentLang
        login_info_text.setText("");
    }

    private void handleLogin() {
        String login = login_login_field.getText();
        String password = login_password_field.getText();
        boolean hasError = false;
        
        if (login == null || login.isEmpty()) {
            login_info_text.setText(AppResources.getEmptyLoginError());
            login_login_field.setPromptText(AppResources.getEmptyLoginError()); // Используем getEmptyLoginError()
            login_login_field.setStyle("-fx-border-color: red;");
            hasError = true;
        } else {
            login_login_field.setPromptText(AppResources.getCurrentLanguage().getLoginLabel());
            login_login_field.setStyle("");
        }
        
        if (password == null || password.isEmpty()) {
            login_info_text.setText(AppResources.getEmptyPasswordError());
            login_password_field.setPromptText(AppResources.getEmptyPasswordError()); // Используем getEmptyPasswordError()
            login_password_field.setStyle("-fx-border-color: red;");
            hasError = true;
        } else {
            login_password_field.setPromptText(AppResources.getCurrentLanguage().getPasswordLabel());
            login_password_field.setStyle("");
        }
        
        if (hasError) {
            return;
        }
        try {
            Answer answer = ClientApp.loginWithAnswer(login, password);
            String answerText = answer.toString();
            if (answer.condition()) {
                // Сохраняем логин и пароль в ClientApp
                ClientApp.setGuiLogin(login);
                ClientApp.setGuiPassword(password);
                login_info_text.setText(AppResources.getCurrentLanguage().getLoginInfo() + ": " + answerText);
                // Переход к MainPage.fxml с передачей логина
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainPage.fxml"));
                    StackPane mainPage = loader.load();
                    MainPageController mainPageController = loader.getController();
                    mainPageController.setUserLogin(login);
                    Scene scene = login_register_button.getScene();
                    StackPane root = (StackPane) scene.getRoot();
                    root.getChildren().setAll(mainPage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    login_info_text.setText(AppResources.get("error.gotoMain") + ": " + ex.getMessage());
                }
            } else if (answerText.contains("Пользователь не найден")) {
                login_info_text.setText(AppResources.getUserNotFoundError());
            } else if (answerText.contains("Неверный пароль")) {
                login_info_text.setText(AppResources.getWrongPasswordError());
            } else {
                login_info_text.setText(AppResources.getCurrentLanguage().getLoginInfo() + ": " + answerText);
            }
        } catch (Exception e) {
            login_info_text.setText(AppResources.get("error.connection") + ": " + e.getMessage());
        }
    }

    private void handleRegister() {
        String login = login_login_field.getText();
        String password = login_password_field.getText();
        boolean hasError = false;
        
        if (login == null || login.isEmpty()) {
            login_info_text.setText(AppResources.getEmptyLoginError());
            login_login_field.setPromptText(AppResources.getEmptyLoginError()); // Используем getEmptyLoginError()
            login_login_field.setStyle("-fx-border-color: red;");
            hasError = true;
        } else {
            login_login_field.setPromptText(AppResources.getCurrentLanguage().getLoginLabel());
            login_login_field.setStyle("");
        }
        
        if (password == null || password.isEmpty()) {
            login_info_text.setText(AppResources.getEmptyPasswordError());
            login_password_field.setPromptText(AppResources.getEmptyPasswordError()); // Используем getEmptyPasswordError()
            login_password_field.setStyle("-fx-border-color: red;");
            hasError = true;
        } else {
            login_password_field.setPromptText(AppResources.getCurrentLanguage().getPasswordLabel());
            login_password_field.setStyle("");
        }
        
        if (hasError) {
            return;
        }
        try {
            Answer answer = ClientApp.registerWithAnswer(login, password);
            String answerText = answer.toString();

            if (answer.condition()) {
                // Сохраняем логин и пароль в ClientApp
                ClientApp.setGuiLogin(login);
                ClientApp.setGuiPassword(password);
                login_info_text.setText(AppResources.getCurrentLanguage().getRegisterButtonText() + ": " + answerText);
                // Переход к MainPage.fxml с передачей логина
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainPage.fxml"));
                    StackPane mainPage = loader.load();
                    MainPageController mainPageController = loader.getController();
                    mainPageController.setUserLogin(login);
                    Scene scene = login_register_button.getScene();
                    StackPane root = (StackPane) scene.getRoot();
                    root.getChildren().setAll(mainPage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    login_info_text.setText(AppResources.get("error.gotoMain") + ": " + ex.getMessage());
                }
            } else if (answerText.equals(AppResources.getUserExistsError())) {
                login_info_text.setText(AppResources.getUserExistsError());
            } else {
                login_info_text.setText(AppResources.getCurrentLanguage().getRegisterButtonText() + ": " + answerText);
            }
        } catch (Exception e) {
            login_info_text.setText(AppResources.get("error.connection") + ": " + e.getMessage());
        }
    }
}