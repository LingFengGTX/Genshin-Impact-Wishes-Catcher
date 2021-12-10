package com.miuul.Analyze;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlListItem;

/**
 * 此类提供的方法用于切换卡池。
 */
public class PageNavigate {
    /**
     * 此类存放 页面中的 data-id
     */
    public static class defines{
        private static final String code_limit="301";
        private static final String code_weapon="302";
        private static final String code_newPlayer="100";
        private static final String code_longTime="200";
    }

    /**
     * 要切换到的祈愿类型
     */
    public enum WishedType{limit,weapon,newPlayer,longTime,NoType};

    /**
     * 切换到目标祈愿页，例如常驻池，限定池，武器池或者新手池
     * @param TargetPage 目标页对象
     * @param type 要切换到的祈愿页类型
     * @param WaitTime 等待页面加载时间（单位：毫秒）
     * @return 返回切换好的祈愿页HtmlPage对象
     * @throws Exception
     */
    public static DataPage selectWished(DataPage TargetPage,WishedType type,long WaitTime) throws Exception{
        //当网页出现 confirm-mask div则会抛出异常
        DomNode EmptyNode=TargetPage.getHtmlPage().getFirstByXPath("//div[@class=\"confirm-mask\"]");
        if(EmptyNode!=null){
            throw new Exception(EmptyNode.getTextContent());
        }
        HtmlDivision comboBox= TargetPage.getHtmlPage().getFirstByXPath("//div[@class=\"scroll-list\"]");
        comboBox.setAttribute("style","");//将div的Style设置为空使其展开
        HtmlListItem listClickTarget=null;
        switch(type){
            case limit:{
                listClickTarget=TargetPage.getHtmlPage().getFirstByXPath("//li[@data-id=\""+defines.code_limit+"\"]");
            };break;
            case weapon:{
                listClickTarget=TargetPage.getHtmlPage().getFirstByXPath("//li[@data-id=\""+defines.code_weapon+"\"]");
            };break;
            case newPlayer:{
                listClickTarget=TargetPage.getHtmlPage().getFirstByXPath("//li[@data-id=\""+defines.code_newPlayer+"\"]");
            };break;
            case longTime:{
                listClickTarget=TargetPage.getHtmlPage().getFirstByXPath("//li[@data-id=\""+defines.code_longTime+"\"]");
            };break;
        }
        listClickTarget.click();//模拟网页单击操作
        Thread.sleep(WaitTime);//设置线程等待时间。
        return (DataPage) TargetPage;
    }

    public static void StartWebClient(DataPage page,AnalyzeWebClient visitClient,String URL, ClientType BrowserType,long WaitTime) throws Exception{
        switch(BrowserType){
            case defaultType:{visitClient=new AnalyzeWebClient(BrowserVersion.BEST_SUPPORTED);};break;
            case Edge:{visitClient=new AnalyzeWebClient(BrowserVersion.EDGE);};break;
            case firefox:{visitClient=new AnalyzeWebClient(BrowserVersion.FIREFOX);};break;
            case chrome:{visitClient=new AnalyzeWebClient(BrowserVersion.CHROME);};break;
            case IE:{visitClient=new AnalyzeWebClient(BrowserVersion.INTERNET_EXPLORER);};break;
            case firefox78:{visitClient=new AnalyzeWebClient(BrowserVersion.FIREFOX_78);};break;
            default:{visitClient=new AnalyzeWebClient(BrowserVersion.BEST_SUPPORTED);};break;
        }

        visitClient.getOptions().setJavaScriptEnabled(true);//启动浏览器的JavaScript
        visitClient.getOptions().setWebSocketEnabled(true);//启用网络接口
        visitClient.waitForBackgroundJavaScript(WaitTime);//设置后台延迟时间
        page.setHtmlPage(visitClient.getPage(URL));
    }
}
