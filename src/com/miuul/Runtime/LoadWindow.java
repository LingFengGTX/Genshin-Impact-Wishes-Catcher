package com.miuul.Runtime;

import com.miuul.Analyze.ClientType;
import com.miuul.Analyze.DataPageClient;
import com.miuul.Analyze.PageAnalyze;
import com.miuul.Analyze.PageNavigate;
import com.miuul.javafx.MessageBox;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoadWindow implements Initializable {
    public static Stage thisStage=null;
    private DataPageClient MainPage=null;
    private Thread mainProcess=null;
    public static Stage TempWindowStage=null;

    public static String URL=null;
    public static PageNavigate.WishedType Wtype= PageNavigate.WishedType.limit;
    public static long delayTime=0;

    @FXML
    private void btn_Cancel(ActionEvent arg){
        this.mainProcess.interrupt();
        System.exit(-1);
    }

    private class LaunchProcessClass implements Runnable{
        @Override
        public void run(){
            try {
                PageNavigate.InitWebPage(MainPage=new DataPageClient(), URL, ClientType.defaultType, delayTime);
                Thread.sleep(LoadWindow.delayTime);
                PageNavigate.selectWished(MainPage,Wtype,delayTime);
                PageAnalyze getter=new PageAnalyze(MainPage,delayTime);
                getter.whileAnalyzeFullPage();
                DataWindow.DataPool= getter.getWishedClass();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Stage dataWindowStage=new Stage();
                            dataWindowStage.initModality(Modality.WINDOW_MODAL);
                            dataWindowStage.setScene(new Scene((Parent) javafx.fxml.FXMLLoader.load(getClass().getResource("DataWindow.fxml")),600,400));
                            dataWindowStage.setResizable(false);
                            dataWindowStage.setTitle("数据窗口");
                            DataWindow.thisStage=dataWindowStage;
                            dataWindowStage.show();
                            LoadWindow.thisStage.close();
                        }catch(Exception exp){
                            MessageBox.Show(exp.toString(),"错误", MessageBox.DialogType.Error);
                            return;
                        }

                    }
                });
            }catch (InterruptedException exp) {/*手动暂停时则只打印消息并不弹窗警告。*/exp.printStackTrace();}
            catch(Exception exp){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        MessageBox.Show(exp.toString(),"错误", MessageBox.DialogType.Error);
                    }
                });
                return;
            }
        }
    }

    @Override public void initialize(URL location, ResourceBundle resources) {
        this.mainProcess=new Thread(new LoadWindow.LaunchProcessClass());
        this.mainProcess.setDaemon(true);
        this.mainProcess.start();
    }
}
