package org.example.gui;

import java.net.URL;
import java.util.ResourceBundle;

import org.example.client.ClientApp;
import org.example.collectionClasses.app.IOManager;
import org.example.collectionClasses.commands.Answer;
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
        // Инициализация клиента для GUI (один раз на приложение)
        try {
            IOManager ioManager = new org.example.collectionClasses.app.IOManager();
            ioManager.setIsClient(true);
            org.example.collectionClasses.app.CommandManager commandManager = new org.example.collectionClasses.app.CommandManager();
            org.example.client.ClientNetworkManager networkManager = new org.example.client.ClientNetworkManager("localhost", 57486);
            org.example.client.ClientApp.initForGUI(ioManager, commandManager, networkManager);
        } catch (Exception e) {
            login_info_text.setText("Ошибка инициализации клиента: " + e.getMessage());
        }
        login_login_button.setOnAction(event -> handleLogin());
        login_register_button.setOnAction(event -> handleRegister());
    }

    private void updateTexts() {
        AppResources.Language lang = AppResources.getCurrentLanguage();
        
        login_info_text.setText(lang.getLoginInfo());
        login_login_text.setText(lang.getLoginLabel());
        login_password_text.setText(lang.getPasswordLabel());
        login_login_button.setText(lang.getLoginButtonText());
        login_register_button.setText(lang.getRegisterButtonText());
        
        login_login_field.setPromptText(lang.getLoginLabel());
        login_password_field.setPromptText(lang.getPasswordLabel());
    }

    private void handleLogin() {
        String login = login_login_field.getText();
        String password = login_password_field.getText();
        boolean hasError = false;
        if (login == null || login.isEmpty()) {
            login_info_text.setText(AppResources.getEmptyLoginError());
            login_login_field.setPromptText(AppResources.getEmptyLoginError());
            login_login_field.setStyle("-fx-border-color: red;");
            hasError = true;
        } else {
            login_login_field.setPromptText(AppResources.getCurrentLanguage().getLoginLabel());
            login_login_field.setStyle("");
        }
        if (password == null || password.isEmpty()) {
            login_info_text.setText(AppResources.getEmptyPasswordError());
            login_password_field.setPromptText(AppResources.getEmptyPasswordError());
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
                login_info_text.setText(AppResources.getCurrentLanguage().getLoginInfo() + ": " + answerText);
                // Переход к MainPage.fxml
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainPage.fxml"));
                    StackPane mainPage = loader.load();
                    Scene scene = login_register_button.getScene();
                    StackPane root = (StackPane) scene.getRoot();
                    root.getChildren().setAll(mainPage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    login_info_text.setText("Ошибка перехода на главную страницу: " + ex.getMessage());
                }
            } else if (answerText.contains(AppResources.getUserNotFoundError())) {
                login_info_text.setText(AppResources.getUserNotFoundError());
            } else if (answerText.contains(AppResources.getWrongPasswordError())) {
                login_info_text.setText(AppResources.getWrongPasswordError());
            } else {
                login_info_text.setText(AppResources.getCurrentLanguage().getLoginInfo() + ": " + answerText);
            }
        } catch (Exception e) {
            login_info_text.setText("Ошибка соединения: " + e.getMessage());
        }
    }

    private void handleRegister() {
        String login = login_login_field.getText();
        String password = login_password_field.getText();
        boolean hasError = false;
        if (login == null || login.isEmpty()) {
            login_info_text.setText(AppResources.getEmptyLoginError());
            login_login_field.setPromptText(AppResources.getEmptyLoginError());
            login_login_field.setStyle("-fx-border-color: red;");
            hasError = true;
        } else {
            login_login_field.setPromptText(AppResources.getCurrentLanguage().getLoginLabel());
            login_login_field.setStyle("");
        }
        if (password == null || password.isEmpty()) {
            login_info_text.setText(AppResources.getEmptyPasswordError());
            login_password_field.setPromptText(AppResources.getEmptyPasswordError());
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
                login_info_text.setText(AppResources.getCurrentLanguage().getRegisterButtonText() + ": " + answerText);
                // Переход к MainPage.fxml
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainPage.fxml"));
                    StackPane mainPage = loader.load();
                    Scene scene = login_register_button.getScene();
                    StackPane root = (StackPane) scene.getRoot();
                    root.getChildren().setAll(mainPage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    login_info_text.setText("Ошибка перехода на главную страницу: " + ex.getMessage());
                }
            } else if (answerText.equals(AppResources.getUserExistsError())) {
                login_info_text.setText(AppResources.getUserExistsError());
            } else {
                login_info_text.setText(AppResources.getCurrentLanguage().getRegisterButtonText() + ": " + answerText);
            }
        } catch (Exception e) {
            login_info_text.setText("Ошибка соединения: " + e.getMessage());
        }
    }
}