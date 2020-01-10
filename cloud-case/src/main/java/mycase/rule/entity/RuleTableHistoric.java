package mycase.rule.entity;

import com.gzcb.creditcard.gykdh.rule.RuleContants;

import java.util.Date;

public class RuleTableHistoric {
    /**
     * 
     * column t_rule_table_historic.historic_id
     */
    private Long historicId;

    /**
     * 
     * column t_rule_table_historic.table_id
     */
    private Long tableId;

    /**
     * 
     * column t_rule_table_historic.rule_id
     */
    private Long ruleId;

    /**
     * 
     * column t_rule_table_historic.table_name
     */
    private String tableName;

    /**
     * 
     * column t_rule_table_historic.table_status
     */
    private String tableStatus;

    /**
     * 
     * column t_rule_table_historic.created_time
     */
    private Date createdTime;

    /**
     * 
     * column t_rule_table_historic.deploy_time
     */
    private Date deployTime;

    /**
     * 
     * column t_rule_table_historic.remove_user
     */
    private Long removeUser;
    
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
    
    public RuleTableHistoric(){}
	public RuleTableHistoric(RuleTable ruleTable){
		
		this.tableId = ruleTable.getTableId();
		this.ruleCondition = ruleTable.getRuleCondition();
		this.createdTime = new Date();
		this.resultScript = ruleTable.getResultScript();
		this.ruleId = ruleTable.getRuleId();
		this.tableStatus = RuleContants.TABLE_STATUS_DELETE;
		this.tableName = ruleTable.getTableName();
	}

    public Long getHistoricId() {
        return historicId;
    }

    public void setHistoricId(Long historicId) {
        this.historicId = historicId;
    }

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

    public Long getRemoveUser() {
        return removeUser;
    }

    public void setRemoveUser(Long removeUser) {
        this.removeUser = removeUser;
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