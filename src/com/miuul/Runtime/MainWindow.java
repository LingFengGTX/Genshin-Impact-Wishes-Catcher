package com.miuul.Runtime;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.miuul.Analyze.PageAnalyze;
import com.miuul.Data.PageNavigate;
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
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindow implements Initializable{
    public static Stage MainStage=null;

    private HtmlPage MainPage=null;
    private WebClient PageBroker=null;
    private PageNavigate.WishedType Wtype= PageNavigate.WishedType.limit;
    private long delayTime=0;
    private Thread processThread=null;

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
            PageBroker=new WebClient();
            try {
                MainPage=com.miuul.Data.PageNavigate.StartWebClient(PageBroker, URLBox.getText(), BrowserVersion.BEST_SUPPORTED, delayTime);
                Thread.sleep(delayTime);
                MainPage=com.miuul.Data.PageNavigate.selectWished(MainPage,Wtype,delayTime);
                PageAnalyze getter=new PageAnalyze(MainPage,delayTime);
                getter.whileAnalyzeFullPage();
                DataWindow.DataPool= getter.GetWishedClass();
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


    @FXML private void btn_LoadURL_FromFile(ActionEvent e){
        FileChooser tempChoser=new FileChooser();
        tempChoser.setTitle("选择包含URL信息的文件");
        File filePoint=tempChoser.showOpenDialog(MainStage);
        if(filePoint==null){
            return;
        }
        try{
            FileInputStream fileInputer=new FileInputStream(filePoint.toString());
            String ContentBuffer="";
            byte[] buffer=new byte[10240];
            int flag=0;
            while((flag=fileInputer.read(buffer))!=-1){
                ContentBuffer+=new String(buffer, 0, flag);
            }
            this.URLBox.setText(ContentBuffer);
        }catch(Exception exp){
            MessageBox.Show(exp.toString(),"错误", MessageBox.DialogType.Error);
            return;
        }
    }
}
