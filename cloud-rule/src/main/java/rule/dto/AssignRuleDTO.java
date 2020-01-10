package rule.dto;

import com.gzcb.creditcard.gykdh.rule.ruleBean.BillInstallRuleBean;
import com.gzcb.creditcard.gykdh.rule.ruleBean.ChangeQuotaRuleBean;
import com.gzcb.creditcard.gykdh.rule.ruleBean.DebtAgainRuleBean;
import com.gzcb.creditcard.gykdh.rule.ruleBean.StopPayRuleBean;

import java.util.List;

public class AssignRuleDTO {

    private String taskId;

    /**
     * 是否退回
     */
    private boolean returned;

    List<StopPayRuleBean> stopPay;

    List<ChangeQuotaRuleBean> changeQuota;

    DebtAgainRuleBean debtAgain;

    BillInstallRuleBean install;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<StopPayRuleBean> getStopPay() {
        return stopPay;
    }

    public void setStopPay(List<StopPayRuleBean> stopPay) {
        this.stopPay = stopPay;
    }

    public List<ChangeQuotaRuleBean> getChangeQuota() {
        return changeQuota;
    }

    public void setChangeQuota(List<ChangeQuotaRuleBean> changeQuota) {
        this.changeQuota = changeQuota;
    }

    public DebtAgainRuleBean getDebtAgain() {
        return debtAgain;
    }

    public void setDebtAgain(DebtAgainRuleBean debtAgain) {
        this.debtAgain = debtAgain;
    }

    public BillInstallRuleBean getInstall() {
        return install;
    }

    public void setInstall(BillInstallRuleBean install) {
        this.install = install;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }
}
