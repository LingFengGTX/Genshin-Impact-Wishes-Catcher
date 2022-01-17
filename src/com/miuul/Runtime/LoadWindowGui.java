package com.miuul.Runtime;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import com.miuul.javafx.MessageBox;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;
import javafx.stage.Stage;
import com.miuul.Analyze.*;
import javafx.stage.WindowEvent;

public class LoadWindowGui extends Application {
    public static void show(String TURL,PageNavigate.WishedType Type,long delay) {
        LoadWindow.URL=TURL;
        LoadWindow.Wtype=Type;
        LoadWindow.delayTime=delay;
        launch(null);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            LoadWindow.thisStage=primaryStage;
            primaryStage.setScene(new Scene((Parent)FXMLLoader.load(getClass().getResource("LoadWindow.fxml")),370,170));
            primaryStage.setTitle("正在查询");
            primaryStage.initStyle(StageStyle.UTILITY);
            primaryStage.setResizable(false);
            primaryStage.show();
        }catch(Exception exp){
            System.err.print(exp.toString());
            return;
        }
    }

}
