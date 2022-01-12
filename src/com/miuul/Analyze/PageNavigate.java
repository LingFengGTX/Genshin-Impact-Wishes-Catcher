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
     * @throws Exception
     */
    public static void selectWished(DataPageFake TargetPage,WishedType type,long WaitTime) throws Exception{
        //当网页出现 confirm-mask div则会抛出异常
        DomNode EmptyNode=TargetPage.getHtmlPage().getFirstByXPath("//div[@class=\"confirm-mask\"]");
        if(EmptyNode!=null){
            throw new Exception(EmptyNode.getTextContent());
        }
        HtmlDivision comboBox= TargetPage.getHtmlPage().getFirstByXPath("//div[@class=\"scroll-list\"]");
        comboBox.setAttribute("style","");//将div的Style设置为空使其展开
        HtmlListItem listClickTarget=null;
        if(type==WishedType.NoType){
            throw new Exception("无类型输入。");
        }
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
    }

    /**
     * 根据所给的 URL 信息载入目标页并初始化 AnalyzeWebClient 对象，该方法可兼容 DataPageClient,需要将第二个参数设置为 null
     * @param page 目标对象
     * @param visitClient 目标 AnalyzeWebClient
     * @param URL 要载入的 URL 信息
     * @param BrowserType 选择访问浏览器类型
     * @param WaitTime 后台载入等待时间
     * @throws Exception
     */
    public static void InitWebPage(DataPageFake page,AnalyzeWebClient visitClient,String URL, ClientType BrowserType,long WaitTime) throws Exception{
        if(page.types==SaveType.OnlyPage){
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
        }else{
            switch(BrowserType){
                case defaultType:{page.setVisitClient(new AnalyzeWebClient(BrowserVersion.BEST_SUPPORTED));};break;
                case Edge:{page.setVisitClient(new AnalyzeWebClient(BrowserVersion.EDGE));};break;
                case firefox:{page.setVisitClient(new AnalyzeWebClient(BrowserVersion.FIREFOX));};break;
                case chrome:{page.setVisitClient(new AnalyzeWebClient(BrowserVersion.CHROME));};break;
                case IE:{page.setVisitClient(new AnalyzeWebClient(BrowserVersion.INTERNET_EXPLORER));};break;
                case firefox78:{page.setVisitClient(new AnalyzeWebClient(BrowserVersion.FIREFOX_78));};break;
                default:{page.setVisitClient(new AnalyzeWebClient(BrowserVersion.BEST_SUPPORTED));};break;
            }
            page.getVisitClient().getOptions().setJavaScriptEnabled(true);//启动浏览器的JavaScript
            page.getVisitClient().getOptions().setWebSocketEnabled(true);//启用网络接口
            page.getVisitClient().waitForBackgroundJavaScript(WaitTime);//设置后台延迟时间
            page.setHtmlPage(page.getVisitClient().getPage(URL));
        }
    }

    /**
     * 根据所给的 URL 信息载入目标页，此方法不可兼容 DataPage
     * @param page 目标对象
     * @param URL 要载入的 URL 信息
     * @param BrowserType 选择访问浏览器类型
     * @param WaitTime 后台载入等待时间
     */
    public static void InitWebPage(DataPageFake page,String URL, ClientType BrowserType,long WaitTime) throws Exception{
        switch(BrowserType){
            case defaultType:{page.setVisitClient(new AnalyzeWebClient(BrowserVersion.BEST_SUPPORTED));};break;
            case Edge:{page.setVisitClient(new AnalyzeWebClient(BrowserVersion.EDGE));};break;
            case firefox:{page.setVisitClient(new AnalyzeWebClient(BrowserVersion.FIREFOX));};break;
            case chrome:{page.setVisitClient(new AnalyzeWebClient(BrowserVersion.CHROME));};break;
            case IE:{page.setVisitClient(new AnalyzeWebClient(BrowserVersion.INTERNET_EXPLORER));};break;
            case firefox78:{page.setVisitClient(new AnalyzeWebClient(BrowserVersion.FIREFOX_78));};break;
            default:{page.setVisitClient(new AnalyzeWebClient(BrowserVersion.BEST_SUPPORTED));};break;
        }
        page.getVisitClient().getOptions().setJavaScriptEnabled(true);//启动浏览器的JavaScript
        page.getVisitClient().getOptions().setWebSocketEnabled(true);//启用网络接口
        page.getVisitClient().waitForBackgroundJavaScript(WaitTime);//设置后台延迟时间
        page.setHtmlPage(page.getVisitClient().getPage(URL));
    }

}
