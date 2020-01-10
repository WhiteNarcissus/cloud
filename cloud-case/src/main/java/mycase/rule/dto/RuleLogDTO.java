package mycase.rule.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * 项目：gykdh1.10
 * 版    权: Copyright(C) 2011-2020,  gzcb. All rights reserved
 * 类名：RuleLogDTO
 * 描述：规则匹配日志
 * 创建人：liting    创建日期：2017年6月7日   版本：
 * 历史
 * <修改人>   <修改日期 >   <修改内容>
 */
public class RuleLogDTO {

	/**
	 * 日志Id
	 */
    private Long logId;


    /**
     * 规则Id
     */
    private Long ruleId;
    
    /**
     * 规则名
     */
    private String ruleName;
    
    /**
     * 规则描述
     */
    private String ruleDesc;


    /**
     * 匹配的规则行
     */
    private Integer ruleRow;

    /**
     * 规则版本
     */
    private String ruleVersion;
   

    /**
     * 创建时间
     */
    private Date createdTime;
    
    /**
     * 客户姓名
     */
    private String custName;
    
    /**
     * 证件号
     */
    private String custIdNo;
    
    /**
     * 匹配条件
     */
	private List<String> condition = new ArrayList<String>();
	
	/**
	 * 结果
	 */
	private List<String> result = new ArrayList<String>();

	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
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

	public String getRuleDesc() {
		return ruleDesc;
	}

	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}

	public Integer getRuleRow() {
		return ruleRow;
	}

	public void setRuleRow(Integer ruleRow) {
		this.ruleRow = ruleRow;
	}

	public String getRuleVersion() {
		return ruleVersion;
	}

	public void setRuleVersion(String ruleVersion) {
		this.ruleVersion = ruleVersion;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustIdNo() {
		return custIdNo;
	}

	public void setCustIdNo(String custIdNo) {
		this.custIdNo = custIdNo;
	}

	public List<String> getCondition() {
		return condition;
	}

	public void setCondition(List<String> condition) {
		this.condition = condition;
	}

	public List<String> getResult() {
		return result;
	}

	public void setResult(List<String> result) {
		this.result = result;
	}
	
	
}
