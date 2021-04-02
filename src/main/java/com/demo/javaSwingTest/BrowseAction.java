package com.demo.javaSwingTest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
//ddddd
public class BrowseAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(MainForm.buttonBrowseSource)) {
            JFileChooser fcDlg = new JFileChooser();
            fcDlg.setDialogTitle("请选择待加密或解密的文件...");
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "文本文件(*.txt;*.kcd)", "txt", "kcd");
            fcDlg.setFileFilter(filter);
            int returnVal = fcDlg.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String filepath = fcDlg.getSelectedFile().getPath();
               // MainForm.sourcefile.setText(filepath);
            }
        } else if (e.getSource().equals(MainForm.buttonBrowseTarget)) {
            JFileChooser fcDlg = new JFileChooser();
            fcDlg.setDialogTitle("请选择加密或解密后的文件存放目录");
            fcDlg.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal = fcDlg.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String filepath = fcDlg.getSelectedFile().getPath();
              //  MainForm.targetfile.setText(filepath);
            }
        }
    }
}

