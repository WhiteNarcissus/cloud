//package common.utils;
//
//import org.dom4j.Document;
//import org.dom4j.Element;
//import org.dom4j.io.SAXReader;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.File;
//import java.util.List;
//
//public class PathUtils {
//
//	private static Logger logger=LoggerFactory.getLogger(PathUtils.class);
//
//	public static String getResourcePath() {
//		try {
//
//			String path = PathUtils.class.getResource("").getPath();
//
//			String packageName = PathUtils.class.getPackage().getName();
//			String packagePath = packageName.replace(".", "/") +"/";
//
//			path = path.replace(packagePath, "");
//			//System.out.println("path:"+path);
//			//System.out.println("pa:"+packagePath);
//
//			return path;
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			return null;
//
//		}
//	}
//	public static String getResourcePath(String subPath){
//		return getResourcePath() + subPath;
//	}
//
//	public static void main(String[] args){
//
//		try{
//		SAXReader reader = new SAXReader();
//		//读取文件路径
//		Document doc = reader.read(PathUtils.getResourcePath(PropertiesUtils.getProperty("import_data_file_config_path")+File.separator+"gjj_info-defs.xml"));
//		Element root = doc.getRootElement();
//		List<?> elements = root.elements();
//		System.out.println(root.attributeValue("entityName"));
//		for(int i=0;i<elements.size();i++){
//
//			System.out.println( ((Element)elements.get(i)).attributeValue("filedName"));
//
//		}}catch(Exception e){
//			e.printStackTrace();
//		}
//	}
//}
