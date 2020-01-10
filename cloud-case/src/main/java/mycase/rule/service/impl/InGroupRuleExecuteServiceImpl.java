package mycase.rule.service.impl;

import com.gzcb.creditcard.gykdh.rule.RuleContants;
import com.gzcb.creditcard.gykdh.rule.entity.RuleDef;
import com.gzcb.creditcard.gykdh.rule.helper.RuleRuntimeHelper;
import com.gzcb.creditcard.gykdh.rule.ruleBean.InGroupRuleBean;
import com.gzcb.creditcard.gykdh.rule.service.InGroupRuleExecuteService;
import com.gzcb.creditcard.gykdh.rule.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * 项目：gykdh1.16
 * 版    权: Copyright(C) 2011-2020,  gzcb. All rights reserved
 * 类名：TaskRoleRuleExecuteService
 * 描述：任务权限人规则执行
 * 创建人：liting    创建日期：2017年9月15日   版本：
 * 历史
 * <修改人>   <修改日期 >   <修改内容>
 */
@Service("inGroupRuleExecuteService")
public class InGroupRuleExecuteServiceImpl implements InGroupRuleExecuteService{

	@Autowired
	private RuleService ruleService;

	@Override
	public String execute(InGroupRuleBean inGroupRuleBean) {
		RuleDef rule=ruleService.queryRuleByName(RuleContants.INGROUP_RULE_NAME);
		RuleRuntimeHelper.execute(rule.getRuleId(), inGroupRuleBean);
		String result=(String)inGroupRuleBean.getResult();
		return result;
	}
}
