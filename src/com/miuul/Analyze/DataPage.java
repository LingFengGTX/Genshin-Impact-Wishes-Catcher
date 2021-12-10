package com.miuul.Analyze;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class DataPage {
    private HtmlPage TargetPage=null;
    public DataPage(HtmlPage page){
        this.TargetPage=page;
    }
    public DataPage(){}
    public HtmlPage getHtmlPage(){
        return this.TargetPage;
    }
    public void setHtmlPage(HtmlPage htmlPage){
        this.TargetPage=htmlPage;
    }
}
