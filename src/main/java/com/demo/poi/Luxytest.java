package com.demo.poi;

import com.demo.SftpTest.SFTPTest;
import com.jcraft.jsch.SftpException;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Luxytest {

	public int dayBack = 0;
	public String path = "C:\\Users\\jky-luxy\\Downloads\\edge\\导出数据2020-12-25_9724.xls";
	public String pathTemp = "C:\\工作文件\\待导入数据\\";

	/*public String basePath = "\\";
	public String direction = "\\Test";
	public String user = "luxy-ftp";
	public String pass = "123456";
	public String ip = "10.2.215.61";
	public int port = 22;*/

	public String basePath = "/";
	public String direction = "/home/jky/data/recBuildingData/";
	public String user = "root";
	public String pass = "jky,12375";
	public String ip = "10.1.121.61";
	public int port = 22;


	
	public String WriteToXml(List<Data> list,int backDay) {

				Date date = new Date();
		        date.setDate(date.getDate()-dayBack);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH");
				SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMddHH");
		        SimpleDateFormat df3 = new SimpleDateFormat("yyyy-MM-dd");



				String dateS = df3.format(date);

		        System.out.println(dateS);

		        String path = pathTemp+dateS;
		        System.out.println(path);


		        File file = new File(path);
		        if(!file.isDirectory()) {
		         boolean isSuccess = file.mkdirs();
		         System.out.println(path+"----创建成功");
		        }else {
		         System.out.println(path+"----已存在");
		        }
		  
		        


				int length = list.size();
		        for(int i=0;i<length;i++ ) {

		        	Data data = list.get(i);
		        	Date timed = data.getDateTime();

					String enter = data.getEnter();
					String leave = data.getLeave();
					//System.out.println(timed.toString());
					//System.out.println(enter);
					//System.out.println(leave);

					String filenameAlt = df.format(timed);
					//System.out.println(filenameAlt);
					String txt = df2.format(timed);
					//System.out.println(txt);



		        	int t = 10+i;
		        	String time = t+""; //4238602291889152_2020-11-30_11_05_51
		        	String fileName = "/4238602291889152_"+filenameAlt+"_05_51.xml";
		        	System.out.println(fileName);
		        	File fileXml = new File(path+fileName);
		        	
		            if(!fileXml.isFile()){
		    			try {
		    				fileXml.createNewFile();
		    				System.out.println("创建文件----"+path+fileName);
		    			} catch (IOException e) {
		    				System.out.println("创建文件失败----"+path+fileName);
		    				e.printStackTrace();
		    			}
		    		}
		    		try {
		    			FileWriter fw = new FileWriter(fileXml);
		    			String str = System.getProperty("line.separator");

		    			fw.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+str); 
		    			fw.write("<root>"+str); 
		    			fw.write("  <common>"+str); 
		    			fw.write("    <user_id>4238602291889152</user_id>"+str); 
		    			fw.write("    <type>energy_data</type>"+str); 
		    			fw.write("  </common>"+str); 
		    			fw.write("  <data operation=\"report\">"+str); 
		    			fw.write("    <time>"+txt+"0551</time>	"+str);
		    			fw.write("    <buildings>"+str); 
		    			fw.write("      <building_id id=\"4660390398249984\">"+str); 
		    			fw.write("        <meters>"+str); 
		    			fw.write("          <meter id=\"E61d9737f0\" name=\"百联东郊人流量\">"+str);
		    			fw.write("            <function id=\"ENP\" error=\"\">"+enter+"</function>"+str); 
		    			fw.write("            <function id=\"LEP\" error=\"\">"+leave+"</function>"+str); 
		    			fw.write("         </meter>"+str); 
		    			fw.write("        </meters>"+str); 
		    			fw.write("      </building_id>"+str); 
		    			fw.write("    </buildings>"+str); 
		    			fw.write("  </data>"+str); 
		    			fw.write("</root>"+str); 
		    			
		    			fw.flush();
		    			fw.close();
		    		} catch (IOException e) {
		    			e.printStackTrace();
		    		}
		        }
		        
		        
		        return path;
			}
	public String GenerateJason(Luxytest luxy) throws ParseException {
		ExcelData sheet1 = new ExcelData(luxy.getPath(), "");
	return "";
	}



	public String GenerateXml(Luxytest luxy) throws ParseException {

				ExcelData sheet1 = new ExcelData(luxy.getPath(), "");

				List<Data> list = sheet1.GetExcelData(2,4,1);

				String filePath = luxy.WriteToXml(list,luxy.getDayBack());
				return filePath;
			}
	public String WriteIntoExcel() throws ParseException {
		ExcelData sheet1 = new ExcelData("D:\\工作文档\\测试.xlsx", "");

		List<Map<String, Object>> list = sheet1.GetDateFromExcel();

		ExcelTool et = new ExcelTool();

		//et.writerExcel();

		return "-------end------";
	}

	public  void transferFile(String pathT){
		SFTPTest sftp = new SFTPTest(user , pass , ip, port );
		sftp.login();
		try{
			sftp.uploadallfile(direction,  pathT);

		} catch ( SftpException e) {
			e.printStackTrace();
		}



		//sftp.upload("/","/home/jky/data_test/recBuildingData/", "导出数据2020-12-23_91055.xls", is);
		//sftp.logout();
	}

	public static void transferXml() throws ParseException {
		Luxytest luxy = new Luxytest();

		//东郊白联 客流量 生成xml文件
		String pathT = luxy.GenerateXml(luxy);
		luxy.transferFile(pathT);
	}

	
		
	public static void main(String[] args) throws ParseException {
		transferXml();

	}
	public int getDayBack() {
		return dayBack;
	}

	public void setDayBack(int dayBack) {
		this.dayBack = dayBack;
	}
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}