package mycase.rule.ruleBean;

import com.gzcb.creditcard.gykdh.rule.AbstractRuleBean;

/****
 *@author songshuai
 *@Description:催收措施的规则bean
 *@date 2018/7/19 13:40
 */
public class CollectMethodRuleBean extends AbstractRuleBean {
    private String collectMethod;

    public String getCollectMethod() {
        return collectMethod;
    }

    public void setCollectMethod(String collectMethod) {
        this.collectMethod = collectMethod;
    }
}
