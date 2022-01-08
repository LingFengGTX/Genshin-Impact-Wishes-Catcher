package com.miuul.Analyze;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * 此类用于封装 htmlunit 的HtmlPage类。
 */
public class DataPage extends DataPageFake{

    public DataPage(HtmlPage page){
        this.types=SaveType.OnlyPage;
        this.TargetPage=page;
    }

    public DataPage(){
        this.types=SaveType.OnlyPage;
    }

    @Override
    public HtmlPage getHtmlPage(){
        return this.TargetPage;
    }

    @Override
    public WebClient getVisitClient() {
        return null;
    }

    @Override
    public void setVisitClient(WebClient client) {}

    @Override
    public void setHtmlPage(HtmlPage htmlPage){
        this.TargetPage=htmlPage;
    }
}
