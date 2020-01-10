package mycase.rule.ruleBean;

import com.gzcb.creditcard.gykdh.rule.AbstractRuleBean;

/****
 *@author songshuai
 *@Description:担保人变更
 *@date 2018/7/19 13:40
 */
public class GuarChangeRuleBean extends AbstractRuleBean {
    /**
     * 担保人是否变更
     */
    private String isGuarChange;

    public String getIsGuarChange() {
        return isGuarChange;
    }

    public void setIsGuarChange(String isGuarChange) {
        this.isGuarChange = isGuarChange;
    }
}
