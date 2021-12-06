package com.miuul.Data;

//该类用是存放祈愿单个数据类
public class WishedItem {
    private String ItemType=null;
    private String ItemName=null;
    private String ItemDate=null;
    private String ItemPoolType=null;

    public WishedItem(String ItemType,String ItemName,String DataPool,String GetDate){
        this.ItemName=ItemName;
        this.ItemType=ItemType;
        this.ItemDate=GetDate;
        this.ItemPoolType=DataPool;
    }

    public String getItemType() {
        return ItemType;
    }

    public String getItemName() {
        return ItemName;
    }

    public String getItemDate() {
        return ItemDate;
    }

    public String ItemPoolType() {
        return ItemPoolType;
    }

}
