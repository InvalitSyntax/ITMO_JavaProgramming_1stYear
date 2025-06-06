package org.example.gui;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

// https://habr.com/ru/articles/474498/

public class AppFX extends Application {
    
    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // FXMLLoader loader = new FXMLLoader();
        // URL xmlUrl = getClass().getResource("mainScene.fxml");
        // loader.setLocation(xmlUrl);
        // Parent root = loader.load();

        // Scene scene = new Scene(root);
        

        // primaryStage.setScene(scene);
        // primaryStage.setWidth(300);
        // primaryStage.setHeight(200);
        // primaryStage.show();

        
        
        StackPane loaded = FXMLLoader.load(getClass().getResource("/SecondScene.fxml"));
        StackPane parent = new StackPane(loaded);
        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}