package org.example.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.example.client.ClientApp;
import org.example.client.ClientMain;
import org.example.collectionClasses.commands.Answer;
import org.example.collectionClasses.model.SpaceMarine;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
    @FXML protected TableView<SpaceMarine> main_table;
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
    @FXML private Button showFieldButton;

    protected String userLogin;
    private Timeline updateTimeline;
    private final ContextMenu contextMenu = new ContextMenu();

    public void setUserLogin(String login) {
        this.userLogin = login;
        if (main_user_info != null) {
            main_user_info.setText(AppResources.getCurrentLanguage().getCurrentUserLabel() + " " + login);
        }
    }

    public String getUserLogin() {
        return userLogin;
    }

    private void updateTexts() {
        showFieldButton.setText(AppResources.get("button.showField"));
        main_command_execute.setText(AppResources.get("button.execute"));
        main_command_box.setPromptText(AppResources.get("commandBox.prompt"));
        main_user_info.setText(AppResources.getCurrentLanguage().getCurrentUserLabel() + " " + userLogin);
        // Переводим команды в ComboBox
        // if (main_command_box != null) {
        //     main_command_box.getItems().setAll(
        //         AppResources.get("command.addElement"),
        //         AppResources.get("command.clearCollection"),
        //         AppResources.get("command.countByWeaponType"),
        //         AppResources.get("command.countLessThanLoyal"),
        //         AppResources.get("command.executeScript"),
        //         AppResources.get("command.filterLessThanChapter"),
        //         AppResources.get("command.info"),
        //         AppResources.get("command.removeFirst"),
        //         AppResources.get("command.removeGreater"),
        //         AppResources.get("command.removeHead")
        //     );
        // }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTableColumns();
        setupUserInfo();
        setupCommandBox();
        setupTableContextMenu();
        updateTexts();
        // --- добавляем обработку смены языка ---
        main_language.getItems().clear();
        MenuItem ru = new MenuItem("Русский");
        MenuItem be = new MenuItem("Беларуская");
        MenuItem el = new MenuItem("Ελληνικά");
        MenuItem es = new MenuItem("Español (NI)");
        ru.setOnAction(e -> { AppResources.setLanguage(AppResources.Lang.RU); setupTableColumns(); updateTexts(); });
        be.setOnAction(e -> { AppResources.setLanguage(AppResources.Lang.BE); setupTableColumns(); updateTexts(); });
        el.setOnAction(e -> { AppResources.setLanguage(AppResources.Lang.EL); setupTableColumns(); updateTexts(); });
        es.setOnAction(e -> { AppResources.setLanguage(AppResources.Lang.ES_NI); setupTableColumns(); updateTexts(); });
        main_language.getItems().addAll(ru, be, el, es);
        // --- конец блока смены языка ---
        updateCollection();
        startCollectionUpdater();
        setInitialWindowSize();
        if (showFieldButton != null) {
            showFieldButton.setOnAction(e -> openFieldWindow());
        }
    }

    private void setupTableColumns() {
        main_id.setText(AppResources.get("table.id"));
        main_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        main_login.setText(AppResources.get("table.login"));
        main_login.setCellValueFactory(new PropertyValueFactory<>("userLogin"));
        main_name.setText(AppResources.get("table.name"));
        main_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        main_creation_date.setText(AppResources.get("table.creationDate"));
        main_creation_date.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        main_health.setText(AppResources.get("table.health"));
        main_health.setCellValueFactory(new PropertyValueFactory<>("health"));
        main_loyal.setText(AppResources.get("table.loyal"));
        main_loyal.setCellValueFactory(new PropertyValueFactory<>("loyal"));
        main_weapon_type.setText(AppResources.get("table.weaponType"));
        main_weapon_type.setCellValueFactory(new PropertyValueFactory<>("weaponType"));
        main_melee_weapon.setText(AppResources.get("table.meleeWeapon"));
        main_melee_weapon.setCellValueFactory(new PropertyValueFactory<>("meleeWeapon"));
        // Координаты
        TableColumn<SpaceMarine, String> coordXCol = new TableColumn<>(AppResources.get("table.coordX"));
        coordXCol.setCellValueFactory(cellData -> new SimpleStringProperty(
            cellData.getValue().getCoordinates() != null ? String.valueOf(cellData.getValue().getCoordinates().getX()) : ""));
        TableColumn<SpaceMarine, String> coordYCol = new TableColumn<>(AppResources.get("table.coordY"));
        coordYCol.setCellValueFactory(cellData -> new SimpleStringProperty(
            cellData.getValue().getCoordinates() != null && cellData.getValue().getCoordinates().getY() != null ? String.valueOf(cellData.getValue().getCoordinates().getY()) : ""));
        // Chapter
        TableColumn<SpaceMarine, String> chapterNameCol = new TableColumn<>(AppResources.get("table.chapterName"));
        chapterNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(
            cellData.getValue().getChapter() != null ? cellData.getValue().getChapter().getName() : ""));
        TableColumn<SpaceMarine, String> chapterWorldCol = new TableColumn<>(AppResources.get("table.chapterWorld"));
        chapterWorldCol.setCellValueFactory(cellData -> new SimpleStringProperty(
            cellData.getValue().getChapter() != null ? cellData.getValue().getChapter().getWorld() : ""));
        main_table.getColumns().setAll(
            main_id, main_login, main_name, main_creation_date, main_health, main_loyal, main_weapon_type, main_melee_weapon,
            coordXCol, coordYCol, chapterNameCol, chapterWorldCol
        );
        main_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void setupUserInfo() {
        main_user_info.setText(AppResources.getCurrentLanguage().getCurrentUserLabel() + " " + userLogin);
    }

    private void setupCommandBox() {
        if (main_command_box != null) {
            main_command_box.getItems().addAll(
                "Add Element", "Clear Collection", "Count By Weapon Type", "Count Less Than Loyal", "Filter Less Than Chapter", "Info", "Remove First", "Remove Greater", "Remove Head"
            );
            main_command_box.valueProperty().addListener((obs, oldVal, newVal) -> {
                // ...логика для loyalCheckBox, если нужно...
            });
        }
        if (main_command_execute != null) {
            main_command_execute.setOnAction(event -> handleCommandExecute());
        }
    }

    private void setupTableContextMenu() {
        main_table.setRowFactory(tv -> {
            TableRow<SpaceMarine> row = new TableRow<>();
            row.setOnMouseClicked(event -> handleRowRightClick(event, row));
            return row;
        });
    }

    private void setInitialWindowSize() {
        if (main_table != null && main_table.getScene() != null) {
            Stage stage = (Stage) main_table.getScene().getWindow();
            if (stage != null) {
                stage.setWidth(2000);
                stage.setHeight(1000);
            }
        }
    }

    protected void handleRowRightClick(MouseEvent event, TableRow<SpaceMarine> row) {
        if (!row.isEmpty() && event.getButton() == MouseButton.SECONDARY) {
            SpaceMarine tableItem = row.getItem();
            main_table.getSelectionModel().select(tableItem);
            if (tableItem.getUserLogin() != null && tableItem.getUserLogin().equals(userLogin)) {
                setupContextMenu();
            } else {
                setupEmptyContextMenu();
            }
            contextMenu.show(row, event.getScreenX(), event.getScreenY());
        } else {
            contextMenu.hide();
        }
    }

    private void setupContextMenu() {
        MenuItem editItem = new MenuItem(AppResources.get("edit"));
        MenuItem deleteItem = new MenuItem(AppResources.get("remove"));
        editItem.setOnAction(event -> handleEditSelectedMarine());
        deleteItem.setOnAction(event -> handleDeleteSelectedMarine());
        contextMenu.getItems().setAll(editItem, deleteItem);
    }

    private void setupEmptyContextMenu() {
        MenuItem item = new MenuItem(AppResources.get("not your marine"));
        contextMenu.getItems().setAll(item);
    }

    public void handleEditSelectedMarine() {
        SpaceMarine marine = main_table.getSelectionModel().getSelectedItem();
        if (marine != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddMarineDialog.fxml"));
                VBox dialogRoot = loader.load();
                AddMarineDialogController dialogController = loader.getController();
                dialogController.setInitialValues(marine);
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Edit SpaceMarine");
                dialogStage.setScene(new Scene(dialogRoot));
                dialogStage.initOwner(main_command_box.getScene().getWindow());
                dialogStage.showAndWait();
                SpaceMarine editedMarine = dialogController.getResult();
                if (editedMarine != null) {
                    org.example.collectionClasses.commands.RemoveByIdCommand removeCmd = new org.example.collectionClasses.commands.RemoveByIdCommand();
                    String[] removeTokens = {"remove_by_id", String.valueOf(marine.getId())};
                    ClientApp.processCommandFromGUI(removeCmd, removeTokens);
                    org.example.collectionClasses.commands.AddCommand addCmd = new org.example.collectionClasses.commands.AddCommand();
                    addCmd.spaceMarine = editedMarine;
                    String[] addTokens = {"add"};
                    ClientApp.processCommandFromGUI(addCmd, addTokens);
                    updateCollection();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void handleDeleteSelectedMarine() {
        SpaceMarine marine = main_table.getSelectionModel().getSelectedItem();
        if (marine != null) {
            org.example.collectionClasses.commands.RemoveByIdCommand cmd = new org.example.collectionClasses.commands.RemoveByIdCommand();
            String[] tokens = {"remove_by_id", String.valueOf(marine.getId())};
            try {
                ClientApp.processCommandFromGUI(cmd, tokens);
            } catch (Exception e) {
                e.printStackTrace();
            }
            updateCollection();
        }
    }

    private void startCollectionUpdater() {
        updateTimeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> updateCollection()));
        updateTimeline.setCycleCount(Timeline.INDEFINITE);
        updateTimeline.play();
    }

    private void updateCollection() {
        try {
            var answer = ClientApp.getCollectionFromServer();
            List<SpaceMarine> collection = answer.getCollection();
            if (collection != null) {
                if (!new HashSet<>(collection).equals(new HashSet<>(main_table.getItems()))) {
                    SpaceMarine selected = main_table.getSelectionModel().getSelectedItem();
                    main_table.getItems().setAll(collection);
                    main_table.sort();
                    if (selected != null) {
                        main_table.getSelectionModel().select(selected);
                    }
                }
            }
        } catch (Exception e) {
            // Можно добавить вывод ошибки в main_user_info или лог
        }
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

    private void openChapterDialog(java.util.function.Consumer<org.example.collectionClasses.model.Chapter> onResult) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle(AppResources.get("chapter.inputTitle"));
        VBox vbox = new VBox(10);
        vbox.setPadding(new javafx.geometry.Insets(20));
        TextField nameField = new TextField();
        nameField.setPromptText(AppResources.get("marine.chapterName") + " (" + AppResources.get("required") + ")");
        TextField worldField = new TextField();
        worldField.setPromptText(AppResources.get("marine.chapterWorld") + " (" + AppResources.get("required") + ")");
        Text errorText = new Text();
        errorText.setStyle("-fx-fill: red;");
        Button okBtn = new Button(AppResources.get("button.accept"));
        okBtn.setOnAction(e -> {
            String name = nameField.getText();
            String world = worldField.getText();
            if (name == null || name.isEmpty()) {
                errorText.setText(AppResources.get("error.emptyChapterName"));
                return;
            }
            if (world == null) {
                errorText.setText(AppResources.get("error.emptyChapterWorld"));
                return;
            }
            org.example.collectionClasses.model.Chapter chapter = new org.example.collectionClasses.model.Chapter(name, world);
            onResult.accept(chapter);
            dialogStage.close();
        });
        Button cancelBtn = new Button(AppResources.get("button.cancel"));
        cancelBtn.setOnAction(e -> dialogStage.close());
        vbox.getChildren().addAll(new Label(AppResources.get("chapter.inputLabel")),
            new Label(AppResources.get("marine.chapterName")), nameField,
            new Label(AppResources.get("marine.chapterWorld")), worldField, errorText, new HBox(10, okBtn, cancelBtn));
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
                filteredTable.getColumns().addAll(idCol, loginCol, nameCol, creationDateCol, healthCol, loyalCol, weaponTypeCol, meleeWeaponCol, coordXCol, coordYCol);
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
            dialog.setTitle(AppResources.get(title));
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
            dialog.setTitle(AppResources.get(title));
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

    private void openFieldWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SpaceFieldView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Space Field");
            stage.setScene(new Scene(loader.load()));
            SpaceFieldController controller = loader.getController();
            controller.setMainPageController(this);
            // Передаём текущую коллекцию
            List<SpaceMarine> marines = main_table.getItems();
            controller.showMarines(marines);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TableView<SpaceMarine> getMainTable() {
        return main_table;
    }
}
