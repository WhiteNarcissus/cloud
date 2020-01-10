package common.utils;

import com.Ostermiller.util.CSVParse;
import com.Ostermiller.util.CSVParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class CSVReader {
	private int currentLineNum;
	private String[] currLine;
	private String[] header;
	private CSVParse csvParser;
	//打印日志
	private static Logger logger = LoggerFactory.getLogger(CSVReader.class);
	public CSVReader(InputStream is) {
		csvParser = new CSVParser(is);
		currentLineNum = csvParser.getLastLineNumber();
	}
	public CSVReader( File file , boolean withHeader) throws IOException{
		FileInputStream fis = null;
		InputStreamReader ssss = null;
		try{
			fis = new FileInputStream(file);
			ssss = new InputStreamReader(fis,IOUtils.getFilecharset(file));
			csvParser = new CSVParser(ssss);
			if(withHeader) {
                header = csvParser.getLine();
            }
			currentLineNum = csvParser.getLastLineNumber();
		} finally{
			/*if(ssss!=null){
				try{
					ssss.close();
				}catch(IOException e){
					logger.error("InputStreamReader资源关闭异常:"+e.getMessage(),e);
				}
			}
			if(fis!=null){
				try{
					fis.close();
				}catch(IOException e){
					logger.error("FileInputStream资源关闭异常:"+e.getMessage(),e);
				}
			}*/
		}
		
	}
	
	public CSVReader( File file , boolean withHeader,char changeDelimiter) throws IOException{
		FileInputStream fis = null;
		InputStreamReader ssss = null;
		try{
			fis = new FileInputStream(file);
			ssss = new InputStreamReader(fis,IOUtils.getFilecharset(file));
			csvParser = new CSVParser(ssss);
			csvParser.changeDelimiter(changeDelimiter);  //重新设置分隔符
			if(withHeader) {
                header = csvParser.getLine();
            }
			currentLineNum = csvParser.getLastLineNumber();
		} finally{

		}
		
	}

	//20190905 有编码默认格式
	public CSVReader( File file , boolean withHeader,char changeDelimiter,String code) throws IOException{
		FileInputStream fis = null;
		InputStreamReader ssss = null;
		try{
			fis = new FileInputStream(file);
			ssss = new InputStreamReader(fis,code);
			csvParser = new CSVParser(ssss);
			csvParser.changeDelimiter(changeDelimiter);  //重新设置分隔符
			if(withHeader) {
				header = csvParser.getLine();
			}
			currentLineNum = csvParser.getLastLineNumber();
		} finally{

		}

	}
	
	public String[] readLine() throws IOException {
		currLine = csvParser.getLine();
		setCurrentLineNum(csvParser.getLastLineNumber());
		return currLine;
	}
		
	public String[] getCurrLine() {
		return currLine;
	}

	public void setCurrLine(String[] currLine) {
		this.currLine = currLine;
	}

	public int getCurrentLineNum() {
		return currentLineNum;
	}

	public void setCurrentLineNum(int lineNum) {
		this.currentLineNum = lineNum;
	}
	
	public void close() throws IOException {
		csvParser.close();
	}
	
	
	public String[] getHeader() {
		return header;
	}
	public void setHeader(String[] header) {
		this.header = header;
	}
			
	public static void printLine(String[] line) {
		for(int i =0; i< line.length; i++) {
			System.out.print(line[i] + "    |");
		}
		System.out.println();
	}
}
