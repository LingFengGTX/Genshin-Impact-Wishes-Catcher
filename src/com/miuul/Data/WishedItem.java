package com.miuul.Data;

/**
 * 此类用于存放数据列表，该项包含 物品类型，物品名称，所属卡池，和获得日期
 */
public class WishedItem {
    private String ItemType=null;
    private String ItemName=null;
    private String ItemDate=null;
    private String ItemPoolType=null;

    /**
     * 实例化一个 WishedItem 对象
     * @param ItemType 物品类型
     * @param ItemName 物品名称
     * @param DataPool  所属卡池
     * @param GetDate 获得时间
     */
    public WishedItem(String ItemType,String ItemName,String DataPool,String GetDate){
        this.ItemName=ItemName;
        this.ItemType=ItemType;
        this.ItemDate=GetDate;
        this.ItemPoolType=DataPool;
    }

    /**
     * 获取物品类型
     * @return
     */
    public String getItemType() {
        return ItemType;
    }

    /**
     * 获取物品名称
     * @return
     */
    public String getItemName() {
        return ItemName;
    }

    /**
     * 获取物品获得的日期
     * @return
     */
    public String getItemDate() {
        return ItemDate;
    }

    /**
     * 获取物品所属的卡池
     * @return
     */
    public String ItemPoolType() {
        return ItemPoolType;
    }

}
