package com.miuul;

import com.miuul.Runtime.Help;

public class MainW {
    public static void main(String[] args){
        Help.isCommandVersion=false;//禁用命令行帮助
        com.miuul.Runtime.Gui.show();
    }
}
