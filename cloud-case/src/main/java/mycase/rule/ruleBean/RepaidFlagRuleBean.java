package mycase.rule.ruleBean;

import com.gzcb.creditcard.gykdh.rule.AbstractRuleBean;

/**
 * @Auther: maojicheng
 * @Date: 2018/11/6 11
 * @Description:还款后支付/降额
 */

public class RepaidFlagRuleBean  extends AbstractRuleBean {
    private String repaidFlag;

    public String getRepaidFlag() {
        return repaidFlag;
    }

    public void setRepaidFlag(String repaidFlag) {
        this.repaidFlag = repaidFlag;
    }
}
