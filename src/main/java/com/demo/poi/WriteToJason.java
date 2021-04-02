package com.demo.poi;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class WriteToJason {

    private final String total = "10000";//总电

    private final String totalA = "10A00";//照明插座与系统用电
    private final String totalA1 = "10A10";//室内照明与插座
    private final String totalA2 = "10A20";//公共区域照明和应急照明
    private final String totalA3 = "10A30";//室外景观照明


    private final String totalB = "10B00";//空调系统用电
    private final String totalB1 = "10B10";//冷热站
    private final String totalB2 = "10B20";//空调末端

    private final String totalC = "10C00";//动力系统用电
    private final String totalC1 = "10C10";//电梯
    private final String totalC2 = "10C20";//水泵
    private final String totalC3 = "10C30";//非空调区域的通排风设备

    private final String totalD = "10D00";//总电
    private final String totalD1 = "10D10";//信息中心
    private final String totalD2 = "10D20";//厨房餐厅
    private final String totalD3 = "10D30";//洗衣房
    private final String totalD4 = "10D40";//游泳池
    private final String totalD6 = "10D60";//车库
    private final String totalD7 = "10D70";//办事大厅

    private final String totalD8 = "10D80";//健身房
    private final String totalD5 = "10D50";//其它
    private List<String> listorder = new ArrayList<>();



    private int beginRowNumber = 2;
    private int lastGradecolumn = 18;
    private int DeviceColumnNumber = 1;


    private Sheet sheet;//这个没初始化
    private String txtPath = "C:\\Test";
    public String path = "C:\\工作文件\\刘志远\\建筑能耗分项模型-金山医院.xlsm";



    public WriteToJason(){
        //一级分项
        listorder.add(total);
        //二级分项
        listorder.add(totalA);
        listorder.add(totalB);
        listorder.add(totalC);
        listorder.add(totalD);

        //三级分项
        listorder.add(totalA1);
        listorder.add(totalA2);
        listorder.add(totalA3);

        listorder.add(totalB1);
        listorder.add(totalB2);

        listorder.add(totalC1);
        listorder.add(totalC2);
        listorder.add(totalC3);

        listorder.add(totalD1);
        listorder.add(totalD2);
        listorder.add(totalD3);
        listorder.add(totalD4);

        listorder.add(totalD5);
        listorder.add(totalD6);
        listorder.add(totalD7);

        listorder.add(totalD8);

    }

    /**
     * 将输入流的数据上传到sftp作为文件。文件完整路径=basePath+directory
     *
     * @param beginRowNumber     上传到该目录
     * @param lastGradecolumn    输入流
     * @param DeviceColumnNumber 上传到该目录
     */

    public List<EnergyConsumptionGrade> getData(int beginRowNumber, int lastGradecolumn, int DeviceColumnNumber) {
        List<EnergyConsumptionGrade> list = new ArrayList<>();
        try {

            File xlsFile = new File(path);
            Workbook workbook = WorkbookFactory.create(xlsFile);
            sheet = workbook.getSheetAt(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int rows = sheet.getPhysicalNumberOfRows();
        Row rowZero = sheet.getRow(beginRowNumber);
        int columnsZero = rowZero.getPhysicalNumberOfCells();
        System.out.println("行数-------" + rows);
        System.out.println("列数-------" + columnsZero);
        System.out.println("数据行数-------" + (rows - beginRowNumber));
        for (int i = beginRowNumber; i < rows; i++) {
             System.out.println("-------第"+i+"行");

            Row row = sheet.getRow(i);
            try {
                String device = row.getCell(DeviceColumnNumber - 1).toString();
                String lastGrade = row.getCell(lastGradecolumn - 1).toString();
                System.out.println("设备号--"+device+"/末级分项--"+lastGrade);
                if (!device.equals("")) {
                    EnergyConsumptionGrade ecg = getGrade(lastGrade);
                    ecg.setDeviceId(device);
                    list.add(ecg);
                }
            } catch (Exception e) {
                System.out.println(e);
            }


        }


        return list;

    }

    public EnergyConsumptionGrade getGrade(String str) {
        EnergyConsumptionGrade ecg = new EnergyConsumptionGrade();
        String cutString;
        boolean finalGrade = false;//最后一级分项是否添加
        int length = str.length();

        for (int i = 0; i < str.length(); i++) {

            if (str.equals("10000")) {
                ecg.setFirstGrade("10000");
                ecg.setFinalGrade("10000");
                break;
            }

            if (i == 0) {
                //四级分项

                cutString = str.substring(length - 1, length);
                str = str.substring(0, length - 1);
                //System.out.println(cutString);


                if (!cutString.equals("0")) {
                    //四级分项有值

                    ecg.setFourthGrade(str + cutString);
                    if (!finalGrade) {
                        ecg.setFinalGrade(str + cutString);
                        finalGrade = true;
                    }

                }  //四级分项没有值


                //str = str+"0";


            } else if (i == 1) {
                //二级分项
                length = str.length();
                cutString = str.substring(length - 1, length);
                str = str.substring(0, length - 1);
                //System.out.println(cutString);
                if (!cutString.equals("0")) {
                    //二级分项有值

                    ecg.setThirdGrade(str + cutString + "0");
                    if (!finalGrade) {
                        ecg.setFinalGrade(str + cutString + "0");
                        finalGrade = true;
                    }

                } else {
                    //二级分项没有值
                }
            } else if (i == 2) {
                //三级分项
                length = str.length();
                cutString = str.substring(length - 1, length);
                str = str.substring(0, length - 1);
                //System.out.println(cutString);
                if (!cutString.equals("0")) {
                    //三级分项有值
                    ecg.setSecondGrade(str + cutString + "00");
                    if (!finalGrade) {
                        ecg.setFinalGrade(str + cutString + "00");
                        finalGrade = true;
                    }
                } else {
                    //三级分项为0
                }
            }
            ecg.setFirstGrade("10000");
        }
        return ecg;
    }


    public List<String> getLastString(String str) {
        List<String> list = new ArrayList<String>();

        int length = str.length();

        String cutString = str.substring(length - 1, length);
        //System.out.println(cutString);

        String remainString = str.substring(0, length - 1);
        //System.out.println(remainString);


        list.add(cutString);
        list.add(remainString);


        return list;
    }

    //int beginRowNumber, int lastGradecolumn, int DeviceColumnNumber
    public static void main(String[] args) throws ParseException {

        WriteToJason WTJ = new WriteToJason();
        List<EnergyConsumptionGrade> list = WTJ.getData(WTJ.beginRowNumber, WTJ.lastGradecolumn, WTJ.DeviceColumnNumber);
        WTJ.writeToXml(list, WTJ.getTxtPath());



    }


    public String writeToXml(List<EnergyConsumptionGrade> list, String path) {

        Date date = new Date();
        // String path = "C:\\工作文件\\待导入数据\\";

        File file = new File(path);
        if (!file.isDirectory()) {
            boolean isSuccess = file.mkdirs();
            System.out.println(path + "----创建成功");
        } else {
            System.out.println(path + "----已存在");
        }


        int length = list.size();




        Map<String, String> map = new HashMap<String, String>();


        for (EnergyConsumptionGrade ecg : list) {
            if (ecg.getFinalGrade().equals(total)) {

                if (!map.containsKey(total)) {
                    map.put(total, total + ":" + ecg.getDeviceId() + "+" + "__" + ecg.getDeviceId() + ",");
                    System.out.println(map.get(total));
                    System.out.println("第一次进入------------");
                } else {
                    System.out.println("测试哈哈哈哈------------");
                    String[] strs = map.get(total).split("__");
                    String str0 = strs[0] + ecg.getDeviceId() + "+";
                    String str1 = strs[1] + ecg.getDeviceId() + ",";
                    map.put(total,str0+"__"+str1);
                }
            }else{
                String typeSecond = ecg.getSecondGrade();
                String typeThird = ecg.getThirdGrade();
                if (!map.containsKey(typeSecond)) {
                    map.put(typeSecond, typeSecond + ":" + ecg.getDeviceId() + "+" + "__" + ecg.getDeviceId() + ",");
                    System.out.println(map.get(typeSecond));
                }else {
                    String[] strs = map.get(typeSecond).split("__");
                    String str0 = strs[0] + ecg.getDeviceId() + "+";
                    String str1 = strs[1] + ecg.getDeviceId() + ",";
                    map.put(typeSecond,str0+"__"+str1);
                }
                if (!map.containsKey(typeThird)) {
                    map.put(typeThird, typeThird + ":" + ecg.getDeviceId() + "+" + "__" + ecg.getDeviceId() + ",");
                    System.out.println(map.get(typeThird));
                }else {
                    String[] strs = map.get(typeThird).split("__");
                    String str0 = strs[0] + ecg.getDeviceId() + "+";
                    String str1 = strs[1] + ecg.getDeviceId() + ",";
                    map.put(typeThird,str0+"__"+str1);
                }

            }
        }

        for(String key : map.keySet()){
                String strtemp = map.get(key);
                System.out.println("--------" + strtemp);
                //去除多余的加号和分隔符
                strtemp = strtemp.replace("+__",":");
                int lengthstr = strtemp.length();
                //去除多余的逗号
                strtemp = strtemp.substring(0,lengthstr-1);
                map.put(key,strtemp);

        }
        String finalstr  = "";
        for(String type:this.listorder){
            if (map.containsKey(type)){
                finalstr += map.get(type);
                finalstr += "\n";
            }
        }

        System.out.println(finalstr);







        String fileName = "jason.txt";
        //System.out.println(fileName);
        File fileXml = new File(path + fileName);

        System.out.println(path + fileName);
        if (!fileXml.isFile()) {
            try {
                fileXml.createNewFile();
                System.out.println("创建文件----" + path + fileName);
            } catch (IOException e) {
                System.out.println("创建文件失败----" + path + fileName);
                e.printStackTrace();
            }
        }
        try {
            FileWriter fw = new FileWriter(fileXml);
            //String str = System.getProperty("line.separator");

            fw.write(finalstr);


            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "已完成";
    }

    public String GenerateJason(Luxytest luxy) throws ParseException {
        ExcelData sheet1 = new ExcelData(luxy.getPath(), "");
        return "";
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTxtPath() {
        return txtPath;
    }

    public void setTxtPath(String txtPath) {
        this.txtPath = txtPath;
    }

    public int getBeginRowNumber() {
        return beginRowNumber;
    }

    public void setBeginRowNumber(int beginRowNumber) {
        this.beginRowNumber = beginRowNumber;
    }

    public int getLastGradecolumn() {
        return lastGradecolumn;
    }

    public void setLastGradecolumn(int lastGradecolumn) {
        this.lastGradecolumn = lastGradecolumn;
    }

    public int getDeviceColumnNumber() {
        return DeviceColumnNumber;
    }

    public void setDeviceColumnNumber(int deviceColumnNumber) {
        DeviceColumnNumber = deviceColumnNumber;
    }
}
