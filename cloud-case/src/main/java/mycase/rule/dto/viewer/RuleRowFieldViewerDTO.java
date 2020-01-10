package mycase.rule.dto.viewer;

public class RuleRowFieldViewerDTO {

	private String attributeId;
	private String attributeName;
	private String attribute;
	private String beanName;
	private String value;
	private String dataType;
	private String operator;
	
	public String getAttributeId() {
		return attributeId;
	}
	public void setAttributeId(String attributeId) {
		this.attributeId = attributeId;
	}
	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getBeanName() {
		return beanName;
	}
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	
	
}
