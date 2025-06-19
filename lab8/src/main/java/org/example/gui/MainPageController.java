package org.example.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.example.client.ClientApp;
import org.example.collectionClasses.model.SpaceMarine;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainPageController implements Initializable {
    @FXML private Text main_user_info;
    @FXML private MenuButton main_language;
    @FXML private TableView<SpaceMarine> main_table;
    @FXML private TableColumn<SpaceMarine, Integer> main_id;
    @FXML private TableColumn<SpaceMarine, String> main_login;
    @FXML private TableColumn<SpaceMarine, String> main_name;
    @FXML private TableColumn<SpaceMarine, String> main_coord_id;
    @FXML private TableColumn<SpaceMarine, String> main_creation_date;
    @FXML private TableColumn<SpaceMarine, Float> main_health;
    @FXML private TableColumn<SpaceMarine, Boolean> main_loyal;
    @FXML private TableColumn<SpaceMarine, String> main_weapon_type;
    @FXML private TableColumn<SpaceMarine, String> main_melee_weapon;
    @FXML private TableColumn<SpaceMarine, String> main_chapter;

    private String userLogin;
    private Timeline updateTimeline;

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
        // PropertyValueFactory для TableView
        if (main_id != null) main_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        if (main_login != null) main_login.setCellValueFactory(new PropertyValueFactory<>("userLogin"));
        if (main_name != null) main_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        if (main_coord_id != null) main_coord_id.setCellValueFactory(cellData -> new SimpleStringProperty(
            cellData.getValue().getCoordinates() != null ? cellData.getValue().getCoordinates().toString() : ""));
        if (main_creation_date != null) main_creation_date.setCellValueFactory(cellData -> new SimpleStringProperty(
            cellData.getValue().getCreationDate() != null ? cellData.getValue().getCreationDate().toString() : ""));
        if (main_health != null) main_health.setCellValueFactory(new PropertyValueFactory<>("health"));
        if (main_loyal != null) main_loyal.setCellValueFactory(new PropertyValueFactory<>("loyal"));
        if (main_weapon_type != null) main_weapon_type.setCellValueFactory(cellData -> new SimpleStringProperty(
            cellData.getValue().getWeaponType() != null ? cellData.getValue().getWeaponType().toString() : ""));
        if (main_melee_weapon != null) main_melee_weapon.setCellValueFactory(cellData -> new SimpleStringProperty(
            cellData.getValue().getMeleeWeapon() != null ? cellData.getValue().getMeleeWeapon().toString() : ""));
        if (main_chapter != null) main_chapter.setCellValueFactory(cellData -> new SimpleStringProperty(
            cellData.getValue().getChapter() != null ? cellData.getValue().getChapter().toString() : ""));
        if (userLogin != null && main_user_info != null) {
            main_user_info.setText(AppResources.getCurrentUserLabel() + " " + userLogin);
        }
        startCollectionUpdater();
    }

    private void startCollectionUpdater() {
        updateTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateCollection()));
        updateTimeline.setCycleCount(Timeline.INDEFINITE);
        updateTimeline.play();
    }

    private void updateCollection() {
        Platform.runLater(() -> {
            try {
                var answer = ClientApp.getCollectionFromServer();
                List<SpaceMarine> collection = answer.getCollection();
                if (collection != null) {
                    main_table.getItems().setAll(collection);
                }
            } catch (Exception e) {
                // Можно добавить вывод ошибки в main_user_info или лог
            }
        });
    }
}
