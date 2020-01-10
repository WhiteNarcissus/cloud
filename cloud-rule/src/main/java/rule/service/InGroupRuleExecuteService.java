package rule.service;

import com.gzcb.creditcard.gykdh.rule.ruleBean.InGroupRuleBean;

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
public interface InGroupRuleExecuteService {

	public String execute(InGroupRuleBean inGroupRuleBean);
}
