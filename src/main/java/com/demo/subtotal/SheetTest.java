package com.demo.subtotal;

import java.io.IOException;
import java.text.ParseException;

public class SheetTest {

    private String path = "C:\\工作文件\\智慧所\\2021_04_13_顾老\\新建文件夹\\2011居住电量.xls";
    private String path1 = "C:\\工作文件\\智慧所\\2021_04_13_顾老\\新建文件夹\\2011居住电量_总和.xlsx";


    private static String[][] getAllSheetsData(String pathRalation) {
        ExcelData excelData = new ExcelData(pathRalation);
        String data[][] = excelData.getAllSheetsData();
        return data;
    }

    public void writeToExcel(String[][] dataFinal, String path,String sheetName) throws IOException {
        System.out.println("写入文件中");
        WriteToExcel wte = new WriteToExcel();

        String status = wte.writeToString(path, sheetName, dataFinal);
        System.out.println(status);
    }

    //写插入sql


    public static void main(String[] args) throws ParseException, IOException {
        SheetTest st = new SheetTest();
        String data[][] = st.getAllSheetsData(st.path);
        st.writeToExcel(data,st.path1,"汇总");

    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}