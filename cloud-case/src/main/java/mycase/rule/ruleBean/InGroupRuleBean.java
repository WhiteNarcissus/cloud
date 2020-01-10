package mycase.rule.ruleBean;

import com.gzcb.creditcard.gykdh.rule.AbstractRuleBean;

/****
 *@author songshuai
 *@Description:入组优先级的规则
 *@date 2018/7/19 13:40
 */
public class InGroupRuleBean extends AbstractRuleBean {

    public  InGroupRuleBean(){
        this.gykCheck1="N";
        this.gykCheck2="N";
        this.gykCheck3="N";
        this.gykCheck4="N";
        this.gykDecreaseQuota="N";
        this.gykStoppay="N";
        this.gykVisit1="N";
        this.gykVisit2="N";
        this.gykVisit3="N";
        this.installMethod="N";
        this.personalMethod="N";
    }

    /***
     * 广赢卡止付策略
     */
    private String gykStoppay;

    /**
     * 广赢卡降额策略
     */
    private String gykDecreaseQuota;

    /**
     *广赢卡排查策略
     */
    private String gykCheck1;
    private String gykCheck2;
    private String gykCheck3;
    private String gykCheck4;


    /**
     *广赢卡外访策略
     */
    private String gykVisit1;
    private String gykVisit2;
    private String gykVisit3;

    /***
     *分期策略
     */
    private String installMethod;

    /***
     *以人为维度的策略
     */
    private String personalMethod;

    public String getGykStoppay() {
        return gykStoppay;
    }

    public void setGykStoppay(String gykStoppay) {
        this.gykStoppay = gykStoppay;
    }

    public String getGykDecreaseQuota() {
        return gykDecreaseQuota;
    }

    public void setGykDecreaseQuota(String gykDecreaseQuota) {
        this.gykDecreaseQuota = gykDecreaseQuota;
    }

    public String getGykCheck1() {
        return gykCheck1;
    }

    public void setGykCheck1(String gykCheck1) {
        this.gykCheck1 = gykCheck1;
    }

    public String getGykCheck2() {
        return gykCheck2;
    }

    public void setGykCheck2(String gykCheck2) {
        this.gykCheck2 = gykCheck2;
    }

    public String getGykCheck3() {
        return gykCheck3;
    }

    public void setGykCheck3(String gykCheck3) {
        this.gykCheck3 = gykCheck3;
    }

    public String getGykCheck4() {
        return gykCheck4;
    }

    public void setGykCheck4(String gykCheck4) {
        this.gykCheck4 = gykCheck4;
    }

    public String getGykVisit1() {
        return gykVisit1;
    }

    public void setGykVisit1(String gykVisit1) {
        this.gykVisit1 = gykVisit1;
    }

    public String getGykVisit2() {
        return gykVisit2;
    }

    public void setGykVisit2(String gykVisit2) {
        this.gykVisit2 = gykVisit2;
    }

    public String getGykVisit3() {
        return gykVisit3;
    }

    public void setGykVisit3(String gykVisit3) {
        this.gykVisit3 = gykVisit3;
    }

    public String getInstallMethod() {
        return installMethod;
    }

    public void setInstallMethod(String installMethod) {
        this.installMethod = installMethod;
    }

    public String getPersonalMethod() {
        return personalMethod;
    }

    public void setPersonalMethod(String personalMethod) {
        this.personalMethod = personalMethod;
    }
}
