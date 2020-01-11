package rule.service;



import rule.dto.CreditEvaluateRuleResultDTO;

import java.util.Map;

public interface CreditEvaluateRuleExeService {
	
	Map<String, CreditEvaluateRuleResultDTO> execute(String custIdNo);
}
