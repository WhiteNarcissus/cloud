package mycase.rule.entity;

import java.util.Date;

public class HisRuleDef {

    private Long hisId;
    /**
     * 规则Id
     * column t_his_rule_def.rule_id
     */
    private Long ruleId;

    /**
     * 规则名
     * column t_his_rule_def.rule_name
     */
    private String ruleName;

    /**
     * 创建时间
     * column t_his_rule_def.created_time
     */
    private Date createdTime;

    /**
     * 销毁时间
     * column t_his_rule_def.deploy_time
     */
    private Date deployTime;

    /**
     * 删除人
     * column t_his_rule_def.deploy_user_id
     */
    private String deployUserId;

    /**
     * 规则版本
     * column t_his_rule_def.rule_version
     */
    private String ruleVersion;

    /**
     * 状态码
     * column t_his_rule_def.stage_code
     */
    private String stageCode;

    /**
     * 脚本
     * column t_his_rule_def.script
     */
    private String script;

    private String ruleDesc;
    
    /**
     * 手动执行
     */
    private String handExe;

    public HisRuleDef(){}

    public HisRuleDef (RuleDef ruleDef){
    	this.ruleId=ruleDef.getRuleId();
        this.ruleName = ruleDef.getRuleName();
        this.createdTime = ruleDef.getCreatedTime();
        this.deployTime = ruleDef.getDeployTime();
        this.script = ruleDef.getScript();
        this.deployUserId = ruleDef.getDeployUserId();
        this.ruleDesc=ruleDef.getRuleDesc();
        this.ruleVersion=ruleDef.getRuleVersion();
        this.handExe=ruleDef.getHandExe();
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName == null ? null : ruleName.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getDeployTime() {
        return deployTime;
    }

    public void setDeployTime(Date deployTime) {
        this.deployTime = deployTime;
    }

    public String getDeployUserId() {
        return deployUserId;
    }

    public void setDeployUserId(String deployUserId) {
        this.deployUserId = deployUserId == null ? null : deployUserId.trim();
    }

    public String getRuleVersion() {
        return ruleVersion;
    }

    public void setRuleVersion(String ruleVersion) {
        this.ruleVersion = ruleVersion == null ? null : ruleVersion.trim();
    }

    public String getStageCode() {
        return stageCode;
    }

    public void setStageCode(String stageCode) {
        this.stageCode = stageCode == null ? null : stageCode.trim();
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script == null ? null : script.trim();
    }

    public Long getHisId() {
        return hisId;
    }

    public void setHisId(Long hisId) {
        this.hisId = hisId;
    }

    public String getRuleDesc() {
        return ruleDesc;
    }

    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc;
    }

	public String getHandExe() {
		return handExe;
	}

	public void setHandExe(String handExe) {
		this.handExe = handExe;
	}


}