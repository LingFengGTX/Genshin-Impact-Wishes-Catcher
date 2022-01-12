package com.miuul.Runtime;

import com.miuul.Analyze.*;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import com.miuul.javafx.MessageBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindow implements Initializable{
    private DataPageClient MainPage=null;
    private Thread processThread=null;
    public static PageNavigate.WishedType Wtype= PageNavigate.WishedType.limit;
    public static long delayTime=0;
    public static Stage MainStage=null;

    @FXML private Label echoLabel=null;
    @FXML private TextArea URLBox=null;
    @FXML private ComboBox WishedTypeBox=null;
    @FXML private Button btn_StartButton=null;
    @FXML private Button btn_StopButton=null;
    @FXML private ComboBox SearchDelaySelector=null;
    @FXML private TitledPane TabControl=null;
    @FXML private MenuButton combo_URLBox=null;

    @Override public void initialize(URL location, ResourceBundle resources) {
        this.WishedTypeBox.getSelectionModel().select(0);
        this.SearchDelaySelector.getSelectionModel().select(2);
    }


    @FXML private void btn_StartButton_Click(ActionEvent e){
        if(this.URLBox.getText().length()==0){
            MessageBox.Show("当前 URL 信息为空。","错误", MessageBox.DialogType.Error);
            return;
        }

        this.delayTime=Long.valueOf(this.SearchDelaySelector.getItems().get(this.SearchDelaySelector.getSelectionModel().getSelectedIndex()).toString());
        processThread=new Thread(new LaunchProcessClass());
        processThread.setDaemon(true);
        processThread.start();
    }

    private boolean IsWindows(){
        return (System.getProperties().getProperty("os.name").toUpperCase().indexOf("WINDOWS")!=-1);
    }

    private class LaunchProcessClass implements Runnable{
        private void SetState(boolean v){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    btn_StartButton.setDisable(!v);
                    btn_StopButton.setDisable(v);
                    echoLabel.setVisible(!v);
                    TabControl.setDisable(!v);
                    URLBox.setEditable(v);
                    combo_URLBox.setDisable(!v);
                }
            });
        }

        @Override
        public void run(){
            this.SetState(false);
            try {
                PageNavigate.InitWebPage(MainPage=new DataPageClient(), URLBox.getText(), ClientType.defaultType, delayTime);
                Thread.sleep(delayTime);
                PageNavigate.selectWished(MainPage,Wtype,delayTime);
                PageAnalyze getter=new PageAnalyze(MainPage,delayTime);
                getter.whileAnalyzeFullPage();
                DataWindow.DataPool= getter.getWishedClass();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Stage dataWindowStage=new Stage();
                            dataWindowStage.initOwner(MainStage);
                            dataWindowStage.initModality(Modality.WINDOW_MODAL);
                            dataWindowStage.setScene(new Scene((Parent) javafx.fxml.FXMLLoader.load(getClass().getResource("DataWindow.fxml")),600,400));
                            dataWindowStage.setResizable(false);
                            dataWindowStage.setTitle("数据窗口");
                            DataWindow.thisStage=dataWindowStage;
                            dataWindowStage.show();
                        }catch(Exception exp){
                            MessageBox.Show(exp.toString(),"错误", MessageBox.DialogType.Error);
                            return;
                        }

                    }
                });
                this.SetState(true);
            }catch (InterruptedException exp) {/*手动暂停时则只打印消息并不弹窗警告。*/exp.printStackTrace();}
            catch(Exception exp){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        MessageBox.Show(exp.toString(),"错误", MessageBox.DialogType.Error);
                    }
                });
                this.SetState(true);
                return;
            }
        }
    }

    @FXML private void btn_StopButton(ActionEvent e){
            this.processThread.interrupt();
            this.processThread=null;
        {
            this.btn_StartButton.setDisable(false);
            this.btn_StopButton.setDisable(true);
            this.echoLabel.setVisible(false);
            this.TabControl.setDisable(false);
            this.URLBox.setEditable(true);
            this.combo_URLBox.setDisable(false);
        }
    }

    @FXML private void btn_ClearURLBox(ActionEvent e){
        this.URLBox.setText("");
    }

    @FXML private void TypeChanged(ActionEvent e){
        switch(this.WishedTypeBox.getSelectionModel().getSelectedIndex()){
            case 0:{this.Wtype= PageNavigate.WishedType.limit;};break;
            case 1:{this.Wtype= PageNavigate.WishedType.weapon;};break;
            case 2:{this.Wtype= PageNavigate.WishedType.longTime;};break;
            case 3:{this.Wtype= PageNavigate.WishedType.newPlayer;};break;
        }
    }

    @FXML private void btn_LoadURLFromGameLogFile(ActionEvent e){
        if(!this.IsWindows()){
            MessageBox.Show("此功能只针对 Windows。","错误", MessageBox.DialogType.Error);
            return;
        }
    }

    @FXML private void btn_LoadURLFromChooseLogFile(ActionEvent e){
        FileChooser tempChooser=new FileChooser();
        tempChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files","*.*"));
        File chosed=tempChooser.showOpenDialog(MainStage);
        if(chosed==null){
            //如果没有选择任何文件则终止方法
            return;
        }
        this.URLBox.setText("");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try{
                    URLBox.setText(com.miuul.Data.In.File.ReadAntherKeyFromFile(chosed.getAbsolutePath()));
                }catch (Exception exp){
                    MessageBox.Show(exp.toString(),"错误", MessageBox.DialogType.Error);
                    return;
                }

            }
        });
    }

    @FXML private void btn_GetHelp(ActionEvent e){

        if(!Version.isCommandVersion){
            MessageBox.Show("窗体版不再提供命令行帮助，如有需要请使用命令版。","提示", MessageBox.DialogType.Information);
            return;
        }

        try {
            HelpGui.showDialogWithApplicationThread(MainStage);
        }catch(Exception exp){
            exp.printStackTrace();
            return;
        }

    }
    @FXML private void btn_About(ActionEvent e){
        try {
            AboutGui.showDialogWithApplicationThread(MainStage);
        }catch(Exception exp){
            exp.printStackTrace();
            return;
        }
    }

    @FXML private void btn_LoadURL_FromFile(ActionEvent e){
        FileChooser tempChoser=new FileChooser();
        tempChoser.setTitle("选择包含URL信息的文件");
        File filePoint=tempChoser.showOpenDialog(MainStage);
        if(filePoint==null){
            return;
        }
        try{
            this.URLBox.setText(com.miuul.Data.In.File.ReadStringFromFile(filePoint.toString()));
        }catch(Exception exp){
            MessageBox.Show(exp.toString(),"错误", MessageBox.DialogType.Error);
            return;
        }
    }
}
