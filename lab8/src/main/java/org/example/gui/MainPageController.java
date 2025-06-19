package org.example.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {
    @FXML private Text main_user_info;
    @FXML private MenuButton main_language;
    @FXML private TableView<?> main_table;
    @FXML private TableColumn<?, ?> main_id;
    @FXML private TableColumn<?, ?> main_login;
    @FXML private TableColumn<?, ?> main_name;
    @FXML private TableColumn<?, ?> main_coord_id;
    @FXML private TableColumn<?, ?> main_creation_date;
    @FXML private TableColumn<?, ?> main_health;
    @FXML private TableColumn<?, ?> main_loyal;
    @FXML private TableColumn<?, ?> main_weapon_type;
    @FXML private TableColumn<?, ?> main_melee_weapon;
    @FXML private TableColumn<?, ?> main_chapter;

    private String userLogin;

    public void setUserLogin(String login) {
        this.userLogin = login;
        if (main_user_info != null) {
            main_user_info.setText(AppResources.getCurrentUserLabel() + " " + login);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Подписи для колонок (на английском)
        if (main_id != null) main_id.setText("ID");
        if (main_login != null) main_login.setText("Login");
        if (main_name != null) main_name.setText("Name");
        if (main_coord_id != null) main_coord_id.setText("Coord ID");
        if (main_creation_date != null) main_creation_date.setText("Creation Date");
        if (main_health != null) main_health.setText("Health");
        if (main_loyal != null) main_loyal.setText("Loyal");
        if (main_weapon_type != null) main_weapon_type.setText("Weapon Type");
        if (main_melee_weapon != null) main_melee_weapon.setText("Melee Weapon");
        if (main_chapter != null) main_chapter.setText("Chapter");
        if (userLogin != null && main_user_info != null) {
            main_user_info.setText(AppResources.getCurrentUserLabel() + " " + userLogin);
        }
    }
}
