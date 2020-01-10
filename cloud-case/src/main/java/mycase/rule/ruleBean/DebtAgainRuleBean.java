package mycase.rule.ruleBean;

import com.gzcb.creditcard.gykdh.rule.AbstractRuleBean;

/****
 *@author songshuai
 *@Description:
 *@date 2018/7/19 13:40
 */
public class DebtAgainRuleBean extends AbstractRuleBean {

    /**
     * 处理结果
     */
    private String handleResult;

    /**
     * 申请金额
     */
    private Integer applyAmount;

    /**
     * 客户分群
     */
    private String custGroup;


    /**
     * 中征信评分
     */
    private Integer crcGrade;

    public String getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult;
    }

    public Integer getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(Integer applyAmount) {
        this.applyAmount = applyAmount;
    }

    public String getCustGroup() {
        return custGroup;
    }

    public void setCustGroup(String custGroup) {
        this.custGroup = custGroup;
    }

    public Integer getCrcGrade() {
        return crcGrade;
    }

    public void setCrcGrade(Integer crcGrade) {
        this.crcGrade = crcGrade;
    }
}
