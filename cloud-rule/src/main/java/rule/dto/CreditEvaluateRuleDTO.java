package rule.dto;

import java.math.BigDecimal;

/**
 * 
 * 项目：gykdh1.10
 * 版    权: Copyright(C) 2011-2020,  gzcb. All rights reserved
 * 类名：CreditEvaluateRuleDTO
 * 描述：信用评估需要值对象
 * 创建人：liting    创建日期：2017年5月23日   版本：
 * 历史
 * <修改人>   <修改日期 >   <修改内容>
 */
public class CreditEvaluateRuleDTO {
	
	/**
	 * 客户证件号
	 */
	private String custIdNo;
	
	/**
	 * 客户姓名
	 */
	private String custName;
	
	/**广赢卡当前逾期期数*/	
	private Integer gykOverDueNum;

	/**信用卡当前逾期期数*/	
	private Integer creditOverDueNum;

	/**
	随心分当前逾期期数	
	*/	
	private Integer divideOverDueNum;

	/**
	个贷当前逾期期数	
	*/	
	private Integer loanOverDueNum;

	/**
	人行当前逾期期数	
	*/	
	private Integer rhOverDueNum;

	/**
	人行近半年逾期总次数	
	*/	
	private Integer rh6MonthOverDueNum;

	/**
	保证人代偿笔数	
	*/	
	private Integer guarPayNum;

	/**
	资产处置笔数	
	*/	
	private Integer assetAccessNum;

	/**
	法院执行笔数	
	*/	
	private Integer courtAccessNum;

	/**
	是否有行政处罚	
	*/	
	private String hasAdmPenalty;

	/**
	人行当前逾期账户数	
	*/	
	private Integer rhOverDueAccountNum;

	/**
	信用负债余额	
	*/	
	private BigDecimal creditDebtBalance;

	/**
	卡片透支余额	
	*/	
	private BigDecimal overdrawBalance;

	/**
	近半年最高延滞期数*/
	private Integer halfYearMaxArrearangePeriod;
		
	/**房产状态是否有二押	*/
	private String houseHasTwoMortgage;

	/**
	房产状态是否均为不一致	
	*/	
	private String houseStaAllDiffer;

	/**
	房产是否有查封	
	*/	
	private String houseHasClose;

	/**
	行发卡机构数	
	*/	
	private Integer rhCardIssuerNum;


	/**
	人行近半年查询次数总和	
	*/	
	private Integer rhQueryTime6Month;

	/**
	人行近一年查询次数总和	*/
	private Integer rhQueryTimeYear;

	/**
	广赢卡近3个月日均透支率	
	*/	
	private BigDecimal gyk3mthOverdraftRate;


	/**
	广赢卡近半年日均透支率	
	*/	
	private BigDecimal gyk6mthOverdraftRate;


	/**
	广赢卡近一年日均透支率	
	*/	
	private BigDecimal gykYearOverdraftRate;


	/**
	近6个月小贷或P2P次数	
	*/	
	private Integer sixMthLoanAndP2pNum;

	/**
	近12个月小贷或P2P次数	
	*/	
	private Integer yearLoanAndP2pNum;

	/**
	同盾是否黑/灰名单	
	*/	
	private String hasBlackName;

	/**
	个贷违约比率	
	*/	
	private BigDecimal loanBreakRate;
	
	/**
	 * 未结束大额分期余额
	 */
	private BigDecimal notCloseBigBalance;

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

	public Integer getGykOverDueNum() {
		return gykOverDueNum;
	}

	public void setGykOverDueNum(Integer gykOverDueNum) {
		this.gykOverDueNum = gykOverDueNum;
	}

	public Integer getCreditOverDueNum() {
		return creditOverDueNum;
	}

	public void setCreditOverDueNum(Integer creditOverDueNum) {
		this.creditOverDueNum = creditOverDueNum;
	}

	public Integer getDivideOverDueNum() {
		return divideOverDueNum;
	}

	public void setDivideOverDueNum(Integer divideOverDueNum) {
		this.divideOverDueNum = divideOverDueNum;
	}

	public Integer getLoanOverDueNum() {
		return loanOverDueNum;
	}

	public void setLoanOverDueNum(Integer loanOverDueNum) {
		this.loanOverDueNum = loanOverDueNum;
	}

	public Integer getGuarPayNum() {
		return guarPayNum;
	}

	public void setGuarPayNum(Integer guarPayNum) {
		this.guarPayNum = guarPayNum;
	}

	public Integer getAssetAccessNum() {
		return assetAccessNum;
	}

	public void setAssetAccessNum(Integer assetAccessNum) {
		this.assetAccessNum = assetAccessNum;
	}

	public Integer getCourtAccessNum() {
		return courtAccessNum;
	}

	public void setCourtAccessNum(Integer courtAccessNum) {
		this.courtAccessNum = courtAccessNum;
	}

	public String getHasAdmPenalty() {
		return hasAdmPenalty;
	}

	public void setHasAdmPenalty(String hasAdmPenalty) {
		this.hasAdmPenalty = hasAdmPenalty;
	}


