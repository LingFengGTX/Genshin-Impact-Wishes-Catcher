package com.miuul.Data.Out;

import java.io.*;
import com.miuul.Data.WishedClass;
import com.miuul.Data.WishedItem;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

public class XML {
    /**
     * 实例化一个XML类
     * 该类将根据传入的 WishedClass 输出数据至XML文件
     */
    public XML(){}
    /**
     * 该方法可将分析出的信息导出至XML文件
     * @param FileName 目标文件名
     * @param dataScore 数据源
     */
    public void WriteToFile(WishedClass dataScore,String FileName) throws Exception{
        if(dataScore.getTotalCount()==0){
            throw new Exception("当前没有数据可以导出。");
        }
        DocumentBuilderFactory XMLFactory=DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder=XMLFactory.newDocumentBuilder();
        Document xmlDoc=docBuilder.newDocument();
        //创建 XML 根节点
        Element MainRoot=xmlDoc.createElement("WishedData");
        {//输出整理数据
            Element AnalyzeData=xmlDoc.createElement("AnalyzeData");
            if(dataScore.isHasFiveStar()){
                //如果获得过五星物品则输出以下信息

                {//查询到拥有五星武器或角色的数量
                    Element ProNode = xmlDoc.createElement("FiveStarCount");
                    Text ValueNode = xmlDoc.createTextNode(String.valueOf(dataScore.getFiveStarCount()));
                    ProNode.appendChild(ValueNode);
                    AnalyzeData.appendChild(ProNode);
                }

                {//查询到最后拥有五星武器或者角色的名称
                    Element ProNode = xmlDoc.createElement("LastFiveStar");
                    Text ValueNode = xmlDoc.createTextNode(dataScore.getLastFiveStar());
                    ProNode.appendChild(ValueNode);
                    AnalyzeData.appendChild(ProNode);
                }

                {//距离上一次获得五星角色或武器有多少发
                    Element ProNode = xmlDoc.createElement("FiveStarCountIndex");
                    Text ValueNode = xmlDoc.createTextNode(String.valueOf(dataScore.getFiveStarCountIndex()));
                    ProNode.appendChild(ValueNode);
                    AnalyzeData.appendChild(ProNode);
                }
            }

            if(dataScore.isHasFourStar()){
                //如果获得过四星物品则输出以下信息

                {//查询到的物品数量
                    Element ProNode = xmlDoc.createElement("TotalCount");
                    Text ValueNode = xmlDoc.createTextNode(String.valueOf(dataScore.getTotalCount()));
                    ProNode.appendChild(ValueNode);
                    AnalyzeData.appendChild(ProNode);
                }

                {//查询到拥有四星武器或角色的数量
                    Element ProNode = xmlDoc.createElement("FourStarCount");
                    Text ValueNode = xmlDoc.createTextNode(String.valueOf(dataScore.getFourStarCount()));
                    ProNode.appendChild(ValueNode);
                    AnalyzeData.appendChild(ProNode);
                }

                {//查询到最后拥有四星武器或者角色的名称
                    Element ProNode = xmlDoc.createElement("LastFourStar");
                    Text ValueNode =null;
                    ValueNode =xmlDoc.createTextNode(dataScore.getLastFourStar());
                    ProNode.appendChild(ValueNode);
                    AnalyzeData.appendChild(ProNode);
                }

                {//距离上一次获得四星角色或武器有多少发
                    Element ProNode = xmlDoc.createElement("FourStarCountIndex");
                    Text ValueNode = xmlDoc.createTextNode(String.valueOf(dataScore.getFourStarCountIndex()));
                    ProNode.appendChild(ValueNode);
                    AnalyzeData.appendChild(ProNode);
                }
            }
            MainRoot.appendChild(AnalyzeData);
        }

        {//输出列表数据
            Element DataList=xmlDoc.createElement("DataList");
            for (WishedItem item:dataScore.getDataList()) {
                Element itemRoot=xmlDoc.createElement("Item");
                Element TypeNode=xmlDoc.createElement("Type");
                TypeNode.setTextContent(item.getItemType());
                Element NameNode=xmlDoc.createElement("Name");
                NameNode.setTextContent(item.getItemName());
                Element DateNode=xmlDoc.createElement("Date");
                DateNode.setTextContent(item.getItemDate());

                itemRoot.appendChild(TypeNode);
                itemRoot.appendChild(NameNode);
                itemRoot.appendChild(DateNode);

                DataList.appendChild(itemRoot);
            }
            MainRoot.appendChild(DataList);
        }

        //注册根节点

        xmlDoc.appendChild(MainRoot);
        TransformerFactory XMLTransformerFact=TransformerFactory.newInstance();
        Transformer formers=XMLTransformerFact.newTransformer();
        formers.transform(new DOMSource(xmlDoc),new StreamResult(new File(FileName)));
    }

}
