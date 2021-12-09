package com.miuul;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.miuul.Analyze.*;
import java.io.File;
import com.miuul.Data.Out.Chart;
import com.miuul.Data.Out.XML;
import com.miuul.Data.PageNavigate;
import com.miuul.Runtime.HelpGui;
import com.miuul.Runtime.Version;

public class Main {
    //-------预设属性-------
    private static String webURL=null; //目标URL
    private static WebClient PageBroker=null;
    private static HtmlPage PageViewSave=null; //目标地址
    public static long ThreadSleep=1000; //线程等待时间，此数值可由用户自行设定。防止后台数据未更新完毕导致数据加载失败。
    private static BrowserVersion viewer=BrowserVersion.BEST_SUPPORTED;   //设置访问目标网页的浏览器类型
    private static String xmlFileName=null; //生成目标xml文件位置
    private static Boolean printTestInfo=false; //是否打印信息
    private static PageNavigate.WishedType getPool= PageNavigate.WishedType.NoType;
    private static Boolean print_count=false; //是否打印物品对应的数量
    private static Boolean print_analyze=false; //是否打印分析出的数据结果
    private static Boolean print_itemList=false; //是否打印物品列表
    private static String chartFileSavePath=null;
    private static Boolean UseGui=false;


    /**
     * 解析命令行参数
     * @param args args对象
     * @throws Exception
     */
    public static void AnalyzeCommand(String[] args) throws Exception{

            for (String cmd : args) {
                String[] cmdString = cmd.split(":");

                if(cmdString[0].equals("-help")){
                    System.out.println(Version.getHelpString());
                    System.exit(0);
                }

                if(cmdString[0].equals("-help-gui")){
                    HelpGui.show();
                    System.exit(0);
                }

                if (cmdString[0].equals("-version")) {
                    if(cmdString.length!=1){
                        if(cmdString[1].equals("all")){
                            System.out.println("htmlunit :"+com.gargoylesoftware.htmlunit.Version.getProductVersion());
                        }
                    }
                    System.out.println(Version.VERSION);
                    System.exit(0);
                }

                if(cmdString[0].equals("-version-about")){
                    com.miuul.Runtime.AboutGui.show();
                }

                if(cmdString[0].equals("-gui")){
                    Main.UseGui=true;
                    break;
                }

                if(cmdString[0].equals("-chart")){
                    Main.chartFileSavePath=Main.FilePath(cmdString);
                    File DirChecker=new File(Main.chartFileSavePath);
                    if(!DirChecker.exists()){
                        System.err.println("目录:"+Main.chartFileSavePath+" 不存在！");
                        System.exit(-1);
                    }
                    continue;
                }

                if (cmdString[0].equals("-pool")) {
                    if (cmdString[1].equals("weapon")) {
                        Main.getPool = PageNavigate.WishedType.weapon;
                    } else if (cmdString[1].equals("limit")) {
                        Main.getPool = PageNavigate.WishedType.limit;
                    } else if (cmdString[1].equals("newplayer")) {
                        Main.getPool = PageNavigate.WishedType.newPlayer;
                    } else if (cmdString[1].equals("longtime")) {
                        Main.getPool = PageNavigate.WishedType.longTime;
                    } else {
                        Main.getPool = PageNavigate.WishedType.NoType;
                    }
                    continue;
                }
                if (cmdString[0].equals("-print")) {
                    Main.printTestInfo = true;
                    String[] items = cmdString[1].split(",");
                    for (String ItemType : items) {
                        if (ItemType.equals("count")) {
                            Main.print_count = true;
                            continue;
                        }
                        if (ItemType.equals("analyze")) {
                            Main.print_analyze = true;
                            continue;
                        }
                        if (ItemType.equals("list")) {
                            Main.print_itemList = true;
                            continue;
                        }
                    }
                    continue;
                }
                if (cmdString[0].equals("-file")) {
                    if (webURL != null) {
                        throw new Exception("不能重复设置目标URL");
                    }
                    webURL=com.miuul.Data.In.File.ReadStringFromFile(cmdString[1]);
                    continue;
                }
                if (cmdString[0].equals("-file-key")) {
                    if (webURL != null) {
                        throw new Exception("不能重复设置目标URL");
                    }
                    webURL=com.miuul.Data.In.File.ReadAntherKeyFromFile(cmdString[1]);
                    continue;
                }
                if (cmdString[0].equals("-viewer")) {
                    if (cmdString[1].equals("chrome")) {
                        Main.viewer = BrowserVersion.CHROME;
                    } else if (cmdString[1].equals("ie")) {
                        Main.viewer = BrowserVersion.INTERNET_EXPLORER;
                    } else if (cmdString[1].equals("edge")) {
                        Main.viewer = BrowserVersion.EDGE;
                    } else if (cmdString[1].equals("firefox")) {
                        Main.viewer = BrowserVersion.FIREFOX;
                    } else if (cmdString[1].equals("firefox78")) {
                        Main.viewer = BrowserVersion.FIREFOX_78;
                    } else if (cmdString[1].equals("default")) {
                        Main.viewer = BrowserVersion.BEST_SUPPORTED;
                    } else {
                        Main.viewer = BrowserVersion.BEST_SUPPORTED;
                    }
                    continue;
                }
                if (cmdString[0].equals("-web")) {
                    if (webURL != null) {
                        throw new Exception("不能重复设置目标URL");
                    }
                    webURL = cmdString[1];
                    continue;
                }
                if (cmdString[0].equals("-xml")) {
                    Main.xmlFileName = Main.FilePath(cmdString);
                    continue;
                }
                if (cmdString[0].equals("-time")) {
                    int timerInt = Integer.parseInt(cmdString[1]);
                    if (timerInt <= 0 || timerInt > 120000) {
                        throw new Exception("时间参数不正确，请在 0~120000 中输入。");
                    }
                    Main.ThreadSleep = timerInt;
                    continue;
                }
                System.err.println("不能读取数据:"+cmdString[0]+" 和 "+cmdString[1]+"，你可以使用 -help,-help-gui 命令来查看相关说明。");
                System.exit(-1);
            }
    }

