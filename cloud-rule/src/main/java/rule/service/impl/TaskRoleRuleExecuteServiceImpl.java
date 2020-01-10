package rule.service.impl;

import com.gzcb.creditcard.gykdh.entity.cust.GykAccount;
import com.gzcb.creditcard.gykdh.rule.RuleContants;
import com.gzcb.creditcard.gykdh.rule.dto.TaskRoleRuleDTO;
import com.gzcb.creditcard.gykdh.rule.entity.RuleDef;
import com.gzcb.creditcard.gykdh.rule.entity.RuleLog;
import com.gzcb.creditcard.gykdh.rule.helper.RuleRuntimeHelper;
import com.gzcb.creditcard.gykdh.rule.ruleBean.TaskRoleRuleBean;
import com.gzcb.creditcard.gykdh.rule.service.RuleService;
import com.gzcb.creditcard.gykdh.rule.service.TaskRoleRuleExecuteService;
import com.gzcb.creditcard.gykdh.service.cust.GykAccountService;
import com.mysql.jdbc.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("taskRoleRuleExecuteService")
public class TaskRoleRuleExecuteServiceImpl implements TaskRoleRuleExecuteService {
	
	private static Logger logger=LoggerFactory.getLogger(TaskRoleRuleExecuteServiceImpl.class);
	
	@Autowired
	private RuleService ruleService;
	
	@Autowired
	private GykAccountService gykAccountService;
		
	@Override
	public String execute(TaskRoleRuleDTO taskRole) {
		String result=null;
		
		if(taskRole!=null && !StringUtils.isNullOrEmpty(taskRole.getCustIdNo()) && !StringUtils.isNullOrEmpty(taskRole.getTaskId())){
			
			logger.info("task role rule start taskId:{}",taskRole.getTaskId());
			TaskRoleRuleBean ruleBean=new TaskRoleRuleBean();
			String custIdNo=taskRole.getCustIdNo();
			GykAccount account=null;
			//gykAccountService.queryGykAccount(custIdNo);
						
			if(account!=null){
				String accountQuota=account.getForeverQuota();
				if(!StringUtils.isNullOrEmpty(accountQuota)){
					BigDecimal accountQuotaDecimal=new BigDecimal(accountQuota);
					ruleBean.setAccountQuota(accountQuotaDecimal);										
					BigDecimal taskAfterQuota=taskRole.getTaskAfterQuota();
					
					ruleBean.setTaskAfterQuota(taskAfterQuota);
					
					//如果为空，默认的调后额度为原来额度
					if(taskAfterQuota==null){
						taskAfterQuota=accountQuotaDecimal;
					}

					if (taskAfterQuota != null) {
						BigDecimal downRange = accountQuotaDecimal.subtract(taskAfterQuota);
						ruleBean.setDownRange(downRange);
						BigDecimal zeroBigDecimal=new BigDecimal("0.00");
						if(accountQuotaDecimal.compareTo(zeroBigDecimal)==0  || accountQuotaDecimal.equals(BigDecimal.ZERO)){
							ruleBean.setDownRangePercent(0D);
							ruleBean.setChangeAfterQuotaPercent(0D);
						}
						else {
							ruleBean.setDownRangePercent(downRange.divide(accountQuotaDecimal, 5, RoundingMode.CEILING).doubleValue());
							ruleBean.setChangeAfterQuotaPercent(taskAfterQuota.divide(accountQuotaDecimal, 5, RoundingMode.CEILING).doubleValue());
						}
					}
				}			
			}	
			
			ruleBean.setTaskResult(taskRole.getTaskResult());
			ruleBean.setTaskType(taskRole.getTaskType());
			ruleBean.setCustLevel(taskRole.getCustLevel());
			
			RuleDef rule=ruleService.queryRuleByName(RuleContants.TASK_ROLE_RULE_NAME);
			RuleRuntimeHelper.execute(rule.getRuleId(), ruleBean);			
			result=(String)ruleBean.getResult();
			
			RuleLog ruleLog = new RuleLog();
			ruleLog.setCreatedTime(new Date());
			ruleLog.setCustIdNo(custIdNo);
			ruleLog.setCustName(taskRole.getCustName());
			ruleLog.setRuleId(rule.getRuleId());
			ruleLog.setRuleRow(ruleBean.getBigoRow());
			ruleLog.setRuleName(RuleContants.TASK_ROLE_RULE_NAME);
			ruleLog.setRuleId(rule.getRuleId());
			ruleLog.setRuleVersion(ruleBean.getVersion());
			List<RuleLog> ruleLogList=new ArrayList<RuleLog>();
			ruleLogList.add(ruleLog);			
			ruleService.addRuleLogList(ruleLogList);
			
			logger.info("task role rule end taskId:{} rule result=",taskRole.getTaskId() ,result);			
		}else{
			logger.info(" taskRole param is null or custIdNo is null");
		}
		
		return result;
	}

}
