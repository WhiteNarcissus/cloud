package mycase.rule.dto;

import java.math.BigDecimal;

public class TaskRoleRuleDTO {
	
	private String taskId;
	
	private String custIdNo;
	
	private String custName;
	
	private String userRole;
	
	private Long userId;
		
	private String taskType;
	
	private String taskResult;
	
	/**
	 * 原额度
	 */
	private BigDecimal accountQuota;
	
	/**
	 * 任务调后额度
	 */
	private BigDecimal taskAfterQuota;
	
	/**
	 * 降幅
	 */
	private BigDecimal downRange;
	
	/**
	 * 降幅百分比
	 */
	private Double downRangePercent;
	
	/**
	 * 客户分级
	 */
	private String custLevel;

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getTaskResult() {
		return taskResult;
	}

	public void setTaskResult(String taskResult) {
		this.taskResult = taskResult;
	}

	public BigDecimal getAccountQuota() {
		return accountQuota;
	}

	public void setAccountQuota(BigDecimal accountQuota) {
		this.accountQuota = accountQuota;
	}

	public BigDecimal getTaskAfterQuota() {
		return taskAfterQuota;
	}

	public void setTaskAfterQuota(BigDecimal taskAfterQuota) {
		this.taskAfterQuota = taskAfterQuota;
	}

	public BigDecimal getDownRange() {
		return downRange;
	}

	public void setDownRange(BigDecimal downRange) {
		this.downRange = downRange;
	}



	public String getCustLevel() {
		return custLevel;
	}

	public void setCustLevel(String custLevel) {
		this.custLevel = custLevel;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getCustIdNo() {
		return custIdNo;
	}

	public void setCustIdNo(String custIdNo) {
		this.custIdNo = custIdNo;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Double getDownRangePercent() {
		return downRangePercent;
	}

	public void setDownRangePercent(Double downRangePercent) {
		this.downRangePercent = downRangePercent;
	}
	
	
}
