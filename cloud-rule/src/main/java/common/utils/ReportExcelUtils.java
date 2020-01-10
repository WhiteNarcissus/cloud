package common.utils;

import jxl.Workbook;
import jxl.biff.DisplayFormat;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 生产Excel create by liting at 2016年5月4日
 * 
 */
public class ReportExcelUtils {
	private static Logger logger = LoggerFactory.getLogger(ReportExcelUtils.class);

	/**
	 * 
	 * function
	 * 
	 * @param output
	 *            输出流
	 * @param filename
	 *            输出文件名
	 * @param header
	 *            报头
	 * @param title
	 *            表头
	 * @param data
	 *            数据
	 */
	public static void exportExcelWeb(HttpServletResponse response, String fileName, String header, List<Map<String,String>> titleHeader, List<Map<String,String>> title, List<Object[]> data) {
		OutputStream output=null;
		try {
			 output = response.getOutputStream();
			response.reset();// 清空输出流
			// 下面是对中文文件名的处理
			response.setCharacterEncoding("UTF-8");// 设置相应内容的编码格式
			fileName = java.net.URLEncoder.encode(fileName, "UTF-8");

			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes("UTF-8"), "GBK") + ".xls");
			response.setContentType("application/msexcel");// 定义输出类型

			exportExcel(output, header,titleHeader, title, data);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}finally{
			if(output!=null){
				try {
					output.close();
				} catch (IOException e) {
					logger.error("OutputStream资源关闭异常:",e);
				}
			}
		}
	}
	
	
	
	

	/**
	 * 
	 * function
	 * 
	 * @param output
	 *            输出流
	 * @param header
	 *            报头
	 * @param title
	 *            表头和格式,0:表头名，1：该列的类型，2：该列显示的格式
	 * @param data
	 *            数据
	 */
	public static void exportExcel(OutputStream output, String header, List<Map<String,String>> titleHeader,List<Map<String,String>> title, List<Object[]> data) {
		// 创建工作薄
		logger.info("export excel has start");
		WritableWorkbook workbook = null;
		try {
			workbook = Workbook.createWorkbook(output);
			fillSheet("First Sheet",0,header, titleHeader,title, data, workbook);
			workbook.write();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {

			try {
				if (workbook != null) {
					workbook.close();
				}
				if (output != null) {
					output.close();
				}
			} catch (Exception e) {
				logger.error(" close the output has a wrong ", e);
			}
		}
	}

	private static void fillSheet(String sheetName,int sheetIndex,String header, List<Map<String,String>> titleHeader,List<Map<String,String>> title, List<Object[]> data, WritableWorkbook workbook)
			throws WriteException, RowsExceededException {
		WritableFont headerFont = new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.BOLD, false,
				UnderlineStyle.NO_UNDERLINE);

		WritableCellFormat headerFormat = new WritableCellFormat();
		headerFormat.setAlignment(Alignment.CENTRE); // 设置居中
		headerFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 设置边框线
		headerFormat.setFont(headerFont);

		// 设置表头格式
		WritableCellFormat titleFormat = new WritableCellFormat();
		titleFormat.setAlignment(Alignment.CENTRE); // 设置居中
		titleFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 设置边框线
		titleFormat.setFont(headerFont);

		WritableCellFormat cellFormat = new WritableCellFormat();
		cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 设置边框线

		// 创建新的一页
		WritableSheet sheet = workbook.createSheet(sheetName, sheetIndex);
		int columsSize = 0;
		if (title == null) {
			title = new ArrayList<Map<String,String>>();
		}

		columsSize = title.size();

		// 设置报头
		int initRow = 0;
		if (!StringUtils.isNullOrEmpty(header)) {
			sheet.mergeCells(0, 0, columsSize - 1, 0);
			sheet.addCell(new Label(0, 0, header, headerFormat));
			initRow =initRow+ 1;
		}

		// 设置表格宽度
		for (int i = 0; i < columsSize; i++) {
			sheet.setColumnView(i, 20);
		}
		
		//设置表格一个表头
		if(titleHeader!=null){
			int i=0;
			int mergeColumNum=0;
			for(Map<String,String> titleHeaderDetail:titleHeader){
				String sizeStr=titleHeaderDetail.get("size");
				if(StringUtils.isNullOrEmpty(sizeStr)){
					sizeStr="1";
				}
				int size=Integer.parseInt(sizeStr);
				String desc=titleHeaderDetail.get("desc");
				sheet.mergeCells(mergeColumNum, initRow, (mergeColumNum+size - 1), initRow);
				sheet.addCell(new Label(mergeColumNum, initRow, desc, headerFormat));
				
				mergeColumNum=mergeColumNum+size;
				i++;
			}
			
			if(i>0){
				initRow = initRow + 1;
			}
		}

		// 设置表头
		for (int i = 0; i < columsSize; i++) {

			sheet.addCell(new Label(i, 0 + initRow, title.get(i).get("desc"), titleFormat));
		}
		if (columsSize > 0) {
			initRow = initRow + 1;
		}

		if (data != null) {
			for (int i = 0; i < data.size(); i++) {
				Object[] values = data.get(i);

				if (values != null) {
					for (int j = 0; j < values.length; j++) {
						Map<String,String> titleAndType = title.get(j);
						String type=titleAndType.get("type");
						String format=titleAndType.get("format");
						if (values[j] != null) {
							if (StringUtils.isNullOrEmpty(format) && !StringUtils.isNullOrEmpty(type)) {
								
								if ("number".equals(type)) {
									Number labelN = new Number(j, i + initRow, Double.valueOf(values[j].toString()),
											cellFormat);
									sheet.addCell(labelN);

								} else if ("date".equals(type) && values[j] instanceof Date) {

									DateTime labelDT = new DateTime(j, i + initRow, (Date) values[j], cellFormat);
									sheet.addCell(labelDT);

								} else if ("boolean".equals(type)) {

									jxl.write.Boolean labelB = new jxl.write.Boolean(j, i + initRow,
											(Boolean) values[j], cellFormat);
									sheet.addCell(labelB);

								} else if ("percent".equals(type)) {
									DisplayFormat displayFormat = NumberFormats.PERCENT_FLOAT;
									WritableCellFormat wcfN = new WritableCellFormat(displayFormat);
									wcfN.setBorder(Border.ALL, BorderLineStyle.THIN); // 设置边框线
									Number labelN = new Number(j, i + initRow, Double.valueOf(values[j].toString()),
											wcfN);
									sheet.addCell(labelN);
								} else {
									sheet.addCell(new Label(j, i + initRow, String.valueOf(values[j]), cellFormat));
								}
							} else if (!StringUtils.isNullOrEmpty(format) && !StringUtils.isNullOrEmpty(type)) {								
								if ("number".equals(type)) {
									Number labelN = null;
									if (!StringUtils.isNullOrEmpty(format)) {
										NumberFormat nf = new NumberFormat(format);
										WritableCellFormat wcfN = new WritableCellFormat(nf);
										wcfN.setBorder(Border.ALL, BorderLineStyle.THIN); // 设置边框线
										labelN = new Number(j, i + initRow, Double.valueOf(values[j].toString()), wcfN);
									} else {
										labelN = new Number(j, i + initRow, Double.valueOf(values[j].toString()),
												cellFormat);
									}
									sheet.addCell(labelN);
								} else if ("date".equals(type) && values[j] instanceof Date) {

									DateTime labelDTF = null;
									if (!StringUtils.isNullOrEmpty(format)) {
										DateFormat df = new DateFormat(format);
										WritableCellFormat wcfDF = new WritableCellFormat(df);
										wcfDF.setBorder(Border.ALL, BorderLineStyle.THIN); // 设置边框线

										labelDTF = new DateTime(j, i + initRow, (Date) values[j], wcfDF);

									} else {
										labelDTF = new DateTime(j, i + initRow, (Date) values[j], cellFormat);
									}
									sheet.addCell(labelDTF);
								} else if ("percent".equals(type)) {
									DisplayFormat displayFormat = NumberFormats.PERCENT_FLOAT;
									WritableCellFormat wcfN = new WritableCellFormat(displayFormat);
									wcfN.setBorder(Border.ALL, BorderLineStyle.THIN); // 设置边框线
									Number labelN = new Number(j, i + initRow, Double.valueOf(values[j].toString()),
											wcfN);
									sheet.addCell(labelN);
								} else {
									sheet.addCell(new Label(j, i + initRow, String.valueOf(values[j]), cellFormat));
								}

							} else {
								sheet.addCell(new Label(j, i + initRow, String.valueOf(values[j]), cellFormat));
							}

						} else {
							sheet.addCell(new Label(j, i + initRow, "", cellFormat));
						}
					}

				}
			}
		}
	}
}
