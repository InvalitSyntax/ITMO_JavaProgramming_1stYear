package org.example.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.example.collectionClasses.model.SpaceMarine;

import java.io.IOException;
import java.util.List;

public class SpaceFieldController {
    @FXML private Pane marinesPane;
    private List<SpaceMarine> lastMarines;
    private Timeline updateTimeline;
    private org.example.gui.MainPageController mainPageController;

    public void setMainPageController(org.example.gui.MainPageController controller) {
        this.mainPageController = controller;
    }

    public void showMarines(List<SpaceMarine> marines) {
        this.lastMarines = marines;
        updateMarines();
        if (updateTimeline == null) {
            updateTimeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> updateMarines()));
            updateTimeline.setCycleCount(Timeline.INDEFINITE);
            updateTimeline.play();
        }
    }

    private void updateMarines() {
        marinesPane.getChildren().clear();
        double fieldSize = marinesPane.getPrefWidth();
        double coordMax = 200.0;
        double minSize = 30.0;
        double maxSize = 120.0;
        double minHealth = 1.0;
        double maxHealth = 10.0;
        double center = fieldSize / 2.0;
        if (lastMarines == null) return;
        for (SpaceMarine marine : lastMarines) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/SpaceMarineView.fxml"));
                Node marineNode = loader.load();
                SpaceMarineViewController controller = loader.getController();
                controller.setMarine(marine);
                controller.setMainPageController(mainPageController);
                // Координаты от центра: -200..+200 -> 0..800
                double x = center + (marine.getCoordinates().getX() / coordMax) * (center - maxSize/2);
                double y = center - (marine.getCoordinates().getY() / coordMax) * (center - maxSize/2);
                double health = Math.max(minHealth, Math.min(maxHealth, marine.getHealth()));
                double size = minSize + (health - minHealth) / (maxHealth - minHealth) * (maxSize - minSize);
                marineNode.setLayoutX(x - size/2);
                marineNode.setLayoutY(y - size/2);
                marineNode.setScaleX(size / maxSize);
                marineNode.setScaleY(size / maxSize);
                marinesPane.getChildren().add(marineNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
