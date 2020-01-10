package mycase.rule.entity;

import java.util.Date;

public class RuleDef {
    /**
     *
     * column t_rule_def.rule_id
     */
    private Long ruleId;

    /**
     * 规则名字
     * column t_rule_def.rule_name
     */
    private String ruleName;

    /**
     * 创建时间
     * column t_rule_def.created_time
     */
    private Date createdTime;

    /**
     *
     * column t_rule_def.deploy_time
     */
    private Date deployTime;

    /**
     *
     * column t_rule_def.deploy_user_id
     */
    private String deployUserId;

    /**
     * 版本
     * column t_rule_def.rule_version
     */
    private String ruleVersion;

    /**
     *
     * column t_rule_def.script
     */
    private String script;

    private String ruleDesc;

    private String ruleResult;
    
    /**
     * 手动执行
     */
    private String handExe;

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


    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script == null ? null : script.trim();
    }

    public String getRuleDesc() {
        return ruleDesc;
    }

    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc;
    }

    public String getRuleResult() {
        return ruleResult;
    }

    public void setRuleResult(String ruleResult) {
        this.ruleResult = ruleResult;
    }

	public String getHandExe() {
		return handExe;
	}

	public void setHandExe(String handExe) {
		this.handExe = handExe;
	}


}