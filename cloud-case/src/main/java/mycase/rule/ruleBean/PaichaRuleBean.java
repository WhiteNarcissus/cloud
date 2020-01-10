package mycase.rule.ruleBean;

import com.gzcb.creditcard.gykdh.rule.AbstractRuleBean;

import java.math.BigDecimal;

public class PaichaRuleBean extends AbstractRuleBean {
	 /**
     * 字段Id，主键
     * column t_paicha_rule_field.field_id
     */
    private Long fieldId;

    /**
     * 证件号
     * column t_paicha_rule_field.cust_id_no
     */
    private String custIdNo;

    /**
     * 客户姓名
     * column t_paicha_rule_field.cust_name
     */
    private String custName;

    /**
     * 客群
     * column t_paicha_rule_field.cust_group
     */
    private String custGroup;

    /**
     * 是否经营业主
     * column t_paicha_rule_field.is_manage_owner
     */
    private String isManageOwner;

    /**
     * 项目代码
     * column t_paicha_rule_field.project_code
     */
    private String projectCode;

    /**
     * 例批代码
     * column t_paicha_rule_field.special_code
     */
    private String specialCode;

    /**
     * 行业代码
     * column t_paicha_rule_field.profession_code
     */
    private String professionCode;

    /**
     * 分支行
     * column t_paicha_rule_field.branch_office
     */
    private String branchOffice;

    /**
     * 客户经理工号
     * column t_paicha_rule_field.cust_manager_id
     */
    private String custManagerId;

    /**
     * 客户经理评级
     * column t_paicha_rule_field.cust_manager_grade
     */
    private String custManagerGrade;

    /**
     * 当前客户经理分支行
     * column t_paicha_rule_field.cur_branch
     */
    private String curBranch;

    /**
     * 当前客户经理工号
     * column t_paicha_rule_field.cur_manager_id
     */
    private String curManagerId;
    
    /**
     * 客户分级
     */
    private String custLevel;

    /**
     * 风险等级
     * column t_paicha_rule_field.risk_grade
     */
    private String riskGrade;

    /**
     * 行为评分
     * column t_paicha_rule_field.behave_score
     */
    private Integer behaveScore;

    /**
     * 中征信评分
     * column t_paicha_rule_field.credit_score
     */
    private Integer creditScore;

    /**
     * 贷后备注
     * column t_paicha_rule_field.loan_remarks
     */
    private String loanRemarks;

    /**
     * 发卡日期
     * column t_paicha_rule_field.card_date
     */
    private String cardDate;

    /**
     * 账龄
     * column t_paicha_rule_field.account_age
     */
    private Integer accountAge;

    /**
     * 实际用卡账龄
     * column t_paicha_rule_field.real_account_age
     */
    private Integer realAccountAge;

    /**
     * 卡标
     * column t_paicha_rule_field.card_status
     */
    private String cardStatus;

    /**
     * 每日境内取现和转账透支限额
     * column t_paicha_rule_field.day_take_amount_limit
     */
    private BigDecimal dayTakeAmountLimit;

    /**
     * 当前逾期期数
     * column t_paicha_rule_field.overdue_num
     */
    private Integer overdueNum;

    /**
     * 当前逾期金额
     * column t_paicha_rule_field.overdue_amount
     */
    private BigDecimal overdueAmount;

    /**
     * 近半年逾期次数
     * column t_paicha_rule_field.halfyear_overdue_num
     */
    private Integer halfyearOverdueNum;

    /**
     * 近一年逾期次数
     * column t_paicha_rule_field.year_overdue_num
     */
    private Integer yearOverdueNum;

    /**
     * 近3个月日均透支率
     * column t_paicha_rule_field.last3mth_overdraft_rate
     */
    private BigDecimal last3mthOverdraftRate;

    /**
     * 近半年日均透支率
     * column t_paicha_rule_field.last6mth_overdraft_rate
     */
    private BigDecimal last6mthOverdraftRate;

    /**
     * 近一年日均透支率
     * column t_paicha_rule_field.lastyear_overdraft_rate
     */
    private BigDecimal lastyearOverdraftRate;

    /**
     * 近3个月月均透支笔数
     * column t_paicha_rule_field.last3mth_overdraft_num
     */
    private Integer last3mthOverdraftNum;

    /**
     * 近3个月透支笔均金额
     * column t_paicha_rule_field.last3mth_overdraft_amount
     */
    private BigDecimal last3mthOverdraftAmount;

    /**
     * 近3个月月均透支天数
     * column t_paicha_rule_field.last3mth_overdraft_days
     */
    private Integer last3mthOverdraftDays;

