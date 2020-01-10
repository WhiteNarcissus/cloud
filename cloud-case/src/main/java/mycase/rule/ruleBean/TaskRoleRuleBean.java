package mycase.rule.ruleBean;

import com.gzcb.creditcard.gykdh.rule.AbstractRuleBean;

import java.math.BigDecimal;

/**
 * 
 * 项目：gykdh1.16
 * 版    权: Copyright(C) 2011-2020,  gzcb. All rights reserved
 * 类名：TaskRoleRuleBean
 * 描述：任务权限规则需要的字段
 * 创建人：liting    创建日期：2017年9月14日   版本：
 * 历史
 * <修改人>   <修改日期 >   <修改内容>
 */
public class TaskRoleRuleBean extends AbstractRuleBean {
	
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
	
	/**
	 * 调后额度百分比
	 */
	private Double changeAfterQuotaPercent;

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



	public Double getDownRangePercent() {
		return downRangePercent;
	}

	public void setDownRangePercent(Double downRangePercent) {
		this.downRangePercent = downRangePercent;
	}

	public String getCustLevel() {
		return custLevel;
	}

	public void setCustLevel(String custLevel) {
		this.custLevel = custLevel;
	}

	public Double getChangeAfterQuotaPercent() {
		return changeAfterQuotaPercent;
	}

	public void setChangeAfterQuotaPercent(Double changeAfterQuotaPercent) {
		this.changeAfterQuotaPercent = changeAfterQuotaPercent;
	}
	
	
	
	
}
