package rule.entity;


import common.utils.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class RuleLogCriteria {

	private String custName;
	
	private String custIdNo;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date beginDate;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endDate;
	
    //长度
	private Integer length;
	//起始位置
	private Integer start;
	
	private String ruleName;
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
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	
	public boolean allFieldIsNull(){
		if (StringUtils.isNullOrEmpty(this.custName) && StringUtils.isNullOrEmpty(this.custIdNo) && StringUtils.isNullOrEmpty(this.ruleName)
				&& this.beginDate == null && this.endDate == null) {
			return true;
		}
		return false;
	}
	
}
