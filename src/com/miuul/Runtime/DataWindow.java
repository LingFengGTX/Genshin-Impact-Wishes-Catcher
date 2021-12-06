package com.miuul.Runtime;

import com.miuul.Data.WishedClass;
import com.miuul.Data.WishedItem;
import com.miuul.javafx.MessageBox;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;
import javafx.scene.control.*;
import java.util.Map;
import java.util.ResourceBundle;

public class DataWindow implements Initializable {
    public static WishedClass DataPool=null;
    public static Stage thisStage=null;

    @FXML public TableView table_MainView=null;
    @FXML public ComboBox combo_typeSwitch=null;
    @FXML private void btn_CloseWindow(ActionEvent e){
        thisStage.close();
    }

    @FXML private void btn_SaveAsXML(ActionEvent e){
        FileChooser tempChooser=new FileChooser();
        tempChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML 标记文档文件","*.xml"));
        tempChooser.setTitle("选择你要保存的位置");
        File chosed=tempChooser.showSaveDialog(thisStage);
        if(chosed==null){
            return;
        }
        try {
            new com.miuul.Data.Out.XML().WriteToFile(chosed.toString(), DataPool);
        }catch(Exception exp){
            MessageBox.Show(exp.toString(),"错误", MessageBox.DialogType.Error);
            return;
        }
    }

    @FXML private void btn_SaveAsChart(ActionEvent e){
        DirectoryChooser tempChooser=new DirectoryChooser();
        tempChooser.setTitle("请选择图表保存的位置");
        File chosed=tempChooser.showDialog(thisStage);
        if(chosed==null){
            return;
        }
        try {
            new com.miuul.Data.Out.Chart().CreateChart(DataPool,chosed.toString());
        }catch(Exception exp){
            MessageBox.Show(exp.toString(),"错误", MessageBox.DialogType.Error);
            return;
        }
    }
    public class ItemData_Analyze {
        private final SimpleStringProperty Type;
        private final SimpleStringProperty Data;

        public ItemData_Analyze(String type,String data){
                this.Type=new SimpleStringProperty(type);
                this.Data=new SimpleStringProperty(data);
        }
        public String getType(){
            return this.Type.get();
        }
        public String getData(){
            return this.Data.get();
        }
    }
    public class ItemData_List {

        private final SimpleStringProperty Type;
        private final SimpleStringProperty Name;
        private final SimpleStringProperty Pool;
        private final SimpleStringProperty Date;
        public ItemData_List(String type,String name,String pool,String date){
            this.Type=new SimpleStringProperty(type);
            this.Name=new SimpleStringProperty(name);
            this.Pool=new SimpleStringProperty(pool);
            this.Date=new SimpleStringProperty(date);
        }

        //访问器定义

        public String getType() {
            return this.Type.get();
        }
        public String getName() {
            return this.Name.get();
        }
        public String getPool() {
            return this.Pool.get();
        }
        public String getDate() {
            return this.Date.get();
        }

    }
    public class ItemData_Count {
        private final SimpleIntegerProperty Count;
        private final SimpleStringProperty Name;

        public ItemData_Count(String Name,int count) {
            this.Count=new SimpleIntegerProperty(count);
            this.Name=new SimpleStringProperty(Name);
        }

        //访问器设置

        public int getCount() {
            return Count.get();
        }

        public String getName() {
            return Name.get();
        }
    }
    private void toAnalyze(){
        TableColumn column_Type=new TableColumn("分析项");
        TableColumn column_Data=new TableColumn("值");

        column_Type.setPrefWidth(390);
        column_Data.setPrefWidth(150);

        column_Type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        column_Data.setCellValueFactory(new PropertyValueFactory<>("Data"));

        this.table_MainView.getColumns().addAll(column_Type,column_Data);

        ObservableList<ItemData_Analyze> data_analyze= FXCollections.observableArrayList();
        {//分析结果初始化
            if(DataPool.isHasFiveStar()){
                data_analyze.add(new ItemData_Analyze("拥有五星物品的数量:",String.valueOf(DataPool.getFiveStarCount())));
                data_analyze.add(new ItemData_Analyze("最后获得的五星物品:",DataPool.getLastFiveStar()));
                data_analyze.add(new ItemData_Analyze("距离下一次五星保底还有多少发:",String.valueOf(DataPool.getFiveStarCountIndex())));
            }

            if(DataPool.isHasFourStar()){
                data_analyze.add(new ItemData_Analyze("拥有四星物品的数量:",String.valueOf(DataPool.getFourStarCount())));
                data_analyze.add(new ItemData_Analyze("最后获得的四星物品:",DataPool.getLastFourStar()));
                data_analyze.add(new ItemData_Analyze("距离下一次四星保底还有多少发:",String.valueOf(DataPool.getFourStarCountIndex())));

            }
            if(DataPool.getTotalCount()!=0){
                data_analyze.add(new ItemData_Analyze("获得物品总数量:",String.valueOf(DataPool.getTotalCount())));
            }
        }
        table_MainView.setItems(data_analyze);
    }
    private void toCount(){
        TableColumn column_Type=new TableColumn("名称");
        TableColumn column_Count=new TableColumn("数量");

        column_Type.setPrefWidth(390);
        column_Count.setPrefWidth(150);

        column_Type.setCellValueFactory(new PropertyValueFactory<>("Name"));
        column_Count.setCellValueFactory(new PropertyValueFactory<>("Count"));

        this.table_MainView.getColumns().addAll(column_Type,column_Count);
        ObservableList<ItemData_Count> data_count= FXCollections.observableArrayList();
        for (Map.Entry<String,Integer> Item : DataPool.getDictionaryDataAddress().entrySet()){
            data_count.add(new ItemData_Count(Item.getKey(),Item.getValue()));
        }
        table_MainView.setItems(data_count);
    }

    @FXML private void SwitchTableData(ActionEvent e){
        this.table_MainView.getItems().clear();
        this.table_MainView.getColumns().clear();

        switch(this.combo_typeSwitch.getSelectionModel().getSelectedIndex()){
            case 0:{this.toAnalyze();};break;
            case 1:{this.toList();};break;
            case 2:{this.toCount();};break;

        }
    }

    private void toList(){
        //物品列表初始化
        TableColumn column_Type=new TableColumn("类型");
        TableColumn column_Name=new TableColumn("名称");
        TableColumn column_Pool=new TableColumn("卡池");
        TableColumn column_Date=new TableColumn("日期");

        column_Type.setPrefWidth(119);
        column_Name.setPrefWidth(153);
        column_Name.setPrefWidth(136);
        column_Name.setPrefWidth(134);

        column_Type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        column_Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        column_Pool.setCellValueFactory(new PropertyValueFactory<>("Pool"));
        column_Date.setCellValueFactory(new PropertyValueFactory<>("Date"));

        this.table_MainView.getColumns().addAll(column_Type,column_Name,column_Pool,column_Date);

        ObservableList<ItemData_List> data_list= FXCollections.observableArrayList();
        for (WishedItem Item : DataPool.getDataList()){
            data_list.add(new ItemData_List(Item.getItemType(),Item.getItemName(), Item.ItemPoolType(), Item.getItemDate()));
        }
        table_MainView.setItems(data_list);
    }
    private void FreshTableData(){
        this.combo_typeSwitch.getSelectionModel().select(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                FreshTableData();
            }
        });

    }
}
