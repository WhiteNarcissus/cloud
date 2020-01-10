package rule.entity;

import java.util.List;

/**
 * 
 * 项目：gykdh1.10
 * 版    权: Copyright(C) 2011-2020,  gzcb. All rights reserved
 * 类名：PaichaRuleResultCriteria
 * 描述：排查规则查询条件
 * 创建人：liting    创建日期：2017年5月19日   版本：
 * 历史
 * <修改人>   <修改日期 >   <修改内容>
 */
public class PaichaRuleResultCriteria {

	/**
	 * 证件号
	 */
	private String custIdNo;
	
	/**
	 * 结果
	 */
	private String ruleResult;
	
	/**
	 * 客户姓名
	 */
	private String custName;
	
	/**
	 * 
	 */
	private List<String> custIdNoList;
	
	private int start;
	
	private int length;
	
	private String isPage;

	public String getCustIdNo() {
		return custIdNo;
	}

	public void setCustIdNo(String custIdNo) {
		this.custIdNo = custIdNo;
	}

	public String getRuleResult() {
		return ruleResult;
	}

	public void setRuleResult(String ruleResult) {
		this.ruleResult = ruleResult;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public List<String> getCustIdNoList() {
		return custIdNoList;
	}

	public void setCustIdNoList(List<String> custIdNoList) {
		this.custIdNoList = custIdNoList;
	}

	

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getIsPage() {
		return isPage;
	}

	public void setIsPage(String isPage) {
		this.isPage = isPage;
	}
	
	
}
