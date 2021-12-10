package com.miuul.Data.Out;

import com.miuul.Data.Out.Resources.ResourcesList;
import com.miuul.Data.WishedClass;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Chart{
    /**
     * 实例化一个图表创建类
     * 该类将根据传入的 WishedClass 输出数据至图表
     */
    public Chart(){}
    /**
     * 将数据导出至 html 图表
     * @param src_Data 数据源
     * @param dir 保存位置
     * @throws Exception
     */
    public void CreateChart(WishedClass src_Data,String dir) throws Exception{
        String TargetDir=dir;

        //生成img目录
        File imgFolder=new File(TargetDir+"/img");
        if(!imgFolder.exists()){
            if(!imgFolder.mkdir()) {
                throw new Exception("错误，创建img目录失败");
            }
        }

        //生成js目录
        File jsFolder=new File(TargetDir+"/js");
        if(!jsFolder.exists()) {
            if (!jsFolder.mkdir()) {
                throw new Exception("错误，创建js目录失败");
            }
        }

        //生成css目录
        File cssFolder=new File(TargetDir+"/css");
        if(!cssFolder.exists()){
            if(!cssFolder.mkdir()){
                throw new Exception("错误，创建css目录失败");
            }
        }


        //导出Index.html
        this.WriteToFile("Index.html",TargetDir);
        //导出img文件
        for(String FileName : ResourcesList.img()){
            this.WriteToFile(FileName,(dir+"/img"));
        }

        //导出css文件
        for(String FileName : ResourcesList.css()){
            this.WriteToFile(FileName,(dir+"/css"));
        }

        //导出js文件
        for(String FileName : ResourcesList.js()){
            this.WriteToFile(FileName,(dir+"/js"));
        }

        //导出Bar图表JS文件
        this.WriteChart_Bar(src_Data,TargetDir+"/js");

        //导出Pie图表JS文件
        this.WriteChart_Pie(src_Data,TargetDir+"/js");
    }

    //此方法将存放的数据整理并替换掉JS中的$DataList$和$Name$对象
    private void WriteChart_Pie(WishedClass src,String dir) throws Exception{
        String item_Data="";
        int Loop=-1;
        for (Map.Entry<String,Integer> item : src.getDictionaryDataAddress().entrySet()) {
            Loop+=1;
            item_Data+=("{value:"+item.getValue()+",name:'"+item.getKey()+"'}");
            if(Loop!=(src.getDictionaryDataAddress().size()-1)){
                item_Data+=",";
            }
        }
        //读取Bar JS文件
        BufferedReader readerBuffer=new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("Resources/chart_Pie.js")));
        String Content_Text="";
        String Content_Line="";
        while((Content_Line=readerBuffer.readLine())!=null){
            Content_Text+=Content_Line;
        }
        Content_Text=Content_Text.replace("$DataList$",item_Data);//替换X坐标轴
        Content_Text=Content_Text.replace("$Name$","数量");//标题

        //实例化一个文件输出对象
        FileOutputStream fileWriter=new FileOutputStream( dir+"/chart_Pie.js",false);
        fileWriter.write(Content_Text.getBytes(StandardCharsets.UTF_8));
        fileWriter.flush();
        fileWriter.close();
    }

    private void WriteChart_Bar(WishedClass src,String dir) throws Exception{
        String X_Data="";
        String Y_Data="";
        int Loop=-1;
        for (Map.Entry<String,Integer> item : src.getDictionaryDataAddress().entrySet()) {
            Loop+=1;
            X_Data+=("\""+item.getKey()+"\"");
            Y_Data+=(item.getValue());
            if(Loop!=(src.getDictionaryDataAddress().size()-1)){
                X_Data+=",";
                Y_Data+=",";
            }
        }
        //读取Bar JS文件
        BufferedReader readerBuffer=new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("Resources/chart_Bar.js")));
        String Content_Text="";
        String Content_Line="";
        while((Content_Line=readerBuffer.readLine())!=null){
            Content_Text+=Content_Line;
        }
        Content_Text=Content_Text.replace("$DataList$",X_Data);//替换X坐标轴
        Content_Text=Content_Text.replace("$DataCount$",Y_Data);//替换Y坐标轴
        Content_Text=Content_Text.replace("$Name$","数量");//标题

        //实例化一个文件输出对象
        FileOutputStream fileWriter=new FileOutputStream( dir+"/chart_Bar.js",false);
        fileWriter.write(Content_Text.getBytes(StandardCharsets.UTF_8));
        fileWriter.flush();
        fileWriter.close();
    }

    //用于写 Resources 中的资源文件
    private void WriteToFile(String FileName,String dir) throws Exception{
        InputStream  readerBuffer=this.getClass().getResourceAsStream("Resources/"+FileName);
        FileOutputStream fileWriter=new FileOutputStream( dir+"/"+ FileName,false);
        byte[] data=new byte[1024];
        int byteLength=-1;
        while((byteLength=readerBuffer.read(data))!=-1){
            fileWriter.write(data,0,byteLength);
        }
        fileWriter.close();
        readerBuffer.close();
    }
}
