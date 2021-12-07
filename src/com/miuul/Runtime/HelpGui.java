package com.miuul.Runtime;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelpGui extends Application {

    public static void show() {
        launch(null);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            HelpWindow.thisStage=primaryStage;
            primaryStage.setScene(new Scene((Parent)FXMLLoader.load(getClass().getResource("HelpWindow.fxml")),300,360));
            primaryStage.setTitle("帮助");
            primaryStage.setResizable(false);
            primaryStage.show();
        }catch(Exception exp){
            System.err.print(exp.toString());
            return;
        }
    }
}