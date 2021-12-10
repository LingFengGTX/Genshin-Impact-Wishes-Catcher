package com.miuul.Analyze;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;

/**
 * 该类为 WebClient 的继承类，用于封装 WebClient
 */
public class AnalyzeWebClient extends WebClient {
    public AnalyzeWebClient(BrowserVersion type){
        super(type);
    }
}
