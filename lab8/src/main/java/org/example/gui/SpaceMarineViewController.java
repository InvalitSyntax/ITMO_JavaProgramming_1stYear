package org.example.gui;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import org.example.collectionClasses.model.SpaceMarine;

public class SpaceMarineViewController {
    @FXML private SVGPath marineShape;
    private SpaceMarine marine;
    private org.example.gui.MainPageController mainPageController;

    public void setMarine(SpaceMarine marine) {
        this.marine = marine;
        String color = getColorForUser(marine.getUserLogin());
        marineShape.setFill(Color.web(color));
    }

    public void setMainPageController(org.example.gui.MainPageController controller) {
        this.mainPageController = controller;
    }

    @FXML
    private void initialize() {
        marineShape.setOnMouseClicked(this::handleClick);
    }

    private void handleClick(MouseEvent event) {
        if (marine != null && mainPageController != null) {
            mainPageController.getMainTable().getSelectionModel().select(marine);
            ContextMenu contextMenu = new ContextMenu();
            String currentUser = mainPageController.userLogin;
            if (marine.getUserLogin() != null && marine.getUserLogin().equals(currentUser)) {
                MenuItem editItem = new MenuItem("edit");
                MenuItem deleteItem = new MenuItem("remove");
                editItem.setOnAction(e -> mainPageController.handleEditSelectedMarine());
                deleteItem.setOnAction(e -> mainPageController.handleDeleteSelectedMarine());
                contextMenu.getItems().addAll(editItem, deleteItem);
            } else {
                MenuItem notYourItem = new MenuItem("not your marine");
                contextMenu.getItems().add(notYourItem);
            }
            contextMenu.show(marineShape, event.getScreenX(), event.getScreenY());
        }
    }

    private String getColorForUser(String user) {
        int hash = user != null ? user.hashCode() : 0;
        int r = 100 + Math.abs(hash) % 156;
        int g = 100 + Math.abs(hash / 10) % 156;
        int b = 100 + Math.abs(hash / 100) % 156;
        return String.format("#%02x%02x%02x", r, g, b);
    }
}
