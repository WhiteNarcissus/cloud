package rule.entity;

public interface IRuleRow {

	public Long getRowId();
	
	public Long getTableId();
	
	public String getRuleCondition();
	
	public Integer getSort();
}
