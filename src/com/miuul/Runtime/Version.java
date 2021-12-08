package com.miuul.Runtime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Version {
    public static final String VERSION="1.0.0.0(BETA)";
    public static boolean isCommandVersion=true;
    public static String getHelpString() throws IOException {

        BufferedReader streamReader=new BufferedReader(new InputStreamReader(Version.class.getResourceAsStream("help.txt"),"UTF-8"));
        String ContentString=null;
        String OutString="";
        while((ContentString= streamReader.readLine())!=null){
            OutString+=(ContentString+"\n");
        }
        return OutString;
    }
}
