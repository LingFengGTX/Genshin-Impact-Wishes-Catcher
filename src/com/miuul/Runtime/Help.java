package com.miuul.Runtime;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Properties;

public class Help {

    public String getHelpString() throws IOException{

        BufferedReader streamReader=new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("help.txt"),"UTF-8"));
        String ContentString=null;
        String OutString="";
        while((ContentString= streamReader.readLine())!=null){
            OutString+=(ContentString+"\n");
        }
        return OutString;
    }
}
