package mycase.rule.ruleBean;

import com.gzcb.creditcard.gykdh.rule.AbstractRuleBean;

import java.math.BigDecimal;

public class CreditEvaluateRuleBean extends AbstractRuleBean{
	/**家庭广赢卡当前逾期期数*/	
	private Integer homeGykOverDueNum;

	/**家庭信用卡当前逾期期数*/	
	private Integer homeCreditOverDueNum;

	/**
	家庭随心分当前逾期期数	
	*/	
	private Integer homeDivideOverDueNum;

	/**
	家庭个贷当前逾期期数	
	*/	
	private Integer homeLoanOverDueNum;

	/**
	家庭人行当前逾期期数	
	*/	
	private Integer homeRHOverDueNum;

	/**
	家庭人行近半年逾期总次数	
	*/	
	private Integer homeRH6MonthOverDueNum;

	/**
	家庭保证人代偿笔数	
	*/	
	private Integer homeGuarPayNum;

	/**
	家庭资产处置笔数	
	*/	
	private Integer homeAssetAccessNum;

	/**
	家庭法院执行笔数	
	*/	
	private Integer homeCourtAccessNum;

	/**
	家庭是否有行政处罚	
	*/	
	private String homeHasAdmPenalty;

	/**
	家庭人行当前逾期账户数	
	*/	
	private Integer homeRHOverDueAccountNum;

	/**
	家庭信用负债余额	
	*/	
	private BigDecimal homeCreditDebtBalance;

	/**
	家庭卡片透支余额	
	*/	
	private BigDecimal homeOverdrawBalance;

	/**
	家庭近半年最高延滞期数*/
	private Integer home6mthMaxArrearangePeriod;
		
	/**家庭房产状态是否有二押	*/
	private String homeHouseHasTwoMortgage;

	/**
	家庭房产状态是否均为不一致	
	*/	
	private String homeHouseStaAllDiffer;

	/**
	家庭房产是否有查封	
	*/	
	private String homeHouseHasClose;

	/**
	家庭人行发卡机构数	
	*/	
	private Integer homeRHCardIssuerNum;

	/**
	家庭人行最高近半年查询次数	*/
	private Integer homeRHMaxQueryTime6Month;

	/**
	家庭人行最高近一年查询次数	
	*/	
	private Integer homeRHMaxQueryTimeYear;

	/**
	家庭人行近半年查询次数总和	
	*/	
	private Integer homeRHQueryTime6Month;

	/**
	家庭人行近一年查询次数总和	*/
	private Integer homeRHQueryTimeYear;

	/**
	客户广赢卡近3个月日均透支率	
	*/	
	private BigDecimal cust3mthOverdraftRate;

	/**
	配偶广赢卡近3个月日均透支率	
	*/	
	private BigDecimal spouse3mthOverdraftRate;

	/**
	客户近半年日均透支率	
	*/	
	private BigDecimal cust6mthOverdraftRate;

	/**
	配偶近半年日均透支率	
	*/	
	private BigDecimal spouse6mthOverdraftRate;

	/**
	客户近一年日均透支率	
	*/	
	private BigDecimal custYearOverdraftRate;

	/**
	配偶近一年日均透支率
	*/	
	private BigDecimal spouseYearOverdraftRate;

	/**
	家庭最高近6个月小贷或P2P次数	
	*/	
	private Integer homeMax6MthLoanAndP2pNum;

	/**
	家庭最高近12个月小贷或P2P次数	
	*/	
	private Integer homeMaxYearLoanAndP2pNum;

	/**
	家庭同盾是否黑/灰名单	
	*/	
	private String homeHasBlackName;

	/**
	家庭最高个贷违约比率	
	*/	
	private BigDecimal homeMaxLoanBreakRate;

	/**
		
	担保人广赢卡当前逾期期数	
	*/	
	private Integer guarGykOverDueNum;

	/**
	担保人信用卡当前逾期期数	
	*/	
	private Integer guarCreditOverDueNum;

	/**
	担保人随心分当前逾期期数	
	*/	
	private Integer guarDivideOverDueNum;

	/**
	担保人个贷当前逾期期数	
	*/	
	private Integer guarLoanOverDueNum;

