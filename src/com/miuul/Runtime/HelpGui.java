package com.miuul.Runtime;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class HelpGui extends Application {

    public static void show() {
        launch(null);
    }

    public static void showDialogWithApplicationThread(Stage Parent) throws IOException {
        Stage winStage=new Stage();
        HelpWindow.thisStage=winStage;
        winStage.setScene(new Scene((Parent) FXMLLoader.load(HelpGui.class.getResource("HelpWindow.fxml")), 300, 360));
        winStage.setResizable(false);
        winStage.setTitle("帮助");
        winStage.initOwner(Parent);
        winStage.initModality(Modality.WINDOW_MODAL);
        winStage.show();
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
