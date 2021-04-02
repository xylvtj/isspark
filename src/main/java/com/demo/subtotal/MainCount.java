package com.demo.subtotal;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainCount {
    private String pathData = "C:\\工作文件\\智慧所\\2021_03_17_叶大师_上海中心水务\\waterdata.xlsx";
    private String pathRalation = "C:\\工作文件\\智慧所\\2021_03_17_叶大师_上海中心水务\\上海中心水务设备分类_陆兴宇_2021_03_17.xlsx";
    private String pathOut1 = "C:\\工作文件\\智慧所\\2021_03_17_叶大师_上海中心水务\\subTotal.xls";

    private String pathOut2 = "C:\\工作文件\\智慧所\\2021_03_17_叶大师_上海中心水务\\subType.xls";

    //从excel中读取分类与数据的映射关系
    public Map<String, String> getRelationFromExcel() {
        ExcelData excelData = new ExcelData(pathRalation, "");

        String data[][] = excelData.getExcelDataExceptHeaders();

        Map<String, String> map = new HashMap<String, String>();

        for (String row[] : data) {
            map.put(row[0], row[1]);
        }

        //excelData.printData(data);
        return map;
    }

    //从excel中读取待分类汇总的数据
    public String[][] getDataFromExcel() {
        ExcelData excelData = new ExcelData(pathData, "");

        String data[][] = excelData.getExcelDataExceptHeaders();
        // excelData.printData(data);
        return data;
    }


    public static String getId(String str) {
        String id = "";
        int idx = str.lastIndexOf("-");
        if (idx < str.length()) {
            id = str.substring(idx + 1, str.length());
            //System.out.println(id);
        }

        return id;
    }


    public Map<String, List<String>> countSubTotal(String[][] data, Map<String, String> relationMap) {
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        for (String[] row : data) {
            //System.out.println(row[0]);
            if (row[0] == null) {
                continue;
            } else {
                String id = getId(row[0]);
                String type = "";
                if (relationMap.containsKey(id)) {
                    type = relationMap.get(id);
                } else {
                    System.out.println(id);
                    type = "其他";
                }


                List<String> list;
                if (map.containsKey(type)) {
                    list = map.get(type);

                    for (int i = 1; i < row.length; i++) {
                        String total = list.get(i);
                        Double num;
                        Double numTotal;
                        try {
                            numTotal = Double.parseDouble(total);
                            num = Double.parseDouble(row[i]);
                            numTotal = numTotal + num;
                            total = numTotal + "";
                            list.set(i, total);
                        } catch (Exception e) {
                        }
                    }


                } else {
                    list = new ArrayList<String>();
                    list.add(type);
                    for (int i = 1; i < row.length; i++) {
                        list.add(row[i]);
                        // list.set(i,row[i]);
                    }
                }
                map.put(type, list);
            }

        }
        // System.out.println(groovy.json.JsonOutput.toJson(map));
        return map;
    }

    public void printMap(Map<String, List<String>> map) {

        for (String key : map.keySet()) {
            List<String> list = map.get(key);
            System.out.println("key-----" + key);
            String str = "";
            for (String num : list) {
                str = str + num + "   ";
            }
            System.out.println("str-----" + str);
        }
    }

    public void writeToExcel(Map<String, List<String>> map, String path) throws IOException {
        WriteToExcel wte = new WriteToExcel();

        //加表头
        map = addHeaders(map);
        //将map转化为数组
        String data[][] = getDataFromMap(map);

        wte.writeToString(path, "", data);
    }

    public void writeToExcel(String[][] dataFinal, String path) throws IOException {
        WriteToExcel wte = new WriteToExcel();

        wte.writeToString(path, "", dataFinal);
    }

    private static String[][] getDataFromMap(Map<String, List<String>> map) {
        List<String> header = map.get("headers");
        int length = map.size();
        int width = header.size();
        String data[][] = new String[length][width];
        int i = 0;
        for (String head : header) {


            data[0][i] = head;

            i++;
        }
        int p = 1;
        for (String key : map.keySet()) {
            if (key.equals("headers")) {
                continue;
            } else {
                List<String> list = map.get(key);
                //  list.set(0,key);
                for (int j = 0; j < list.size(); j++) {
                   /* if(j == 0){
                        data[p][j] = key;
                    }else{

                    }*/
                    data[p][j] = list.get(j);
                }
                p++;
            }

        }


        return data;
    }

    public static Map<String, List<String>> addHeaders(Map<String, List<String>> map) {
        List<String> list = new ArrayList<>();

        list.add("类别");

        list.add("2020/7/1");
        list.add("2020/8/1");
        list.add("2020/9/1");
        list.add("2020/10/1");
        list.add("2020/11/1");
        list.add("2020/12/1");
        list.add("2021/1/1");
        list.add("2021/2/1");

        map.put("headers", list);
        return map;
    }

    public String[][] getSubType(String[][] data, Map<String, String> relationMap) {


        Map<String, List<List<String>>> map = new HashMap<String, List<List<String>>>();
        for (String[] row : data) {
            //System.out.println(row[0]);
            if (row[0] == null) {
                continue;
            } else {
                String name  = row[0];
                String id = getId(name);
                String type = "";
                if (relationMap.containsKey(id)) {
                    type = relationMap.get(id);
                } else {
                    System.out.println(id);
                    type = "其他";
                }


                List<List<String>> listSum = new ArrayList<List<String>>();
                if (map.containsKey(type)) {
                    listSum = map.get(type);

                    List<String> listTotal = listSum.get(0);

                    List<String> list = new ArrayList<String>();
                    list.add(type);
                    list.add(name);
                    list.add(id);

                    for (int i = 1; i < row.length; i++) {
                        String total = listTotal.get(i+2);
                        Double num;
                        Double numTotal;
                        try {
                            numTotal = Double.parseDouble(total);
                            num = Double.parseDouble(row[i]);
                            numTotal = numTotal + num;
                            total = numTotal + "";
                            listTotal.set(i+2, total);
                        } catch (Exception e) {
                        }
                        list.add(row[i]);




                    }
                    listSum.set(0,listTotal);
                    listSum.add(list);
                } else {

                    List<String> listTotal = new ArrayList<String>();
                    List<String> list = new ArrayList<String>();
                    list.add(type);
                    list.add(name);
                    list.add(id);
                    listTotal.add(type);
                    listTotal.add("总量");
                    listTotal.add("");
                    for (int i = 1; i < row.length; i++) {
                        list.add(row[i]);
                        listTotal.add(row[i]);
                        // list.set(i,row[i]);
                    }
                    listSum.add(listTotal);
                    listSum.add(list);
                }
                map.put(type, listSum);
            }

        }

        int rowNumbers = 0;
        int length = 0;
        for(String type : map.keySet()){
            List<List<String>> listSum = map.get(type);
            rowNumbers += listSum.size();
            List<String> list = listSum.get(0);
            length = list.size();
        }

        String dataFinal[][] = new String[rowNumbers][length];
        int lastPosition = 0;
        for(String type : map.keySet()){
            System.out.println(type);
            List<List<String>> listSum = map.get(type);



            int j = listSum.size();

            for(int i = lastPosition+1; i<lastPosition+j; i++){

                List<String> listTem = listSum.get(i-lastPosition);

                for(int p = 0;p<listTem.size();p++){

                    dataFinal[i-1][p] = listTem.get(p);
                }

                for(int p = 0;p<listTem.size();p++){

                    dataFinal[i][p] = listSum.get(0).get(p);
                }


            }
            lastPosition = lastPosition + j;

         /*   for(){

            }*/
        }
dataFinal = getHeaders(dataFinal);

        return dataFinal;
    }

    private String[][] getHeaders(String[][] dataFinal) {
        int length = dataFinal.length;
        int width = dataFinal[0].length;
        String dataHeaders[][] = new String[length+1][width];
     /*   list.add("类别");

        list.add("2020/7/1");
        list.add("2020/8/1");
        list.add("2020/9/1");
        list.add("2020/10/1");
        list.add("2020/11/1");
        list.add("2020/12/1");
        list.add("2021/1/1");
        list.add("2021/2/1");*/
        dataHeaders[0][0] = "类别";
        dataHeaders[0][1] = "名称";
        dataHeaders[0][2] = "ID";
        dataHeaders[0][3] = "2020/7/1";
        dataHeaders[0][4] = "2020/8/1";
        dataHeaders[0][5] = "2020/9/1";
        dataHeaders[0][6] = "2020/10/1";
        dataHeaders[0][7] = "2020/11/1";
        dataHeaders[0][8] = "2020/12/1";
        dataHeaders[0][9] = "2021/1/1";
        dataHeaders[0][10] = "2021/2/1";

        for(int i = 0; i<length; i++){
            dataHeaders[i+1] = dataFinal[i];
        }
        return dataHeaders;
    }


    public static void main(String[] args) throws ParseException, IOException {
        MainCount mc = new MainCount();

        // mc.getId("诚品书店1-诚品区域-DN50-190493282");
        //得到关系表中的数据 返回map
        Map<String, String> relationMap = mc.getRelationFromExcel();
        //取得待分类表中的数据(除表头)
        String[][] data = mc.getDataFromExcel();


        String[][] dataFinal = mc.getSubType(data, relationMap);

        //写入到excel中
        mc.writeToExcel(dataFinal, mc.getPathOut2());

        //分类汇总 并输出到excel
        Map<String, List<String>> map = mc.countSubTotal(data, relationMap);
       // mc.printMap(map);
         mc.writeToExcel(map,mc.getPathOut1());

    }

    public String getPathData() {
        return pathData;
    }

    public void setPathData(String pathData) {
        this.pathData = pathData;
    }

    public String getPathRalation() {
        return pathRalation;
    }

    public void setPathRalation(String pathRalation) {
        this.pathRalation = pathRalation;
    }

    public String getPathOut1() {
        return pathOut1;
    }

    public void setPathOut1(String pathOut1) {
        this.pathOut1 = pathOut1;
    }

    public String getPathOut2() {
        return pathOut2;
    }

    public void setPathOut2(String pathOut2) {
        this.pathOut2 = pathOut2;
    }
}
