package com.miuul.Runtime;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Help {
    public void echoHelp() throws IOException{
        BufferedReader streamReader=new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("help.txt")));
        String ContentString=null;
        while((ContentString= streamReader.readLine())!=null){
            System.out.println(ContentString);
        }
    }
}
