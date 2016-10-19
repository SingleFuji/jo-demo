package com.jo.demo.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jo.demo.po.DlbMchMap;
import com.jo.demo.redis.RedisStringService;
import com.jo.demo.utils.JsonUtils;

public class DlbExcelUtils {  
    public static List<DlbMchMap> readExcelData(String url)throws Exception{  
          
        // 从XLSX/ xls文件创建的输入流  
        FileInputStream fis = new FileInputStream(url);  
          
        // 创建工作薄Workbook  
        Workbook workBook = null;  
          
        // 读取2007版，以    .xlsx 结尾  
        if(url.toLowerCase().endsWith("xlsx")){  
            try {  
                workBook = new XSSFWorkbook(fis);  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        // 读取2003版，以   .xls 结尾  
        else if(url.toLowerCase().endsWith("xls")){  
            try {  
                workBook = new HSSFWorkbook(fis);  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        
        List<DlbMchMap> mchList = new ArrayList<DlbMchMap>();
        
        //Get the number of sheets in the xlsx file  
        int numberOfSheets = workBook.getNumberOfSheets();  
        
        // 循环 numberOfSheets  
        for(int sheetNum = 0; sheetNum < numberOfSheets; sheetNum++){  
              
            // 得到 工作薄 的第 N个表  
            Sheet sheet = workBook.getSheetAt(sheetNum);  
            Row row;  
            String cell;
            
            for(int i = sheet.getFirstRowNum(); i < sheet.getPhysicalNumberOfRows(); i++){  
                // 循环行数  
                row = sheet.getRow(i);
                DlbMchMap mch = new DlbMchMap();
                for(int j = row.getFirstCellNum(); j < row.getPhysicalNumberOfCells(); j++){  
                    // 循环列数  
                    cell = row.getCell(j).toString();
                    setDlbMchInfo(mch, j, cell);  
                }
                JsonUtils.objectToJson(mch);
                mchList.add(mch);
            }  
        }  
        return mchList;  
    }  
    public static void main(String[] args)throws Exception {
    	String curPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    	String fileName = File.separator + "excels"+ File.separator+"测试商户.xlsx";
        List list = readExcelData(curPath+fileName);   
    }
    
    private static void setDlbMchInfo(DlbMchMap mch, int i, String value){
    	switch (i) {
		case 0:
			mch.setJlMchNo(value);
			return;
		case 1:
			mch.setJlTermNo(value);
			return;
		case 2:
			mch.setDlbMchNo(value);
			return;
		case 3:
			mch.setDlbShopNo(value);
			return;
		case 4:
			mch.setDlbTermNo(value);
			return;
		default:
			return;
		}
    }
}
