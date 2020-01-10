package rule.entity;

public class RuleRowEdit implements IRuleRow{
    /**
     * 
     * column t_rule_row_edit.row_id
     */
    private Long rowId;

    /**
     * 
     * column t_rule_row_edit.table_id
     */
    private Long tableId;

    /**
     * 
     * column t_rule_row_edit.sort
     */
    private Integer sort;

    /**
     * 
     * column t_rule_row_edit.rule_condition
     */
    private String ruleCondition;
    
    public RuleRowEdit(){}
    
    public RuleRowEdit(RuleRow ruleRow){
    	this.rowId = ruleRow.getRowId();
		this.tableId = ruleRow.getTableId();
		this.ruleCondition = ruleRow.getRuleCondition();
		this.sort = ruleRow.getSort();
    }

    @Override
    public Long getRowId() {
        return rowId;
    }

    public void setRowId(Long rowId) {
        this.rowId = rowId;
    }

    @Override
    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    @Override
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String getRuleCondition() {
        return ruleCondition;
    }

    public void setRuleCondition(String ruleCondition) {
        this.ruleCondition = ruleCondition == null ? null : ruleCondition.trim();
    }
}