package com.demo.subtotal;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountEnergy {
    private static String path1 = "C:\\工作文件\\智慧所\\2021_04_06_肖哥_世博园300号\\energyData.xlsx";
    private static String path2 = "C:\\工作文件\\智慧所\\2021_04_06_肖哥_世博园300号\\3月能耗明细.xls";
    private static String path3 = "C:\\工作文件\\智慧所\\2021_04_06_肖哥_世博园300号\\";

    public static String[][] getDataFromExcel(String path, String sheetName) {

        ExcelData excelData = new ExcelData(path, sheetName);

        String data[][] = excelData.getExcelDataExceptHeaders();
        // excelData.printData(data);
        return data;
    }

    public static String[][] getDataFromExcel(String path, int sheetnum) {
        ExcelData excelData = new ExcelData(path, 0);

        String data[][] = excelData.getExcelDataExceptHeaders();
        // excelData.printData(data);
        return data;
    }

    private static String[][] getData(String[][] data, String[][] data1) {

        String[][] dataFinal = new String[data1.length][3];
        int i = 0;

        Map<String, String[]> map = getMap(data1);



    /*    for(String[] dataTem : data){
            if(dataTem[1]!=null&&map.containsKey(dataTem[1])){
                dataFinal[i][0] = dataTem[1];
                dataFinal[i][1] = dataTem[5];
                dataFinal[i][2] = dataTem[4];
                i++;
            }
        }*/

        for (String[] dataTem : data1) {
            for (String[] dataEnergy : data) {
                // System.out.println(dataTem[0]+"  "+dataEnergy[1]);
                if (dataTem[0] != null && dataEnergy[1] != null) {
                    if (dataTem[0].equals(dataEnergy[1])) {
                        dataFinal[i][2] = dataTem[0];
                        dataFinal[i][1] = dataEnergy[5];
                        dataFinal[i][0] = dataEnergy[4];
                        break;
                    } else {
                        dataFinal[i][2] = dataTem[0];
                        dataFinal[i][1] = "nothing";
                        dataFinal[i][0] = "nothing";
                    }

                }
            }
            i++;
        }

        System.out.println(i);
        return dataFinal;
    }

    private static Map<String, String[]> getMap(String[][] data1) {
        Map<String, String[]> map = new HashMap<String, String[]>();
        for (String[] tem : data1) {
            String key = tem[0];
            if (!map.containsKey(key)) {
                map.put(key, tem);
                System.out.println(key);
            }
        }
        return map;
    }

    public static void writeToExcel(String[][] data, String path, String sheetName) throws IOException {
        WriteToExcel wte = new WriteToExcel();

        wte.writeToString(path, sheetName, data);
    }

    public static void main(String[] args) throws ParseException, IOException {
        System.out.println("start");


        ExcelData excelData = new ExcelData();


       // String sheetname1 = "商务委";

        //String sheetname1 = "咖啡办";

       //String sheetname1 = "合作交流办";

        ////////////////////

       // String sheetname1 = "民宗委";

      //  String sheetname1 = "房管局";

        //String sheetname1 = "民政局";

        //String sheetname1 = "经信党委";

      //   String sheetname1 = "经信委";

     //   String sheetname1 = "卫计委";

//  String sheetname1 = "审计局";

    //  String sheetname1 = "人社局";

          //String sheetname1 = "交通委";

        // String sheetname1 = "知产局办事大厅";

        String sheetname1 = "知产局";/**/






        String[][] data = getDataFromExcel(path1, 0);

       // String[][] data1 = getDataFromExcel(path2, "商务委");

        String[][] data1 = getDataFromExcel(path2, sheetname1);


        //excelData.printData(data1);

        //excelData.printData(data);

        String[][] dataFinal = getData(data, data1);

        writeToExcel(dataFinal, path3+sheetname1+".xls", "结果");


        System.out.println("end");

    }


}
