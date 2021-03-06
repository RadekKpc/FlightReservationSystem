package com.wesolemarcheweczki.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    private static Stage primaryStage;
    private double xOffset = 0;
    private double yOffset = 0;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.setPrimaryStage(primaryStage);
        Parent root = FXMLLoader.load(getClass().getResource("/views/LoginRegister.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.DECORATED);
        scene.getStylesheets().add("/css/Home.css");
        scene.getStylesheets().add("/css/AddClient.css");

        root.setOnMousePressed((event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged((event) -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });


        primaryStage.show();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage pStage) {
        Main.primaryStage = pStage;
    }

    public static void setScene(Scene scene) {
        Main.primaryStage.setScene(scene);
    }
}
