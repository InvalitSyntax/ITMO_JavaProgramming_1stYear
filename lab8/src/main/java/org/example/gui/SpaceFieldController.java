package org.example.gui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.example.collectionClasses.model.SpaceMarine;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class SpaceFieldController {
    @FXML private Pane marinesPane;
    private List<SpaceMarine> lastMarines;
    private Timeline updateTimeline;
    private org.example.gui.MainPageController mainPageController;

    public void setMainPageController(org.example.gui.MainPageController controller) {
        this.mainPageController = controller;
    }

    public void showMarines(List<SpaceMarine> marines) {
        boolean changed = false;
        if (lastMarines == null || lastMarines.size() != marines.size()) {
            changed = true;
        } else {
            for (int i = 0; i < marines.size(); i++) {
                SpaceMarine m1 = marines.get(i);
                SpaceMarine m2 = lastMarines.get(i);
                if (m1.getId() != m2.getId() ||
                    !Objects.equals(m1.getCoordinates(), m2.getCoordinates()) ||
                    m1.getHealth() != m2.getHealth()) {
                    changed = true;
                    break;
                }
            }
        }
        this.lastMarines = marines;
        if (changed) {
            updateMarinesWithAnimation();
        } else {
            updateMarinesWithoutAnimation();
        }
        if (updateTimeline == null) {
            updateTimeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> showMarines(this.lastMarines)));
            updateTimeline.setCycleCount(Timeline.INDEFINITE);
            updateTimeline.play();
        }
    }

    private void updateMarinesWithAnimation() {
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
                double x = center + (marine.getCoordinates().getX() / coordMax) * (center - maxSize/2);
                double y = center - (marine.getCoordinates().getY() / coordMax) * (center - maxSize/2);
                double health = Math.max(minHealth, Math.min(maxHealth, marine.getHealth()));
                double size = minSize + (health - minHealth) / (maxHealth - minHealth) * (maxSize - minSize);
                // Начальные значения для анимации
                marineNode.setLayoutX(center - size/2);
                marineNode.setLayoutY(center - size/2);
                marineNode.setScaleX(0.1);
                marineNode.setScaleY(0.1);
                marinesPane.getChildren().add(marineNode);
                // Анимация перемещения и увеличения
                Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(0),
                        new KeyValue(marineNode.layoutXProperty(), center - size/2),
                        new KeyValue(marineNode.layoutYProperty(), center - size/2),
                        new KeyValue(marineNode.scaleXProperty(), 0.1),
                        new KeyValue(marineNode.scaleYProperty(), 0.1)
                    ),
                    new KeyFrame(Duration.seconds(0.7),
                        new KeyValue(marineNode.layoutXProperty(), x - size/2),
                        new KeyValue(marineNode.layoutYProperty(), y - size/2),
                        new KeyValue(marineNode.scaleXProperty(), size / maxSize),
                        new KeyValue(marineNode.scaleYProperty(), size / maxSize)
                    )
                );
                timeline.play();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateMarinesWithoutAnimation() {
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
