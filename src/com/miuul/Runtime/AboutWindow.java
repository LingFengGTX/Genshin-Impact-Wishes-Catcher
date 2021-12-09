package com.miuul.Runtime;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.lang.management.ManagementFactory;
import javafx.scene.control.*;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class AboutWindow implements Initializable {
    @FXML
    private TextArea EchoBox=null;

    private void appendString(String comment,String value){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                EchoBox.appendText(String.format("%s\t%s\n",comment,value));
            }
        });

    }
    private void appendStatusString(String comment,String value){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(value.equals("")){
                    EchoBox.appendText(String.format("[%s]->[Null]\n",comment,value));
                }else{
                    EchoBox.appendText(String.format("[%s]->[%s]\n",comment,value));
                }
                EchoBox.positionCaret(0);
            }
        });

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.appendString("程序版本:",Version.VERSION);
        this.appendString("形式:",Version.isCommandVersion?"命令版":"图形版");
        this.appendString("JVM:",ManagementFactory.getRuntimeMXBean().getVmName());
        this.appendString("JVM版本",ManagementFactory.getRuntimeMXBean().getVmVersion());
        this.appendString("最后检查时间:",new java.util.Date().toString());

            this.appendString("\nJVM属性信息:","");
            for(Map.Entry<String,String> item: ManagementFactory.getRuntimeMXBean().getSystemProperties().entrySet()){
                this.appendStatusString(item.getKey(), item.getValue());
            }
    }
}
