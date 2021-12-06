package com.miuul.Runtime;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;


public class HelpWindow implements Initializable {

    public static Stage thisStage=null;

    @FXML private TextArea eachoBox=null;

    @Override public void initialize(URL location, ResourceBundle resources) {

        try {
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("help.txt")));
            String ContentString = null;
            String helpString="";
            while ((ContentString = streamReader.readLine()) != null) {
                helpString+=(ContentString+"\n");
            }
            String finalHelpString = helpString;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    eachoBox.setText(finalHelpString);
                }
            });
        }catch(Exception exp){
            System.err.println(exp);
        }
    }

    @FXML private void btn_OK(ActionEvent e){
        thisStage.close();
    }
}
