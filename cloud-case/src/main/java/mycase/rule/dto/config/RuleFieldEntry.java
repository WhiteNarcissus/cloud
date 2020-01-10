package mycase.rule.dto.config;

import java.util.ArrayList;

public class RuleFieldEntry {
	private RuleBeanEntry beanEntry;
	private String description;
	private String showName;
	private String attr;
	private String unit;
	private String id;
	private Integer sort;
	private ArrayList<String> ope;
	
	public RuleBeanEntry getBeanEntry() {
		return beanEntry;
	}
	public void setBeanEntry(RuleBeanEntry beanEntry) {
		this.beanEntry = beanEntry;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getShowName() {
		return showName;
	}
	public void setShowName(String showName) {
		this.showName = showName;
	}
	public String getAttr() {
		return attr;
	}
	public void setAttr(String attr) {
		this.attr = attr;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public ArrayList<String> getOpe() {
		return ope;
	}
	public void setOpe(ArrayList<String> ope) {
		this.ope = ope;
	}
	//<attribute attr="cityCode" showName="cityCode" description="" unit="null" />
}
