package com.miuul.Analyze;


import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * DataPage 公共虚类，可用于拓展 DataPage 的功能
 */
public abstract class DataPageFake {

    protected HtmlPage TargetPage=null;

    protected SaveType types=null;

    /**
     * 获取该类存储的 HtmlPage
     * @return
     */
    public abstract HtmlPage getHtmlPage();

    /**
     * 重设该类存储的 HtmlPage
     * @param htmlPage HtmlPage
     * @return
     */
    public abstract void setHtmlPage(HtmlPage htmlPage);

    /**
     * 获取该类存储的 WebClient
     */
    public abstract WebClient getVisitClient();

    /**
     * 重设该类存储的 WebClient
     * @param client WebClient
     */
    public abstract void setVisitClient(WebClient client);
}
