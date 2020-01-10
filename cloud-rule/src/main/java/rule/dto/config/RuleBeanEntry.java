package rule.dto.config;

import java.util.Map;

public class RuleBeanEntry {

	private String packageName;
	private String showName;
	private String cls;
	private Map<String,RuleFieldEntry> fieldsMap;
	
	private String result;
	
	private String name;
	
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getShowName() {
		return showName;
	}
	public void setShowName(String showName) {
		this.showName = showName;
	}
	public String getCls() {
		return cls;
	}
	public void setCls(String cls) {
		this.cls = cls;
	}
	
	public Map<String, RuleFieldEntry> getFieldsMap() {
		return fieldsMap;
	}
	public void setFieldsMap(Map<String, RuleFieldEntry> fieldsMap) {
		this.fieldsMap = fieldsMap;
	}

	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
