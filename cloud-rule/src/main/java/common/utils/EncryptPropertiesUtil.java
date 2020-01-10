package common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 
 * @author jm
 *
 */


public class EncryptPropertiesUtil extends PropertyPlaceholderConfigurer {
	
   private static final String PASSWORD="password";
   private static Logger logger=LoggerFactory.getLogger(EncryptPropertiesUtil.class) ;
	
	@Override
	protected String convertProperty(String propertyName, String propertyValue) {	
		if (!StringUtils.isNullOrEmpty(propertyName)) {
			if (propertyName.toLowerCase().endsWith(PASSWORD)) {
				try {
					propertyValue = AESUtils.aesDecrypt(propertyValue);
				} catch (Exception e) {
					logger.error("解密失败", e);
				}
			}
		}
		return propertyValue;
		
	}
	

	

}