    /**
     * 近3个月月均还款笔数
     * column t_paicha_rule_field.last3mth_pay_num
     */
    private Integer last3mthPayNum;

    /**
     * 近3个月还款笔均金额
     * column t_paicha_rule_field.last3mth_pay_amount
     */
    private BigDecimal last3mthPayAmount;

    /**
     * 分期-未结束大额分期余额
     * column t_paicha_rule_field.not_close_bigBalance
     */
    private BigDecimal notCloseBigbalance;

    /**
     * 分期-未结束消费分期余额
     * column t_paicha_rule_field.not_close_consume_balance
     */
    private BigDecimal notCloseConsumeBalance;

    /**
     * 信用卡-当前逾期期数
     * column t_paicha_rule_field.credit_over_due_num
     */
    private Integer creditOverDueNum;

    /**
     * 信用卡 -当前逾期金额
     * column t_paicha_rule_field.credit_over_due_amount
     */
    private BigDecimal creditOverDueAmount;

    /**
     * 信用卡-近三个月还款率
     * column t_paicha_rule_field.three_months_repayment_rate
     */
    private BigDecimal threeMonthsRepaymentRate;

    /**
     * 个贷-逾期期数
     * column t_paicha_rule_field.loan_overdue_num
     */
    private Integer loanOverdueNum;

    /**
     * 个贷-逾期本金
     * column t_paicha_rule_field.loan_overdue_amount
     */
    private BigDecimal loanOverdueAmount;

    /**
     * 个贷-逾期利息
     * column t_paicha_rule_field.loan_overdue_interest
     */
    private BigDecimal loanOverdueInterest;

    /**
     * 个贷-已结清期数
     * column t_paicha_rule_field.closed_period
     */
    private Integer closedPeriod;

    /**
     * 个贷-累计违约次数
     * column t_paicha_rule_field.breakrule_num
     */
    private Integer breakruleNum;

    /**
     * 人行征信-当前负债
     * column t_paicha_rule_field.current_debt
     */
    private BigDecimal currentDebt;

    /**
     * 人行征信-信用负债余额
     * column t_paicha_rule_field.credit_debt_remain
     */
    private BigDecimal creditDebtRemain;

    /**
     * 人行征信-信用贷款余额
     * column t_paicha_rule_field.loan_remain
     */
    private BigDecimal loanRemain;

    /**
     * 人行征信-卡片透支余额
     * column t_paicha_rule_field.card_overdraw_remain
     */
    private BigDecimal cardOverdrawRemain;

    /**
     * 人行征信-当前逾期账户数
     * column t_paicha_rule_field.current_overdue_account_num
     */
    private Integer currentOverdueAccountNum;

    /**
     * 人行征信-当前逾期期数
     * column t_paicha_rule_field.current_overdue_period
     */
    private Integer currentOverduePeriod;

    /**
     * 人行征信-当前逾期总金额
     * column t_paicha_rule_field.current_overdue_amount
     */
    private BigDecimal currentOverdueAmount;

    /**
     * 人行征信-近半年最高延滞期数
     * column t_paicha_rule_field.last_half_year_max_arrearange_period
     */
    private Integer lastHalfYearMaxArrearangePeriod;

    /**
     * 人行征信-近半年逾期总次数
     * column t_paicha_rule_field.last_half_year_overdue_times
     */
    private Integer lastHalfYearOverdueTimes;

    /**
     * 人行征信-近24个月信贷逾期次数
     * column t_paicha_rule_field.last24_month_overdue_times
     */
    private Integer last24MonthOverdueTimes;

    /**
     * 人行征信-近三个月查询次数
     * column t_paicha_rule_field.last3_month_query_times
     */
    private Integer last3MonthQueryTimes;

    /**
     * 人行征信-近半年查询次数
     * column t_paicha_rule_field.last_half_year_query_times
     */
    private Integer lastHalfYearQueryTimes;

    /**
     * 人行征信-近一年查询次数
     * column t_paicha_rule_field.last_year_query_times
     */
    private Integer lastYearQueryTimes;

    /**
     * 人行征信-保证人代偿笔数
     * column t_paicha_rule_field.bondsman_compensatory
     */
    private Integer bondsmanCompensatory;

    /**
     * 人行征信-资产处置笔数
     * column t_paicha_rule_field.asset_disposal_counts
     */
    private Integer assetDisposalCounts;

    /**
     * 人行征信-法院执行笔数
     * column t_paicha_rule_field.court_enforcement_counts
     */
    private Integer courtEnforcementCounts;

