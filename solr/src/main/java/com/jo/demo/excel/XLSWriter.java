package com.jo.demo.excel;

import java.io.FileOutputStream;  

import org.apache.poi.xssf.usermodel.XSSFCell;  
import org.apache.poi.xssf.usermodel.XSSFRichTextString;  
import org.apache.poi.xssf.usermodel.XSSFRow;  
import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  
/** 
 *  
 * @author Snow 
 * 简单的写入 excel 
 * HSSF实现 excel 2003(以 .xls 结尾的文件) 
 * XSSF实现 excel 2007(以 .xlsx 结尾的文件) 
 */  
public class XLSWriter {  
    public static void main(String[] args)throws Exception{  
        // 创建工作薄  
        XSSFWorkbook workBook = new XSSFWorkbook();  
        // 在工作薄中创建一工作表  
        XSSFSheet sheet = workBook.createSheet();  
        // 在指定的索引处创建一行  
        XSSFRow row = sheet.createRow(0);  
        // 在指定的索引处创建一列（单元格）  
        XSSFCell code = row.createCell(0);  
        // 定义单元格为字符串类型  
        code.setCellType(XSSFCell.CELL_TYPE_STRING);  
        // 在单元格输入内容  
        XSSFRichTextString codeContent = new XSSFRichTextString("医院编号");  
        code.setCellValue(codeContent);  
        XSSFCell city = row.createCell(1);  
        city.setCellType(XSSFCell.CELL_TYPE_STRING);  
        XSSFRichTextString cityContent = new XSSFRichTextString("城市");  
        city.setCellValue(cityContent);  
        // 新建一输出流并把相应的excel文件存盘  
        FileOutputStream fos = new FileOutputStream("E:/hos.xlsx");  
        workBook.write(fos);  
        fos.flush();  
        //操作结束，关闭流  
        fos.close();  
        System.out.println("文件生成");  
    }  
}