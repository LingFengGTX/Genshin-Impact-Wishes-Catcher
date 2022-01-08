package com.miuul.Analyze;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.WebClient;

/**
 * 该类在原有的 DataPage 上增加 WebClient的子类，可减少代码编写量。
 */
public class DataPageClient extends DataPageFake{

    private WebClient visitClient=null;

    public DataPageClient(HtmlPage page){
        this.types=SaveType.WithClient;
        this.TargetPage=page;
    }

    public DataPageClient(){
        this.types=SaveType.WithClient;
    }

    @Override
    public HtmlPage getHtmlPage(){
        return this.TargetPage;
    }

    @Override
    public WebClient getVisitClient() {
        return this.visitClient;
    }

    @Override
    public void setVisitClient(WebClient client) {
        this.visitClient=client;
    }

    @Override
    public void setHtmlPage(HtmlPage htmlPage){
        this.TargetPage=htmlPage;
    }
}
