package rule.ruleBean;


import rule.AbstractRuleBean;

/****
 *@author songshuai
 *@Description:
 *@date 2018/7/19 13:40
 */
public class ChangeQuotaRuleBean extends AbstractRuleBean {
    /**
     * 产品类型
     */
    private String productType;

    /****
     * 卡产品编码
     */
    private String cardProductCode;

    /**
     * 处理结果
     */
    private String handleResult;

    /**
     * 客户等级
     */
    private String custGrade;

    /**
     * 开通后额度比例
     */
    private Integer quotaChangeRange;

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getCardProductCode() {
        return cardProductCode;
    }

    public void setCardProductCode(String cardProductCode) {
        this.cardProductCode = cardProductCode;
    }

    public String getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult;
    }

    public String getCustGrade() {
        return custGrade;
    }

    public void setCustGrade(String custGrade) {
        this.custGrade = custGrade;
    }

    public Integer getQuotaChangeRange() {
        return quotaChangeRange;
    }

    public void setQuotaChangeRange(Integer quotaChangeRange) {
        this.quotaChangeRange = quotaChangeRange;
    }
}
