package com.miuul.Analyze;

import com.miuul.Data.WishedItem;

//此类是用于存放缓冲数据类
public class Buffer {
    protected WishedItem Item;

    /**
     * 获取缓冲项
     * @return
     */
    public WishedItem getWishedItem(){
        return this.Item;
    }
}
