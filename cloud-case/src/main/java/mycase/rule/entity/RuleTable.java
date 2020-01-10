package mycase.rule.entity;

import java.util.Date;

public class RuleTable {
    /**
     * 
     * column t_rule_table.table_id
     */
    private Long tableId;

    /**
     * 
     * column t_rule_table.rule_id
     */
    private Long ruleId;

    /**
     * 
     * column t_rule_table.table_name
     */
    private String tableName;

    /**
     * 
     * column t_rule_table.table_status
     */
    private String tableStatus;

    /**
     * 
     * column t_rule_table.created_time
     */
    private Date createdTime;

    /**
     * 
     * column t_rule_table.deploy_time
     */
    private Date deployTime;
    
    /**
     * 
     * column t_rule_table.rule_condition
     */
    private String ruleCondition;

    /**
     * 
     * column t_rule_table.result_script
     */
    private String resultScript;

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    public String getTableStatus() {
        return tableStatus;
    }

    public void setTableStatus(String tableStatus) {
        this.tableStatus = tableStatus == null ? null : tableStatus.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getDeployTime() {
        return deployTime;
    }

    public void setDeployTime(Date deployTime) {
        this.deployTime = deployTime;
    }

	public String getRuleCondition() {
		return ruleCondition;
	}

	public void setRuleCondition(String ruleCondition) {
		this.ruleCondition = ruleCondition;
	}

	public String getResultScript() {
		return resultScript;
	}

	public void setResultScript(String resultScript) {
		this.resultScript = resultScript;
	}
    
    
}