package com.miuul.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

/**
 * 此类是用于存放分析数据的类。
 * 该类可获得以下数据:
 * 数据分析结果
 * 物品列表
 * 物品数量
 */
public class WishedClass {
    private ArrayList<WishedItem> dataList = null;//封装此字段
    private HashMap<String, Integer> data = null;
    private int fourStarCount = 0;//获取四星角色或武器的数量
    private String lastFourStar = null; //最后获取四星物品的名称
    private String lastFiveStar = null;   //最后获取五星物品的名称
    private int fourStarCountIndex = 0;//获取最后一个四星角色(或武器)的索引值
    private int fiveStarCount = 0;//获取五星角色的数量
    private int fiveStarCountIndex = 0;//获取最后一个五星角色的索引值
    private boolean isHasFourStarM = false;//是否拥有四星物品
    private boolean isHasFiveStarM = false;//是否拥有五星物品
    private int TotalCount = 0;//物品的总数量或者索引值
    private int fiveLimitCount = 90;//五星保底次数
    private int fourLimitCount = 10;//四星保底次数
    /**
     * 网页公告文本
     */
    private String PageNotice = null;

    /**
     * 初始化 WishedClass 类
     */
    public WishedClass() {
        //构造方法初始化
        this.data = new HashMap<>();
        this.dataList = new ArrayList<>();
    }

    public String getWebSitePageContext() {
        return this.PageNotice;
    }

    /**
     * 初始化 WishedClass 类，可自定义保底次数
     *
     * @param fiveLimitCount 五星最大保底次数
     * @param fourLimitCount 四星最大保底次数
     */
    public WishedClass(int fiveLimitCount, int fourLimitCount) {
        //这个构造方法可以设置保底次数
        this.setFiveLimitCount(fiveLimitCount);
        this.setFourLimitCount(fourLimitCount);
        this.data = new HashMap<>();
        this.dataList = new ArrayList<>();
    }

    /**
     * 设置此数据的页面公告文本
     */
    public void SetPageNotice(String pageNotice) {
        this.PageNotice = pageNotice;
    }


    /**
     * 添加数据，如果已经存在此数据则会将此数据的Count向上添加。如果没有则为该数据添加到数据字典
     *
     * @param memTarget 要添加的对象
     */
    public void Add(WishedItem memTarget) {
        String Name = memTarget.getItemName();
        //向列表中添加数据
        this.dataList.add(memTarget);
        TotalCount = getTotalCount() + 1;
        //判断字符串中是否含有五星或者四星字样，如果有则添加
        if (Name.contains("五星")) {
            this.fiveStarCount = this.getFiveStarCount() + 1;
            if (!(isHasFiveStar())) {
                this.lastFiveStar = Name;
                this.fiveStarCountIndex = this.getTotalCount();
                this.isHasFiveStarM = true;
            }
        } else if (Name.contains(("四星"))) {
            this.fourStarCount = this.getFourStarCount() + 1;
            if (!(isHasFourStar())) {
                this.lastFourStar = Name;
                this.fourStarCountIndex = this.getTotalCount();
                this.isHasFourStarM = true;
            }
        }
        for (Map.Entry<String, Integer> item : data.entrySet()) {
            if (Name.equals(item.getKey())) {
                item.setValue(item.getValue() + 1);
                return;//执行完毕并返回方法
            }
        }
        //当遍历后的数据未能找到此类数据则添加一个新的关系值
        this.data.put(Name  , 1);
        return;
    }

    /**
     * 获取当前的分析数据
     *
     * @return 分析 Dictionary 对象的拷贝
     */
    public HashMap<String, Integer> getDictionaryData() {
        return (HashMap<String, Integer>) this.data.clone();
    }

    /**
     * 获取当前的分析数据
     *
     * @return 分析 Dictionary 对象的引用
     */
    public HashMap<String, Integer> getDictionaryDataAddress() {
        return (HashMap<String, Integer>) this.data;
    }

    /**
     * 获取对象列表的拷贝
     *
     * @return 分析 HasMap 对象的拷贝
     */
    public ArrayList<WishedItem> getDataList() {
        return (ArrayList<WishedItem>) this.dataList.clone();
    }

