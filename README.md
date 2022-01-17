# Genshin Impact Wishes Catcher

## 1.描述:

​		用来获取玩家在游戏中的祈愿信息，还可以导出XML文件和图表页(HTML)，在使用方面该程序提供了两种使用方式，你可以使用快捷的命令行或者使用更易于操作的图形模式，由于是基于Java编写的，因此可以实现跨平台运行。



## 2.要求

​		你可以前往 [Java下载页](https://www.java.com/)下载最新的Java运行时环境。或者使用JDK8及以上的版本来运行此工具。

​		**特别注意**：如果使用JDK来运行本工具，建议使用 JDK8来运行。因为在JDK11及以后的版本中不会有javafx框架，使用图形模式会报错。要解决此问题可前往[openjfx官方网站](https://openjfx.cn/)，下载javafx框架，然后在终端键入以下命令来启动图形模式:

```shell
export PATH_TO_FX=path/to/javafx-sdk/lib
java -jar --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml WishesCatcher.jar -gui
```

## 3.使用方法	

1. 下载本工具并安装好Java环境。		
2. 在终端中键入以下命令查看工具是否可以正常工作。

```shell
java -jar WishesCatcher.jar -version
```

3. 你也可以使用以下命令启动图形模式

   ```shell
   java -jar WishesCatcher.jar -gui
   ```

## 4.命令行

​		本工具支持以下命令：（🔍查看具体使用请在工具中使用 -help 或 -gui-help查看详情）

​		

| 命令         | 描述                                                         |
| ------------ | ------------------------------------------------------------ |
| -help        | 显示所有帮助信息。                                           |
| -gui-help    | 以图形方式显示帮助信息。                                     |
| -gui         | 以图形方式启动。                                             |
| -gui-load    | 以图形化方式替代命令行模式，分析完成后直接使用图形界面展示结果。 |
| -gui-version | 显示版本和JVM信息对话框。                                    |
| -version     | 显示版本信息。                                               |
| -file        | 指定一个包含目标URL的纯文本文件。                            |
| -file-key    | 作用与 -file 大致相同，此命令会提取文件中的 url 。           |
| -pool        | 指定解析的祈愿池。                                           |
| -print       | 打印数据至控制台。                                           |
| -time        | 设置解析延迟，用于处理网络不好时不能获取目标数据。           |
| -xml         | 将分析结果保存至 XML 文档。                                  |
| -chart       | 将分析结果保存至图表页(HTML)                                 |



## 5.其他引用

​		本工具除了使用 Javafx框架，同时引用了[HtmlUnit(点击访问)](https://github.com/HtmlUnit/htmlunit)。在此特别注明。

