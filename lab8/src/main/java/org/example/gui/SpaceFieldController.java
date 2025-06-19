package org.example.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import org.example.collectionClasses.model.SpaceMarine;
import java.io.IOException;
import java.util.List;

public class SpaceFieldController {
    @FXML private Pane marinesPane;

    public void showMarines(List<SpaceMarine> marines) {
        marinesPane.getChildren().clear();
        for (SpaceMarine marine : marines) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/SpaceMarineView.fxml"));
                Node marineNode = loader.load();
                SpaceMarineViewController controller = loader.getController();
                controller.setMarine(marine);
                // Пример: координаты и размер
                double x = marine.getCoordinates().getX();
                double y = marine.getCoordinates().getY();
                double size = Math.max(40, Math.min(120, marine.getHealth()));
                marineNode.setLayoutX(x);
                marineNode.setLayoutY(y);
                marineNode.setScaleX(size / 120.0);
                marineNode.setScaleY(size / 120.0);
                marinesPane.getChildren().add(marineNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
