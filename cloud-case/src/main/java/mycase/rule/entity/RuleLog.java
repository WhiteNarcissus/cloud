package mycase.rule.entity;

import java.util.Date;

public class RuleLog {
    /**
     * 
     * column t_rule_log.log_id
     */
    private Long logId;

    /**
     * 
     * column t_rule_log.rule_id
     */
    private Long ruleId;

    /**
     * 
     * column t_rule_log.rule_row
     */
    private Integer ruleRow;

    /**
     * 
     * column t_rule_log.rule_version
     */
    private String ruleVersion;

    /**
     * 
     * column t_rule_log.created_time
     */
    private Date createdTime;
    
    private String custName;
    
    private String custIdNo;
    
    private String ruleName;
    
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
        this.ruleVersion = ruleVersion == null ? null : ruleVersion.trim();
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

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
    
    
}