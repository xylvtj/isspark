package com.demo.javaSwingTestLuxy;

import com.demo.javaSwingTestLuxy.BrowseAction;
import com.demo.poi.EnergyConsumptionGrade;
import com.demo.poi.WriteToJason;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.List;

public class MainForm extends JFrame {
    /**
     * 构造界面
     *
     * @author 1109030125
     */
    private static final long serialVersionUID = 1L;
    /* 主窗体里面的若干元素 */
    private JFrame mainForm = new JFrame("分项工具2.0"); // 主窗体，标题为“TXT文件加密”

    public static JButton buttonBrowseSource = new JButton("选择导入excel文件"); // 浏览按钮

    public static JButton buttonTxtPath = new JButton("选择生成txt文件的目录"); // 浏览按钮

    public static JButton buttonenter = new JButton("生成txt"); // 浏览按钮

   private JTextField textField1 = new JTextField(3);

    private JTextField textField2 = new JTextField(3);

    private JTextField textField3 = new JTextField(3);

    private String excelpath = "";

    private String txtpath = "";


    public String getExcelpath() {
        return excelpath;
    }

    public void setExcelpath(String excelpath) {
        this.excelpath = excelpath;
    }

    public MainForm() {


        buttonBrowseSource.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (e.getSource().equals(com.demo.javaSwingTestLuxy.MainForm.buttonBrowseSource)) {
                    JFileChooser fcDlg = new JFileChooser();
                    fcDlg.setDialogTitle("请选择需要导入的excel文件...");
                    FileNameExtensionFilter filter = new FileNameExtensionFilter(
                            "文本文件(*.xls;*.xlsx;*.xlsm)", "xls","xlsx","xlsm");
                    fcDlg.setFileFilter(filter);
                    int returnVal = fcDlg.showOpenDialog(null);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        excelpath = fcDlg.getSelectedFile().getPath();
                        String name = fcDlg.getSelectedFile().getName();
                         int length  = name.length();
                        txtpath = excelpath.substring(0,excelpath.length()-length);

                        System.out.println(txtpath);
                    }
                }

            }
        });

        buttonenter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(com.demo.javaSwingTestLuxy.MainForm.buttonenter)) {
                    int beginRowNumber;
                    int lastGradecolumn;
                    int DeviceColumnNumber;
                    beginRowNumber = Integer.parseInt(textField1.getText());
                    lastGradecolumn = Integer.parseInt(textField3.getText());
                    DeviceColumnNumber = Integer.parseInt(textField2.getText());


                    System.out.println(beginRowNumber);
                    System.out.println(lastGradecolumn);
                    System.out.println(DeviceColumnNumber);


                    WriteToJason WTJ = new WriteToJason();
                    WTJ.setPath(excelpath);
                    List<EnergyConsumptionGrade> list = WTJ.getData(beginRowNumber,lastGradecolumn,DeviceColumnNumber);
                    WTJ.writeToXml(list, txtpath);
                }
            }
        });

        /*
         * 同时显示文本和图片
         */
        JLabel label03 = new JLabel();
        label03.setText("");
        Dimension d = new Dimension(240,310);

        label03.setPreferredSize(d);


        String realPath = MainForm.class.getClassLoader().getResource("")
                .getFile();
       // java.io.File file = new java.io.File(realPath);
        //realPath = file.getParentFile().getAbsolutePath(); //获取jar包的上级目录
        try{
            realPath = URLDecoder.decode(realPath, "UTF-8");
        }catch(Exception e){
            System.out.println(e);
        }
        System.out.println(realPath);
        //label03.setIcon(new ImageIcon("C:\\liuzhiyuan222.jpg"));
        label03.setIcon(new ImageIcon(realPath+"liuzhiyuan222.jpg"));
        label03.setHorizontalTextPosition(SwingConstants.CENTER);   // 水平方向文本在图片中心
        label03.setVerticalTextPosition(SwingConstants.BOTTOM);     // 垂直方向文本在图片下方
        label03.setText("刘志远的分项工具2.0");

        //文本输入框 输入数据起始行数 设备号列数 末项分级列数
        textField1.setDocument(new NumberTextField());
        textField1.setHorizontalAlignment(JTextField.LEFT);
        JLabel textFieldLab1=new JLabel("输入数据起始行数:");

        textField2.setDocument(new NumberTextField());
        textField2.setHorizontalAlignment(JTextField.LEFT);
        JLabel textFieldLab2=new JLabel("           设备号列数:");

        textField3.setDocument(new NumberTextField());
        textField3.setHorizontalAlignment(JTextField.LEFT);
        JLabel textFieldLab3=new JLabel("        末项分级列数:");

        // 创建第一个水平箱容器
        Box hBox01 = Box.createHorizontalBox();
        hBox01.add(textFieldLab1);
        hBox01.add(textField1);

       // hBox01.add(Box.createHorizontalGlue()); // 添加一个水平方向胶状的不可见组件，撑满剩余水平空间

        // 创建第二水平箱容器
        Box hBox02 = Box.createHorizontalBox();
        hBox02.add(textFieldLab2);
        hBox02.add(textField2);

       // hBox02.add(Box.createHorizontalGlue()); // 添加一个水平方向胶状的不可见组件，撑满剩余水平空间

        Box hBox03 = Box.createHorizontalBox();
        hBox03.add(textFieldLab3);
        hBox03.add(textField3);

       // hBox03.add(Box.createHorizontalGlue()); // 添加一个水平方向胶状的不可见组件，撑满剩余水平空间

        Box hBox04 = Box.createHorizontalBox();
        hBox04.add(buttonBrowseSource);
       // hBox04.add(Box.createHorizontalGlue()); // 添加一个水平方向胶状的不可见组件，撑满剩余水平空间

      /*  Box hBox05 = Box.createHorizontalBox();
        hBox05.add(buttonTxtPath);
        hBox05.add(Box.createHorizontalGlue()); // 添加一个水平方向胶状的不可见组件，撑满剩余水平空间*/

        Box hBox06 = Box.createHorizontalBox();
        hBox06.add(buttonenter);
       // hBox06.add(Box.createHorizontalGlue()); // 添加一个水平方向胶状的不可见组件，撑满剩余水平空间

        Box hBox07 = Box.createHorizontalBox();
        hBox07.add(label03);
        //hBox07.add(Box.createHorizontalGlue()); // 添加一个水平方向胶状的不可见组件，撑满剩余水平空间


        // 创建一个垂直箱容器，放置上面两个水平箱（Box组合嵌套）
        Box vBox = Box.createVerticalBox();
        vBox.add(hBox01);
        vBox.add(hBox02);
        vBox.add(hBox03);
        vBox.add(hBox04);
       // vBox.add(hBox05);
        vBox.add(hBox06);
        vBox.add(hBox07);
        mainForm.setContentPane(vBox);
        mainForm.setSize(300, 500);
        mainForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);// 设置主窗体关闭按钮样式
        mainForm.setLocationRelativeTo(null);// 设置居于屏幕中央
        mainForm.setResizable(false);// 设置窗口不可缩放
        mainForm.setVisible(true);// 显示窗口
        mainForm.pack();
    }
    class NumberTextField extends PlainDocument {
        public NumberTextField() {
            super();
        }

        public void insertString(int offset, String str, AttributeSet attr)
                throws javax.swing.text.BadLocationException {
            if (str == null) {
                return;
            }

            char[] s = str.toCharArray();
            int length = 0;
            // 过滤非数字
            for (int i = 0; i < s.length; i++) {
                if ((s[i] >= '0') && (s[i] <= '9')) {
                    s[length++] = s[i];
                }
                // 插入内容
                super.insertString(offset, new String(s, 0, length), attr);
            }
        }
    }

    public static void main(String args[]) {

        new MainForm();
    }

    public JTextField getTextField1() {
        return textField1;
    }

    public void setTextField1(JTextField textField1) {
        this.textField1 = textField1;
    }

    public JTextField getTextField2() {
        return textField2;
    }

    public void setTextField2(JTextField textField2) {
        this.textField2 = textField2;
    }

    public JTextField getTextField3() {
        return textField3;
    }

    public void setTextField3(JTextField textField3) {
        this.textField3 = textField3;
    }
}

