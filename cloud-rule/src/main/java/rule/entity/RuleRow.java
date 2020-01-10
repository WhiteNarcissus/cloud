package rule.entity;

public class RuleRow implements IRuleRow{
    /**
     *
     * column t_rule_row.row_id
     */
    private Long rowId;

    /**
     *
     * column t_rule_row.table_id
     */
    private Long tableId;

    /**
     * 排序
     * column t_rule_row.sort
     */
    private Integer sort;

    /**
     *
     * column t_rule_row.rule_condition
     */
    private String ruleCondition;

    public RuleRow(){

    }

    public RuleRow(RuleRowEdit ruleRowEdit){
        this.rowId = ruleRowEdit.getRowId();
        this.tableId = ruleRowEdit.getTableId();
        this.ruleCondition = ruleRowEdit.getRuleCondition();
        this.sort = ruleRowEdit.getSort();
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