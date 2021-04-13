package com.demo.subtotal;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShangHaiTowerCount {
    private static String path1 = "C:\\工作文件\\智慧所\\2021_03_30_肖朋林_上海中心\\deviceSMDefine.xlsx";
    private static String path2 = "C:\\工作文件\\智慧所\\2021_03_30_肖朋林_上海中心\\电表数据汇总.xlsx";
    private static String path3 = "C:\\工作文件\\智慧所\\2021_03_30_肖朋林_上海中心\\分类汇总_readingType_1.xls";
    private static String path4 = "C:\\工作文件\\智慧所\\2021_03_30_肖朋林_上海中心\\分类汇总_readingType_2.xls";

    //从excel中读取待分类汇总的数据
    public static String[][] getDataFromExcel(String path) {
        ExcelData excelData = new ExcelData(path, "");

        String data[][] = excelData.getExcelDataExceptHeaders();
        // excelData.printData(data);
        return data;
    }

    public static String getString(Object o) {
        String str = "";
        if (o != null) {
            str = o.toString();
        }
        return str;
    }

    ;


    public static Map<String, DeviceSMDefine> getDsdFromData(String data[][]) {
        Map<String, DeviceSMDefine> map = new HashMap<String, DeviceSMDefine>();
        for (String datas[] : data) {
            DeviceSMDefine dsd = new DeviceSMDefine();
            if (datas != null) {
                dsd.setSmType(getString(datas[0]));
                dsd.setDeviceNo(getString(datas[1]));
                dsd.setDeviceUsage(getString(datas[2]));
                dsd.setSwitchBoxNo(getString(datas[3]));
                dsd.setPower(getString(datas[4]));
                dsd.setBudgetType(getString(datas[5]));
                dsd.setSubSM_Type(getString(datas[6]));
                dsd.setThirdSM_Type(getString(datas[7]));
                dsd.setIsShare(getString(datas[8]));
                dsd.setShareTypeId(getString(datas[9]));
                dsd.setLocation(getString(datas[10]));

                dsd.setLocationDesc(getString(datas[11]));
                dsd.setIsIgnore(getString(datas[12]));

                dsd.setType(getString(datas[13]));
                dsd.setNum(getString(datas[14]));
                dsd.setTenants(getString(datas[15]));
                dsd.setHighVoltage(getString(datas[16]));
                dsd.setUnit(getString(datas[17]));
                dsd.setMeter(getString(datas[18]));
                dsd.setReadingType(getString(datas[19]));

                map.put(dsd.getDeviceNo(), dsd);
            }

        }
        return map;
    }


    /*public static void print(List<DeviceSMDefine> list ,int num){
        for(){
        }
    }*/


    public static Map<String, String[]> getMapFromData(String[][] data) {
        Map<String, String[]> map = new HashMap<String, String[]>();

        for (String datas[] : data) {
            //System.out.println(datas[0]);
            map.put(datas[0], datas);
        }

        return map;
    }


    public static Map<String, String[]> getSubType(Map<String, DeviceSMDefine> mapRalation, String[][] data1, String countType) {
        Map<String, String[]> map = new HashMap<String, String[]>();
        for (String[] dataadd : data1) {
            //计算reaadingType=1 分类汇总sm_type
            if (countType.equals("1")) {
                countType1(dataadd, mapRalation, map);
            } else if (countType.equals("2")) {
                countType2(dataadd, mapRalation, map);
            }
        }
        return map;
    }

    //计算 分类汇总sm_type z2-z6  的 非租户
    private static void countType1(String[] dataAdd, Map<String, DeviceSMDefine> mapRalation, Map<String, String[]> map) {
        String smType = "";
        String deviceId = dataAdd[0];
        System.out.println(deviceId);
        String[] dataSum = new String[12];
        if (map.isEmpty()) {
            String[] headers = new String[12];
            headers[0] = "smType";
            headers[1] = "2020/1/1  0:00:00";
            headers[2] = "2020/2/1  0:00:00";
            headers[3] = "2020/3/1  0:00:00";
            headers[4] = "2020/4/1  0:00:00";
            headers[5] = "2020/5/1  0:00:00";
            headers[6] = "2020/6/1  0:00:00";
            headers[7] = "2020/7/1  0:00:00";
            headers[8] = "2020/8/1  0:00:00";
            headers[9] = "2020/9/1  0:00:00";
            headers[10] = "2020/10/1  0:00:00";
            headers[11] = "2020/11/1  0:00:00";
            map.put("headers", headers);
        }

        if (mapRalation.containsKey(deviceId)) {
            smType = mapRalation.get(deviceId).getSmType();
            System.out.println(smType);
            //判断条件 计算 分类汇总sm_type z2-z6  的 非租户
            String location = mapRalation.get(deviceId).getLocation();
            System.out.println(location);
//||location.equals("Z3")||location.equals("Z4")||location.equals("Z5")||location.equals("Z6")
           // if ((!smType.equals("租户"))&(location.equals("Z6"))) {
                if (true) {
                System.out.println("符合");
                //if (true) {
                if (!smType.equals("")) {
                    if (map.containsKey(smType)) {
                        dataSum = map.get(smType);
                        try {
                            // for (int i = 1; i < dataSum.length; i++) {
                            for (int i = 1; i < dataSum.length; i++) {
                                Double sum = Double.parseDouble(dataSum[i]);
                                Double add = Double.parseDouble(dataAdd[i]);
                                sum = sum + add;
                                dataSum[i] = sum + "";
                                //System.out.println(dataAdd[i]+"--"+dataSum[i]+"--"+smType);
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    } else {
                        dataSum = dataAdd;
                        dataSum[0] = smType;
                    }
                    map.put(smType, dataSum);
                }
            }else{
                System.out.println("不符合");
            }
        } else {
            System.out.println("不在列表中----" + deviceId);
        }
    }

    //计算reaadingType=2 分类汇总sm_type
    private static void countType2(String[] dataAdd, Map<String, DeviceSMDefine> mapRalation, Map<String, String[]> map) {
        String smType = "";

        String deviceId = dataAdd[0];
        String[] dataSum = new String[12];
        if (map.isEmpty()) {
            String[] headers = new String[12];
            headers[0] = "smType";
            headers[1] = "2020/1/1  0:00:00";
            headers[2] = "2020/2/1  0:00:00";
            headers[3] = "2020/3/1  0:00:00";
            headers[4] = "2020/4/1  0:00:00";
            headers[5] = "2020/5/1  0:00:00";
            headers[6] = "2020/6/1  0:00:00";
            headers[7] = "2020/7/1  0:00:00";
            headers[8] = "2020/8/1  0:00:00";
            headers[9] = "2020/9/1  0:00:00";
            headers[10] = "2020/10/1  0:00:00";
            headers[11] = "2020/11/1  0:00:00";
            map.put("headers", headers);
        }
        if (mapRalation.containsKey(deviceId)) {
            smType = mapRalation.get(deviceId).getSmType();
            if (mapRalation.get(deviceId).getReadingType().equals("2")) {
                //if (true) {
                if (map.containsKey(smType)) {
                    dataSum = map.get(smType);
                    try {
                        for (int i = 1; i < dataSum.length; i++) {
                            Double sum = Double.parseDouble(dataSum[i]);
                            Double add = Double.parseDouble(dataAdd[i]);
                            sum = sum + add;
                            dataSum[i] = sum + "";
                            //System.out.println(dataAdd[i]+"----"+dataSum[i]);
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                } else {
                    dataSum = dataAdd;
                    dataSum[0] = smType;
                }
                if (!smType.equals("")) {
                    map.put(smType, dataSum);
                }

            }
        } else {
            System.out.println("不在列表中----" + deviceId);
        }


    }

    private static String[][] getDataFromMap(Map<String, String[]> map) {
        String[] header = map.get("headers");
        int length = map.size();
        int width = header.length;
        String data[][] = new String[length][width];
        int i = 0;
        data[0] = header;
        int p = 1;
        for (String key : map.keySet()) {
            if (key.equals("headers")) {
                continue;
            } else {
                data[p] = map.get(key);
            }
            p++;
        }


        return data;
    }

    public void writeToExcel(Map<String, String[]> map, String path) throws IOException {

        WriteToExcel wte = new WriteToExcel();

        //加表头
        // map = addHeaders(map);
        //将map转化为数组
        String data[][] = getDataFromMap(map);

        wte.writeToString(path, "", data);
    }


    public static void main(String[] args) throws ParseException, IOException {
        System.out.println("start");




        ShangHaiTowerCount stc = new ShangHaiTowerCount();
        String[][] data = getDataFromExcel(path1);
        String[][] data1 = getDataFromExcel(path2);
        Map<String, DeviceSMDefine> map = getDsdFromData(data);
        //System.out.print(list);
        // Map<String, String[]> mapData = getMapFromData(data1);

        //计算reaadingType=1 分类汇总sm_type
        Map<String, String[]> subMap1 = getSubType(map, data1, "1");
        //计算reaadingType=2 分类汇总sm_type
        // Map<String, String[]> subMap2 = getSubType(map, data1, "2");

        stc.writeToExcel(subMap1, path3);
        //stc.writeToExcel(subMap2, path4);
    }
}
