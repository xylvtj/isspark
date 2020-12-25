package com.demo.xmlTest;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class GetAllFile {

    public static void main(String[] args) {
        //路径   这里写一个路径进去
        String path = "C:\\Users\\jky-luxy\\Documents\\WeChat Files\\wxid_vxdd8vnie1iv11\\FileStorage\\File\\2020-12\\SendData\\SendData";
        //调用方法
        getFiles(path);

    }

    /**
     * 递归获bai取某路径下的所有文件，文件夹，并输出
     */
    public static void getFiles(String path) {

        File file = new File(path);
        // 如果这个路径是文件夹
        if (file.isDirectory()) {
            // 获取路径下的所有文件
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                // 如果还是文件夹 递归获取里面的文件 文件夹
                if (files[i].isDirectory()) {
                    System.out.println("目录：" + files[i].getPath());
                    getFiles(files[i].getPath());
                } else {
                    System.out.println("文件：" + files[i].getPath());
                }
            }
        } else {
            System.out.println("文件：" + file.getPath());
        }
    }

    public static Object GetBean() {
        try {
            // 创建文档对象
            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder _builder = dFactory.newDocumentBuilder();
            Document doc;
            doc = _builder.parse(new File("src/config.xml"));


            // 获取包含类的文本节点
            NodeList nl = doc.getElementsByTagName("ClassName");
            Node classNode = nl.item(0).getFirstChild();
            String name = classNode.getNodeValue();

            // 通过类名生成实例对象并将其返回
            Class c = Class.forName(name);
            Object obj = c.newInstance();
            return obj;

        } catch (Exception _ex) {
            _ex.printStackTrace();
            return null;
        }
    }
}