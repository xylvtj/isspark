package com.demo.subtotal;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShanghaiTowerCount0402 {
    private static String path1 = "C:\\工作文件\\智慧所\\2021_04_02_叶大师_上海中心\\dataB1.xls";

    public static String[][] getDataFromExcelBySplit(String path) {
        ExcelData excelData = new ExcelData(path, "");

        String data[][] = excelData.getExcelData();
        // excelData.printData(data);
        return data;
    }


    public static String[][] getDataFromExcel(String path) {
        ExcelData excelData = new ExcelData(path, "");

        String data[][] = excelData.getExcelDataExceptHeaders();
        // excelData.printData(data);
        return data;
    }

    public static void print(String[][] data) {
        for (String[] temp : data) {
            String str = "";
            for (String s : temp) {
                if(s==null){
                    s = "";
                }
                str += s + "  ";
            }

            System.out.println(str);
        }

    }
    private static Map<String, List<CommonType>> subType(String[][] data) {
        Map<String, List<CommonType>> map = new HashMap<String, List<CommonType>>();
        for (String[] temp : data) {
            if (temp[0] != null) {
                List<CommonType> list = new ArrayList<CommonType>();
                String type = temp[1];
                CommonType ct = new CommonType();
                ct.setCell1(temp[0]);
                ct.setCell2(temp[2]);
                ct.setCell3(temp[3]);
                if (map.containsKey(type)) {
                    list = map.get(type);
                }
                list.add(ct);
                map.put(type, list);
            } else {
                continue;
            }
        }
        return map;
    }

    private static String[][] processe(Map<String, List<CommonType>> map) {
        String[][] data = new String[map.size()][4];
        int i = 0;
        for(String type :map.keySet()){
            CommonType[] listNew = new CommonType[2];
            CommonType ctt1 = new CommonType();
            ctt1.setCell2("9999999999");
            CommonType ctt2 = new CommonType();
            ctt2.setCell2("0");
            listNew[0]=ctt1;
            listNew[1]=ctt2;

            List<CommonType> list = map.get(type);
            for (CommonType ct : list) {
                compareCt(ct,listNew);
            }
            data[i][0] = type;
           // System.out.println(listNew[0].getCell2());
          //  System.out.println(listNew[1].getCell2());
            Double d1 = Double.parseDouble(listNew[0].getCell2());
            Double d2 = Double.parseDouble(listNew[1].getCell2());
            data[i][1] = (d2-d1)+"";
            i++;
        }
        return data;
    }

    private static void compareCt(CommonType ct, CommonType[] listNew) {
        String timeCt = ct.getCell2();
        String timeCt1 = listNew[0].getCell2();
        String timeCt2 = listNew[1].getCell2();

      //  System.out.println(timeCt1+"    "+timeCt2);
        if (compareStringAsDouble(timeCt, timeCt1).equals("1")) {
            listNew[0] = ct;
         //   System.out.println("<");
        }
        if (compareStringAsDouble(timeCt, timeCt2).equals("2")) {
            listNew[1] = ct;
          //  System.out.println(">");
        }
    }

    private static String compareStringAsDouble(String s1,String s2){
        String status = "0";
        Double d1 = Double.parseDouble(s1);
        Double d2 = Double.parseDouble(s2);

        //System.out.println("d1 = "+d1+"----d2 = "+d2);
        if(d1<d2){
            status = "1";
        }else if(d1>d2){
            status = "2";
        }
        System.out.println(status);
        return status;
    }

    public static void main(String[] args) throws ParseException, IOException {

        System.out.println("start");
        ShangHaiTowerCount stc = new ShangHaiTowerCount();
        String[][] data = getDataFromExcel(path1);
        Map<String, List<CommonType>> map = subType(data);

        String[][] processedData = processe(map);

        print(processedData);
    }




}
