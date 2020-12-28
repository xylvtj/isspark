package com.demo.javaSwingTestLuxy;

import com.demo.poi.WriteToJason;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
public class BrowseAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(com.demo.javaSwingTestLuxy.MainForm.buttonBrowseSource)) {
            JFileChooser fcDlg = new JFileChooser();
            fcDlg.setDialogTitle("请选择需要导入的excel文件...");
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "文本文件(*.xls;*.xlsx)", "xls","xlsx");
            fcDlg.setFileFilter(filter);
            int returnVal = fcDlg.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String filepath = fcDlg.getSelectedFile().getPath();
                String fileName = fcDlg.getSelectedFile().getName();

               // int beginRowNumber =
                System.out.println(filepath);
                System.out.println(fileName);

                WriteToJason wtj = new WriteToJason();

            }
        }
        if (e.getSource().equals(com.demo.javaSwingTestLuxy.MainForm.buttonBrowseSource)) {
            JFileChooser fcDlg = new JFileChooser();
            fcDlg.setDialogTitle("生成txt文件");
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "文本文件(*.xls;*.xlsx)", "xls","xlsx");
            fcDlg.setFileFilter(filter);
            int returnVal = fcDlg.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String filepath = fcDlg.getSelectedFile().getPath();
                String fileName = fcDlg.getSelectedFile().getName();

                // int beginRowNumber =
                System.out.println(filepath);
                System.out.println(fileName);

                WriteToJason wtj = new WriteToJason();

            }
        }
    }
}

