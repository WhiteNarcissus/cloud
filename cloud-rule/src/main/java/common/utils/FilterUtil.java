package common.utils;

public class FilterUtil {
	public static String isSecurePath(String filePath) {
		String pathFilterList = ParameterUtils.getParameterValue("PATH_FILTER");
		String[]  blackListCharsStrings = pathFilterList.split(",");
		if(StringUtils.indexOfAny(filePath,blackListCharsStrings)<0){
			return filePath;
		}else {
			throw new RuntimeException("文件路径存在问题:"+filePath);
		}
	}
}
