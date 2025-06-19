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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: инициализация таблицы и логики главной страницы
    }
}
