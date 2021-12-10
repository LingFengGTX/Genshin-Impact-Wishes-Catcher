package com.miuul.Data;

/**
 * 此类用于存放数据列表，该项包含 物品类型，物品名称，所属卡池，和获得日期
 */
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
