package rule.entity;

import java.util.Date;

public class PaichaRuleResult {
    /**
     * 
     * column t_paicha_rule_result.result_id
     */
    private Long resultId;

    /**
     * 证件号
     * column t_paicha_rule_result.cust_id_no
     */
    private String custIdNo;

    /**
     * 客户姓名
     * column t_paicha_rule_result.cust_name
     */
    private String custName;

    /**
     * 规则执行结果
     * column t_paicha_rule_result.rule_result
     */
    private String ruleResult;

    /**
     * 创建时间
     * column t_paicha_rule_result.created_time
     */
    private Date createdTime;

    /**
     * 更新时间
     * column t_paicha_rule_result.updated_time
     */
    private Date updatedTime;

    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
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

    public String getRuleResult() {
        return ruleResult;
    }

    public void setRuleResult(String ruleResult) {
        this.ruleResult = ruleResult == null ? null : ruleResult.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
}