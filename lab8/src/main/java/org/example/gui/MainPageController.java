package org.example.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

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
    @FXML private ComboBox<String> main_command_box;
    @FXML private Button main_command_execute;

    private String userLogin;
    private Timeline updateTimeline;
    private CheckBox loyalCheckBox;

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
                btn.setOnAction(event -> {
                    SpaceMarine marine = getTableView().getItems().get(getIndex());
                    try {
                        org.example.collectionClasses.commands.RemoveByIdCommand cmd = new org.example.collectionClasses.commands.RemoveByIdCommand();
                        String[] tokens = {"remove_by_id", String.valueOf(marine.getId())};
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
        if (main_command_box != null) {
            main_command_box.getItems().addAll("Add Element", "Clear Collection", "Count By Weapon Type", "Count Less Than Loyal", "Execute Script", "Filter Less Than Chapter", "Info", "Remove First", "Remove Greater", "Remove Head");
        }
        if (main_command_execute != null) {
            main_command_execute.setOnAction(event -> handleCommandExecute());
        }
        // Добавим чекбокс для выбора loyal
        CheckBox loyalCheckBox = new CheckBox("loyal");
        loyalCheckBox.setVisible(false);
        if (main_command_box != null) {
            main_command_box.valueProperty().addListener((obs, oldVal, newVal) -> {
                loyalCheckBox.setVisible("Count Less Than Loyal".equals(newVal));
            });
        }
        if (main_table != null && main_table.getScene() != null && main_table.getScene().getRoot() instanceof VBox) {
            VBox root = (VBox) main_table.getScene().getRoot();
            if (!root.getChildren().contains(loyalCheckBox)) {
                root.getChildren().add(1, loyalCheckBox); // после HBox с кнопками
            }
        }
        this.loyalCheckBox = loyalCheckBox;
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

    private void openAddDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddMarineDialog.fxml"));
            VBox dialogRoot = loader.load();
            AddMarineDialogController dialogController = loader.getController();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add SpaceMarine");
            dialogStage.setScene(new Scene(dialogRoot));
            dialogStage.initOwner(main_command_box.getScene().getWindow());
            dialogStage.showAndWait();
            SpaceMarine newMarine = dialogController.getResult();
            if (newMarine != null) {
                // Отправить на сервер команду добавления
                org.example.collectionClasses.commands.AddCommand cmd = new org.example.collectionClasses.commands.AddCommand();
                cmd.spaceMarine = newMarine;
                String[] tokens = {"add"};
                ClientApp.processCommandFromGUI(cmd, tokens);
                updateCollection();
            }
        } catch (Exception e) {
            // Можно добавить вывод ошибки
        }
    }

    // Новый диалог для ввода Chapter
    private void openChapterDialog(java.util.function.Consumer<org.example.collectionClasses.model.Chapter> onResult) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Введите Chapter");
        VBox vbox = new VBox(10);
        vbox.setPadding(new javafx.geometry.Insets(20));
        TextField nameField = new TextField();
        nameField.setPromptText("Name (обязательно)");
        TextField worldField = new TextField();
        worldField.setPromptText("World (обязательно)");
        Text errorText = new Text();
        errorText.setStyle("-fx-fill: red;");
        Button okBtn = new Button("OK");
        okBtn.setOnAction(e -> {
            String name = nameField.getText();
            String world = worldField.getText();
            if (name == null || name.isEmpty()) {
                errorText.setText("Name не может быть пустым");
                return;
            }
            if (world == null) {
                errorText.setText("World не может быть null");
                return;
            }
            org.example.collectionClasses.model.Chapter chapter = new org.example.collectionClasses.model.Chapter(name, world);
            onResult.accept(chapter);
            dialogStage.close();
        });
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(e -> dialogStage.close());
        vbox.getChildren().addAll(new Label("Введите Chapter для фильтрации:"),
            new Label("Name:"), nameField, new Label("World:"), worldField, errorText, new HBox(10, okBtn, cancelBtn));
        dialogStage.setScene(new Scene(vbox));
        dialogStage.initOwner(main_command_box.getScene().getWindow());
        dialogStage.showAndWait();
    }

    private void handleCommandExecute() {
        String selected = main_command_box.getValue();
        if (selected == null) return;
        if (selected.equals("Clear Collection")) {
            try {
                org.example.collectionClasses.commands.ClearCommand cmd = new org.example.collectionClasses.commands.ClearCommand();
                String[] tokens = {"clear"};
                ClientApp.processCommandFromGUI(cmd, tokens);
                updateCollection();
            } catch (Exception e) {
                // Можно добавить вывод ошибки
            }
        } else if (selected.equals("Add Element")) {
            openAddDialog();
        } else if (selected.equals("Count By Weapon Type")) {
            try {
                org.example.collectionClasses.commands.CountByWeaponeTypeCommand cmd = new org.example.collectionClasses.commands.CountByWeaponeTypeCommand();
                String[] tokens = {"count_by_weapone_type"};
                var answer = ClientApp.processCommandFromGUI(cmd, tokens);
                showInfoDialog("Result", answer.toString());
            } catch (Exception e) {
                showInfoDialog("Error", e.getMessage());
            }
        } else if (selected.equals("Count Less Than Loyal")) {
            try {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Count Less Than Loyal");
                dialog.setHeaderText("Введите значение loyal (true/false):");
                dialog.setContentText("loyal:");
                dialog.initOwner(main_command_box.getScene().getWindow());
                dialog.showAndWait().ifPresent(loyalValue -> {
                    try {
                        org.example.collectionClasses.commands.CountLessThanLoyalCommand cmd = new org.example.collectionClasses.commands.CountLessThanLoyalCommand();
                        String[] tokens = {"count_less_than_loyal", loyalValue};
                        var answer = ClientApp.processCommandFromGUI(cmd, tokens);
                        showInfoDialog("Result", answer.toString());
                    } catch (Exception e) {
                        showInfoDialog("Error", e.getMessage());
                    }
                });
            } catch (Exception e) {
                showInfoDialog("Error", e.getMessage());
            }
        } else if (selected.equals("Execute Script")) {
            try {
                javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
                fileChooser.setTitle("Выберите скрипт для выполнения");
                fileChooser.setInitialDirectory(new java.io.File(System.getProperty("user.dir")));
                java.io.File file = fileChooser.showOpenDialog(main_command_box.getScene().getWindow());
                if (file != null) {
                    org.example.collectionClasses.commands.ExecuteScriptCommand cmd = new org.example.collectionClasses.commands.ExecuteScriptCommand();
                    String[] tokens = {"execute_script", file.getAbsolutePath()};
                    var answer = ClientApp.processCommandFromGUI(cmd, tokens);
                    showInfoDialog("Script Result", answer.toString());
                }
            } catch (Exception e) {
                showInfoDialog("Error", e.getMessage());
            }
        } else if (selected.equals("Filter Less Than Chapter")) {
            openChapterDialog(chapter -> {
                if (chapter == null) return;
                List<SpaceMarine> all = main_table.getItems();
                List<SpaceMarine> filtered = all.stream()
                    .filter(marine -> chapter.compareTo(marine.getChapter()) > 0)
                    .toList();
                // Создаём новую таблицу с такими же свойствами, как в initialize
                TableView<SpaceMarine> filteredTable = new TableView<>();
                TableColumn<SpaceMarine, Integer> idCol = new TableColumn<>("ID");
                idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
                TableColumn<SpaceMarine, String> nameCol = new TableColumn<>("Name");
                nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                TableColumn<SpaceMarine, String> loginCol = new TableColumn<>("Login");
                loginCol.setCellValueFactory(new PropertyValueFactory<>("userLogin"));
                TableColumn<SpaceMarine, String> creationDateCol = new TableColumn<>("Creation Date");
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withLocale(Locale.getDefault());
                creationDateCol.setCellValueFactory(cellData -> new SimpleStringProperty(
                    cellData.getValue().getCreationDate() != null ? cellData.getValue().getCreationDate().format(dateFormatter) : ""));
                TableColumn<SpaceMarine, Float> healthCol = new TableColumn<>("Health");
                healthCol.setCellValueFactory(new PropertyValueFactory<>("health"));
                TableColumn<SpaceMarine, Boolean> loyalCol = new TableColumn<>("Loyal");
                loyalCol.setCellValueFactory(new PropertyValueFactory<>("loyal"));
                TableColumn<SpaceMarine, String> weaponTypeCol = new TableColumn<>("Weapon Type");
                weaponTypeCol.setCellValueFactory(cellData -> new SimpleStringProperty(
                    cellData.getValue().getWeaponType() != null ? cellData.getValue().getWeaponType().toString() : ""));
                TableColumn<SpaceMarine, String> meleeWeaponCol = new TableColumn<>("Melee Weapon");
                meleeWeaponCol.setCellValueFactory(cellData -> new SimpleStringProperty(
                    cellData.getValue().getMeleeWeapon() != null ? cellData.getValue().getMeleeWeapon().toString() : ""));
                TableColumn<SpaceMarine, String> coordXCol = new TableColumn<>("Coord X");
                coordXCol.setCellValueFactory(cellData -> new SimpleStringProperty(
                    cellData.getValue().getCoordinates() != null ? String.valueOf(cellData.getValue().getCoordinates().getX()) : ""));
                TableColumn<SpaceMarine, String> coordYCol = new TableColumn<>("Coord Y");
                coordYCol.setCellValueFactory(cellData -> new SimpleStringProperty(
                    cellData.getValue().getCoordinates() != null && cellData.getValue().getCoordinates().getY() != null ? String.valueOf(cellData.getValue().getCoordinates().getY()) : ""));
                TableColumn<SpaceMarine, String> chapterNameCol = new TableColumn<>("Chapter Name");
                chapterNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(
                    cellData.getValue().getChapter() != null ? cellData.getValue().getChapter().getName() : ""));
                TableColumn<SpaceMarine, String> chapterWorldCol = new TableColumn<>("Chapter World");
                chapterWorldCol.setCellValueFactory(cellData -> new SimpleStringProperty(
                    cellData.getValue().getChapter() != null ? cellData.getValue().getChapter().getWorld() : ""));
                filteredTable.getColumns().addAll(idCol, loginCol, nameCol, creationDateCol, healthCol, loyalCol, weaponTypeCol, meleeWeaponCol, coordXCol, coordYCol, chapterNameCol, chapterWorldCol);
                filteredTable.getItems().setAll(filtered);
                filteredTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                filteredTable.setPrefWidth(main_table.getWidth());
                filteredTable.setPrefHeight(main_table.getHeight());
                VBox box = new VBox(filteredTable);
                box.setPadding(new javafx.geometry.Insets(20));
                VBox.setVgrow(filteredTable, javafx.scene.layout.Priority.ALWAYS);
                Stage dialog = new Stage();
                dialog.setTitle("Filter Result");
                Scene scene = new Scene(box, main_table.getWidth(), main_table.getHeight());
                dialog.setScene(scene);
                dialog.initOwner(main_command_box.getScene().getWindow());
                // Закрываем окно ввода параметров фильтрации
                Stage filterStage = (Stage) main_command_box.getScene().getWindow();
                dialog.show();
                // Можно закрыть filterStage, если оно не главное окно
            });
        } else if (selected.equals("Info")) {
            try {
                org.example.collectionClasses.commands.InfoCommand cmd = new org.example.collectionClasses.commands.InfoCommand();
                String[] tokens = {"info"};
                var answer = ClientApp.processCommandFromGUI(cmd, tokens);
                showScrollableInfoDialog("Info", answer.toString());
            } catch (Exception e) {
                showInfoDialog("Error", e.getMessage());
            }
        } else if (selected.equals("Remove First")) {
            try {
                org.example.collectionClasses.commands.RemoveFirstCommand cmd = new org.example.collectionClasses.commands.RemoveFirstCommand();
                String[] tokens = {"remove_first"};
                var answer = ClientApp.processCommandFromGUI(cmd, tokens);
                showInfoDialog("Remove First", answer.toString());
                updateCollection();
            } catch (Exception e) {
                showInfoDialog("Error", e.getMessage());
            }
        } else if (selected.equals("Remove Greater")) {
            openMarineDialog(marine -> {
                if (marine == null) return;
                try {
                    org.example.collectionClasses.commands.RemoveGreater cmd = new org.example.collectionClasses.commands.RemoveGreater();
                    cmd.setSpaceMarine(marine);
                    String[] tokens = {"remove_greater"};
                    var answer = ClientApp.processCommandFromGUI(cmd, tokens);
                    showScrollableInfoDialog("Remove Greater", answer.toString());
                    updateCollection();
                } catch (Exception e) {
                    showInfoDialog("Error", e.getMessage());
                }
            });
        } else if (selected.equals("Remove Head")) {
            try {
                org.example.collectionClasses.commands.RemoveHeadCommand cmd = new org.example.collectionClasses.commands.RemoveHeadCommand();
                String[] tokens = {"remove_head"};
                var answer = ClientApp.processCommandFromGUI(cmd, tokens);
                showScrollableInfoDialog("Remove Head", answer.toString());
                updateCollection();
            } catch (Exception e) {
                showInfoDialog("Error", e.getMessage());
            }
        }
        if (main_command_box != null) {
            main_command_box.getItems().addAll("Remove Head");
        }
    }

    private void showInfoDialog(String title, String message) {
        Platform.runLater(() -> {
            Stage dialog = new Stage();
            dialog.setTitle(title);
            VBox box = new VBox(new Text(message));
            box.setPadding(new javafx.geometry.Insets(20));
            dialog.setScene(new Scene(box));
            dialog.initOwner(main_command_box.getScene().getWindow());
            dialog.showAndWait();
        });
    }

    private void showScrollableInfoDialog(String title, String message) {
        Platform.runLater(() -> {
            Stage dialog = new Stage();
            dialog.setTitle(title);
            javafx.scene.control.TextArea textArea = new javafx.scene.control.TextArea(message);
            textArea.setWrapText(true);
            textArea.setEditable(false);
            textArea.setPrefWidth(500);
            textArea.setPrefHeight(400);
            VBox box = new VBox(textArea);
            box.setPadding(new javafx.geometry.Insets(20));
            dialog.setScene(new Scene(box));
            dialog.initOwner(main_command_box.getScene().getWindow());
            dialog.showAndWait();
        });
    }

    // Универсальный диалог для ввода десантника (для RemoveGreater и других команд)
    private void openMarineDialog(java.util.function.Consumer<SpaceMarine> onResult) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddMarineDialog.fxml"));
            VBox dialogRoot = loader.load();
            AddMarineDialogController dialogController = loader.getController();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("SpaceMarine Input");
            dialogStage.setScene(new Scene(dialogRoot));
            dialogStage.initOwner(main_command_box.getScene().getWindow());
            dialogStage.showAndWait();
            SpaceMarine newMarine = dialogController.getResult();
            onResult.accept(newMarine);
        } catch (Exception e) {
            // Можно добавить вывод ошибки
        }
    }
}
