package org.example.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.example.client.ClientApp;
import org.example.collectionClasses.model.SpaceMarine;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
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
    @FXML private TableColumn<SpaceMarine, String> main_creation_date;
    @FXML private TableColumn<SpaceMarine, Float> main_health;
    @FXML private TableColumn<SpaceMarine, Boolean> main_loyal;
    @FXML private TableColumn<SpaceMarine, String> main_weapon_type;
    @FXML private TableColumn<SpaceMarine, String> main_melee_weapon;
    @FXML private TableColumn<SpaceMarine, String> main_chapter;
    @FXML private TableColumn<SpaceMarine, String> main_coord_id;

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
        if (main_health != null) main_health.setText("Health");
        if (main_loyal != null) main_loyal.setText("Loyal");
        if (main_weapon_type != null) main_weapon_type.setText("Weapon Type");
        if (main_melee_weapon != null) main_melee_weapon.setText("Melee Weapon");
        // Форматтер даты по локали
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withLocale(Locale.getDefault());
        if (main_creation_date != null) main_creation_date.setText("Creation Date");
        if (main_creation_date != null) main_creation_date.setCellValueFactory(cellData -> new SimpleStringProperty(
            cellData.getValue().getCreationDate() != null ? cellData.getValue().getCreationDate().format(dateFormatter) : ""));
        if (main_id != null) main_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        if (main_login != null) main_login.setCellValueFactory(new PropertyValueFactory<>("userLogin"));
        if (main_name != null) main_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        if (main_health != null) main_health.setCellValueFactory(new PropertyValueFactory<>("health"));
        if (main_loyal != null) main_loyal.setCellValueFactory(new PropertyValueFactory<>("loyal"));
        if (main_weapon_type != null) main_weapon_type.setCellValueFactory(cellData -> new SimpleStringProperty(
            cellData.getValue().getWeaponType() != null ? cellData.getValue().getWeaponType().toString() : ""));
        if (main_melee_weapon != null) main_melee_weapon.setCellValueFactory(cellData -> new SimpleStringProperty(
            cellData.getValue().getMeleeWeapon() != null ? cellData.getValue().getMeleeWeapon().toString() : ""));
        // Координаты по отдельным колонкам
        TableColumn<SpaceMarine, String> coordXCol = new TableColumn<>("Coord X");
        TableColumn<SpaceMarine, String> coordYCol = new TableColumn<>("Coord Y");
        coordXCol.setCellValueFactory(cellData -> new SimpleStringProperty(
            cellData.getValue().getCoordinates() != null ? String.valueOf(cellData.getValue().getCoordinates().getX()) : ""));
        coordYCol.setCellValueFactory(cellData -> new SimpleStringProperty(
            cellData.getValue().getCoordinates() != null && cellData.getValue().getCoordinates().getY() != null ? String.valueOf(cellData.getValue().getCoordinates().getY()) : ""));
        // Chapter по отдельным колонкам
        TableColumn<SpaceMarine, String> chapterNameCol = new TableColumn<>("Chapter Name");
        TableColumn<SpaceMarine, String> chapterWorldCol = new TableColumn<>("Chapter World");
        chapterNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(
            cellData.getValue().getChapter() != null ? cellData.getValue().getChapter().getName() : ""));
        chapterWorldCol.setCellValueFactory(cellData -> new SimpleStringProperty(
            cellData.getValue().getChapter() != null ? cellData.getValue().getChapter().getWorld() : ""));
        // Кнопка удаления
        TableColumn<SpaceMarine, Void> actionCol = new TableColumn<>("Action");
        actionCol.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Remove");
            {
                btn.setStyle("-fx-background-color: #ff6666; -fx-text-fill: white;");
                btn.setOnMouseEntered(event -> btn.setStyle("-fx-background-color: #ff3333; -fx-text-fill: white;"));
                btn.setOnMouseExited(event -> btn.setStyle("-fx-background-color: #ff6666; -fx-text-fill: white;"));
            }
            {
                btn.setOnAction(event -> {
                    SpaceMarine marine = getTableView().getItems().get(getIndex());
                    try {
                        org.example.collectionClasses.commands.RemoveByIdCommand cmd = new org.example.collectionClasses.commands.RemoveByIdCommand();
                        String[] tokens = {"remove_by_id", String.valueOf(marine.getId())};
                        // После удаления сразу обновляем коллекцию
                        ClientApp.processCommandFromGUI(cmd, tokens);
                        updateCollection();
                    } catch (Exception e) {
                        // Можно добавить вывод ошибки
                    }
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });
        // Добавляем новые колонки если их нет
        if (main_table != null) {
            if (main_table.getColumns().stream().noneMatch(c -> c.getText().equals("Coord X"))) main_table.getColumns().add(coordXCol);
            if (main_table.getColumns().stream().noneMatch(c -> c.getText().equals("Coord Y"))) main_table.getColumns().add(coordYCol);
            if (main_table.getColumns().stream().noneMatch(c -> c.getText().equals("Chapter Name"))) main_table.getColumns().add(chapterNameCol);
            if (main_table.getColumns().stream().noneMatch(c -> c.getText().equals("Chapter World"))) main_table.getColumns().add(chapterWorldCol);
            if (main_table.getColumns().stream().noneMatch(c -> c.getText().equals("Action"))) main_table.getColumns().add(0, actionCol);
            main_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        }
        if (userLogin != null && main_user_info != null) {
            main_user_info.setText(AppResources.getCurrentUserLabel() + " " + userLogin);
        }
        updateCollection();
        startCollectionUpdater();
    }

    private void startCollectionUpdater() {
        updateTimeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> updateCollection()));
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
