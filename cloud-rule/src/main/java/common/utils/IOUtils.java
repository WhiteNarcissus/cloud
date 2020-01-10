package common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IOUtils {
	
	private static Logger logger = LoggerFactory.getLogger(IOUtils.class);
	/**
	 * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
	 * 
	 * @param fileName
	 *            文件的名
	 */
	public static void readFileByBytes(String fileName) {
		fileName = FilterUtil.isSecurePath(fileName);
		File file = new File(fileName);
		InputStream in = null;
		try {
			logger.info("以字节为单位读取文件内容，一次读一个字节：");
			// 一次读一个字节
			in = new FileInputStream(file);
			int tempbyte;
			while ((tempbyte = in.read()) != -1) {
				System.out.write(tempbyte);
			}
			in.close();
		} catch (IOException e) {
			logger.error("IO资源操作异常:"+e.getMessage(),e);
			return;
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					logger.error("InputStream资源关闭异常:"+e.getMessage(),e);
				}
			}
		}
	}

	/**
	 * 以字符为单位读取文件，常用于读文本，数字等类型的文件
	 * 
	 * @param fileName
	 *            文件名
	 */
	public static void readFileByChars(String fileName) {
		fileName = FilterUtil.isSecurePath(fileName);
		File file = new File(fileName);
		FileInputStream fis = null;
		Reader reader = null;
		/*try {
			logger.info("以字符为单位读取文件内容，一次读一个字节：");
			// 一次读一个字符
			reader = new InputStreamReader(new FileInputStream(file),"GBK");
			int tempchar;
			while ((tempchar = reader.read()) != -1){
				//对于windows下，/r/n这两个字符在一起时，表示一个换行。
				//但如果这两个字符分开显示时，会换两次行。
				//因此，屏蔽掉/r，或者屏蔽/n。否则，将会多出很多空行。
				if (((char)tempchar) != '\r'){
					System.out.print((char)tempchar);
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		try {
			logger.info("以字符为单位读取文件内容，一次读多个字节：");
			// 一次读多个字符
			char[] tempchars = new char[30];
			int charread = 0;
			fis = new FileInputStream(fileName);
			reader = new InputStreamReader(fis, "GBK");
			// 读入多个字符到字符数组中，charread为一次读取字符数
			while ((charread = reader.read(tempchars)) != -1) {
				// 同样屏蔽掉/r不显示
				if ((charread == tempchars.length)
						&& (tempchars[tempchars.length - 1] != '\r')) {
					logger.info(tempchars.toString());
				} else {
					for (int i = 0; i < charread; i++) {
						if (tempchars[i] == '\r') {
							continue;
						} else {
//							
						}
					}
				}
			}

		} catch (Exception e1) {
			logger.error("读取文件失败:"+e1.getMessage());
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					logger.error("Reader关闭异常:"+e1.getMessage(),e1);
				}
			}
			if(fis!=null){
				try{
					fis.close();
				}catch(IOException e1){
					logger.error("FileInputStream关闭异常:"+e1.getMessage(),e1);
				}
			}
		}
	}

	public static List<String> readFileByCharsLength(String fileName) {
		fileName = FilterUtil.isSecurePath(fileName);
		File file = new File(fileName);
		FileInputStream fileInputStream = null;
		Reader reader = null;
		List<String> list = new ArrayList<String>();
		try {
			logger.info("以字符为单位读取文件内容，一次读多个字符：");
			// 一次读多个字符
			
			char[] tempchars = new char[2008];
			int charread = 0;
			fileInputStream = new FileInputStream(fileName);
			reader = new InputStreamReader(fileInputStream, "GBK");
			// 读入多个字符到字符数组中，charread为一次读取字符数
			while ((charread = reader.read(tempchars)) != -1) {
				// 同样屏蔽掉/r不显示
				if ((charread == tempchars.length)
						&& (tempchars[tempchars.length - 1] != '\r')) {
					String tempStr=new String(tempchars);
					list.add(tempStr);
				} else {
					for (int i = 0; i < charread; i++) {
						if (tempchars[i] == '\r') {
							continue;
						} else {
							String tempStr=new String(tempchars);
							list.add(tempStr);
						}
					}
				}

				
			}
		} catch (Exception e1) {
			logger.error("读取文件信息异常:"+e1.getMessage(),e1);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ex) {
					logger.error("Reader资源关闭异常:"+ex.getMessage(),ex);
				}
			}
			if(fileInputStream!=null){
				try{
					fileInputStream.close();
				}catch(IOException ex){
					logger.error("FileInputStream资源关闭异常:"+ex.getMessage(),ex);
				}
			}
		}
		return list;
	}

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 * 
	 * @param fileName
	 *            文件名
	 */
	public static void readFileByLines(String fileName) {
		fileName = FilterUtil.isSecurePath(fileName);
		File file = new File(fileName);
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader reader = null;
		try {
			logger.info("以行为单位读取文件内容，一次读一整行：");
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis,"GBK");
			reader = new BufferedReader(isr);
			
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				logger.info("line {} : {}",line ,tempString);
				line++;
			}
			reader.close();
		} catch (IOException e) {
			logger.error("读取文件信息异常:"+e.getMessage(),e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					logger.error("Reader资源关闭异常:"+e1.getMessage(),e1);
				}
			}
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException e1) {
					logger.error("InputStreamReader资源关闭异常:"+e1.getMessage(),e1);
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e1) {
					logger.error("FileInputStream资源关闭异常:"+e1.getMessage(),e1);
				}
			}
		}
	}

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件,返回list
	 * 
	 * @param fileName
	 *            文件名
	 */
	public static List<String> readFileByLine(String fileName) {
		fileName = FilterUtil.isSecurePath(fileName);
		File file = new File(fileName);
		String encode = getFilecharset(file);
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader reader = null;
		try {
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis,encode);
			reader = new BufferedReader(isr);
			String tempString = null;
			List<String> list = new ArrayList<String>();
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				list.add(tempString);
			}
			reader.close();
			return list;
		} catch (IOException e) {
			logger.error("读取文件信息异常:"+e.getMessage(),e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					logger.error("Reader资源关闭异常:"+e1.getMessage(),e1);
				}
			}
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException e1) {
					logger.error("InputStreamReader资源关闭异常:"+e1.getMessage(),e1);
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e1) {
					logger.error("FileInputStream资源关闭异常:"+e1.getMessage(),e1);
				}
			}
		}
		return null;
	}
	

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件,返回list
	 * 
	 * @param fileName 文件名
	 * @param index  起始行
	 * @param size  读取条数
	 * @return
	 */
	public static List<String> readFileByLine(String fileName , long index  , int size) {
		fileName = FilterUtil.isSecurePath(fileName);
		File file = new File(fileName);
		String encode = getFilecharset(file);
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader reader = null;
		try {
			fis = new FileInputStream(file);
			isr =new InputStreamReader(fis,encode);
			reader = new BufferedReader(isr);
			String tempString = null;
			List<String> list = new ArrayList<String>();
			// 一次读入一行，直到读入null为文件结束
			reader.skip(index);
			while ((tempString = reader.readLine()) != null && size > 0) {
				list.add(tempString);
				size --;
				/*if(index <= 0){
					list.add(tempString);
					size --;
				}else{
					index --;
				}*/
			}
			reader.close();
			return list;
		} catch (IOException e) {
			logger.error("文件读取异常:"+e.getMessage(),e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					logger.error("Reader资源关闭异常:"+e1.getMessage(),e1);
				}
			}
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException e1) {
					logger.error("InputStreamReader资源关闭异常:"+e1.getMessage(),e1);
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e1) {
					logger.error("FileInputStream资源关闭异常:"+e1.getMessage(),e1);
				}
			}
		}
		return null;
	}
	
	/**
	 * 随机读取文件内容
	 * 
	 * @param fileName
	 *            文件名
	 */
	public static void readFileByRandomAccess(String fileName) {
		RandomAccessFile randomFile = null;
		try {
			logger.info("随机读取一段文件内容：");
			// 打开一个随机访问文件流，按只读方式
			randomFile = new RandomAccessFile(fileName, "r");
			// 文件长度，字节数
			long fileLength = randomFile.length();
			// 读文件的起始位置
			int beginIndex = (fileLength > 4) ? 4 : 0;
			// 将读文件的开始位置移到beginIndex位置。
			randomFile.seek(beginIndex);
			byte[] bytes = new byte[10];
			int byteread = 0;
			// 一次读10个字节，如果文件内容不足10个字节，则读剩下的字节。
			// 将一次读取的字节数赋给byteread
			while ((byteread = randomFile.read(bytes)) != -1) {
				System.out.write(bytes, 0, byteread);
			}
		} catch (IOException e) {
			logger.error("随机读取文件异常:"+e.getMessage(),e);
		} finally {
			if (randomFile != null) {
				try {
					randomFile.close();
				} catch (IOException e1) {
					logger.error("RandomFile资源关闭异常:"+e1.getMessage(),e1);
				}
			}
		}
	}

	/**
	 * 显示输入流中还剩的字节数
	 * 
	 * @param in
	 */
	private static void showAvailableBytes(InputStream in) {
		try {
			logger.info("当前字节输入流中的字节数为:{}", in.available());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getFilecharset(File sourceFile) {
		String charset = "GBK";
		byte[] first3Bytes = new byte[3];
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		try {
			boolean checked = false;
			fis = new FileInputStream(sourceFile);
			bis = new BufferedInputStream(fis);
			bis.mark(0);
			int read = bis.read(first3Bytes, 0, 3);
			if (read == -1) {
				return charset; // 文件编码为 ANSI
			} else if (first3Bytes[0] == (byte) 0xFF
					&& first3Bytes[1] == (byte) 0xFE) {
				charset = "UTF-16LE"; // 文件编码为 Unicode
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xFE
					&& first3Bytes[1] == (byte) 0xFF) {
				charset = "UTF-16BE"; // 文件编码为 Unicode big endian
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xEF
					&& first3Bytes[1] == (byte) 0xBB
					&& first3Bytes[2] == (byte) 0xBF) {
				charset = "UTF-8"; // 文件编码为 UTF-8
				checked = true;
			}
			bis.reset();
			if (!checked) {
				//int loc = 0;
				while ((read = bis.read()) != -1) {
					//loc++;
					if (read >= 0xF0) {
                        break;
                    }
					if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
                    {
                        break;
                    }
					if (0xC0 <= read && read <= 0xDF) {
						read = bis.read();
						if (0x80 <= read && read <= 0xBF) // 双字节 (0xC0 - 0xDF)
							// (0x80
							// - 0xBF),也可能在GB编码内
                        {
                            continue;
                        } else {
                            break;
                        }
					} else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小
						read = bis.read();
						if (0x80 <= read && read <= 0xBF) {
							read = bis.read();
							if (0x80 <= read && read <= 0xBF) {
								charset = "UTF-8";
								break;
							} else {
                                break;
                            }
						} else {
                            break;
                        }
					}
				}
			}
			bis.close();
		} catch (Exception e) {
			logger.error("获取文件字符集异常:"+e.getMessage(),e);
		} finally{
			if(bis!=null){
				try{
					bis.close();
				}catch(IOException e1){
					logger.error("BufferedInputStream资源关闭异常:"+e1.getMessage(),e1);
				}
			}
			if(fis!=null){
				try{
					fis.close();
				}catch(IOException e1){
					logger.error("FileInputStream资源关闭异常:"+e1.getMessage(),e1);
				}
			}
		}
		return charset;
	}

    public static String trimGBK(byte[] buf,int begin,int end){
    	return null;

    }  


	/**
	 * 文件操作:拷贝，删除
	 * 
	 * @param executepath
	 * @param backupfile
	 */
	public static boolean copyFile(String executepath, String backupfile) {
		FileInputStream fileInputStream = null;
		FileOutputStream fileOutputStream = null;
		// 将执行区域的文件拷贝至备份区域
		InputStream fis = null;
		OutputStream fos = null;
		executepath = FilterUtil.isSecurePath(executepath);
		backupfile = FilterUtil.isSecurePath(backupfile);
		File efile = new File(executepath);
		File bfile = new File(backupfile);
		try {
			fileInputStream = new FileInputStream(efile);
			fileOutputStream = new FileOutputStream(bfile);
			fis = new BufferedInputStream(fileInputStream);
			fos = new BufferedOutputStream(fileOutputStream);
			byte[] buffer = new byte[1024];
			int i;
			while ((i = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, i);
			}
		} catch (Exception e) {
			logger.error("复制文件内容异常:"+e.getMessage(),e);
			return false;
		} finally {
			try {
				if(fileOutputStream!=null){
					fileOutputStream.close();
				}
				if(fileInputStream!=null){
					fileInputStream.close();
				}
				if (fos != null){
					fos.flush();
					fos.close();
				}
				if (fis != null){
					fis.close();
				}
			} catch (Exception e) {
				logger.error("资源关闭异常:"+e.getMessage(),e);
			}
		}
		return true;
	}
	
	public static void main(String[] args) throws Exception {
		String fileName = "D:/attach/Bill/unZip/6413-STMT0002-01-20150515";
		fileName = FilterUtil.isSecurePath(fileName);
		File file = new File(fileName);
		String encoding=IOUtils.getFilecharset(file);

		List<String> list = IOUtils.readFileByLine(fileName  ,  819 , 10);
		//List<String> list1 = IOUtils.readFileByLine(fileName  ,  0 , 10);
		logger.info("集合大小为：{}" , list.size());
//		byte[] bytes = list.get(0).getBytes("GBK");
//		byte[] b = new byte[8];
//		b[0] = bytes[46];
//		b[1] = bytes[47];
//		b[2] = bytes[48];
//		b[3] = bytes[49];
//		b[4] = bytes[50];
//		b[5] = bytes[51];
//		b[6] = bytes[52];
//		b[7] = bytes[53];
//		
//		
//		String newStr = new String(b,"GBK");
	}
}
