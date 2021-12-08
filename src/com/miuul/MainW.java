package com.miuul;

import com.miuul.Runtime.Version;

public class MainW {
    public static void main(String[] args){
        Version.isCommandVersion=false;//禁用命令行帮助
        com.miuul.Runtime.Gui.show();
    }
}