	/**
	担保人人行当前逾期期数	
	*/	
	private Integer guarRHOverDueNum;

	/**
	担保人人行近半年逾期总次数	
	*/	
	private Integer guarRH6MonthOverDueNum;

	/**
	担保人保证人代偿笔数	
	*/	
	private Integer guarGuarPayNum;

	/**
	担保人资产处置笔数	
	*/	
	private Integer guarAssetAccessNum;

	/**
	担保人法院执行笔数	
	*/	
	private Integer guarCourtAccessNum;

	/**
	担保人是否有行政处罚
	*/	
	private String guarHasAdmPenalty;

	/**
	担保人人行当前逾期账户数
	*/	
	private Integer guarRHOverDueAccountNum;

	public Integer getHomeGykOverDueNum() {
		return homeGykOverDueNum;
	}

	public void setHomeGykOverDueNum(Integer homeGykOverDueNum) {
		this.homeGykOverDueNum = homeGykOverDueNum;
	}

	public Integer getHomeCreditOverDueNum() {
		return homeCreditOverDueNum;
	}

	public void setHomeCreditOverDueNum(Integer homeCreditOverDueNum) {
		this.homeCreditOverDueNum = homeCreditOverDueNum;
	}

	public Integer getHomeDivideOverDueNum() {
		return homeDivideOverDueNum;
	}

	public void setHomeDivideOverDueNum(Integer homeDivideOverDueNum) {
		this.homeDivideOverDueNum = homeDivideOverDueNum;
	}

	public Integer getHomeLoanOverDueNum() {
		return homeLoanOverDueNum;
	}

	public void setHomeLoanOverDueNum(Integer homeLoanOverDueNum) {
		this.homeLoanOverDueNum = homeLoanOverDueNum;
	}

	public Integer getHomeRHOverDueNum() {
		return homeRHOverDueNum;
	}

	public void setHomeRHOverDueNum(Integer homeRHOverDueNum) {
		this.homeRHOverDueNum = homeRHOverDueNum;
	}

	public Integer getHomeRH6MonthOverDueNum() {
		return homeRH6MonthOverDueNum;
	}

	public void setHomeRH6MonthOverDueNum(Integer homeRH6MonthOverDueNum) {
		this.homeRH6MonthOverDueNum = homeRH6MonthOverDueNum;
	}

	public Integer getHomeGuarPayNum() {
		return homeGuarPayNum;
	}

	public void setHomeGuarPayNum(Integer homeGuarPayNum) {
		this.homeGuarPayNum = homeGuarPayNum;
	}

	public Integer getHomeAssetAccessNum() {
		return homeAssetAccessNum;
	}

	public void setHomeAssetAccessNum(Integer homeAssetAccessNum) {
		this.homeAssetAccessNum = homeAssetAccessNum;
	}

	public Integer getHomeCourtAccessNum() {
		return homeCourtAccessNum;
	}

	public void setHomeCourtAccessNum(Integer homeCourtAccessNum) {
		this.homeCourtAccessNum = homeCourtAccessNum;
	}

	public String getHomeHasAdmPenalty() {
		return homeHasAdmPenalty;
	}

	public void setHomeHasAdmPenalty(String homeHasAdmPenalty) {
		this.homeHasAdmPenalty = homeHasAdmPenalty;
	}

	public Integer getHomeRHOverDueAccountNum() {
		return homeRHOverDueAccountNum;
	}

	public void setHomeRHOverDueAccountNum(Integer homeRHOverDueAccountNum) {
		this.homeRHOverDueAccountNum = homeRHOverDueAccountNum;
	}

	public BigDecimal getHomeCreditDebtBalance() {
		return homeCreditDebtBalance;
	}

	public void setHomeCreditDebtBalance(BigDecimal homeCreditDebtBalance) {
		this.homeCreditDebtBalance = homeCreditDebtBalance;
	}

	public BigDecimal getHomeOverdrawBalance() {
		return homeOverdrawBalance;
	}

	public void setHomeOverdrawBalance(BigDecimal homeOverdrawBalance) {
		this.homeOverdrawBalance = homeOverdrawBalance;
	}

	public String getHomeHouseStaAllDiffer() {
		return homeHouseStaAllDiffer;
	}

