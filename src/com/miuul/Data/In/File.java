package com.miuul.Data.In;

import java.io.FileInputStream;

public class File {
    /**
     * 用于获取文件的内容并读取。
     * @throws Exception
     */
    public static String ReadStringFromFile(String FileName) throws Exception{
        FileInputStream fileInputer=new FileInputStream(FileName);
        String ContentBuffer="";
        byte[] buffer=new byte[10240];
        int flag=0;
        while((flag=fileInputer.read(buffer))!=-1){
            ContentBuffer+=new String(buffer, 0, flag);
        }

       return ContentBuffer;
    }
    /**
     * 用于获取文件的内容并提取 AntherKey，如果不存在则抛出异常。
     * @throws Exception
     */
    public static String ReadAntherKeyFromFile(String FileName) throws Exception{
        String FileBufferString=ReadStringFromFile(FileName);
        StringBuilder AntherKeyContent=new StringBuilder();
        int FindedIndex=FileBufferString.indexOf("https://webstatic.mihoyo.com/");
        if(FindedIndex==-1){
            throw new Exception("错误，请检查此文件是否包含 AntherKey 信息。");
        }
        for(int charLoop=0;FileBufferString.charAt(charLoop)!='#';charLoop+=1){
                AntherKeyContent.append(FileBufferString.charAt(charLoop));
        }
        AntherKeyContent.append("#log");
        return AntherKeyContent.toString();
    }
}
