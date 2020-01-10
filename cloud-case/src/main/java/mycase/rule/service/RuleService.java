package mycase.rule.service;

import com.gzcb.creditcard.gykdh.dto.PageObjectDTO;
import com.gzcb.creditcard.gykdh.dto.PageReturnDTO;
import com.gzcb.creditcard.gykdh.dto.ReturnObjectDTO;
import com.gzcb.creditcard.gykdh.rule.dto.RuleLogDTO;
import com.gzcb.creditcard.gykdh.rule.dto.config.RuleFieldEntry;
import com.gzcb.creditcard.gykdh.rule.dto.viewer.RuleLogViewerDTO;
import com.gzcb.creditcard.gykdh.rule.dto.viewer.RuleRowViewerDTO;
import com.gzcb.creditcard.gykdh.rule.entity.*;

import java.util.List;
import java.util.Map;

public interface RuleService {

	public List<RuleDef> queryAllRule();
	
	public RuleDef queryRuleById(Long ruleId);
	
	public RuleDef queryRuleByName(String ruleName);
	
	public List<RuleTable> queryRuleTableByRuleId(Long ruleId, String status);

	public List<RuleTable> queryRuleTableByName(Long ruleId, String name);

	public RuleTable getRuleTable(Long tableId, String status);

	public ReturnObjectDTO addRuleTable(RuleTable ruleTable);

	public List<RuleRow> queryRuleRowByTableId(Long tableId);

	public void addConditions(Long userId, Long tableId, String[] conditions, String[] results);

	public void deletePublishedRule(final Long tableId, final Long userId);

	public void deleteEditRule(Long tableId);

	public List<RuleRowViewerDTO> formatRuleConditions(Long tableId);

	public void addTableDetail(final RuleTable ruleTable, List<RuleFieldEntry> fieldList,
                               Map<String, String[]> valuesMap, Map<String, String[]> operatersMap, Map<String, String[]> resultMap);

	public void deploy(final Long tableId, final Long userId);

	public PageReturnDTO<RuleLogDTO> queryRuleLog(RuleLogCriteria log, PageObjectDTO pageObjectDTO);

	public List<HisRuleDef> queryHisRuleDefByRuleIdList(List<Long> ruleIdList);

	public void addRuleLogList(List<RuleLog> logList);


	public Map<String,RuleLogViewerDTO> formatRuleLogViwerDTO(String ruleName, String ruleScript);
}