	public BigDecimal getCreditDebtBalance() {
		return creditDebtBalance;
	}

	public void setCreditDebtBalance(BigDecimal creditDebtBalance) {
		this.creditDebtBalance = creditDebtBalance;
	}

	public BigDecimal getOverdrawBalance() {
		return overdrawBalance;
	}

	public void setOverdrawBalance(BigDecimal overdrawBalance) {
		this.overdrawBalance = overdrawBalance;
	}

	public Integer getHalfYearMaxArrearangePeriod() {
		return halfYearMaxArrearangePeriod;
	}

	public void setHalfYearMaxArrearangePeriod(Integer halfYearMaxArrearangePeriod) {
		this.halfYearMaxArrearangePeriod = halfYearMaxArrearangePeriod;
	}

	public String getHouseHasTwoMortgage() {
		return houseHasTwoMortgage;
	}

	public void setHouseHasTwoMortgage(String houseHasTwoMortgage) {
		this.houseHasTwoMortgage = houseHasTwoMortgage;
	}

	public String getHouseStaAllDiffer() {
		return houseStaAllDiffer;
	}

	public void setHouseStaAllDiffer(String houseStaAllDiffer) {
		this.houseStaAllDiffer = houseStaAllDiffer;
	}

	public String getHouseHasClose() {
		return houseHasClose;
	}

	public void setHouseHasClose(String houseHasClose) {
		this.houseHasClose = houseHasClose;
	}

	
	public BigDecimal getGyk3mthOverdraftRate() {
		return gyk3mthOverdraftRate;
	}

	public void setGyk3mthOverdraftRate(BigDecimal gyk3mthOverdraftRate) {
		this.gyk3mthOverdraftRate = gyk3mthOverdraftRate;
	}

	public BigDecimal getGyk6mthOverdraftRate() {
		return gyk6mthOverdraftRate;
	}

	public void setGyk6mthOverdraftRate(BigDecimal gyk6mthOverdraftRate) {
		this.gyk6mthOverdraftRate = gyk6mthOverdraftRate;
	}

	public BigDecimal getGykYearOverdraftRate() {
		return gykYearOverdraftRate;
	}

	public void setGykYearOverdraftRate(BigDecimal gykYearOverdraftRate) {
		this.gykYearOverdraftRate = gykYearOverdraftRate;
	}

	public String getHasBlackName() {
		return hasBlackName;
	}

	public void setHasBlackName(String hasBlackName) {
		this.hasBlackName = hasBlackName;
	}


	public BigDecimal getLoanBreakRate() {
		return loanBreakRate;
	}

	public void setLoanBreakRate(BigDecimal loanBreakRate) {
		this.loanBreakRate = loanBreakRate;
	}

	public Integer getSixMthLoanAndP2pNum() {
		return sixMthLoanAndP2pNum;
	}

	public void setSixMthLoanAndP2pNum(Integer sixMthLoanAndP2pNum) {
		this.sixMthLoanAndP2pNum = sixMthLoanAndP2pNum;
	}

	public Integer getYearLoanAndP2pNum() {
		return yearLoanAndP2pNum;
	}

	public void setYearLoanAndP2pNum(Integer yearLoanAndP2pNum) {
		this.yearLoanAndP2pNum = yearLoanAndP2pNum;
	}

	public Integer getRhOverDueNum() {
		return rhOverDueNum;
	}

	public void setRhOverDueNum(Integer rhOverDueNum) {
		this.rhOverDueNum = rhOverDueNum;
	}

	public Integer getRh6MonthOverDueNum() {
		return rh6MonthOverDueNum;
	}

	public void setRh6MonthOverDueNum(Integer rh6MonthOverDueNum) {
		this.rh6MonthOverDueNum = rh6MonthOverDueNum;
	}

	public Integer getRhOverDueAccountNum() {
		return rhOverDueAccountNum;
	}

	public void setRhOverDueAccountNum(Integer rhOverDueAccountNum) {
		this.rhOverDueAccountNum = rhOverDueAccountNum;
	}

	public Integer getRhCardIssuerNum() {
		return rhCardIssuerNum;
	}

	public void setRhCardIssuerNum(Integer rhCardIssuerNum) {
		this.rhCardIssuerNum = rhCardIssuerNum;
	}

	public Integer getRhQueryTime6Month() {
		return rhQueryTime6Month;
	}

	public void setRhQueryTime6Month(Integer rhQueryTime6Month) {
		this.rhQueryTime6Month = rhQueryTime6Month;
	}

	public Integer getRhQueryTimeYear() {
		return rhQueryTimeYear;
	}

	public void setRhQueryTimeYear(Integer rhQueryTimeYear) {
		this.rhQueryTimeYear = rhQueryTimeYear;
	}

	public BigDecimal getNotCloseBigBalance() {
		return notCloseBigBalance;
	}

	public void setNotCloseBigBalance(BigDecimal notCloseBigBalance) {
		this.notCloseBigBalance = notCloseBigBalance;
	}

	
}
