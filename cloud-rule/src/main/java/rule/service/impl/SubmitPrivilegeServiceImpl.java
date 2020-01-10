package rule.service.impl;

import com.gzcb.creditcard.gykdh.dao.ntask.TaskDealMapper;
import com.gzcb.creditcard.gykdh.rule.AbstractRuleBean;
import com.gzcb.creditcard.gykdh.rule.RuleContants;
import com.gzcb.creditcard.gykdh.rule.entity.RuleDef;
import com.gzcb.creditcard.gykdh.rule.entity.RuleLog;
import com.gzcb.creditcard.gykdh.rule.helper.RuleRuntimeHelper;
import com.gzcb.creditcard.gykdh.rule.ruleBean.*;
import com.gzcb.creditcard.gykdh.rule.service.RuleService;
import com.gzcb.creditcard.gykdh.rule.service.SubmitPrivilegeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("submitPrivilegeService")
public class SubmitPrivilegeServiceImpl implements SubmitPrivilegeService {
	private static Logger logger=LoggerFactory.getLogger(SubmitPrivilegeServiceImpl.class);



	@Autowired
	private RuleService ruleService;

	@Autowired
	TaskDealMapper taskDealMapper;

	@Override
	public String executeStopPayRule(StopPayRuleBean stopPayRuleBean,String taskId,String custIdNo,String customerName) {
		return executePrivilegeCommonRule(stopPayRuleBean,taskId,custIdNo,customerName,RuleContants.STOP_PAY_RULE_NAME);
	}

	@Override
	public String executeChangeQuotaRule(ChangeQuotaRuleBean changeQuotaRuleBean,String taskId,String custIdNo,String customerName) {
		return executePrivilegeCommonRule(changeQuotaRuleBean,taskId,custIdNo,customerName,RuleContants.CHANGE_QUOTA_RULE_NAME);
	}

	@Override
	public String executeDebtAgainRule(DebtAgainRuleBean debtAgainRuleBean,String taskId,String custIdNo,String customerName) {
		return executePrivilegeCommonRule(debtAgainRuleBean,taskId,custIdNo,customerName,RuleContants.DEBT_AGAIN_RULE_NAME);
	}

	@Override
	public String executeBillInstallRule(BillInstallRuleBean billInstallRuleBean,String taskId,String custIdNo,String customerName){
		return executePrivilegeCommonRule(billInstallRuleBean,taskId,custIdNo,customerName,RuleContants.BILL_INSTALL_RULE_NAME);
	}

	@Override
	public String executeCollectMethodRule(CollectMethodRuleBean collectMethodRuleBean, String taskId, String custIdNo, String customerName) {
		return executePrivilegeCommonRule(collectMethodRuleBean,taskId,custIdNo,customerName,RuleContants.COLLECT_METHOd_RULE_NAME);
	}

	@Override
	public String executePrePayRule(PrePayRuleBean prePayRuleBean, String taskId, String custIdNo, String customerName) {
		return executePrivilegeCommonRule(prePayRuleBean,taskId,custIdNo,customerName,RuleContants.PRE_PAY_RULE_NAME);
	}

	@Override
	public String executeRepaidFlagRule(RepaidFlagRuleBean repaidFlagRuleBean, String taskId, String custIdNo, String customerName) {
		return executePrivilegeCommonRule(repaidFlagRuleBean,taskId,custIdNo,customerName,RuleContants.REPAID_FLAG_RULE_NAME);
	}

	@Override
	public String executePostLoanMeasureRule(PostLoanMeasureRuleBean postLoanMeasureRuleBean, String taskId, String custIdNo, String customerName) {
		return executePrivilegeCommonRule(postLoanMeasureRuleBean,taskId,custIdNo,customerName,RuleContants.POSTLOAN_MEASURE_RULE_NAME);
	}

	@Override
	public String executeGuarChangeRule(GuarChangeRuleBean guarChangeRuleBean, String taskId, String custIdNo, String customerName) {
		return executePrivilegeCommonRule(guarChangeRuleBean,taskId,custIdNo,customerName,RuleContants.GUAR_CHANGE_RULE_NAME);
	}

	private String executePrivilegeCommonRule(AbstractRuleBean abstractRuleBean, String taskId, String custIdNo, String customerName, String ruleName){
		String result=null;

		if(abstractRuleBean!=null){
			logger.info("Privilege Rule start taskId:{}",taskId);

			RuleDef rule=ruleService.queryRuleByName(ruleName);
			RuleRuntimeHelper.execute(rule.getRuleId(), abstractRuleBean);
			result=(String)abstractRuleBean.getResult();

			saveRuleExecuteLog(custIdNo,customerName,rule,abstractRuleBean);

			logger.info("Privilege Rule end taskId:{} rule result={}",taskId ,result);
		}else{
			logger.info("Privilege Rule param is null or custIdNo is null");
		}

		return result;
	}


	private void saveRuleExecuteLog(String custIdNo, String customerName,RuleDef rule, AbstractRuleBean ruleBean){
		RuleLog ruleLog = new RuleLog();
		ruleLog.setCreatedTime(new Date());
		ruleLog.setCustIdNo(custIdNo);
		ruleLog.setCustName(customerName);
		ruleLog.setRuleId(rule.getRuleId());
		ruleLog.setRuleRow(ruleBean.getBigoRow());
		ruleLog.setRuleName(RuleContants.TASK_ROLE_RULE_NAME);
		ruleLog.setRuleId(rule.getRuleId());
		ruleLog.setRuleVersion(ruleBean.getVersion());
		List<RuleLog> ruleLogList=new ArrayList<RuleLog>();
		ruleLogList.add(ruleLog);
		ruleService.addRuleLogList(ruleLogList);
	}
}