	public void setHomeHouseStaAllDiffer(String homeHouseStaAllDiffer) {
		this.homeHouseStaAllDiffer = homeHouseStaAllDiffer;
	}

	public String getHomeHouseHasClose() {
		return homeHouseHasClose;
	}

	public void setHomeHouseHasClose(String homeHouseHasClose) {
		this.homeHouseHasClose = homeHouseHasClose;
	}

	public Integer getHomeRHCardIssuerNum() {
		return homeRHCardIssuerNum;
	}

	public void setHomeRHCardIssuerNum(Integer homeRHCardIssuerNum) {
		this.homeRHCardIssuerNum = homeRHCardIssuerNum;
	}

	public Integer getHomeRHMaxQueryTimeYear() {
		return homeRHMaxQueryTimeYear;
	}

	public void setHomeRHMaxQueryTimeYear(Integer homeRHMaxQueryTimeYear) {
		this.homeRHMaxQueryTimeYear = homeRHMaxQueryTimeYear;
	}

	public Integer getHomeRHQueryTime6Month() {
		return homeRHQueryTime6Month;
	}

	public void setHomeRHQueryTime6Month(Integer homeRHQueryTime6Month) {
		this.homeRHQueryTime6Month = homeRHQueryTime6Month;
	}

	public BigDecimal getCust3mthOverdraftRate() {
		return cust3mthOverdraftRate;
	}

	public void setCust3mthOverdraftRate(BigDecimal cust3mthOverdraftRate) {
		this.cust3mthOverdraftRate = cust3mthOverdraftRate;
	}

	public BigDecimal getSpouse3mthOverdraftRate() {
		return spouse3mthOverdraftRate;
	}

	public void setSpouse3mthOverdraftRate(BigDecimal spouse3mthOverdraftRate) {
		this.spouse3mthOverdraftRate = spouse3mthOverdraftRate;
	}

	public BigDecimal getCust6mthOverdraftRate() {
		return cust6mthOverdraftRate;
	}

	public void setCust6mthOverdraftRate(BigDecimal cust6mthOverdraftRate) {
		this.cust6mthOverdraftRate = cust6mthOverdraftRate;
	}

	public BigDecimal getSpouse6mthOverdraftRate() {
		return spouse6mthOverdraftRate;
	}

	public void setSpouse6mthOverdraftRate(BigDecimal spouse6mthOverdraftRate) {
		this.spouse6mthOverdraftRate = spouse6mthOverdraftRate;
	}

	public BigDecimal getCustYearOverdraftRate() {
		return custYearOverdraftRate;
	}

	public void setCustYearOverdraftRate(BigDecimal custYearOverdraftRate) {
		this.custYearOverdraftRate = custYearOverdraftRate;
	}

	public BigDecimal getSpouseYearOverdraftRate() {
		return spouseYearOverdraftRate;
	}

	public void setSpouseYearOverdraftRate(BigDecimal spouseYearOverdraftRate) {
		this.spouseYearOverdraftRate = spouseYearOverdraftRate;
	}

	public Integer getHomeMax6MthLoanAndP2pNum() {
		return homeMax6MthLoanAndP2pNum;
	}

	public void setHomeMax6MthLoanAndP2pNum(Integer homeMax6MthLoanAndP2pNum) {
		this.homeMax6MthLoanAndP2pNum = homeMax6MthLoanAndP2pNum;
	}

	public Integer getHomeMaxYearLoanAndP2pNum() {
		return homeMaxYearLoanAndP2pNum;
	}

	public void setHomeMaxYearLoanAndP2pNum(Integer homeMaxYearLoanAndP2pNum) {
		this.homeMaxYearLoanAndP2pNum = homeMaxYearLoanAndP2pNum;
	}

	public String getHomeHasBlackName() {
		return homeHasBlackName;
	}

	public void setHomeHasBlackName(String homeHasBlackName) {
		this.homeHasBlackName = homeHasBlackName;
	}

	public BigDecimal getHomeMaxLoanBreakRate() {
		return homeMaxLoanBreakRate;
	}

	public void setHomeMaxLoanBreakRate(BigDecimal homeMaxLoanBreakRate) {
		this.homeMaxLoanBreakRate = homeMaxLoanBreakRate;
	}

