package com.miuul.Analyze;

import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.WebWindow;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * 该类为 HtmlPage 的继承类，用于封装 HtmlPage
 */
public class DataPage extends HtmlPage{

    public DataPage(WebResponse webResponse, WebWindow webWindow) {
        super(webResponse, webWindow);
    }
}
