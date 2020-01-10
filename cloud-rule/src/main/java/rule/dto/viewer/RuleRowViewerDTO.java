package rule.dto.viewer;

import java.util.LinkedHashMap;
import java.util.Map;

public class RuleRowViewerDTO {

	private Integer sort;
	private Long key;
	private Map<String,RuleRowFieldViewerDTO> rowConditions = new LinkedHashMap<String, RuleRowFieldViewerDTO>();
	private Map<String,RuleRowResultViewerDTO> rowResults = new LinkedHashMap<String, RuleRowResultViewerDTO>();
	
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Long getKey() {
		return key;
	}
	public void setKey(Long key) {
		this.key = key;
	}
	public Map<String, RuleRowFieldViewerDTO> getRowConditions() {
		return rowConditions;
	}
	public void setRowConditions(Map<String, RuleRowFieldViewerDTO> rowConditions) {
		this.rowConditions = rowConditions;
	}
	public Map<String,RuleRowResultViewerDTO> getRowResults() {
		return rowResults;
	}
	public void setRowResults(Map<String,RuleRowResultViewerDTO> rowResults) {
		this.rowResults = rowResults;
	}
	
	
}