    /**
     * 人行征信-是否有行政处罚
     * column t_paicha_rule_field.has_administrative_penalty
     */
    private String hasAdministrativePenalty;


    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public String getCustIdNo() {
        return custIdNo;
    }

    public void setCustIdNo(String custIdNo) {
        this.custIdNo = custIdNo == null ? null : custIdNo.trim();
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName == null ? null : custName.trim();
    }

    public String getCustGroup() {
        return custGroup;
    }

    public void setCustGroup(String custGroup) {
        this.custGroup = custGroup == null ? null : custGroup.trim();
    }

    public String getIsManageOwner() {
        return isManageOwner;
    }

    public void setIsManageOwner(String isManageOwner) {
        this.isManageOwner = isManageOwner == null ? null : isManageOwner.trim();
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode == null ? null : projectCode.trim();
    }

    public String getSpecialCode() {
        return specialCode;
    }

    public void setSpecialCode(String specialCode) {
        this.specialCode = specialCode == null ? null : specialCode.trim();
    }

    public String getProfessionCode() {
        return professionCode;
    }

    public void setProfessionCode(String professionCode) {
        this.professionCode = professionCode == null ? null : professionCode.trim();
    }

    public String getBranchOffice() {
        return branchOffice;
    }

    public void setBranchOffice(String branchOffice) {
        this.branchOffice = branchOffice == null ? null : branchOffice.trim();
    }

    public String getCustManagerId() {
        return custManagerId;
    }

    public void setCustManagerId(String custManagerId) {
        this.custManagerId = custManagerId == null ? null : custManagerId.trim();
    }

    public String getCustManagerGrade() {
        return custManagerGrade;
    }

    public void setCustManagerGrade(String custManagerGrade) {
        this.custManagerGrade = custManagerGrade == null ? null : custManagerGrade.trim();
    }

    public String getCurBranch() {
        return curBranch;
    }

    public void setCurBranch(String curBranch) {
        this.curBranch = curBranch == null ? null : curBranch.trim();
    }

    public String getCurManagerId() {
        return curManagerId;
    }

    public void setCurManagerId(String curManagerId) {
        this.curManagerId = curManagerId == null ? null : curManagerId.trim();
    }

    public String getRiskGrade() {
        return riskGrade;
    }

    public void setRiskGrade(String riskGrade) {
        this.riskGrade = riskGrade == null ? null : riskGrade.trim();
    }



    public Integer getBehaveScore() {
		return behaveScore;
	}

	public void setBehaveScore(Integer behaveScore) {
		this.behaveScore = behaveScore;
	}

	public Integer getCreditScore() {
		return creditScore;
	}

	public void setCreditScore(Integer creditScore) {
		this.creditScore = creditScore;
	}

	public String getLoanRemarks() {
        return loanRemarks;
    }

    public void setLoanRemarks(String loanRemarks) {
        this.loanRemarks = loanRemarks == null ? null : loanRemarks.trim();
    }

    public String getCardDate() {
        return cardDate;
    }

    public void setCardDate(String cardDate) {
        this.cardDate = cardDate == null ? null : cardDate.trim();
    }

    public Integer getAccountAge() {
        return accountAge;
    }

    public void setAccountAge(Integer accountAge) {
        this.accountAge = accountAge;
    }

    public Integer getRealAccountAge() {
        return realAccountAge;
    }

    public void setRealAccountAge(Integer realAccountAge) {
        this.realAccountAge = realAccountAge;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus == null ? null : cardStatus.trim();
    }

    public BigDecimal getDayTakeAmountLimit() {
        return dayTakeAmountLimit;
    }

    public void setDayTakeAmountLimit(BigDecimal dayTakeAmountLimit) {
        this.dayTakeAmountLimit = dayTakeAmountLimit;
    }

    public Integer getOverdueNum() {
        return overdueNum;
    }

    public void setOverdueNum(Integer overdueNum) {
        this.overdueNum = overdueNum;
    }

    public BigDecimal getOverdueAmount() {
        return overdueAmount;
    }

    public void setOverdueAmount(BigDecimal overdueAmount) {
        this.overdueAmount = overdueAmount;
    }

    public Integer getHalfyearOverdueNum() {
        return halfyearOverdueNum;
    }

    public void setHalfyearOverdueNum(Integer halfyearOverdueNum) {
        this.halfyearOverdueNum = halfyearOverdueNum;
    }

    public Integer getYearOverdueNum() {
        return yearOverdueNum;
    }

