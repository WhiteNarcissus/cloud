package rule.dto.viewer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * <修改人>   <修改日期 >   <修改内容>
 */
public class RuleLogViewerDTO {
	List<String> condition = new ArrayList<String>();
	List<String> result = new ArrayList<String>();
	
	public List<String> getCondition() {
		return condition;
	}
	public void addCondition(String condition) {
		this.condition.add(condition);
	}
	public List<String> getResult() {
		return result;
	}
	public void addResult(String result) {
		this.result.add(result);
	}
}
