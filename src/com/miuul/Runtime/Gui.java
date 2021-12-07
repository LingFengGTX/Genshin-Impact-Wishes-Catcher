package com.miuul.Runtime;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Gui extends Application {

    public static void show() {
        launch(null);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            MainWindow.MainStage=primaryStage;
            Parent WindowParent = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
            primaryStage.setScene(new Scene(WindowParent,600,450));
            primaryStage.setTitle("Genshin Impact Wishes Catcher");
            primaryStage.setResizable(false);
            primaryStage.show();

        }catch(Exception exp){
            System.err.print(exp.toString());
            return;
        }
    }
}
