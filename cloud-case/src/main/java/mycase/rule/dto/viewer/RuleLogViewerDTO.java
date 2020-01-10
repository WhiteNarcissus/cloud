package mycase.rule.dto.viewer;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 项目：gykdh1.10
 * 版    权: Copyright(C) 2011-2020,  gzcb. All rights reserved
 * 类名：RuleLogViewerDTO
 * 描述：规则日志匹配，结果和条件显示
 * 创建人：liting    创建日期：2017年6月7日   版本：
 * 历史
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
