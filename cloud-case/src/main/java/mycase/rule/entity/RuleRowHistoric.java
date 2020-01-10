package mycase.rule.entity;

public class RuleRowHistoric {
    /**
     * 
     * column t_rule_row_historic.historic_id
     */
    private Long historicId;

    /**
     * 
     * column t_rule_row_historic.row_id
     */
    private Long rowId;

    /**
     * 
     * column t_rule_row_historic.table_id
     */
    private Long tableId;

    /**
     * 
     * column t_rule_row_historic.sort
     */
    private Integer sort;

    /**
     * 
     * column t_rule_row_historic.rule_condition
     */
    private String ruleCondition;
    
    public RuleRowHistoric(){}
    public RuleRowHistoric(RuleRow ruleRow){
		this.rowId = ruleRow.getRowId();
		this.tableId = ruleRow.getTableId();
		this.ruleCondition = ruleRow.getRuleCondition();
		this.sort = ruleRow.getSort();
    }
    

    public Long getHistoricId() {
        return historicId;
    }

    public void setHistoricId(Long historicId) {
        this.historicId = historicId;
    }

    public Long getRowId() {
        return rowId;
    }

    public void setRowId(Long rowId) {
        this.rowId = rowId;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getRuleCondition() {
        return ruleCondition;
    }

    public void setRuleCondition(String ruleCondition) {
        this.ruleCondition = ruleCondition == null ? null : ruleCondition.trim();
    }
}