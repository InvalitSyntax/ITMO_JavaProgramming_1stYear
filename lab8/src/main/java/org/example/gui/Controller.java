package org.example.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Controller implements Initializable {
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
}