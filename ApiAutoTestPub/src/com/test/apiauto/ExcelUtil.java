package com.test.apiauto;
import com.test.apiauto.GlobalSetting;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;









import jxl.read.biff.CellValue;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;



public class ExcelUtil {
	//用例的路径
	public static String Path = GlobalSetting.Path;
	//sheet的名称
	public static String SheetN = GlobalSetting.sheetN;
	//
	static ArrayList<String> paramNameList = new ArrayList<>();

	

	
	//public String filePath = "D:/workspace/javaexample/excel/商城接口测试用例.xls";

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		//初始化Excel全局参数
		GlobalSetting gl = new GlobalSetting();
		ExcelUtil.iniExcPar(gl);//初始化全局参数
		List<Param> read = read(gl);
		
		for (Param param : read) {
			System.out.println("111---------------");
			System.out.println(param.getRow());
			System.out.println(param.getRun());	
			System.out.println(param.getMethod());	
			System.out.println(param.getUrl());
			System.out.println(param.getParam());
			System.out.println(param.parName);
			System.out.println(param.parValue);
			System.out.println("111---------------");
			String parString = "";
			//System.out.println("==="+parString);
			for(int i=0; i<= param.parName.size(); i++){
				if (param.parName.get(i).length()==0) {
					continue;
				}
				if (param.parValue.size()<param.parName.size()) {
					param.parValue.add("");
				}
				parString += param.parName.get(i)+"="+param.parValue.get(i)+"&";
			}
			parString = parString.substring(0, parString.length()-1);
			System.out.println(parString);
			param.setParam(parString);
			//String json = JsonsUtil.BuildJson(param);
			//JSONArray retrun = JsonsUtil.ParseJson(json);
			//System.out.println(retrun.getJSONObject(0).getString("Method"));
		}
		
		
		
	
	}
	
	
	

    /**读取Excel 2003或2007 一整行的数据
     * @param gl 全局参数
     * @param rowNum 要读取的行号
     * @return  返回rowNum行的数据
     */
    public static List<Param> readRow(GlobalSetting gl,int rowNum){
    	//全局
    	int CNRun = gl.getCNRun();
    	int CNMet = gl.getCNMet();
    	int CNIP = gl.getCNIP();
    	int CNUrl = gl.getCNUrl();
    	int CNPar = gl.getCNPar();
    	int CNRet = gl.getCNRet();
    	int CNAssKey = gl.getCNAssKey();
    	int CNActRes = gl.getCNActRes();
    	int CNExpRes = gl.getCNExpRes();
    	int CNTesRes = gl.getCNTesRes();
    	int CNHeadType = gl.getCNHeadType();
    	int CNHeadValue = gl.getCNHeadValue();
    	int CNParFis = CNUrl+1; //请求参数的第一个列号
    	int CNParLas = CNHeadType-1;  //请求参数最后一个列号
    	System.out.println("CNIP="+CNIP);
    	try {
    		FileInputStream is = new FileInputStream(Path);
	    	Workbook wb = null;  
	    	
	    	//取出扩展名 判断excel格式
	    	String fileType = Path.substring(Path.lastIndexOf(".")+1);
	        if (fileType.equals("xls")) {  
	        	 wb = new HSSFWorkbook(is);  
	        }  
	        else if (fileType.equals("xlsx")) {  
	        	 wb = new XSSFWorkbook(is);  
	        	
	        }  
	        else {  
	            System.out.println("您输入的excel格式不正确");  
	        }  
	        
	        //取得sheet
	         Sheet sheet = wb.getSheet(SheetN);//getSheetAt(0);
	         
	         //将参数存入param集合中
	         List<Param> params=new ArrayList<Param>();
         
        	 Row r = sheet.getRow(rowNum);
             Param param= new Param();
             //判断如何是否要执行这行的用例 run=Y才执行
             System.out.println("CNRun="+CNRun);
             
             if (r.getCell(CNRun)==null) {
            	 System.out.println("其中第"+rowNum+"行 run=null,不执行");
			 }else {
				 //System.out.println(r.getCell(3).getStringCellValue());
				 r.getCell(CNRun).setCellType(HSSFCell.CELL_TYPE_STRING);
				 String Run = r.getCell(CNRun).getStringCellValue();
				 System.out.println("Run="+Run);
				 
				 if (Run.equals("N")){	 
					 //System.out.println("第"+i+"行 run=N,不执行");
					 r.getCell(CNRun).setCellType(HSSFCell.CELL_TYPE_STRING);
					 param.setRow(rowNum);
					 param.setRun(r.getCell(CNRun).getStringCellValue());
					 
				 }else if (Run.equals("Run")) {
					 //如果是Run行，取请求的参数名
					 param.setRow(rowNum);
					 for (int i = CNParFis; i <= CNParLas; i++) {
						r.getCell(i);
						r.getCell(i).setCellType(HSSFCell.CELL_TYPE_STRING);
						String cellString = r.getCell(i).getStringCellValue();
						//如果参数名是空的，就不存入
						if (! "".equals(cellString)) {
							System.out.println("test=="+cellString);
							param.parName.add(r.getCell(i).getStringCellValue());  //setRun(r.getCell(CNRun).getStringCellValue());
						}
					}
					 
				}else if (Run.equals("Y")) {
					//如果是Y行，表示要执行的行，取请求的参数信息
					 //设置单元格为string格式
					 r.getCell(CNRun).setCellType(HSSFCell.CELL_TYPE_STRING);
					 r.getCell(CNMet).setCellType(HSSFCell.CELL_TYPE_STRING);
					 r.getCell(CNIP).setCellType(HSSFCell.CELL_TYPE_STRING);
					 r.getCell(CNUrl).setCellType(HSSFCell.CELL_TYPE_STRING);
					 r.getCell(CNPar).setCellType(HSSFCell.CELL_TYPE_STRING);
					 r.getCell(CNHeadType).setCellType(HSSFCell.CELL_TYPE_STRING);
					 r.getCell(CNHeadValue).setCellType(HSSFCell.CELL_TYPE_STRING);
					 //r.getCell(CNRet).setCellType(HSSFCell.CELL_TYPE_STRING);
					 r.getCell(CNAssKey).setCellType(HSSFCell.CELL_TYPE_STRING);
					 //r.getCell(CNActRes).setCellType(HSSFCell.CELL_TYPE_STRING);
					 r.getCell(CNExpRes).setCellType(HSSFCell.CELL_TYPE_STRING);
					 //r.getCell(CNTesRes).setCellType(HSSFCell.CELL_TYPE_STRING);
					 						 
					 //用getStringCellValue读取单元格值
					 param.setRow(rowNum);
					 param.setRun(r.getCell(CNRun).getStringCellValue());
					 param.setMethod(r.getCell(CNMet).getStringCellValue());
					 param.setIP(r.getCell(CNIP).getStringCellValue());
					 param.setUrl(r.getCell(CNUrl).getStringCellValue());
					 param.setParam(r.getCell(CNPar).getStringCellValue());
					 param.setHeadType(r.getCell(CNHeadType).getStringCellValue());
					 param.setHeadValue(r.getCell(CNHeadValue).getStringCellValue());
					 //param.setReturn(r.getCell(CNRet).getStringCellValue());	
					 param.setAssKey(r.getCell(CNAssKey).getStringCellValue());
					 //param.setActResult(r.getCell(CNActRes).getStringCellValue());	
					 param.setExpResult(r.getCell(CNExpRes).getStringCellValue());	
					 //param.setTestResult(r.getCell(CNTesRes).getStringCellValue());	
				 }
			 }
			 //System.out.println(cell.getRichStringCellValue());
             params.add(param);
	         
	         is.close();
	         wb.close();
	         //返回params集合
	         return params;
		} catch (Exception e) {
		     e.printStackTrace();
		}
		return null;
    	
    }
	
	
    /**读取Excel 2003或2007 中数据
     * @return
     * @throws Exception
     */
    public static List<Param> read(GlobalSetting gl){
    	//全局
    	int CNRun = gl.getCNRun();
    	int CNMet = gl.getCNMet();
    	int CNIP = gl.getCNIP();
    	int CNUrl = gl.getCNUrl(); 
    	int CNPar = gl.getCNPar();
    	int CNRet = gl.getCNRet();
    	int CNAssKey = gl.getCNAssKey();
    	int CNActRes = gl.getCNActRes();
    	int CNExpRes = gl.getCNExpRes();
    	int CNTesRes = gl.getCNTesRes();
    	int CNHeadType = gl.getCNHeadType();
    	int CNHeadValue = gl.getCNHeadValue();
    	int CNParFis = CNUrl+1; //请求参数的第一个列号
    	int CNParLas = CNHeadType-1;  //请求参数最后一个列号
    	if (CNRun==0) {
    		System.out.println("请初始化全局参数 先执行ExcelUtil.iniExcPar"+CNRun);
		}
    	
    	try {
    		FileInputStream is = new FileInputStream(Path);
	    	Workbook wb = null;  
	    	
	    	//取出扩展名 判断excel格式
	    	String fileType = Path.substring(Path.lastIndexOf(".")+1);
	        if (fileType.equals("xls")) {  
	        	 wb = new HSSFWorkbook(is);  
	        }  
	        else if (fileType.equals("xlsx")) {  
	        	 wb = new XSSFWorkbook(is);  
	        	
	        }  
	        else {  
	            System.out.println("您输入的excel格式不正确");  
	        }  
	        
	        //取得sheet
	         Sheet s = wb.getSheet(SheetN);//getSheetAt(0);
	         //获得EXCEL行数
	         int rowNums=s.getLastRowNum();
	         System.out.println("有效用例行数rowNums="+rowNums);
	         //获得Excell列数
	         //int columnNum=r.getPhysicalNumberOfCells();
	         //将参数存入param集合中
	         List<Param> params=new ArrayList<Param>();
	         for(int i=0;i<=rowNums;i++){
	        	 Row r = s.getRow(i);
	             Param param= new Param();
	             //判断如何是否要执行这行的用例 run=Y才执行
	             r.getCell(CNRun).setCellType(HSSFCell.CELL_TYPE_STRING);
	             String runStr = r.getCell(CNRun).getStringCellValue();
	             if (r.getCell(CNRun)==null) {
	            	 //System.out.println("其中第"+i+"行 run=null,不执行");
	            	 continue;
				 }else {
					 //System.out.println("CNRun = "+CNRun);
					 //System.out.println("runStr = "+runStr);
					 if (runStr.equals("N")){	 
						 //System.out.println("第"+i+"行 run=N,不执行");
						 //r.getCell(CNRun).setCellType(HSSFCell.CELL_TYPE_STRING);
						 //param.setRow(i);
						 //param.setRun(r.getCell(CNRun).getStringCellValue());
						 continue;
					 //如果行是Run，则存参数名
					 }else if (runStr.equals("Run")) {
						 //System.out.println("第"+i+"行 run=Run,不执行");
						 //param.setRow(i);
						 paramNameList.clear();
						 for (int parN = CNParFis; parN <= CNParLas; parN++) {
							 if (r.getCell(parN) != null) {
								 r.getCell(parN).setCellType(HSSFCell.CELL_TYPE_STRING);
								 String parValue = r.getCell(parN).getStringCellValue();
								 paramNameList.add(parValue);
							}
						 }
						 continue;
					//如果行是Y，则存请求参数内容
					} else if (runStr.equals("Y")){
						//存请求的参数名
						Iterator<String> it1 = paramNameList.iterator();
						while(it1.hasNext()){
				            //System.out.println(it1.next());
							param.parName.add(it1.next().toString());
				        }
						//存请求的参数值
						for (int parN = CNParFis; parN <= CNParLas; parN++) {
							//System.out.println("parN-"+parN);
							 //参数不为空时存入list中
							 if (r.getCell(parN) != null) {
								 r.getCell(parN).setCellType(HSSFCell.CELL_TYPE_STRING);
								 String parValue = r.getCell(parN).getStringCellValue();
								 param.parValue.add(parValue);
							}
						}
						//设置单元格为string格式
						r.getCell(CNRun).setCellType(HSSFCell.CELL_TYPE_STRING);
						r.getCell(CNMet).setCellType(HSSFCell.CELL_TYPE_STRING);
						r.getCell(CNIP).setCellType(HSSFCell.CELL_TYPE_STRING);
						r.getCell(CNUrl).setCellType(HSSFCell.CELL_TYPE_STRING);
						//r.getCell(CNPar).setCellType(HSSFCell.CELL_TYPE_STRING);
						//r.getCell(CNRet).setCellType(HSSFCell.CELL_TYPE_STRING);
						r.getCell(CNAssKey).setCellType(HSSFCell.CELL_TYPE_STRING);
						//r.getCell(CNActRes).setCellType(HSSFCell.CELL_TYPE_STRING);
						r.getCell(CNExpRes).setCellType(HSSFCell.CELL_TYPE_STRING);
						//r.getCell(CNTesRes).setCellType(HSSFCell.CELL_TYPE_STRING);
												
						//用getStringCellValue读取单元格值
						param.setRow(i);
						param.setRun(r.getCell(CNRun).getStringCellValue());
						param.setMethod(r.getCell(CNMet).getStringCellValue());
						param.setIP(r.getCell(CNIP).getStringCellValue());
						param.setUrl(r.getCell(CNIP).getStringCellValue()+r.getCell(CNUrl).getStringCellValue());
						//param.setParam(r.getCell(CNPar).getStringCellValue());
						//param.setReturn(r.getCell(CNRet).getStringCellValue());	
						param.setAssKey(r.getCell(CNAssKey).getStringCellValue());
						//param.setActResult(r.getCell(CNActRes).getStringCellValue());	
						param.setExpResult(r.getCell(CNExpRes).getStringCellValue());	
						//param.setTestResult(r.getCell(CNTesRes).getStringCellValue());	
					 }
				 }
				 //System.out.println(cell.getRichStringCellValue());
	             params.add(param);
	         }
	         is.close();
	         wb.close();
	         //返回params集合
	         return params;
		} catch (Exception e) {
		     e.printStackTrace();
		}
		return null;
    	
    }
    

   
    /**
     * 写入Excel 2003或2007，在任意坐标处写入数据。
     * String value：你要输入的内容
     * int x ：行坐标，Excel从 0 算起
     * int y   ：列坐标，Excel从 0 算起
     */
    public static void writeCell(int x,int y,String value) {
    	try {
		   // 打开用例
		   //String Path = "D:/workspace/javaexample/excel/商城接口测试用例.xls";
    		FileInputStream is = new FileInputStream(Path);
    		Workbook wb = null;
    		//取出扩展名 判断excel格式
	    	String fileType = Path.substring(Path.lastIndexOf(".")+1);
	        if (fileType.equals("xls")) {  
	        	 wb = new HSSFWorkbook(is);  
	        }  
	        else if (fileType.equals("xlsx")) {  
	        	 wb = new XSSFWorkbook(is);  
	        	
	        }  
	        else {  
	            System.out.println("您输入的excel格式不正确");  
	        }  
	        Sheet sheet = wb.getSheet(SheetN);
	        Row row = sheet.getRow(x);
	        Cell cell = row.getCell(y);
	        /*		  
	        HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(Path));
	        HSSFSheet sheet=wb.getSheet(SheetN);//getSheetAt(0);
	        HSSFRow row=sheet.getRow(x);
	        HSSFCell cell=row.getCell(y);
	        */
	        //如果是单元格是空的，需要创建单元格，否则无法写入，会报空指针异常
	        if (cell==null) {
	           cell = row.createCell(y);
	        }
	        //设置单元格的值
	        cell.setCellValue(value);
	        //如果是testReusult列表，成功为绿色，失败为红色
	        if ("Pass".equals(value)) {
	           System.out.println("green");
	           //HSSFCellStyle style = wb.createCellStyle();
	           CellStyle style = wb.createCellStyle();
	           style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
	           style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	           cell.setCellStyle(style);
	        }else if ("Fail".equals(value)) {
	           System.out.println("red");
	           //HSSFCellStyle style = wb.createCellStyle();
	           CellStyle style = wb.createCellStyle(); 
	           style.setFillForegroundColor(IndexedColors.RED.getIndex());
	           style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	           cell.setCellStyle(style);
	        }
	        //创建输出流，向wookbook写入数据
	        FileOutputStream os =  new FileOutputStream(Path);
	        wb.write(os);
	        os.flush();
	        //关闭各种流
	    
	       if (os != null) {
	    	   os.close();
	       }
	       if (wb != null) {
	    	   wb.close();
	       }
		} catch (Exception e) {
		       e.printStackTrace();
		}
	 }
	 


    /**
     * 写入Excel2007，在任意坐标处写入数据。
     * String value：你要输入的内容
     * int x ：行坐标，Excel从 0 算起
     * int y   ：列坐标，Excel从 0 算起
     */
	 public static void writeCell2007(int x,int y,String value) {
		try {
		   // 打开用例
		   String Path = "D:/workspace/javaexample/excel/商城接口测试用例.xls";
		   XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(Path));
		   XSSFSheet sheet=wb.getSheetAt(0);
		   XSSFRow row=sheet.getRow(x);
		   XSSFCell cell=row.getCell(y);
		   //如果是单元格是空的，需要创建单元格，否则无法写入，会报空指针异常
		   if (cell==null) {
			   cell = row.createCell(y);
		   }
		   //设置单元格的值
		   cell.setCellValue(value);
		   //如果是testReusult列表，成功为绿色，失败为红色
		   if (value.equals("Pass")) {
			   System.out.println("green");
			   XSSFCellStyle style = wb.createCellStyle();
			   style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
			   style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		       cell.setCellStyle(style);
		   }else if (value.equals("Fail")) {
			   System.out.println("red");
			   XSSFCellStyle style = wb.createCellStyle();
			   style.setFillForegroundColor(IndexedColors.RED.getIndex());
			   style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	           cell.setCellStyle(style);
		   }
		   //创建输出流，向wookbook写入数据
	       FileOutputStream os =  new FileOutputStream(Path);
	       wb.write(os);
	       os.flush();
	       //关闭各种流
	       if (os != null) {
	    	   os.close();
	       }
	       if (wb != null) {
	    	   wb.close();
	       }
	     
		} catch (Exception e) {
		       e.printStackTrace();
		}
	 }
	 

    /**
     * 写入Excel2007，初始化数据：读取第一行的标题，获取列数。存入全局参数
     */
	 public static void iniExcPar(GlobalSetting glSetting) {
		try {
		   //String Path = "D:/workspace/javaexample/excel/商城接口测试用例.xls";
		   XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(Path));
		   XSSFSheet sheet=wb.getSheet(SheetN);
		   XSSFRow row = sheet.getRow(0);
		   //获得Excell列数
		   int cellNum = row.getPhysicalNumberOfCells();
		   //GlobalSetting glSetting = new GlobalSetting();
		   for (int i = 0; i < cellNum; i++) {
			   row.getCell(i).setCellType(HSSFCell.CELL_TYPE_STRING);
			   String CellValue = row.getCell(i).getStringCellValue();
			   //System.out.println(CellValue);
			   if (CellValue.equals("Run")) {
				   glSetting.setCNRun(i);
			   }else if (CellValue.equals("Method")) {
				   glSetting.setCNMet(i);
			   }else if (CellValue.equals("IP")) {
				   glSetting.setCNIP(i);
			   }else if (CellValue.equals("Url")) {
				   glSetting.setCNUrl(i);
			   }else if (CellValue.equals("Param")) {
				   glSetting.setCNPar(i);
			   }else if (CellValue.equals("Return")) {
				   glSetting.setCNRet(i);
			   }else if (CellValue.equals("AssKey")) {
				   glSetting.setCNAssKey(i);
			   }else if (CellValue.equals("ActResult")) {
				   glSetting.setCNActRes(i);
			   }else if (CellValue.equals("ExpResult")) {
				   glSetting.setCNExpRes(i);
			   }else if (CellValue.equals("TestResult")) {
				   glSetting.setCNTesRes(i);
			   }else if (CellValue.equals("HeadersType")) {
				   glSetting.setCNHeadType(i);
			   }else if (CellValue.equals("HeadersValue")) {
				   glSetting.setCNHeadValue(i);
			   }
			   
		   }
		   
	       //关闭各种流
	
	       if (wb != null) {
	    	   wb.close();
	       }
	     
		} catch (Exception e) {
		       e.printStackTrace();
		}
	 }

}
