package com.miuul.Analyze;
import com.gargoylesoftware.htmlunit.html.*;
import com.miuul.Data.WishedClass;
import com.miuul.Data.WishedItem;

public class PageAnalyze {
    private HtmlPage TargetPage=null;//目标页对象
    private int PageIndex=0; //页面页数
    private long WaitTime=800;//页面加载时间
    private WishedClass data=null;
    private Buffer bufferClass=null;

    /**
     * 初始化该类
     * @param Target 目标HtmlPage
     * @throws Exception 异常抛出
     */
    public PageAnalyze(HtmlPage Target) throws Exception {
        DomNode EmptyNode=Target.getFirstByXPath("//div[@class=\"empty-row\"]");
        if(EmptyNode!=null){
            throw new Exception(EmptyNode.getTextContent());
        }
        this.TargetPage=Target;//回调对象
        this.data=new WishedClass();
        this.bufferClass=new Buffer();
        this.data.SetPageNotice(((DomNode)this.TargetPage.getFirstByXPath("//div[@class=\"tips\"]")).getTextContent());
        this.PageIndex=Integer.parseInt(((DomNode)this.TargetPage.getFirstByXPath("//span[@class=\"page-item\"]")).getTextContent());
    }

    public PageAnalyze(HtmlPage Target,long waitTime) throws Exception {
        DomNode EmptyNode=Target.getFirstByXPath("//div[@class=\"empty-row\"]");
        if(EmptyNode!=null){
            throw new Exception(EmptyNode.getTextContent());
        }
        this.TargetPage=Target;//回调对象
        this.WaitTime=waitTime;
        this.data=new WishedClass();
        this.bufferClass=new Buffer();
        this.data.SetPageNotice(((DomNode)this.TargetPage.getFirstByXPath("//div[@class=\"tips\"]")).getTextContent());
        this.PageIndex=Integer.parseInt(((DomNode)this.TargetPage.getFirstByXPath("//span[@class=\"page-item\"]")).getTextContent());
    }

    /**
     * 设置翻页的等待时间
     * @param waitTime 等待时间 （单位：毫秒）
     */
    public void SetWaitTime(long waitTime){
        this.WaitTime=waitTime;
    }
    /**
     * 将当前数据页翻往下一页
     * @return 如果下一页与当前页一致则返回假否则返回真
     * @throws Exception 执行翻页操作时所抛出的异常
     */
    public boolean nextPage() throws Exception{
        HtmlSpan nextButton=this.TargetPage.getFirstByXPath("//span[@class=\"page-item to-next selected\"]");
        nextButton.click();
        Thread.sleep(this.WaitTime);
        int Index=Integer.parseInt(((DomNode)this.TargetPage.getFirstByXPath("//span[@class=\"page-item\"]")).getTextContent());
        if(Index== this.getPageIndex()){
            return false;
        }else{
            this.PageIndex=Index;
            return true;
        }
    }


    /**
     *遍历祈愿页的所有数据
     * @throws Exception 执行目标方法或者翻页操作时所抛出的异常
     */
    public void whileAnalyzeFullPage() throws Exception{
        this.analyzeThisPage();
        while(this.nextPage()){
            this.analyzeThisPage();
        }
    }

    /**
     * 遍历祈愿页的所有数据，此方法你可以自定义遍历时应该做什么
     * @param function 要重写的目标方法
     * @throws Exception 异常抛出
     */
    public void whileAnalyzeFullPage(WhileDo function) throws Exception{
        this.analyzeThisPage();
        function.Do();
        while(this.nextPage()){
            this.analyzeThisPage();
            function.Do();
        }
    }

    /**
     * 获取当前 limitWished 的对象引用
     * @return 返回 limitWished 对象
     */
    public WishedClass getWishedClass(){
        return this.data;
    }
    /**
     * 分析当前数据并将分析的结果保存至数据字典
     * @throws Exception 异常抛出
     */
    public void analyzeThisPage()throws Exception{
        HtmlElement BodyElement=(HtmlElement)this.TargetPage.getFirstByXPath("//div[@class=\"table-content\"]");
        DomNode divList=BodyElement.getChildNodes().get(2);
        if(divList.getChildNodes().getLength()==0){
            return;
        }
        for(DomNode tmp : divList.getChildNodes()) {
            String temp_Name=FormatString(tmp.getChildNodes().get(2).getTextContent());
            String temp_Type=FormatString(tmp.getChildNodes().get(0).getTextContent());
            String temp_Date=tmp.getChildNodes().get(6).getTextContent();
            String temp_PoolType=tmp.getChildNodes().get(4).getTextContent();
            this.bufferClass.Item=new WishedItem(temp_Type,temp_Name,temp_PoolType,temp_Date);
            data.Add(this.bufferClass.Item);
        }
    }

    public Buffer getBufferClass(){
        return this.bufferClass;
    }

    private String FormatString(String pop){
        return pop.replace("\n","").replace(" ","");
    }


    /**
     * 获取当前数据页码
     * @return 页码
     */
    public int getPageIndex() {
        return PageIndex;
    }
}
