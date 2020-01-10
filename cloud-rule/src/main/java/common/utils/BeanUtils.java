package common.utils;

import org.apache.commons.beanutils.PropertyUtilsBean;

import java.beans.PropertyDescriptor;
import java.util.LinkedHashMap;
import java.util.Map;

public class BeanUtils {
	public static Map<String, Object> objectToMap(Object object) throws Exception {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		PropertyUtilsBean utilsBean = new PropertyUtilsBean();
		PropertyDescriptor[] descriptor = utilsBean.getPropertyDescriptors(object);
		for (int i = 0; i < descriptor.length; i++) {
			String name = descriptor[i].getName();
			if (!"class".equals(name)) {
				Object value = utilsBean.getNestedProperty(object, name);
				params.put(name, value);
			}
		}

		return params;

	}
	
}
