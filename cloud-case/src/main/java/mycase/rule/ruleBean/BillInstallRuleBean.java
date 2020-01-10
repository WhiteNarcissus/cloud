package mycase.rule.ruleBean;

import com.gzcb.creditcard.gykdh.rule.AbstractRuleBean;

/****
 *@author songshuai
 *@Description:
 *@date 2018/7/19 13:40
 */
public class BillInstallRuleBean extends AbstractRuleBean {

    /**
     * 处理结果
     */
    private String handleResult;

    /**
     * 地区
     */
    private String area;

    /**
     * 客户等级
     */
    private String custGrade;

    /**
     * 额度
     */
    private Integer quota;

    /***
     * 重组类型（个人，家庭，同一经营主体）
     */
    private String gykAgainType;

    public String getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCustGrade() {
        return custGrade;
    }

    public void setCustGrade(String custGrade) {
        this.custGrade = custGrade;
    }

    public Integer getQuota() {
        return quota;
    }

    public void setQuota(Integer quota) {
        this.quota = quota;
    }

    public String getGykAgainType() {
        return gykAgainType;
    }

    public void setGykAgainType(String gykAgainType) {
        this.gykAgainType = gykAgainType;
    }
}
