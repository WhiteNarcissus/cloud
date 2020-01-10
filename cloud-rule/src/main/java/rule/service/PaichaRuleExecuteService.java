package rule.service;

import com.gzcb.creditcard.gykdh.rule.entity.PaichaRuleResult;
import com.gzcb.creditcard.gykdh.rule.entity.PaichaRuleResultCriteria;

import java.util.List;

/**
 * 
 * 项目：gykdh1.10
 * 版    权: Copyright(C) 2011-2020,  gzcb. All rights reserved
 * 类名：PaichaRuleExecuteService
 * 描述：排查规则执行
 * 创建人：liting    创建日期：2017年5月18日   版本：
 * 历史
 * <修改人>   <修改日期 >   <修改内容>
 */
public interface PaichaRuleExecuteService {

	void execute();
	
	List<PaichaRuleResult> queryRuleResultList(PaichaRuleResultCriteria paichaRuleResultCriteria);
	
	int countRuleResultList(PaichaRuleResultCriteria paichaRuleResultCriteria);
}
