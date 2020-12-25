package com.demo.javaSwingTestLuxy;

import com.demo.javaSwingTest.MainForm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
public class BrowseAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(com.demo.javaSwingTestLuxy.MainForm.buttonBrowseSource)) {
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
        }
    }
}

