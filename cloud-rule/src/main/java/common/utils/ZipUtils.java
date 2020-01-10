package common.utils;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Enumeration;

public class ZipUtils {
	
	private static Logger logger=LoggerFactory.getLogger(ZipUtils.class);

	/**
	 * 创建ZIP文件
	 * 
	 * @param sourcePath
	 *            文件或文件夹路径
	 * @param zipPath
	 *            生成的zip文件存在路径（包括文件名）
	 */
	public static void createZip(String sourcePath, String zipPath) {
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
		try {
			fos = new FileOutputStream(zipPath);
			zos = new ZipOutputStream(fos);
			sourcePath = FilterUtil.isSecurePath(sourcePath);
			writeZip(new File(sourcePath), "", zos);
		} catch (FileNotFoundException e) {
			logger.error("文件不存在:"+e.getMessage());
		} finally {
			try {
				if (zos != null) {
					zos.close();
				}
			} catch (IOException e) {
				logger.error("ZipOutputStream资源关闭失败:",e);
			}
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				logger.error("FileOutputStream资源关闭失败:",e);
			}
		}
	}

	private static void writeZip(File file, String parentPath, ZipOutputStream zos) {
		if (file.exists()) {
			// 处理文件夹
			if (file.isDirectory()) {
				parentPath += file.getName() + File.separator;
				File[] files = file.listFiles();
				for (File f : files) {
					writeZip(f, parentPath, zos);
				}
			} else {
				FileInputStream fis = null;
				BufferedInputStream bis = null;
				DataInputStream dis = null;
				try {
					fis = new FileInputStream(file);
					bis = new BufferedInputStream(fis);
					dis = new DataInputStream(bis);
					ZipEntry ze = new ZipEntry(parentPath + file.getName());
					zos.putNextEntry(ze);
					// 添加编码，如果不添加，当文件以中文命名的情况下，会出现乱码
					// ZipOutputStream的包一定是apache的ant.jar包。JDK也提供了打压缩包，但是不能设置编码
					zos.setEncoding("GBK");
					byte[] content = new byte[1024];
					int len;
					while ((len = fis.read(content)) != -1) {
						zos.write(content, 0, len);
						zos.flush();
					}
				} catch (FileNotFoundException e) {
					logger.error("文件不存在:"+e.getMessage());
				} catch (IOException e) {
					logger.error("IO处理异常:"+e.getMessage());
				} finally {
					try {
						if (dis != null) {
							dis.close();
						}
					} catch (IOException e) {
						logger.error("DataInputStream资源关闭异常:",e);
					}
				}
			}
		}
	}
	/**
	 * 文件解压到目标路径
	 * @param zipFilePath
	 * @param targetPath
	 * @throws IOException
	 */
	public static void unzip(String zipFilePath, String targetPath)
			throws IOException {
		
		InputStream is = null;
		ZipFile zipFile = null;
		try {
			logger.info("解压文件开始，文件路基{}，解压路径{}",zipFilePath,targetPath);
			zipFile = new ZipFile(zipFilePath);
			String directoryPath = "";
			if (null == targetPath || "".equals(targetPath)) {
				directoryPath = zipFilePath.substring(0, zipFilePath
						.lastIndexOf("."));
			} else {
				directoryPath = targetPath;
			}
			Enumeration entryEnum = zipFile.getEntries();
			if (null != entryEnum) {
				ZipEntry zipEntry = null;
				while (entryEnum.hasMoreElements()) {
					zipEntry = (ZipEntry) entryEnum.nextElement();
					if (zipEntry.isDirectory()) {
						directoryPath = directoryPath + File.separator + zipEntry.getName();
						continue;
					}
					if (zipEntry.getSize() > 0 || zipEntry.getName().indexOf(".")>=0) {
						// 文件
						String filePath = directoryPath + File.separator + zipEntry.getName();

						filePath = FilterUtil.isSecurePath(filePath);
						File targetFile = new File(filePath);

						String filePath1 = targetFile.getParentFile().getAbsolutePath();
						filePath1 = FilterUtil.isSecurePath(filePath1);
						File file = new File(filePath1);
						if (!file.exists()) {
							file.mkdirs();
						}

						FileOutputStream fos = null;
						OutputStream os = null;
						try {
							fos = new FileOutputStream(targetFile);
							os = new BufferedOutputStream(fos);
							is = zipFile.getInputStream(zipEntry);
							byte[] buffer = new byte[4096];
							int readLen = 0;
							while ((readLen = is.read(buffer, 0, 4096)) >= 0) {
								os.write(buffer, 0, readLen);
							}
							
						} catch (Exception e) {
							throw e;
						} finally {
							if (os != null) {
								try {
									os.flush();
									os.close();
								} catch (IOException e) {
									logger.error("OutputStream资源关闭异常:", e);
								}

							}
							if (fos != null) {
								try {
									fos.close();
								} catch (IOException e) {
									logger.error("FileOutputStream资源关闭异常:", e);
								}
							}
						}
					} else {
						// 空目录
						String filepath = directoryPath + File.separator+ zipEntry.getName();
						filepath = FilterUtil.isSecurePath(filepath);
						File file = new File(filepath);
						if(!file.exists()){
							file.mkdirs();
						}
					}
				}
			}
		} catch (IOException ex) {
			throw ex;
		} finally {
			if(null != zipFile){
				zipFile.close();
				zipFile = null;				
			}
			if(is!=null){
				try{
					is.close();
				}catch(IOException e){
					logger.error("InputStream资源关闭异常:",e);
				}
				
			}
		}
	}

	/**
	 * 根据原始rar路径，解压到指定文件夹下.
	 * 
	 * @param srcRarPath
	 *            原始rar路径
	 * @param dstDirectoryPath
	 *            解压到的文件夹
	 * @throws Exception
	 */
	public static void unRarFile(String srcRarPath, String dstDirectoryPath) throws Exception {
		long startTime=System.currentTimeMillis();
		logger.info("up rar file start");
		if (!srcRarPath.toLowerCase().endsWith(".rar")) {
			logger.info("非rar文件！");
			return;
		}
		dstDirectoryPath = FilterUtil.isSecurePath(dstDirectoryPath);
		File dstDiretory = new File(dstDirectoryPath);
		if (!dstDiretory.exists()) {// 目标目录不存在时，创建该文件夹
			dstDiretory.mkdirs();
		}
		Archive archive = null;
		try {
			srcRarPath = FilterUtil.isSecurePath(srcRarPath);
			archive = new Archive(new File(srcRarPath));

				FileHeader fileHeader = archive.nextFileHeader();
				while (fileHeader != null) {
					if (fileHeader.isDirectory()) { // 文件夹
						String filePathString = dstDirectoryPath + File.separator + fileHeader.getFileNameString();
						filePathString = FilterUtil.isSecurePath(filePathString);
						File fileDir = new File(filePathString);
						fileDir.mkdirs();
					} else { // 文件
						String filePathString1 = dstDirectoryPath + File.separator + fileHeader.getFileNameString().trim();
						filePathString1 = FilterUtil.isSecurePath(filePathString1);
						File out = new File(filePathString1);
						FileOutputStream os = null;
						try {// 之所以这么写try，是因为万一这里面有了异常，不影响继续解压.
							if (!out.exists()) {
								if (!out.getParentFile().exists()) {// 相对路径可能多级，可能需要创建父目录.
									out.getParentFile().mkdirs();
								}
								out.createNewFile();
							}
							os = new FileOutputStream(out);
							archive.extractFile(fileHeader, os);
							os.close();
						} catch (Exception ex) {
							logger.error(ex.getMessage(), ex);
						} finally{
							if(os!=null){
								try{
									os.close();
								}catch(IOException e){
									logger.error("FileOutputStream资源关闭异常:",e);
								}
							}
						}
					fileHeader = archive.nextFileHeader();
				}

			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			if (archive != null) {
				archive.close();
			}
		}
		
		logger.info("up rar file end sumTime:{}",(System.currentTimeMillis()-startTime));
	}

	public static void main(String[] args) {
		
		try {
			ZipUtils.unzip("F:/广州银行信用卡中心/系统部/需求/广赢卡/双贷后合并/生产/plm_data-2018-11-1901.zip", "D:/plm/attach/in/plm_data-2018-11-1901");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
