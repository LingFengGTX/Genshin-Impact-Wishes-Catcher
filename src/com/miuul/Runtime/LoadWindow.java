package com.miuul.Runtime;

import com.miuul.Analyze.ClientType;
import com.miuul.Analyze.DataPageClient;
import com.miuul.Analyze.PageAnalyze;
import com.miuul.Analyze.PageNavigate;
import com.miuul.javafx.MessageBox;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.commons.lang3.ObjectUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class LoadWindow implements Initializable {
    public static Stage thisStage=null;
    private DataPageClient MainPage=null;
    private Thread mainProcess=null;
    public static boolean ExitWithAsk=true;
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
                            dataWindowStage.setTitle("????????????");
                            DataWindow.thisStage=dataWindowStage;
                            dataWindowStage.show();
                            LoadWindow.thisStage.close();
                        }catch(Exception exp){
                            MessageBox.Show(exp.toString(),"??????", MessageBox.DialogType.Error);
                            mainProcess.interrupt();
                            thisStage.close();
                        }

                    }
                });
            }catch (InterruptedException exp) {/*??????????????????????????????????????????????????????*/exp.printStackTrace();}
            catch(Exception exp){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        MessageBox.Show(exp.toString(),"??????", MessageBox.DialogType.Error);
                        mainProcess.interrupt();
                        ExitWithAsk=false;
                        thisStage.close();
                    }
                });
                return;
            }
        }
    }

    @Override public void initialize(URL location, ResourceBundle resources) {
        thisStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if(mainProcess!= null){
                    mainProcess.interrupt();
                }else {
                    return;
                }
            }
        });
        this.mainProcess=new Thread(new LoadWindow.LaunchProcessClass());
        this.mainProcess.setDaemon(true);
        this.mainProcess.start();
    }
}