    private static String FilePath(String[] cmds){
       //此方法是解决分割后冒号丢失方法
        String Road="";
        for(int Loop=1;Loop<=(cmds.length-1);Loop+=1){
            Road+=cmds[Loop];
            if(Loop!=(cmds.length-1)){
                Road+=":";
            }
        }
        return Road;
    }

    public static void main(String[] args){
	// write your code here
        try {

            if(args.length!=0){
                Main.AnalyzeCommand(args);
            }

            if(Main.UseGui){
                com.miuul.Runtime.Gui.show();
                System.exit(0);
            }

            //引用 WebClient 并实力化然后获取目标HtmlPage对象
            PageViewSave=PageNavigate.StartWebClient(PageBroker,webURL,viewer,ThreadSleep);
            Thread.sleep(ThreadSleep);
            if(Main.getPool== PageNavigate.WishedType.NoType){
                throw new Exception("当前没有选择祈愿类型。请使用 -help 命令查看获取祈愿池的类型");
            }
            PageViewSave=PageNavigate.selectWished(PageViewSave,Main.getPool,ThreadSleep);
            PageAnalyze getter=new PageAnalyze(PageViewSave);
            getter.whileAnalyzeFullPage();
            if(Main.printTestInfo){
                if(Main.print_itemList){
                    getter.getWishedClass().printItemsList();
                }
                if(Main.print_analyze){
                    getter.getWishedClass().printAnalyzeData();
                }
                if(Main.print_count){
                    getter.getWishedClass().printItemCountList();
                }
            }

            if(Main.xmlFileName!=null){
                com.miuul.Data.Out.XML xmlWritter=new XML();
                xmlWritter.WriteToFile(Main.xmlFileName,getter.getWishedClass());
            }

            if(Main.chartFileSavePath!=null){
                Chart temp=new Chart();
                temp.CreateChart(getter.getWishedClass(), chartFileSavePath);
                System.exit(-1);
            }

        }catch(Exception exp){
            System.err.println(exp.toString());
            System.exit(-1);
        }

    }
}
