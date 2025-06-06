package org.example.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;

// rotate transition, translate transition и остальные, там куча готовых методов


public class Controller implements Initializable {
    @FXML
    private StackPane mainRoot;
    @FXML
    private VBox firstV;
    @FXML
    private Text megaText;
    @FXML
    private Button megaButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        megaButton.setOnAction(this::printHuy);
    }

    public void printHuy(ActionEvent event) {
        System.out.println("Huy");
        megaText.setText("BlaBlaBla");

        try {


            //Timeline timeline = new Timeline();
            StackPane loaded = FXMLLoader.load(getClass().getResource("/ThirdScene.fxml"));
            Scene currentScene = megaButton.getScene();
            StackPane root = (StackPane) currentScene.getRoot();
            loaded.translateXProperty().set(currentScene.getWidth());
            root.getChildren().add(loaded);
            KeyValue keyValue = new KeyValue(loaded.translateXProperty(), 0, Interpolator.EASE_IN);
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), keyValue);
            Timeline timeline = new Timeline(keyFrame);

            
            timeline.setOnFinished(e -> {
                root.getChildren().remove(mainRoot);
                
            });
            timeline.play();
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }


    
}
