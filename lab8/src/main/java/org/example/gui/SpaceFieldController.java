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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SpaceFieldController {
    @FXML private Pane marinesPane;
    private List<SpaceMarine> lastMarines;
    private Timeline updateTimeline;
    private org.example.gui.MainPageController mainPageController;
    private Map<Integer, SpaceMarine> lastMarinesById = new HashMap<>();
    private boolean firstShow = true;

    public void setMainPageController(org.example.gui.MainPageController controller) {
        this.mainPageController = controller;
    }

    public void showMarines(List<SpaceMarine> marines) {
        Map<Integer, SpaceMarine> newMap = new HashMap<>();
        for (SpaceMarine m : marines) newMap.put(m.getId(), m);
        boolean animateAll = firstShow;
        firstShow = false;
        this.lastMarines = marines;
        if (animateAll) {
            updateMarinesWithAnimation(marines, null);
        } else {
            updateMarinesWithAnimation(marines, lastMarinesById);
        }
        lastMarinesById = newMap;
        if (updateTimeline == null) {
            updateTimeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> showMarines(this.lastMarines)));
            updateTimeline.setCycleCount(Timeline.INDEFINITE);
            updateTimeline.play();
        }
    }

    private void updateMarinesWithAnimation(List<SpaceMarine> marines, Map<Integer, SpaceMarine> prevMap) {
        // Удаляем исчезнувших
        marinesPane.getChildren().removeIf(node -> {
            Integer id = (Integer) node.getUserData();
            return marines.stream().noneMatch(m -> m.getId() == id);
        });
        double fieldSize = marinesPane.getPrefWidth();
        double coordMax = 200.0;
        double minSize = 30.0;
        double maxSize = 120.0;
        double minHealth = 1.0;
        double maxHealth = 10.0;
        double center = fieldSize / 2.0;
        for (SpaceMarine marine : marines) {
            Node existingNode = marinesPane.getChildren().stream()
                .filter(n -> marine.getId() == (n.getUserData() instanceof Integer ? (Integer) n.getUserData() : -1))
                .findFirst().orElse(null);
            double x = center + (marine.getCoordinates().getX() / coordMax) * (center - maxSize/2);
            double y = center - (marine.getCoordinates().getY() / coordMax) * (center - maxSize/2);
            double health = Math.max(minHealth, Math.min(maxHealth, marine.getHealth()));
            double size = minSize + (health - minHealth) / (maxHealth - minHealth) * (maxSize - minSize);
            boolean animate = false;
            if (prevMap == null) {
                animate = true;
            } else {
                SpaceMarine prev = prevMap.get(marine.getId());
                if (prev == null ||
                    prev.getHealth() != marine.getHealth() ||
                    !prev.getCoordinates().equals(marine.getCoordinates())) {
                    animate = true;
                }
            }
            if (existingNode == null) {
                // Новый десантник
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/SpaceMarineView.fxml"));
                    Node marineNode = loader.load();
                    SpaceMarineViewController controller = loader.getController();
                    controller.setMarine(marine);
                    controller.setMainPageController(mainPageController);
                    marineNode.setUserData(marine.getId());
                    if (animate) {
                        marineNode.setLayoutX(center - size/2);
                        marineNode.setLayoutY(center - size/2);
                        marineNode.setScaleX(0.1);
                        marineNode.setScaleY(0.1);
                        marinesPane.getChildren().add(marineNode);
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
                    } else {
                        marineNode.setLayoutX(x - size/2);
                        marineNode.setLayoutY(y - size/2);
                        marineNode.setScaleX(size / maxSize);
                        marineNode.setScaleY(size / maxSize);
                        marinesPane.getChildren().add(marineNode);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Уже существующий десантник
                if (animate) {
                    Timeline timeline = new Timeline(
                        new KeyFrame(Duration.seconds(0),
                            new KeyValue(existingNode.layoutXProperty(), existingNode.getLayoutX()),
                            new KeyValue(existingNode.layoutYProperty(), existingNode.getLayoutY()),
                            new KeyValue(existingNode.scaleXProperty(), existingNode.getScaleX()),
                            new KeyValue(existingNode.scaleYProperty(), existingNode.getScaleY())
                        ),
                        new KeyFrame(Duration.seconds(0.7),
                            new KeyValue(existingNode.layoutXProperty(), x - size/2),
                            new KeyValue(existingNode.layoutYProperty(), y - size/2),
                            new KeyValue(existingNode.scaleXProperty(), size / maxSize),
                            new KeyValue(existingNode.scaleYProperty(), size / maxSize)
                        )
                    );
                    timeline.play();
                } else {
                    existingNode.setLayoutX(x - size/2);
                    existingNode.setLayoutY(y - size/2);
                    existingNode.setScaleX(size / maxSize);
                    existingNode.setScaleY(size / maxSize);
                }
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
