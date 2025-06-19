package org.example.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import org.example.collectionClasses.model.*;

public class AddMarineDialogController {
    @FXML private Text infoText;
    @FXML private TextField nameField;
    @FXML private TextField coordXField;
    @FXML private TextField coordYField;
    @FXML private TextField healthField;
    @FXML private CheckBox loyalCheck;
    @FXML private ComboBox<Weapon> weaponTypeBox;
    @FXML private ComboBox<MeleeWeapon> meleeWeaponBox;
    @FXML private TextField chapterNameField;
    @FXML private TextField chapterWorldField;
    @FXML private Button addButton;
    @FXML private Button cancelButton;
    @FXML private Text nameError;
    @FXML private Text coordXError;
    @FXML private Text coordYError;
    @FXML private Text healthError;
    @FXML private Text weaponTypeError;
    @FXML private Text meleeWeaponError;
    @FXML private Text chapterNameError;

    private SpaceMarine result;

    @FXML
    public void initialize() {
        addButton.setText("Accept");
        weaponTypeBox.getItems().setAll(Weapon.values());
        meleeWeaponBox.getItems().setAll(MeleeWeapon.values());
        addButton.setOnAction(e -> handleAdd());
        cancelButton.setOnAction(e -> ((Stage) addButton.getScene().getWindow()).close());
    }

    private void handleAdd() {
        boolean hasError = false;
        infoText.setText("");
        resetFieldStyles();
        nameError.setText("");
        coordXError.setText("");
        coordYError.setText("");
        healthError.setText("");
        weaponTypeError.setText("");
        meleeWeaponError.setText("");
        chapterNameError.setText("");
        try {
            String name = nameField.getText();
            if (name == null || name.isEmpty()) {
                setFieldError(nameField, nameError, "Name required");
                hasError = true;
            }
            Double x = null;
            if (!coordXField.getText().isEmpty()) {
                try {
                    x = Double.parseDouble(coordXField.getText());
                } catch (Exception e) {
                    setFieldError(coordXField, coordXError, "X must be a double");
                    hasError = true;
                }
            }
            float y = 0;
            try {
                y = Float.parseFloat(coordYField.getText());
                if (y <= -501) {
                    setFieldError(coordYField, coordYError, "Y > -501");
                    hasError = true;
                }
            } catch (Exception e) {
                setFieldError(coordYField, coordYError, "Y must be a float");
                hasError = true;
            }
            float health = 0;
            try {
                health = Float.parseFloat(healthField.getText());
                if (health <= 0) {
                    setFieldError(healthField, healthError, "Health > 0");
                    hasError = true;
                }
            } catch (Exception e) {
                setFieldError(healthField, healthError, "Health must be a float");
                hasError = true;
            }
            boolean loyal = loyalCheck.isSelected();
            Weapon weapon = weaponTypeBox.getValue(); // не обязателен
            MeleeWeapon melee = meleeWeaponBox.getValue(); // не обязателен
            String chapterName = chapterNameField.getText();
            String chapterWorld = chapterWorldField.getText();
            Chapter chapter = null;
            if ((chapterName != null && !chapterName.isEmpty()) || (chapterWorld != null && !chapterWorld.isEmpty())) {
                // Если хотя бы одно поле заполнено, то оба должны быть валидны
                if (chapterName == null || chapterName.isEmpty()) {
                    setFieldError(chapterNameField, chapterNameError, "Name required if chapter is set");
                    hasError = true;
                }
                if (chapterWorld == null) {
                    setFieldError(chapterWorldField, chapterNameError, "World required if chapter is set");
                    hasError = true;
                }
                if (!hasError) {
                    chapter = new Chapter(chapterName, chapterWorld);
                }
            }
            if (hasError) return;
            Coordinates coords = new Coordinates(x != null ? x : 0, y);
            SpaceMarine marine = new SpaceMarine();
            marine.setName(name);
            marine.setCoordinates(coords);
            marine.setHealth(health);
            marine.setLoyal(loyal);
            if (weapon != null) marine.setWeaponType(weapon);
            if (melee != null) marine.setMeleeWeapon(melee);
            marine.setChapter(chapter);
            this.result = marine;
            ((Stage) addButton.getScene().getWindow()).close();
        } catch (Exception ex) {
            infoText.setText("Ошибка: " + ex.getMessage());
        }
    }

    private void setFieldError(Control field, Text errorText, String message) {
        field.setStyle("-fx-border-color: red;");
        errorText.setText(message);
    }

    private void resetFieldStyles() {
        nameField.setStyle("");
        coordXField.setStyle("");
        coordYField.setStyle("");
        healthField.setStyle("");
        weaponTypeBox.setStyle("");
        meleeWeaponBox.setStyle("");
        chapterNameField.setStyle("");
        nameError.setText("");
        coordXError.setText("");
        coordYError.setText("");
        healthError.setText("");
        weaponTypeError.setText("");
        meleeWeaponError.setText("");
        chapterNameError.setText("");
    }

    public SpaceMarine getResult() {
        return result;
    }

    public void setInitialValues(SpaceMarine marine) {
        if (marine == null) return;
        nameField.setText(marine.getName());
        coordXField.setText(marine.getCoordinates() != null ? String.valueOf(marine.getCoordinates().getX()) : "");
        coordYField.setText(marine.getCoordinates() != null && marine.getCoordinates().getY() != null ? String.valueOf(marine.getCoordinates().getY()) : "");
        healthField.setText(String.valueOf(marine.getHealth()));
        loyalCheck.setSelected(marine.getLoyal());
        weaponTypeBox.setValue(marine.getWeaponType());
        meleeWeaponBox.setValue(marine.getMeleeWeapon());
        chapterNameField.setText(marine.getChapter() != null ? marine.getChapter().getName() : "");
        chapterWorldField.setText(marine.getChapter() != null ? marine.getChapter().getWorld() : "");
    }
}