    /**
     * 获取对象引用
     *
     * @return 分析 HasMap 对象的引用
     */
    public ArrayList<WishedItem> getDataListAddress() {
        return this.dataList;
    }

    /**
     * 打印整理数据
     */
    public void printAnalyzeData() {
        if(isHasFourStar()||isHasFourStar()){
            System.out.print("\n");
        }
        if (isHasFourStar()) {
            System.out.println("\n拥有的四星角色和武器数量:" + this.getFourStarCount());
            System.out.println("最后获得四星的角色(或武器)是:" + this.getLastFourStar());
            System.out.println("距离下一次四星保底还有: " + (this.getFourLimitCount() - getFourStarCountIndex()) + " 发");
        } else {
            System.out.println("暂时未查到有关四星的数据");
        }

        if (this.isHasFiveStar()) {
            System.out.println("\n拥有的五星角色数量:" + this.getFiveStarCount());
            System.out.println("最后获得五星的角色是:" + this.getLastFiveStar());
            System.out.println("距离下一次五星保底还有: " + (this.getFiveLimitCount() - getFiveStarCountIndex()) + " 发");
        } else {
            System.out.println("暂时未查到有关五星的数据");
        }
    }

    /**
     * 打印数据字典
     */
    public void printItemCountList() {
        if (this.data.isEmpty()) {
            System.out.println("###暂无数据###");
            return;
        }

        for (Map.Entry<String, Integer> item : data.entrySet()) {
            System.out.printf("\n%s\t%s",item.getKey(),item.getValue());
        }

    }

    /**
     * 获取四星角色或武器数量
     *
     * @return
     */
    public int getFourStarCount() {
        return fourStarCount;
    }

    /**
     * 获取最后一次获取四星物品名称
     *
     * @return
     */
    public String getLastFourStar() {
        return lastFourStar;
    }

    /**
     * 获取最后一次获取五星星物品名称
     *
     * @return
     */
    public String getLastFiveStar() {
        return lastFiveStar;
    }

    /**
     * 获取最后四星获取的索引位置
     *
     * @return
     */
    public int getFourStarCountIndex() {
        return fourStarCountIndex;
    }

    /**
     * 获得下次五星物品保底次数
     * @return
     */
    public int getNextFiveStarIndex(){
        return (this.fiveLimitCount-this.fiveStarCountIndex);
    }

    /**
     * 获得下次星物品保底次数
     * @return
     */
    public int getNextFourStarIndex(){
        return (this.fourLimitCount-this.fourStarCountIndex);
    }

    /**
     * 获取五星角色或武器数量
     *
     * @return
     */
    public int getFiveStarCount() {
        return fiveStarCount;
    }

    /**
     * 获取最后五星获取的索引位置
     *
     * @return
     */
    public int getFiveStarCountIndex() {
        return fiveStarCountIndex;
    }

    /**
     * 是否拥有四星武器和角色
     *
     * @return
     */
    public boolean isHasFourStar() {
        return isHasFourStarM;
    }

    /**
     * 是否拥有五星武器和角色
     *
     * @return
     */
    public boolean isHasFiveStar() {
        return isHasFiveStarM;
    }

    /**
     * 获取查到的数据个数
     *
     * @return
     */
    public int getTotalCount() {
        return TotalCount;
    }

    /**
     * 打印物品列表
     */
    public void printItemsList() {
        for (WishedItem item : this.dataList
        ) {
            System.out.printf("\n%s\t%s\t%s\t%s", item.getItemType(), item.getItemName(), item.ItemPoolType(),item.getItemDate());
        }
    }

    /**
     * 获取五星物品保底次数
     * @return
     */
    public int getFiveLimitCount() {
        return fiveLimitCount;
    }

    /**
     * 设置五星物品保底次数
     * @param fiveLimitCount 最大保底次数
     */
    public void setFiveLimitCount(int fiveLimitCount) {
        this.fiveLimitCount = fiveLimitCount;
    }

    /**
     * 获取四星物品保底次数
     * @return
     */
    public int getFourLimitCount() {
        return fourLimitCount;
    }

    /**
     * 设置四星物品保底次数
     * @param fourLimitCount
     */
    public void setFourLimitCount(int fourLimitCount) {
        this.fourLimitCount = fourLimitCount;
    }
}
