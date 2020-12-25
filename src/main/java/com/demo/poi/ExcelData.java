package com.demo.poi;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;



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
		String cell = row1.getCell(column).toString();
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

	public List<Data> GetExcelData(int firstColumnNumber,int lastColumnNumber,int firstRow) throws ParseException {
		// 取得行数

		int rows = sheet.getPhysicalNumberOfRows();
		System.out.println(rows+"------行数");
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

	public static void main(String[] args) throws ParseException {
		System.out.print("sb luxy  test begin --------");

		ExcelData sheet1 = new ExcelData("D:\\工作文档\\金山医院电表回路清单2020.11.12.xlsx", "");

		sheet1.readExcelData();
		//sheet1.GetExcelData(2,4,1);

	}
}
