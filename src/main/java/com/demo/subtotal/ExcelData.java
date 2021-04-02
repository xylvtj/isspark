package com.demo.subtotal;

import com.demo.poi.Data;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExcelData {
	private Sheet sheet;//这个没初始化
	private List<Data> list = new ArrayList<Data>();
	SimpleDateFormat dfhh = new SimpleDateFormat("yyyy-MM-dd HH:mm");


	ExcelData(String filePath, String sheetName) {
		try {
			File xlsFile = new File(filePath);
			Workbook workbook = WorkbookFactory.create(xlsFile);
			sheet = workbook.getSheetAt(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public List<Map<String, Object>> GetDateFromExcel(){
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		int rows = sheet.getPhysicalNumberOfRows();
		System.out.println(rows);
		int columns = sheet.getRow(0).getPhysicalNumberOfCells();
		System.out.println(columns);
		String[][] data = new String[rows][columns] ;
		for (int i = 0; i < rows; i++) {
			Row row = sheet.getRow(i);
			//int columns = row.getPhysicalNumberOfCells();
			Map<String, Object> map = new HashMap<String, Object>();

			for (int j = 0; j < columns; j++) {
				String cell = row.getCell(j).toString();
				map.put(""+i,cell);
				//data[i][j] = cell;
				//System.out.print(cell);
				//System.out.print("  ");
			}
			listMap.add(map);
			//System.out.println("--");
		}

		return listMap;
	}
	public String getExcelDateByIndex(int row, int column) {
		Row row1 = sheet.getRow(row);
		String cell = "";
		if(row1!=null){
			if(row1.getCell(column)!=null){
				row1.getCell(column).setCellType(CellType.STRING);
				cell = row1.getCell(column).toString();
				//System.out.println(row1.getCell(column).getCellType());
			}

		}

		return cell;
	}

	public String getCellByCaseName(String caseName, int currentColumn, int targetColumn) {
		String operateSteps = "";
		int rows = sheet.getPhysicalNumberOfRows();
		for (int i = 0; i < rows; i++) {
			Row row = sheet.getRow(i);
			String cell = row.getCell(currentColumn).toString();
			if (cell.equals(caseName)) {
				operateSteps = row.getCell(targetColumn).toString();
				break;
			}
		}
		return operateSteps;
	}

	public void readExcelData() {
		int rows = sheet.getPhysicalNumberOfRows();
		System.out.println(rows);
		System.out.println(sheet.getRow(0).getPhysicalNumberOfCells());
		for (int i = 0; i < 155; i++) {
			Row row = sheet.getRow(i);
			int columns = row.getPhysicalNumberOfCells();
			for (int j = 0; j < 9; j++) {
				String cell = row.getCell(j).toString();
				//System.out.print(cell);
				//System.out.print("  ");
			}
			//System.out.println("--");
		}
	}
	public String[][] getExcelData(){
		int rows = sheet.getPhysicalNumberOfRows();
		int columns = sheet.getRow(0).getPhysicalNumberOfCells();

		//System.out.println(rows);
		//System.out.println(columns);
		String data[][] = null;
		data = new String[rows][columns];
		for(int i=0;i<rows;i++){
			for(int j=0;j<columns;j++){

				data[i][j]=getExcelDateByIndex(i,j);
			}
		}
		return data;
	};

	public String[][] getExcelData(String splite){
		int rows = sheet.getPhysicalNumberOfRows();
		String temp = getExcelDateByIndex(0,0);
		int columns = temp.split(splite).length;

		//System.out.println(rows);
		//System.out.println(columns);
		String data[][] = null;
		data = new String[rows][columns];
		for(int i=0;i<rows;i++){
			data[i] = getExcelDateByIndex(i,0).split(splite);

		}
		return data;
	};

	public List<Data> GetExcelData(int firstColumnNumber,int lastColumnNumber,int firstRow) throws ParseException {
		// 取得行数

		int rows = sheet.getPhysicalNumberOfRows();
		//System.out.println(rows+"------行数");
		for (int i = firstRow; i < rows; i++) {
			Row row = sheet.getRow(i);
			Data dataCell = new Data();

			//int columns = row.getPhysicalNumberOfCells();
			String d = row.getCell(firstColumnNumber).toString();
			d = getTime(d);

			//System.out.println(d);

			dataCell.setDateTime(dfhh.parse(d));

			//System.out.println(dataCell.getDateTime());


			String e =row.getCell(firstColumnNumber+1).toString();
			e = getNumber(e);
			dataCell.setEnter(e);
			//System.out.println(e);

		    String l = row.getCell(firstColumnNumber+2).toString();
			l = getNumber(l);
			//System.out.println(l);
			dataCell.setLeave(l);
			/*for (int j = firstColumnNumber; j < lastColumnNumber; j++) {
				String cell = row.getCell(j).toString();
				System.out.println(cell);
			}*/
			list.add(dataCell);
		}

		return list;

	}

	private String getNumber(String e) {
		String str = "";
		String temp = "";
		int i = 0;
		do{
			temp = e.substring(i,i+1);
			str = str+temp;
			i++;
			temp = e.substring(i,i+1);
		}while(!temp.equals("."));
		return str;
	}
	private String getTime(String e) {
		String str = "";
		String temp = "";

		int i = 0;
		do{
			temp = e.substring(i,i+1);

			str = str+temp;
			i++;
			temp = e.substring(i,i+1);
		}while(!temp.equals("~"));
		return str;
	}
	public String[][] getExcelDataExceptHeaders(){
		int rows = sheet.getPhysicalNumberOfRows();
		int columns = sheet.getRow(1).getPhysicalNumberOfCells();

		//System.out.println(rows);
		//System.out.println(columns);

		String data[][] = null;
		data = new String[rows][columns];
		for(int i=1;i<rows;i++){
			for(int j=0;j<columns;j++){

				data[i-1][j]=getExcelDateByIndex(i,j);
			}
		}
		return data;
	}

	public void printData(String[][] data){
		for(int i=0;i<data.length;i++){
			String str = "";
			for(int j=0;j<data[0].length;j++){

				str = str + data[i][j] +"   ";
			}
			System.out.println(str);
		}
	}

	public static void main(String[] args) throws ParseException {
		System.out.print("sb luxy  test begin --------");

		ExcelData sheet1 = new ExcelData("C:\\工作文件\\智慧所\\2021_03_17_叶大师_上海中心水务\\waterdata.xlsx", "");

		String data[][]= sheet1.getExcelData();
		sheet1.printData(data);
		//sheet1.GetExcelData(2,4,1);

	}
}
