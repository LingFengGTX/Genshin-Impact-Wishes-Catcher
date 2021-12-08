package com.miuul.Runtime;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;


public class HelpWindow implements Initializable {

    public static Stage thisStage=null;

    @FXML private TextArea eachoBox=null;

    @Override public void initialize(URL location, ResourceBundle resources) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                    eachoBox.setText(Version.getHelpString());
                    }catch(Exception exp){
                        System.err.println(exp.toString());
                    }
                }
            });
    }

    @FXML private void btn_OK(ActionEvent e){
        thisStage.close();
    }
}
