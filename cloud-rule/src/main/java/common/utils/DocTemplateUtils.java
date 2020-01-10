package common.utils;


import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 项目：DHGL2.0
 * 版    权: Copyright(C) 2011-2020,  gzcb. All rights reserved
 * 类名：DocTemplateUtils
 * 描述：word 文档导出工具类
 * 创建人：gcb    创建日期：2016年8月31日   版本：
 * 历史
 * <修改人>   <修改日期 >   <修改内容>
 */
public class DocTemplateUtils {
	private static Logger logger = LoggerFactory.getLogger(DocTemplateUtils.class);

	public static File getTemplateFile(String templateDir, String templateName, Map<String, Object> data,String docName) throws Exception {
		logger.info("getTemplateFile start");
					
		// 初始化Freemarker配置
		FileOutputStream outputStream =null;
		OutputStreamWriter outputStreamWriter=null;
		try {
			String templateDirName = FilterUtil.isSecurePath(templateDir);
			File file = new File(templateDirName);	
			Configuration configuration = new Configuration();
			configuration.setDefaultEncoding("utf-8");
			configuration.setDirectoryForTemplateLoading(file);
			Template template = configuration.getTemplate(templateName);
			
			//将数据放入到模板中
			String docName1 = FilterUtil.isSecurePath(docName);
			File outFile = new File(docName1);
			Writer out = null;
			outputStream = new FileOutputStream(outFile);
			outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");// 这个地方对流的编码不可或缺，
			out = new BufferedWriter(outputStreamWriter);
			template.process(data, out);
			
			logger.info("getTemplateFile end");
			
			return outFile;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw e;
		}finally{
			if(outputStreamWriter!=null){
				outputStreamWriter.close();
			}
			
			if(outputStream!=null){
				outputStream.close();
			}
		}
		
		
	}
	
	public static void main(String[] args){
		Map<String,Object> data=new HashMap<String,Object>();
		data.put("custName", "李婷");
		data.put("telPhone", "15818549420");
		data.put("company", 20);
		try {
			String tempFilePath = PropertiesUtils.getProperty("DOWN_LOAD_DOC");
			//当天目录不存在，创建目录
			String date=DateUtils.format(new Date(), "yyyyMMdd");
			String fileDir=tempFilePath + File.separator +date;
			String fileDirName = FilterUtil.isSecurePath(fileDir);
			File fieDirFile=new File(fileDirName);
			if(!fieDirFile.exists()){
				fieDirFile.mkdirs();
			}

			DocTemplateUtils.getTemplateFile("D:\\testDoc", "D:\\testDoc\\visitDocTemplate.ftl", data,fileDir+File.separator+"test.doc");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

