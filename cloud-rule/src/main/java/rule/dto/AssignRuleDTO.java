package rule.dto;



import rule.ruleBean.ChangeQuotaRuleBean;

import java.util.List;

public class AssignRuleDTO {

    private String taskId;

    /**
     * 是否退回
     */
    private boolean returned;


    List<ChangeQuotaRuleBean> changeQuota;



    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }


    public List<ChangeQuotaRuleBean> getChangeQuota() {
        return changeQuota;
    }

    public void setChangeQuota(List<ChangeQuotaRuleBean> changeQuota) {
        this.changeQuota = changeQuota;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }
}
