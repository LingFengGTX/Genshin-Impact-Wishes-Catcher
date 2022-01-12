package com.miuul;

import com.miuul.Analyze.*;
import com.miuul.Data.Out.*;
import com.miuul.Runtime.HelpGui;
import com.miuul.Runtime.LoadWindowGui;
import com.miuul.Runtime.Version;

public class Main {
    //-------预设属性-------//
    private static String webURL=null; //目标URL
    private static DataPageClient PageViewSave=null; //目标地址
    public static long ThreadSleep=1000; //线程等待时间，此数值可由用户自行设定。防止后台数据未更新完毕导致数据加载失败。
    private static ClientType viewer=ClientType.defaultType;   //设置访问目标网页的浏览器类型
    private static String xmlFileName=null; //生成目标xml文件位置
    private static Boolean printTestInfo=false; //是否打印信息
    private static PageNavigate.WishedType getPool= PageNavigate.WishedType.NoType;
    private static Boolean print_count=false; //是否打印物品对应的数量
    private static Boolean print_analyze=false; //是否打印分析出的数据结果
    private static Boolean print_itemList=false; //是否打印物品列表
    private static String chartFileSavePath=null; //图表位置
    private static Boolean UseGui=false; //是否以图形模式展示
    private static Boolean IsLoadWithGui=false;//是否以图形化方式加载数据并显示


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
                    System.out.println(Version.VERSION);
                    System.exit(0);
                }

                if(cmdString[0].equals("-version-about")){
                    com.miuul.Runtime.AboutGui.show();
                }

                if(cmdString[0].equals("-gui")){
                    if(Main.IsLoadWithGui){
                        new Exception("不能同时使用 -gui 和 -gui-load命令.");
                    }
                    Main.UseGui=true;
                    break;
                }

                if(cmdString[0].equals("-gui-load")){
                    if(Main.UseGui){
                        new Exception("不能同时使用 -gui 和 -gui-load命令.");
                    }
                    Main.IsLoadWithGui=true;
                    break;
                }

                if(cmdString[0].equals("-chart")){
                    Main.chartFileSavePath=Main.FilePath(cmdString);
                    java.io.File DirChecker=new java.io.File(Main.chartFileSavePath);
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
                        Main.viewer = ClientType.chrome;
                    } else if (cmdString[1].equals("ie")) {
                        Main.viewer = ClientType.IE;
                    } else if (cmdString[1].equals("edge")) {
                        Main.viewer = ClientType.Edge;
                    } else if (cmdString[1].equals("firefox")) {
                        Main.viewer = ClientType.firefox;
                    } else if (cmdString[1].equals("firefox78")) {
                        Main.viewer = ClientType.firefox78;
                    } else if (cmdString[1].equals("default")) {
                        Main.viewer = ClientType.defaultType;
                    } else {
                        Main.viewer = ClientType.defaultType;
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

    private static void LoadWithGui(){
        LoadWindowGui.show(Main.webURL,Main.getPool,Main.ThreadSleep);
        System.exit(0);
    }

    public static void main(String[] args){
	// write your code here
        try {

            if(args.length!=0){
                Main.AnalyzeCommand(args);
            }
            if(Main.IsLoadWithGui){
                LoadWithGui();
            }
            if(Main.UseGui){
                com.miuul.Runtime.Gui.show();
                System.exit(0);
            }

            //引用 WebClient 并实力化然后获取目标HtmlPage对象
            PageNavigate.InitWebPage(PageViewSave=new DataPageClient(),webURL,viewer,ThreadSleep);
            Thread.sleep(ThreadSleep);
            if(Main.getPool== PageNavigate.WishedType.NoType){
                throw new Exception("当前没有选择祈愿类型。请使用 -help 命令查看获取祈愿池的类型");
            }
            PageNavigate.selectWished(PageViewSave,Main.getPool,ThreadSleep);
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
                xmlWritter.WriteToFile(getter.getWishedClass(),Main.xmlFileName);
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
