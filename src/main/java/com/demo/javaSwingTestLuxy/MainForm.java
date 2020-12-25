package com.demo.javaSwingTestLuxy;

import com.demo.javaSwingTestLuxy.BrowseAction;

import javax.swing.*;
import java.awt.*;
public class MainForm extends JFrame {
    /**
     * 构造界面
     *
     * @author 1109030125
     */
    private static final long serialVersionUID = 1L;
    /* 主窗体里面的若干元素 */
    private JFrame mainForm = new JFrame("luxyTest"); // 主窗体，标题为“TXT文件加密”

    public static JButton buttonBrowseSource = new JButton("浏览"); // 浏览按钮

    public MainForm() {
        mainForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);// 设置主窗体关闭按钮样式
        mainForm.setLocationRelativeTo(null);// 设置居于屏幕中央
        mainForm.setResizable(false);// 设置窗口不可缩放
        mainForm.setLayout(null);
        mainForm.setVisible(true);// 显示窗口
        /*
         * 同时显示文本和图片
         */
        JLabel label03 = new JLabel();
        label03.setText("刘志远的分项工具1.0");
        Dimension d = new Dimension(200,300);

        label03.setPreferredSize(d);
        label03.setIcon(new ImageIcon("C:\\Test\\liuzhiyuan.png"));
        label03.setHorizontalTextPosition(SwingConstants.CENTER);   // 水平方向文本在图片中心
        label03.setVerticalTextPosition(SwingConstants.BOTTOM);     // 垂直方向文本在图片下方
        JPanel panel = new JPanel(new FlowLayout());

        panel.add(label03);
        buttonBrowseSource.addActionListener(new BrowseAction());
        panel.add(buttonBrowseSource);

        mainForm.setContentPane(panel);
        mainForm.pack();



    }
    public static void main(String args[]) {

        new MainForm();
    }
}

