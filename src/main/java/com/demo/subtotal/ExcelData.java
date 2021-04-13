package com.demo.subtotal;

import com.demo.poi.Data;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class ExcelData {
	private int sheetNum = 1;//excel中sheet的数量	
	private Sheet sheet;//这个没初始化
	private List<Data> list = new ArrayList<Data>();
	SimpleDateFormat dfhh = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	private Workbook workbook;


	ExcelData(String filePath, String sheetName) {
		try {
			File xlsFile = new File(filePath);
			workbook = WorkbookFactory.create(xlsFile);

			sheet = workbook.getSheet(sheetName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	ExcelData(String filePath, int Sheetnum) {
		try {
			File xlsFile = new File(filePath);
			workbook = WorkbookFactory.create(xlsFile);
			sheet = workbook.getSheetAt(Sheetnum);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	ExcelData(String filePath) {
		try {
			File xlsFile = new File(filePath);
			workbook = WorkbookFactory.create(xlsFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	ExcelData() {

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



	/**
	 * @auther luxy
	 *读取excel 特定单元格内的字段
	 *通过行数和列数定位单元格
	 */
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




	/**
	 * @auther luxy
	 *读取excel中的数据
	 * 后台打印
	 */

	public void readExcelData() {
		int rows = sheet.getPhysicalNumberOfRows();
		System.out.println(rows);
		System.out.println(sheet.getRow(0).getPhysicalNumberOfCells());
		for (int i = 0; i < rows; i++) {
			Row row = sheet.getRow(i);
			int columns = row.getPhysicalNumberOfCells();
			for (int j = 0; j < columns; j++) {
				String cell = row.getCell(j).toString();
				//System.out.print(cell);
				//System.out.print("  ");
			}
			//System.out.println("--");
		}
	}
	/**
	 * @auther luxy
	 *读取excel中的数据
	 *返回数组 String[][]
	 */

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


	/**
	 * @auther luxy
	 *读取excel中的csv格式数据
	 *数据只存于第一列以分隔符分开
	 *输入 分隔符 split
	 */
	public String[][] getExcelData(String split){
		int rows = sheet.getPhysicalNumberOfRows();
		String temp = getExcelDateByIndex(0,0);
		int columns = temp.split(split).length;
		//System.out.println(rows);
		//System.out.println(columns);
		String data[][] = null;
		data = new String[rows][columns];
		for(int i=0;i<rows;i++){
			data[i] = getExcelDateByIndex(i,0).split(split);

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
			d = getStringAfterSplit(d,"~");

			//System.out.println(d);

			dataCell.setDateTime(dfhh.parse(d));

			//System.out.println(dataCell.getDateTime());


			String e =row.getCell(firstColumnNumber+1).toString();
			e = getStringAfterSplit(e,".");
			dataCell.setEnter(e);
			//System.out.println(e);

		    String l = row.getCell(firstColumnNumber+2).toString();
			l = getStringAfterSplit(l,".");
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
	/**
	 * @auther luxy
	 *取得字符串中分隔符后面的字符
	 *数据只存于第一列以分隔符分开
	 *输入 分隔符 split
	 */

	private String getStringAfterSplit(String e,String split) {
		String str = "";
		String temp = "";
		int i = 0;
		do{
			temp = e.substring(i,i+1);
			str = str+temp;
			i++;
			temp = e.substring(i,i+1);
		}while(!temp.equals(split));
		return str;
	}


	/**
	 * @auther luxy
	 *读取excel中的所有sheet的数据返回成拼接在一起的数组
	 *返回数组 String[][]
	 */
	public String[][] getAllSheetsData(){

		Iterator<Sheet> iterator = workbook.iterator();
		Iterator<Sheet> iterator1 = workbook.iterator();

		int totalRow = 1;

		int total = 0;
		int columnsInit = 0;
		System.out.println("hahahha");
		while(iterator1.hasNext()){
			String sheetTem = iterator1.next().getSheetName();
			sheet = workbook.getSheet(sheetTem);
			int rows = sheet.getPhysicalNumberOfRows();
			total = total + rows;
		}

		sheet = workbook.getSheetAt(0);
		columnsInit = sheet.getRow(0).getPhysicalNumberOfCells();

		System.out.println(total);
		System.out.println(columnsInit);
		String[][] dataall = new String[total][columnsInit];
		while(iterator.hasNext()){
			String sheetTem = iterator.next().getSheetName();
			System.out.println(sheetTem);

			sheet = workbook.getSheet(sheetTem);
			int rows = sheet.getPhysicalNumberOfRows();
			int columns = sheet.getRow(0).getPhysicalNumberOfCells();
			System.out.println("totalRow ----  "+totalRow);
			System.out.println("rows ----  "+rows);
			if(totalRow == 1){
				for(int i=0;i<1;i++){
					for(int j=0;j<columns;j++){

						dataall[i][j]=getExcelDateByIndex(i,j);
					}
				}
			}

			for(int i=1;i<rows;i++){
				for(int j=0;j<columns;j++){

					dataall[totalRow+i-1][j]=getExcelDateByIndex(i,j);
				}
			}
			totalRow = totalRow+rows;
		}

		return dataall;
	}


	/**
	 * @auther luxy
	 *读取excel中的数据 除了第一行以外的数据
	 *返回数组 String[][]
	 */
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

	/**
	 * @auther luxy
	 *后台打印数组
	 */

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
