package common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class FileEncodeUtils {

	public static Logger logger = LoggerFactory.getLogger(FileEncodeUtils.class);
	public static final int LANG = 2;

	public static String getFileEncoding(File file) {
		String result = "";
		FileCharsetDetector detector=new FileCharsetDetector();
		String charsets;
		try {
			charsets = detector.guessFileEncoding(file, LANG);
			if (charsets!=null) {
				String[] charset = charsets.split(",");
				if (charset!=null) {
					if (charset.length>=1) {
						result=charset[0];
					}
				}
			}else{
				result = "UTF-8";
			}
		} catch (IOException e) {
			logger.error("获取文件编码错误。",e);
		}
		return result ;
	}

}
