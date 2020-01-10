package common.utils;

import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageEncoder;
import com.sun.media.jai.codec.JPEGEncodeParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

public class TifToJpegUtils {
	
	private static Logger logger=LoggerFactory.getLogger(TifToJpegUtils.class);

	/**
	 * 创建ZIP文件
	 * 
	 * @param sourcePath
	 *            文件或文件夹路径
	 * @param zipPath
	 *            生成的zip文件存在路径（包括文件名）
	 * @throws Exception 
	 */
	public static String getJpgPath(String inputString,String output) throws Exception {
		RenderedOp src=JAI.create("url", new URL(inputString));
		String[] rString=inputString.split("/");
		String filenameaString=rString[rString.length-1];
		String fileName=filenameaString.substring(0, 19)+".jpg";
		output=output+fileName;
		output = FilterUtil.isSecurePath(output);
		OutputStream os2 = null;
		try{
			os2 = new FileOutputStream(output);
			JPEGEncodeParam param2 = new JPEGEncodeParam();
			//指定格式类型，jpg 属于 JPEG 类型
			ImageEncoder enc2 = ImageCodec.createImageEncoder("JPEG", os2, param2);
			enc2.encode(src);
		}catch(IOException e){
			logger.error("获取JPG图片路径失败:"+e.getMessage());
		}finally{
			if(os2!=null){
				try{
					os2.close();
				}catch(IOException i){
					logger.error("OutputStream资源关闭异常:",i);
				}
			}
		}
		return fileName;
	}
	public static boolean deleteJpgFile(String path){
		path = FilterUtil.isSecurePath(path);
		File file= new File(path);
		if (file.exists()) {
			file.delete();
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		try {
			URL url=new URL("http://180.1.18.14:8082/ImageSystemServer/imageQueryServlet?ABPATH=/2016/3/16022209330120249/2016022210100592/2016022210100592001.tif");
			//TifToJpegUtils.getJpgPath(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}