	public Integer getGuarGykOverDueNum() {
		return guarGykOverDueNum;
	}

	public void setGuarGykOverDueNum(Integer guarGykOverDueNum) {
		this.guarGykOverDueNum = guarGykOverDueNum;
	}

	public Integer getGuarCreditOverDueNum() {
		return guarCreditOverDueNum;
	}

	public void setGuarCreditOverDueNum(Integer guarCreditOverDueNum) {
		this.guarCreditOverDueNum = guarCreditOverDueNum;
	}

	public Integer getGuarDivideOverDueNum() {
		return guarDivideOverDueNum;
	}

	public void setGuarDivideOverDueNum(Integer guarDivideOverDueNum) {
		this.guarDivideOverDueNum = guarDivideOverDueNum;
	}

	public Integer getGuarLoanOverDueNum() {
		return guarLoanOverDueNum;
	}

	public void setGuarLoanOverDueNum(Integer guarLoanOverDueNum) {
		this.guarLoanOverDueNum = guarLoanOverDueNum;
	}

	public Integer getGuarRHOverDueNum() {
		return guarRHOverDueNum;
	}

	public void setGuarRHOverDueNum(Integer guarRHOverDueNum) {
		this.guarRHOverDueNum = guarRHOverDueNum;
	}

	public Integer getGuarRH6MonthOverDueNum() {
		return guarRH6MonthOverDueNum;
	}

	public void setGuarRH6MonthOverDueNum(Integer guarRH6MonthOverDueNum) {
		this.guarRH6MonthOverDueNum = guarRH6MonthOverDueNum;
	}

	public Integer getGuarGuarPayNum() {
		return guarGuarPayNum;
	}

	public void setGuarGuarPayNum(Integer guarGuarPayNum) {
		this.guarGuarPayNum = guarGuarPayNum;
	}

	public Integer getGuarAssetAccessNum() {
		return guarAssetAccessNum;
	}

	public void setGuarAssetAccessNum(Integer guarAssetAccessNum) {
		this.guarAssetAccessNum = guarAssetAccessNum;
	}

	public Integer getGuarCourtAccessNum() {
		return guarCourtAccessNum;
	}

	public void setGuarCourtAccessNum(Integer guarCourtAccessNum) {
		this.guarCourtAccessNum = guarCourtAccessNum;
	}

	public String getGuarHasAdmPenalty() {
		return guarHasAdmPenalty;
	}

	public void setGuarHasAdmPenalty(String guarHasAdmPenalty) {
		this.guarHasAdmPenalty = guarHasAdmPenalty;
	}

	public Integer getGuarRHOverDueAccountNum() {
		return guarRHOverDueAccountNum;
	}

	public void setGuarRHOverDueAccountNum(Integer guarRHOverDueAccountNum) {
		this.guarRHOverDueAccountNum = guarRHOverDueAccountNum;
	}

	public Integer getHome6mthMaxArrearangePeriod() {
		return home6mthMaxArrearangePeriod;
	}

	public void setHome6mthMaxArrearangePeriod(Integer home6mthMaxArrearangePeriod) {
		this.home6mthMaxArrearangePeriod = home6mthMaxArrearangePeriod;
	}

	public String getHomeHouseHasTwoMortgage() {
		return homeHouseHasTwoMortgage;
	}

	public void setHomeHouseHasTwoMortgage(String homeHouseHasTwoMortgage) {
		this.homeHouseHasTwoMortgage = homeHouseHasTwoMortgage;
	}

	public Integer getHomeRHQueryTimeYear() {
		return homeRHQueryTimeYear;
	}

	public void setHomeRHQueryTimeYear(Integer homeRHQueryTimeYear) {
		this.homeRHQueryTimeYear = homeRHQueryTimeYear;
	}

	public Integer getHomeRHMaxQueryTime6Month() {
		return homeRHMaxQueryTime6Month;
	}

	public void setHomeRHMaxQueryTime6Month(Integer homeRHMaxQueryTime6Month) {
		this.homeRHMaxQueryTime6Month = homeRHMaxQueryTime6Month;
	}
	
	
}
