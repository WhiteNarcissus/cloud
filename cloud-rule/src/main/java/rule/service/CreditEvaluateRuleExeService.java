package rule.service;

import com.gzcb.creditcard.gykdh.rule.dto.CreditEvaluateRuleResultDTO;

import java.util.Map;

public interface CreditEvaluateRuleExeService {
	
	Map<String,CreditEvaluateRuleResultDTO> execute(String custIdNo);
}
