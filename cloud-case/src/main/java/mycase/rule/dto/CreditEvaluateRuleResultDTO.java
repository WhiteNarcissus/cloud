package mycase.rule.dto;

import java.util.List;

/**
 * 
 * 项目：gykdh1.10
 * 版    权: Copyright(C) 2011-2020,  gzcb. All rights reserved
 * 类名：CreditEvaluateRuleResultDTO
 * 描述：信用评估规则结果
 * 创建人：liting    创建日期：2017年5月25日   版本：
 * 历史
 * <修改人>   <修改日期 >   <修改内容>
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
