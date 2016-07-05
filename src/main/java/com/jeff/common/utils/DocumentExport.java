package com.jeff.common.utils;

import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64.OutputStream;


/**
 * 
 * @name DocumentExport
 * @author jeffwcx
 * @description 文件自动生成导出
 */
public class DocumentExport {
	
	public static HSSFWorkbook buildExcel(Excel excel, List<Object[]> queryList){
		HSSFWorkbook workBook = new HSSFWorkbook();
		HSSFSheet sheet = workBook.createSheet();
		//设置列的宽度
		for(int i=0; i<excel.getCellWidths().length; i++){
			sheet.setColumnWidth(i, excel.getCellWidths()[i]);
		}
		//创建字体样式
		HSSFFont columnHeadFont = workBook.createFont();
		columnHeadFont.setFontName("黑体");
		columnHeadFont.setFontHeightInPoints((short)12);
		columnHeadFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		//设置列头的样式
		HSSFCellStyle columnHeadStyle = workBook.createCellStyle();
		columnHeadStyle.setFont(columnHeadFont);
		columnHeadStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		columnHeadStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		columnHeadStyle.setLocked(true);
		columnHeadStyle.setWrapText(true);
		//设置单元格背景颜色
		columnHeadStyle.setFillBackgroundColor(HSSFColor.WHITE.index);
		
		
		HSSFFont columnFont = workBook.createFont();
		columnFont.setFontName("黑体");
		columnFont.setFontHeightInPoints((short)11);
		HSSFCellStyle columnStyle = workBook.createCellStyle();
		columnStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		columnStyle.setFont(columnFont);
		columnStyle.setLocked(true);
		columnStyle.setWrapText(true);
		//第一行
		HSSFRow row0 = sheet.createRow(0);
		row0.setHeight((short)900);
		HSSFCell cell0 = row0.createCell(0);
		cell0.setCellValue(new HSSFRichTextString(excel.getTitle()));
		cell0.setCellStyle(columnHeadStyle);
		//合并多个个单元格
		CellRangeAddress range = new CellRangeAddress(0,0,0,excel.getCellValues().length-1);
		sheet.addMergedRegion(range);
		HSSFRow row1 = sheet.createRow(1);
		
		row1.setHeight((short)500);
		for(int i=0; i<excel.getCellValues().length; i++){
			HSSFCell cell = row1.createCell(i);
			cell.setCellValue(new HSSFRichTextString(excel.getCellValues()[i]));
			cell.setCellStyle(columnHeadStyle);
		}
		
		for(int i=0; i<queryList.size();i++){
			HSSFRow row = sheet.createRow(i+2);
			row.setHeight((short)400);
			Object[] temp = queryList.get(i);
			for(int j=0; j<temp.length; j++){
				HSSFCell cell = row.createCell(j);
				cell.setCellValue(new HSSFRichTextString(temp[j]==null?"":temp[j].toString()));
				cell.setCellStyle(columnStyle);
			}
		}
		
		return workBook;
		
	}
	
	/**
	 * 
	 * @author jeffwcx
	 * @method buildPdf
	 * @param @param out,输出流
	 * @param @param pdf,pdf描述对象
	 * @param @param fontUrl生成Pdf需要导入字体
	 * @param @throws Exception
	 * @return void
	 * @description 根据描述生成 Pdf文档
	 * @date 2016年2月25日 下午7:43:30
	 */
	public static void buildPdf(OutputStream out, Pdf pdf, String fontUrl) throws Exception{
		Document document = new Document(PageSize.A4,20,20,20,20);
		String[] header =  pdf.getHeader();
		int colNum = header.length;
		List<Object[]> dataList = pdf.getData();
		if(dataList.size()>0&&dataList!=null){
			if(dataList.get(0).length!=header.length){
				throw new Exception("表头的长度与数据的长度不等");
			}
		}
		PdfWriter pdfWriter = PdfWriter.getInstance(document, out);
		BaseFont baseFont= BaseFont.createFont(fontUrl, BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
		document.open();
		 PdfPCell cell = null;
	        // 创建需要填入文档的元素
	        PdfPTable table = new PdfPTable(colNum);
	        //生成标题
	        cell = new PdfPCell(new Paragraph(
	                new Paragraph(pdf.getTitle(), new Font(baseFont))));
	        cell.setColspan(colNum); 
	        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	        table.addCell(cell);
	        table.setHorizontalAlignment(Element.ALIGN_CENTER);
	        table.setWidthPercentage(100);
	        //生成头部
	        for(String title : header){
	        	cell = new PdfPCell(new Paragraph(
	 	                new Paragraph(title, new Font(baseFont))));
	 	        cell.setColspan(1); 
	 	        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 	        table.addCell(cell);
	        }
	        
	        if(dataList.size()==0){
	        	cell = new PdfPCell(new Paragraph(
	 	                new Paragraph("记录为空", new Font(baseFont))));
	 	        cell.setColspan(colNum); 
	 	        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	 	        table.addCell(cell);
	        }
	        else
	        {
	        	for(int i=0; i<dataList.size(); i++){
		        	Object[] temp = dataList.get(i);
		        	for(int j=0; j<temp.length; j++){
		        		if(temp[j]==null){
		        			temp[j]="";
		        		}
		        		cell = new PdfPCell(new Paragraph(
			 	                new Paragraph(String.valueOf(temp[j]), new Font(baseFont))));
			 	        cell.setColspan(1); 
			 	        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 	        table.addCell(cell);
		        	}
		        }
	        }
	        
		document.add(table);
		document.close();
		
	}
	
	
}
