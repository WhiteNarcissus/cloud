package mycase.rule.helper;


import com.gzcb.creditcard.gykdh.common.utils.FilterUtil;
import com.gzcb.creditcard.gykdh.common.utils.PathUtils;
import com.gzcb.creditcard.gykdh.common.utils.PropertiesUtils;
import com.gzcb.creditcard.gykdh.common.utils.StringUtils;
import com.gzcb.creditcard.gykdh.rule.dto.config.RuleBeanEntry;
import com.gzcb.creditcard.gykdh.rule.dto.config.RuleFieldEntry;
import com.gzcb.creditcard.gykdh.rule.dto.config.RuleResultEntry;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultAttribute;
import org.dom4j.tree.DefaultElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.lang.reflect.Method;
import java.util.*;

public class RuleConfigHelper {
	public static final String PATH =PropertiesUtils.getProperty("RULE_CONFIG_PATH");
	private static Logger logger=LoggerFactory.getLogger(RuleConfigHelper.class);
	
	private static final Map<String,RuleBeanEntry> RULE_FIELD_MAP = Collections.synchronizedMap(new LinkedHashMap<String, RuleBeanEntry>());
	private static final Map<String,RuleResultEntry> RULE_RESULT_MAP = Collections.synchronizedMap(new LinkedHashMap<String, RuleResultEntry>());
	
	/**
	 * 获取规则实体配置
	 * @return
	 */
	public static Map<String,RuleBeanEntry> getRuleBeanEntities(){
		if(RULE_FIELD_MAP.isEmpty()){
			parseField();
			parseResult();
		}
		
		return RULE_FIELD_MAP;
	}

	
	/**
	 * 获取规则结果配置
	 * @return
	 */
	public static Map<String,RuleResultEntry> getRuleResultEntities(){
		if(RULE_FIELD_MAP.isEmpty()){
			parseField();
			parseResult();
		}
		return RULE_RESULT_MAP;
	}
	
	/**
	 * 获取某个规则要素
	 * @param beanName
	 * @param fieldName
	 * @return
	 */
	public static RuleFieldEntry getRuleFieldEntry(String beanName, String fieldName){
		if(RULE_FIELD_MAP.isEmpty()){
			parseField();
			parseResult();
		}
		
		return RULE_FIELD_MAP.get(beanName).getFieldsMap().get(fieldName);
	}
	
	public static RuleResultEntry getRuleResultEntry(String key){
		if(RULE_FIELD_MAP.isEmpty()){
			parseField();
			parseResult();
		}
		
		return RULE_RESULT_MAP.get(key);
	}
	
	
	private static synchronized void parseField(){
		if(!RULE_FIELD_MAP.isEmpty()) {
            return;
        }
		
		String path =PathUtils.getResourcePath(PATH);
		path = FilterUtil.isSecurePath(path);
		File filePath = new File(path);
		File[] fieldFileList = filePath.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				String name = pathname.getName();
				if(name.toUpperCase().startsWith("FIELD_")) {
                    return true;
                } else {
                    return false;
                }
			}
		});
		for(int i=0;i<fieldFileList.length;i++){
			File file = fieldFileList[i];
			RuleBeanEntry beanEntry = parseField(file);
			if(RULE_FIELD_MAP.containsKey(beanEntry.getName())) {
                throw new IllegalArgumentException("rule bean duplicated! name : " + beanEntry.getName());
            }
			RULE_FIELD_MAP.put(beanEntry.getName(), beanEntry);
		}
	}
	
	/**
	 * 处理规则要素定义文件
	 * @param file
	 * @throws Exception 
	 * @throws  
	 */
	private static RuleBeanEntry parseField(File file){
		FileInputStream fis =null;
		try{
			fis = new FileInputStream(file);
			Element element = getElement(fis);
			
			RuleBeanEntry beanEntry = getObject(RuleBeanEntry.class,element);
			
			Map<String, RuleFieldEntry> fieldsMap = new HashMap<String, RuleFieldEntry>();
			beanEntry.setFieldsMap(fieldsMap);
				
			for (Iterator<?> it = element.elementIterator("field"); it.hasNext();) {
				RuleFieldEntry field = getObject(RuleFieldEntry.class, (Element) it.next());
				if(fieldsMap.containsKey(field.getAttr())) {
					throw new IllegalArgumentException("field duplicated! name : " + field.getAttr());
				}
				field.setBeanEntry(beanEntry);
				fieldsMap.put(field.getAttr(), field);
			}
			return beanEntry;
		}catch(Exception ex){
			logger.error(ex.getMessage(), ex);
			throw new RuntimeException(ex);
		}finally{
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					logger.error("FileInputStream资源关闭失败",e);
				}
			}
			
		}
	}
	
	private static synchronized void parseResult(){
		if(!RULE_RESULT_MAP.isEmpty()) {
            return;
        }
		InputStream in = null;
		try{
			ClassPathResource classPathResource = new ClassPathResource(PATH + "/Rule_Result.xml");
			
			in = classPathResource.getInputStream();
		
			Element element = getElement(in);
			
			for (Iterator<?> it = element.elementIterator("resultDef"); it.hasNext();) {
				
				Element subElement = (Element) it.next();
				RuleResultEntry resultDef = getObject(RuleResultEntry.class, subElement);
				
				if(RULE_RESULT_MAP.containsKey(resultDef.getKey())) {
                    throw new IllegalArgumentException("field duplicate! name : " + resultDef.getKey());
                }
				
				List<?> list = subElement.content();
				for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
					Object obj = (Object) iterator.next();
					if(obj instanceof DefaultElement){
						DefaultElement defaultElement = (DefaultElement) obj;
						if("script".equalsIgnoreCase((defaultElement.getName()))) {
                            resultDef.setScript(defaultElement.getStringValue());
                        }
					}
				}
				RULE_RESULT_MAP.put(resultDef.getKey(), resultDef);
			}
			
		}catch(Exception ex){
			logger.error(ex.getMessage(), ex);
			throw new RuntimeException(ex);
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					logger.error("InputStream资源关闭失败:",e);
				}
			}
		}
	}
		
	private static Element getElement(InputStream in) throws DocumentException {
		SAXReader reader = null;
		Document document = null;
		Element root = null;
		try {
			reader = new SAXReader();
			document = reader.read(in);
			return document.getRootElement();
		}finally {
			if(root != null) {
                root.clearContent();
            }
			if(document != null) {
                document.clearContent();
            }
		}
	}
	
	private static <T> T getObject(Class<T> cls, Element element) {
		try {
			T obj = cls.newInstance();
			for(Iterator<?> it=element.attributes().iterator();it.hasNext();){
				DefaultAttribute attribute = (DefaultAttribute)it.next();
				String attrName = attribute.getName();
				String value = attribute.getValue();
				String methodName = attrName.substring(0,1).toUpperCase()+attrName.substring(1);
				methodName = "set" + methodName;
				
				Method method =  obj.getClass().getMethod(methodName, String.class);
				method.invoke(obj, new Object[]{value});
			}
			List<String> ope = new ArrayList<String>();
			for(Iterator<?> it=element.elementIterator();it.hasNext();){
				Element ele = (Element)it.next();
				if(StringUtils.isNullOrEmpty(ele.getTextTrim())) {
                    break;
                }
				ope.add(ele.getTextTrim());
			}
			if(ope.size() > 1){
				String methodName = "Ope";
				Method method =  obj.getClass().getMethod("get" + methodName);
				method =  obj.getClass().getMethod("set" + methodName, method.getReturnType());
				method.invoke(obj, new Object[]{ope});
			}
			return obj;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
