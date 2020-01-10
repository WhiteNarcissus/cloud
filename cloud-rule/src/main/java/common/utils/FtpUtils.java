package common.utils;

import com.gzcb.creditcard.gykdh.job.JobContants;
import com.gzcb.creditcard.gykdh.job.entity.FtpConnection;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class FtpUtils {

	private static Logger logger = LoggerFactory.getLogger(FtpUtils.class);
	/**
	 * Description: 向FTP服务器上传文件
	 *
	 * @Version1.0
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param path
	 *            FTP服务器保存目录,如果是根目录则为“/”
	 * @param filename
	 *            上传到FTP服务器上的文件名
	 * @param input
	 *            本地文件输入流
	 * @return 成功返回true，否则返回false
	 * @throws Exception
	 */
	public static boolean uploadFile(FtpConnection connection, InputStream input) throws Exception {
		boolean result = false;
		FTPClient ftpClient = new FTPClient();
		try {
			int reply;
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftpClient.connect(connection.getUrl());
			// 登录
			ftpClient.login(connection.getUsername(), connection.getPassword());
			ftpClient.setControlEncoding(connection.getEncoding());
			ftpClient.setConnectTimeout(connection.getConnectTimeOut());
			ftpClient.setDataTimeout(connection.getReadTimeOut());
			// 检验是否连接成功
			reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				logger.info("ftp 连接失败");
				ftpClient.disconnect();
				throw new Exception("ftp 链接失败");
			}
			//被动模式
			ftpClient.setUseEPSVwithIPv4(true);
			ftpClient.enterLocalPassiveMode();
			// 转移工作目录至指定目录下
			boolean change = ftpClient.changeWorkingDirectory(connection.getPath());
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			if (change) {
				String fileName = new String(connection.getFileName().getBytes(connection.getEncoding()), "iso-8859-1");
				result = ftpClient.storeFile(fileName, input);
				logger.info("ftp 上传数据成功");
			}else{
				logger.info("ftp 目录不存在");
				throw new Exception("ftp 目录不存在");
			}
			
		} catch (Exception e) {
			logger.error("ftp upLoad file wrong", e);
			throw e;
		} finally {
			ftpClient.logout();
			if (input != null) {
				try {
					input.close();
				} catch (Exception e) {
					logger.error("ftp downLoad file wrong", e);
				}
			}
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (Exception ioe) {
					logger.error("ftp downLoad file wrong", ioe);
				}
			}
		}
		logger.info("ftp upload result:{}" , result);

		return result;
	}

	/**
	 * 创建文件夹
	 *
	 * @param url
	 * @param username
	 * @param password
	 * @param path
	 * @param filename
	 * @return
	 */
	public static boolean createDir(FtpConnection connection) {
		FTPClient ftpClient = new FTPClient();
		// 设置文件类型（二进制）
		try {
			ftpClient.connect(connection.getUrl());
			System.out.println("ftpconnection_createDir:" + ftpClient.isConnected() + " " + ftpClient.isAvailable());
			ftpClient.login(connection.getUsername(), connection.getPassword());
			ftpClient.setConnectTimeout(connection.getConnectTimeOut());
			ftpClient.setDataTimeout(connection.getReadTimeOut());

			if (ftpClient.makeDirectory(connection.getPath() + connection.getFileName())) {
                return true;
            }

		} catch (Exception e) {
			logger.error("ftp createDir file wrong", e);
		} finally {
			try {
				ftpClient.logout();
				ftpClient.disconnect();
			} catch (IOException e) {
				logger.error("ftp createDir file wrong", e);
			}
		}
		return false;

	}

	/**
	 * Description: 从FTP服务器下载文件
	 *
	 * @Version1.0
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param remotePath
	 *            FTP服务器上的相对路径
	 * @param fileName
	 *            要下载的文件名
	 * @param localPath
	 *            下载后保存到本地的路径
	 * @return
	 * @throws Exception
	 */
	public static String downFile(FtpConnection connection) throws Exception {
		String result = JobContants.FTP_RESULT_CODE_SUCCESS;
		FTPClient ftpClient = new FTPClient();
		boolean downResult=true;
		try {
			int reply;
			ftpClient.setControlEncoding(connection.getEncoding());
			ftpClient.connect(connection.getUrl(), connection.getPort());
			ftpClient.setConnectTimeout(connection.getConnectTimeOut());
			ftpClient.setDataTimeout(connection.getReadTimeOut());
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftpClient.login(connection.getUsername(), connection.getPassword());// 登录
			// 设置文件传输类型为二进制
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			// 获取ftp登录应答代码
			reply = ftpClient.getReplyCode();
			// 验证是否登陆成功
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				logger.error("ftp 链接失败");
				result=JobContants.FTP_RESULT_CODE_CONTECT_FAIL;				
				return result;
			}
			
			// 转移到FTP服务器目录至指定的目录下
			//被动模式
			ftpClient.setUseEPSVwithIPv4(true);
			ftpClient.enterLocalPassiveMode();
			boolean change = ftpClient.changeWorkingDirectory(connection.getPath());
			// 获取文件列表
			boolean fileIsExist=false; //文件是否存在
			if (change) {
				FTPFile[] fs = ftpClient.listFiles();
				for (FTPFile ff : fs) {
					if (ff.getName().trim().equals(connection.getFileName())) {
						fileIsExist=true;
						String filePath = connection.getLocalPath() + "/" + ff.getName();
						filePath = FilterUtil.isSecurePath(filePath);
						File localFile = new File(filePath);
						OutputStream is = new FileOutputStream(localFile);
						downResult = ftpClient.retrieveFile(ff.getName(), is);
						is.close();
						break;
					}
				}
				
				if (!fileIsExist) { // 判断文件是否存在
					logger.error("需要下载的文件不存在：{}", connection.getFileName());
					result = JobContants.FTP_RESULT_CODE_FILE_NOT_EXIST;
				} else {
					if (!downResult) {
						result = JobContants.FTP_RESULT_CODE_DOWN_FAIL;
						logger.error("下载文件失败！：{}", connection.getPath() + connection.getFileName());
					} else {
						logger.info("下载文件成功！：{}", connection.getPath() + connection.getFileName());
						result = JobContants.FTP_RESULT_CODE_SUCCESS;
					}
				}
			} else {
				logger.error("ftp 目录不存在");
				result=JobContants.FTP_RESULT_CODE_DIR_NOT_EXIST;
			}
		} catch (Exception e) {
			logger.error("ftp 下载数据文件失败", e);
			result=JobContants.FTP_RESULT_CODE_EXCEPTION;
		} finally {
			ftpClient.logout();
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException ioe) {

				}
			}
		}
		return result;
	}

	/**
	 * Description: 从FTP服务器删除单个文件
	 *
	 * @Version1.0
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param remotePath
	 *            FTP服务器上的相对路径
	 * @param fileName
	 *            要下载的文件名
	 * @param localPath
	 *            下载后保存到本地的路径
	 * @return
	 * @throws IOException 
	 */
	public static boolean deleteSingleFile(FtpConnection connection) throws IOException {
		FTPClient ftpClient = new FTPClient();
		boolean result = false;
		try {
			int reply;
			ftpClient.setControlEncoding(connection.getEncoding());
			ftpClient.connect(connection.getUrl(), connection.getPort());
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftpClient.login(connection.getUsername(), connection.getPassword());// 登录
			// 设置文件传输类型为二进制
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			// 获取ftp登录应答代码
			reply = ftpClient.getReplyCode();
			ftpClient.setConnectTimeout(connection.getConnectTimeOut());
			ftpClient.setDataTimeout(connection.getReadTimeOut());
			// 验证是否登陆成功
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				System.err.println("FTP server refused connection.");
				return result;
			}
			// 转移到FTP服务器目录至指定的目录下
			ftpClient.changeWorkingDirectory(new String(connection.getPath().getBytes(connection.getEncoding()), "iso-8859-1"));
			// 获取文件列表
			FTPFile[] fs = ftpClient.listFiles();
			for (FTPFile ff : fs) {
				if (ff.getName().equals(connection.getFileName())) {
					result = ftpClient.deleteFile(connection.getFileName());
				}
			}

			

		} catch (IOException e) {
			logger.error("删除文件异常"+e.getMessage(),e);
		} finally {
			ftpClient.logout();
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException ioe) {
					logger.error("FTPClient断开连接异常:"+ioe.getMessage(),ioe);
				}
			}
		}
		return result;
	}

	
	/**
	 * 检查文件是否存在
	 * @return
	 * @throws Exception 
	 */
	public static String checkFileExist(FtpConnection connection) throws Exception{
		FTPClient ftpClient = new FTPClient();
		String result = JobContants.FTP_RESULT_CODE_SUCCESS;
		
		try {
			int reply;
			ftpClient.setControlEncoding(connection.getEncoding());
			ftpClient.connect(connection.getUrl(), connection.getPort());
			ftpClient.setConnectTimeout(connection.getConnectTimeOut());
			ftpClient.setDataTimeout(connection.getReadTimeOut());
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftpClient.login(connection.getUsername(), connection.getPassword());// 登录
			// 设置文件传输类型为二进制
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			// 获取ftp登录应答代码
			reply = ftpClient.getReplyCode();
			
			// 验证是否登陆成功
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				logger.error("ftp 链接失败");
				return JobContants.FTP_RESULT_CODE_CONTECT_FAIL;
			}
			
			// 转移到FTP服务器目录至指定的目录下
			//被动模式
			ftpClient.setUseEPSVwithIPv4(true);
			ftpClient.enterLocalPassiveMode();
			boolean change = ftpClient.changeWorkingDirectory(connection.getPath());
			// 获取文件列表
			boolean fileIsExist=false; //文件是否存在
			if (change) {
				FTPFile[] fs = ftpClient.listFiles();
				for (FTPFile ff : fs) {
					if (ff.getName().trim().equals(connection.getFileName())) {
						fileIsExist=true;
						break;
					}
				}	
				
				if(!fileIsExist){ //判断文件是否存在
					logger.error("文件不存在：{}",connection.getFileName());
					result=JobContants.FTP_RESULT_CODE_FILE_NOT_EXIST;					
				}else{
					result = JobContants.FTP_RESULT_CODE_SUCCESS;
				}
			
			} else {
				logger.error("ftp 目录不存在");
				result=JobContants.FTP_RESULT_CODE_DIR_NOT_EXIST;
			}
		} catch (Exception e) {
			logger.error("ftp 检查文件失败", e);
			result=JobContants.FTP_RESULT_CODE_EXCEPTION;
		} finally {
			ftpClient.logout();
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException ioe) {

				}
			}
		}
		
		return result;
		
	}

}
