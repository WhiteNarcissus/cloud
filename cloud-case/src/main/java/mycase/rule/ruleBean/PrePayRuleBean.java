package mycase.rule.ruleBean;

import com.gzcb.creditcard.gykdh.rule.AbstractRuleBean;

/**
 * @Auther: maojicheng
 * @Date: 2018/11/6 10
 * @Description:提前还款规则bean
 */

public class PrePayRuleBean  extends AbstractRuleBean {
    private String prePay ;

    public String getPrePay() {
        return prePay;
    }

    public void setPrePay(String prePay) {
        this.prePay = prePay;
    }
}
