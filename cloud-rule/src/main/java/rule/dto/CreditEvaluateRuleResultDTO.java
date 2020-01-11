package rule.dto;

import java.util.List;

/**
 *
 */
public class CreditEvaluateRuleResultDTO {

	
	private String result;	//结果
	private Integer bigoRow;	//规则踩中的行序号
	private String version;	//当前的规则版本
	private Long ruleId;
	private String ruleName;
	
	/**
	 * 匹配的规则行
	 */
	private List<String> mapRuleRow;
	
	public List<String> getMapRuleRow() {
		return mapRuleRow;
	}
	public void setMapRuleRow(List<String> mapRuleRow) {
		this.mapRuleRow = mapRuleRow;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Integer getBigoRow() {
		return bigoRow;
	}
	public void setBigoRow(Integer bigoRow) {
		this.bigoRow = bigoRow;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Long getRuleId() {
		return ruleId;
	}
	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	

	
	
}
