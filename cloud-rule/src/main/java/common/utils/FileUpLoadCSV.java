package common.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class FileUpLoadCSV {
	

	public static File fileUpLoad(MultipartFile file) throws IllegalStateException, IOException{
		  File uploadFile = null;
		  if(!file.isEmpty()){
				String fileDirPath = PropertiesUtils.getProperty("TEMP_FILE_PATH");
				 fileDirPath = FilterUtil.isSecurePath(fileDirPath);
				File managerFile = new File(fileDirPath);
				if(!managerFile.exists() && !managerFile.isDirectory()) {
					 managerFile.mkdir();
				}
				String origfileName = file.getOriginalFilename();
				String fileType="";
				if(!StringUtils.isNullOrEmpty(origfileName)){
					int typeIndex=origfileName.lastIndexOf(".");
					fileType=origfileName.substring(typeIndex+1);
					origfileName=origfileName.substring(0,typeIndex);
					
				}
				
				String newFileName = origfileName + "_" + DateUtils.format(new Date(), "yyyyMMddHHmmss") +"."+fileType;
				String path = File.separator + newFileName; //文件相对路径
				String filePath=fileDirPath+path;  //文件绝对路径
				String filePathUrl = FilterUtil.isSecurePath(filePath);
				uploadFile = new File(filePathUrl);
				file.transferTo(uploadFile);//转存文件到指定路径
		  }
		return uploadFile;
	}
}
