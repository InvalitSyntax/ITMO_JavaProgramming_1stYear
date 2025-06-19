package org.example.gui;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import org.example.collectionClasses.model.SpaceMarine;

public class SpaceMarineViewController {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private Canvas marineCanvas;

    @FXML private SVGPath marineShape;
    private SpaceMarine marine;

    public void setMarine(SpaceMarine marine) {
        this.marine = marine;
        // Цвет по пользователю
        String color = getColorForUser(marine.getUserLogin());
        marineShape.setFill(Color.web(color));
        // Можно добавить отображение других свойств
    }

    @FXML
    public void initialize() {
        marineShape.setOnMouseClicked(this::handleClick);
    }

    private void handleClick(MouseEvent event) {
        if (marine != null) {
            // Показываем информацию о десантнике (можно заменить на диалог)
            System.out.println(marine);
        }
    }

    private String getColorForUser(String user) {
        // Простейший способ: цвет по хешу логина
        int hash = user != null ? user.hashCode() : 0;
        int r = 100 + Math.abs(hash) % 156;
        int g = 100 + Math.abs(hash / 10) % 156;
        int b = 100 + Math.abs(hash / 100) % 156;
        return String.format("#%02x%02x%02x", r, g, b);
    }
}