    public void setYearOverdueNum(Integer yearOverdueNum) {
        this.yearOverdueNum = yearOverdueNum;
    }

    public BigDecimal getLast3mthOverdraftRate() {
        return last3mthOverdraftRate;
    }

    public void setLast3mthOverdraftRate(BigDecimal last3mthOverdraftRate) {
        this.last3mthOverdraftRate = last3mthOverdraftRate;
    }

    public BigDecimal getLast6mthOverdraftRate() {
        return last6mthOverdraftRate;
    }

    public void setLast6mthOverdraftRate(BigDecimal last6mthOverdraftRate) {
        this.last6mthOverdraftRate = last6mthOverdraftRate;
    }

    public BigDecimal getLastyearOverdraftRate() {
        return lastyearOverdraftRate;
    }

    public void setLastyearOverdraftRate(BigDecimal lastyearOverdraftRate) {
        this.lastyearOverdraftRate = lastyearOverdraftRate;
    }

    public Integer getLast3mthOverdraftNum() {
        return last3mthOverdraftNum;
    }

    public void setLast3mthOverdraftNum(Integer last3mthOverdraftNum) {
        this.last3mthOverdraftNum = last3mthOverdraftNum;
    }

    public BigDecimal getLast3mthOverdraftAmount() {
        return last3mthOverdraftAmount;
    }

    public void setLast3mthOverdraftAmount(BigDecimal last3mthOverdraftAmount) {
        this.last3mthOverdraftAmount = last3mthOverdraftAmount;
    }

    public Integer getLast3mthOverdraftDays() {
        return last3mthOverdraftDays;
    }

    public void setLast3mthOverdraftDays(Integer last3mthOverdraftDays) {
        this.last3mthOverdraftDays = last3mthOverdraftDays;
    }

    public Integer getLast3mthPayNum() {
        return last3mthPayNum;
    }

    public void setLast3mthPayNum(Integer last3mthPayNum) {
        this.last3mthPayNum = last3mthPayNum;
    }

    public BigDecimal getLast3mthPayAmount() {
        return last3mthPayAmount;
    }

    public void setLast3mthPayAmount(BigDecimal last3mthPayAmount) {
        this.last3mthPayAmount = last3mthPayAmount;
    }

    public BigDecimal getNotCloseBigbalance() {
        return notCloseBigbalance;
    }

    public void setNotCloseBigbalance(BigDecimal notCloseBigbalance) {
        this.notCloseBigbalance = notCloseBigbalance;
    }

    public BigDecimal getNotCloseConsumeBalance() {
        return notCloseConsumeBalance;
    }

    public void setNotCloseConsumeBalance(BigDecimal notCloseConsumeBalance) {
        this.notCloseConsumeBalance = notCloseConsumeBalance;
    }

    public Integer getCreditOverDueNum() {
        return creditOverDueNum;
    }

    public void setCreditOverDueNum(Integer creditOverDueNum) {
        this.creditOverDueNum = creditOverDueNum;
    }

    public BigDecimal getCreditOverDueAmount() {
        return creditOverDueAmount;
    }

    public void setCreditOverDueAmount(BigDecimal creditOverDueAmount) {
        this.creditOverDueAmount = creditOverDueAmount;
    }

    public BigDecimal getThreeMonthsRepaymentRate() {
        return threeMonthsRepaymentRate;
    }

    public void setThreeMonthsRepaymentRate(BigDecimal threeMonthsRepaymentRate) {
        this.threeMonthsRepaymentRate = threeMonthsRepaymentRate;
    }

    public Integer getLoanOverdueNum() {
        return loanOverdueNum;
    }

    public void setLoanOverdueNum(Integer loanOverdueNum) {
        this.loanOverdueNum = loanOverdueNum;
    }

    public BigDecimal getLoanOverdueAmount() {
        return loanOverdueAmount;
    }

    public void setLoanOverdueAmount(BigDecimal loanOverdueAmount) {
        this.loanOverdueAmount = loanOverdueAmount;
    }

    public BigDecimal getLoanOverdueInterest() {
        return loanOverdueInterest;
    }

    public void setLoanOverdueInterest(BigDecimal loanOverdueInterest) {
        this.loanOverdueInterest = loanOverdueInterest;
    }

    public Integer getClosedPeriod() {
        return closedPeriod;
    }

    public void setClosedPeriod(Integer closedPeriod) {
        this.closedPeriod = closedPeriod;
    }

    public Integer getBreakruleNum() {
        return breakruleNum;
    }

    public void setBreakruleNum(Integer breakruleNum) {
        this.breakruleNum = breakruleNum;
    }

