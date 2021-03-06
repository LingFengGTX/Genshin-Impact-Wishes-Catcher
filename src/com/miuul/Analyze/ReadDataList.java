package com.miuul.Analyze;
import com.miuul.Data.WishedItem;

/**
 * 该类用于读取物品列表
 */
public class ReadDataList {
    private PageAnalyze targetPage=null;
    public ReadDataList(PageAnalyze target){
        this.targetPage=target;
    }
    private int Index=0;
    public Boolean ReadNext(){
        if((Index-1)>targetPage.getWishedClass().getDataListAddress().size()){
            return false;
        }
        return true;
    }
    public WishedItem getDataItem() throws Exception{
        if((Index-1)>targetPage.getWishedClass().getDataListAddress().size()){
            throw new Exception("超出了对象引用。");
        }
        return targetPage.getWishedClass().getDataListAddress().get(Index);
    }
}
