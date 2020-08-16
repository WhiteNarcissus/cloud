//package common.utils;
//
//import com.gzcb.creditcard.gykdh.util.ConfigUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Map;
//
//public class PropertiesUtils {
//
//	private static Logger logger=LoggerFactory.getLogger(PropertiesUtils.class);
//	private static final Map<String, String> config=ConfigUtil.getInstance().getDefaultConfig();
//
//	public static String getProperty(String key){
//		try {
//
//			String property = config.get(key);
//			return property;
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			return null;
//		}
//	}
//
//	public static void main(String[] args){
//		System.out.println(PropertiesUtils.getProperty("MATES_LIST"));
//	}
//
//}