    public BigDecimal getCurrentDebt() {
        return currentDebt;
    }

    public void setCurrentDebt(BigDecimal currentDebt) {
        this.currentDebt = currentDebt;
    }

    public BigDecimal getCreditDebtRemain() {
        return creditDebtRemain;
    }

    public void setCreditDebtRemain(BigDecimal creditDebtRemain) {
        this.creditDebtRemain = creditDebtRemain;
    }

    public BigDecimal getLoanRemain() {
        return loanRemain;
    }

    public void setLoanRemain(BigDecimal loanRemain) {
        this.loanRemain = loanRemain;
    }

    public BigDecimal getCardOverdrawRemain() {
        return cardOverdrawRemain;
    }

    public void setCardOverdrawRemain(BigDecimal cardOverdrawRemain) {
        this.cardOverdrawRemain = cardOverdrawRemain;
    }

    public Integer getCurrentOverdueAccountNum() {
        return currentOverdueAccountNum;
    }

    public void setCurrentOverdueAccountNum(Integer currentOverdueAccountNum) {
        this.currentOverdueAccountNum = currentOverdueAccountNum;
    }

    public Integer getCurrentOverduePeriod() {
        return currentOverduePeriod;
    }

    public void setCurrentOverduePeriod(Integer currentOverduePeriod) {
        this.currentOverduePeriod = currentOverduePeriod;
    }

    public BigDecimal getCurrentOverdueAmount() {
        return currentOverdueAmount;
    }

    public void setCurrentOverdueAmount(BigDecimal currentOverdueAmount) {
        this.currentOverdueAmount = currentOverdueAmount;
    }

    public Integer getLastHalfYearMaxArrearangePeriod() {
        return lastHalfYearMaxArrearangePeriod;
    }

    public void setLastHalfYearMaxArrearangePeriod(Integer lastHalfYearMaxArrearangePeriod) {
        this.lastHalfYearMaxArrearangePeriod = lastHalfYearMaxArrearangePeriod;
    }

    public Integer getLastHalfYearOverdueTimes() {
        return lastHalfYearOverdueTimes;
    }

    public void setLastHalfYearOverdueTimes(Integer lastHalfYearOverdueTimes) {
        this.lastHalfYearOverdueTimes = lastHalfYearOverdueTimes;
    }

    public Integer getLast24MonthOverdueTimes() {
        return last24MonthOverdueTimes;
    }

    public void setLast24MonthOverdueTimes(Integer last24MonthOverdueTimes) {
        this.last24MonthOverdueTimes = last24MonthOverdueTimes;
    }

    public Integer getLast3MonthQueryTimes() {
        return last3MonthQueryTimes;
    }

    public void setLast3MonthQueryTimes(Integer last3MonthQueryTimes) {
        this.last3MonthQueryTimes = last3MonthQueryTimes;
    }

    public Integer getLastHalfYearQueryTimes() {
        return lastHalfYearQueryTimes;
    }

    public void setLastHalfYearQueryTimes(Integer lastHalfYearQueryTimes) {
        this.lastHalfYearQueryTimes = lastHalfYearQueryTimes;
    }

    public Integer getLastYearQueryTimes() {
        return lastYearQueryTimes;
    }

    public void setLastYearQueryTimes(Integer lastYearQueryTimes) {
        this.lastYearQueryTimes = lastYearQueryTimes;
    }


  

    public Integer getBondsmanCompensatory() {
		return bondsmanCompensatory;
	}

	public void setBondsmanCompensatory(Integer bondsmanCompensatory) {
		this.bondsmanCompensatory = bondsmanCompensatory;
	}

	public Integer getAssetDisposalCounts() {
		return assetDisposalCounts;
	}

	public void setAssetDisposalCounts(Integer assetDisposalCounts) {
		this.assetDisposalCounts = assetDisposalCounts;
	}

	public Integer getCourtEnforcementCounts() {
        return courtEnforcementCounts;
    }

    public void setCourtEnforcementCounts(Integer courtEnforcementCounts) {
        this.courtEnforcementCounts = courtEnforcementCounts;
    }

    public String getHasAdministrativePenalty() {
        return hasAdministrativePenalty;
    }

    public void setHasAdministrativePenalty(String hasAdministrativePenalty) {
        this.hasAdministrativePenalty = hasAdministrativePenalty == null ? null : hasAdministrativePenalty.trim();
    }

	public String getCustLevel() {
		return custLevel;
	}

	public void setCustLevel(String custLevel) {
		this.custLevel = custLevel;
	}
    
    
}
