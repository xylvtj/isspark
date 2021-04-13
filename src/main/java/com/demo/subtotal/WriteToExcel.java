package com.demo.subtotal;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class WriteToExcel {
    public String writeToString(String path,String sheetName,String[][] data) throws IOException {
        String status = "文件写入成功！    ";

        XSSFWorkbook mWorkbook = new XSSFWorkbook ();
        XSSFSheet mSheet = mWorkbook.createSheet(sheetName);

        // 创建Excel标题行，第一行。


        for(int i=0;i<data.length;i++){
            XSSFRow headRow = mSheet.createRow(i);
            for(int j=0;j<data[0].length;j++){
                headRow.createCell(j).setCellValue(data[i][j]);
                //str = str + data[i][j] +"   ";
            }
        }


        //File xlsFile = new File("C:\\工作文件\\智慧所\\2021_03_17_叶大师_上海中心水务\\subTotal.xls");
        FileOutputStream xlsFile = new FileOutputStream (path);
        mWorkbook.write(xlsFile);// 或者以流的形式写入文件 mWorkbook.write(new FileOutputStream(xlsFile));
        mWorkbook.close();
        return status;
    }

    public static void main(String[] args) throws Exception {



    }

    // 创建Excel的一行数据。
    private static void createCell(int id, String name, String gender, int age, HSSFSheet sheet) {
        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);

        dataRow.createCell(0).setCellValue(id);
        dataRow.createCell(1).setCellValue(name);
        dataRow.createCell(2).setCellValue(gender);
        dataRow.createCell(3).setCellValue(age);
    }


}