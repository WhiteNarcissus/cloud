package rule.service.impl;

import com.gzcb.creditcard.gykdh.common.contants.ResultCodeContants;
import com.gzcb.creditcard.gykdh.common.utils.StringUtils;
import com.gzcb.creditcard.gykdh.dao.rule.*;
import com.gzcb.creditcard.gykdh.dto.PageObjectDTO;
import com.gzcb.creditcard.gykdh.dto.PageReturnDTO;
import com.gzcb.creditcard.gykdh.dto.ReturnObjectDTO;
import com.gzcb.creditcard.gykdh.rule.RuleContants;
import com.gzcb.creditcard.gykdh.rule.dto.RuleLogDTO;
import com.gzcb.creditcard.gykdh.rule.dto.config.RuleBeanEntry;
import com.gzcb.creditcard.gykdh.rule.dto.config.RuleFieldEntry;
import com.gzcb.creditcard.gykdh.rule.dto.config.RuleResultEntry;
import com.gzcb.creditcard.gykdh.rule.dto.viewer.RuleLogViewerDTO;
import com.gzcb.creditcard.gykdh.rule.dto.viewer.RuleRowViewerDTO;
import com.gzcb.creditcard.gykdh.rule.entity.*;
import com.gzcb.creditcard.gykdh.rule.helper.RuleConfigHelper;
import com.gzcb.creditcard.gykdh.rule.helper.RuleEditHelper;
import com.gzcb.creditcard.gykdh.rule.helper.RuleRuntimeHelper;
import com.gzcb.creditcard.gykdh.rule.service.RuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service("ruleService")
public class RuleServiceImpl implements RuleService{
	
	private static Logger logger=LoggerFactory.getLogger(RuleServiceImpl.class);
	
	@Autowired
	private RuleDefMapper ruleDefMapper;
	
	@Autowired
	private HisRuleDefMapper hisRuleDefMapper;
	
	@Autowired
	private RuleTableMapper ruleTableMapper;
	
	
	@Autowired
	private RuleRowMapper ruleRowMapper;
	
	@Autowired
	private RuleRowEditMapper ruleRowEditMapper;
	
	@Autowired
	private RuleTableHistoricMapper  ruleTableHistoricMapper;
	
	@Autowired
	private RuleRowHistoricMapper ruleRowHistoricMapper;
	
	@Autowired
	private RuleLogMapper ruleLogMapper;
	
	
	
	@Override
	public List<RuleDef> queryAllRule() {
		
		return ruleDefMapper.selectAllRule();
	}

	@Override
	public RuleDef queryRuleById(Long ruleId) {
		RuleDef rule=ruleDefMapper.selectByPrimaryKey(ruleId);
		return rule;
	}

	@Override
	public List<RuleTable> queryRuleTableByRuleId(Long ruleId,String status) {	
		List<RuleTable> ruleTableList=null;
		if(RuleContants.TABLE_STATUS_PUBLISH.equals(status)){					
			ruleTableList=ruleTableMapper.selectRuleTableByRuleId(ruleId);
		}else if(RuleContants.TABLE_STATUS_EDIT.equals(status)){					
			ruleTableList=ruleTableMapper.selectRuleTableEditByRuleId(ruleId);
		}else{
			ruleTableList=new ArrayList<RuleTable>();
		}
		
		return ruleTableList;
	}
	
	@Override
	public List<RuleTable> queryRuleTableByName(Long ruleId,String name) {						
		return ruleTableMapper.selectRuleTableByRuleIdAndName(ruleId, name);
	}
	
	@Override
    public ReturnObjectDTO addRuleTable(RuleTable ruleTable){
		ReturnObjectDTO result = new ReturnObjectDTO();
		result.setCode(ResultCodeContants.SUCCESS);
		result.setMessage("创建规则表成功");
		if (StringUtils.isNullOrEmpty(ruleTable.getTableName()) || ruleTable.getRuleId() == null
				|| ruleTable.getRuleId() <= 0) {
			logger.error("规则表名为空");
			result.setCode(ResultCodeContants.FAIL);
			result.setMessage("必要信息不全");
		} else {
			String tableName = ruleTable.getTableName();
			ruleTable.setTableName(tableName.trim()); // 去除空格
			ruleTable.setTableStatus(RuleContants.TABLE_STATUS_EDIT);
			List<RuleTable> oldTableList = this.queryRuleTableByName(ruleTable.getRuleId(), ruleTable.getTableName());
			if (oldTableList != null && oldTableList.size() > 0) {
				result.setCode(ResultCodeContants.FAIL);
				result.setMessage("该规则表名重复");
			} else {
				ruleTable.setCreatedTime(new Date());
				ruleTableMapper.insertEdit(ruleTable);
				result.setResult(ruleTable);				
			}

		}
		return result;
	}

	@Override
	public RuleTable getRuleTable(Long tableId,String status) {
		RuleTable ruleTable=null;
		if(RuleContants.TABLE_STATUS_PUBLISH.equals(status)){					
			ruleTable=ruleTableMapper.selectByPrimaryKey(tableId);
		}else if(RuleContants.TABLE_STATUS_EDIT.equals(status)){					
			ruleTable=ruleTableMapper.selectRuleTableEditByPrimaryKey(tableId);
		}
		return ruleTable;
	}
	
	@Override
    public List<RuleRow> queryRuleRowByTableId(Long tableId){
		return ruleRowMapper.selectRuleRowByTableId(tableId);	
	}
	
	/**
	 * 编辑规则条件
	 * @param userId
	 * @param tableId
	 * @param conditions
	 * @param results
	 */
	@Override
    @Transactional
	public void addConditions(Long userId, Long tableId,String[] conditions, String[] results){
		RuleTable ruleTableEdit = this.getRuleTable(tableId, RuleContants.TABLE_STATUS_EDIT);		
		boolean isNewTableEdit=false;					
		//编辑已发布规则逻辑
		List<RuleRowEdit> rowEditList = new LinkedList<RuleRowEdit>();
		if(ruleTableEdit == null){
			RuleTable ruleTable =this.getRuleTable(tableId, RuleContants.TABLE_STATUS_PUBLISH);
			ruleTableEdit =ruleTable;						
			List<RuleRow> rowList =this.queryRuleRowByTableId(tableId);		
			for(RuleRow ruleRow:rowList){				
				RuleRowEdit rowEdit = new RuleRowEdit(ruleRow);
				rowEditList.add(rowEdit);
			}
			
			isNewTableEdit=true;
		}
		
		String conditionStr = RuleEditHelper.orgConditionString(conditions);
		String resultStr = RuleEditHelper.orgResultString(results);
		ruleTableEdit.setRuleCondition(conditionStr);
		ruleTableEdit.setResultScript(resultStr);
		ruleTableEdit.setTableStatus(RuleContants.TABLE_STATUS_EDIT);
		if(isNewTableEdit){
			ruleTableMapper.insertEdit(ruleTableEdit);
		}else{
			ruleTableMapper.updateEditByPrimaryKey(ruleTableEdit);
		}
				
		List<RuleRowEdit> rowEditListOld=ruleRowEditMapper.selectByTableId(tableId)	;	
		Map<Long,RuleRowEdit> rowEditListOldMap=new HashMap<Long,RuleRowEdit>();
		if(rowEditListOld!=null && rowEditListOld.size()>0){
			for(RuleRowEdit edit:rowEditListOld){
				rowEditListOldMap.put(edit.getRowId(), edit);
			}
		}
		
		for(RuleRowEdit edit:rowEditList){
			RuleRowEdit editOld=rowEditListOldMap.get(edit.getRowId());
			if(editOld!=null){
				ruleRowEditMapper.updateByPrimaryKeyWithBLOBs(edit);
			}else{
				ruleRowEditMapper.insert(edit);
			}
		}
	}
	
	/**
	 * 删除正在编辑的规则表
	 * @param tableId
	 */
	@Override
    @Transactional
	public void deleteEditRule(Long tableId){
		ruleRowEditMapper.deleteByTableId(tableId);
		ruleTableMapper.deleteEditByPrimaryKey(tableId);					
	}

	/**
	 * 删除已经发布的规则表 
	 * @param tableId
	 */
	@Override
    @Transactional
	public void deletePublishedRule(final Long tableId , final Long userId){

		RuleTable ruleTable = ruleTableMapper.selectByPrimaryKey(tableId);
		List<RuleTable> tableList = ruleTableMapper.selectRuleTableByRuleId(ruleTable.getRuleId());

		RuleTableHistoric ruleTableHis = new RuleTableHistoric(ruleTable);
		ruleTableHis.setRemoveUser(userId);

		List<RuleRow> rowList = ruleRowMapper.selectRuleRowByTableId(tableId);
		ruleRowMapper.deleteByTableId(tableId);

		for (RuleRow row : rowList) {
			RuleRowHistoric rowHis = new RuleRowHistoric(row);
			ruleRowHistoricMapper.insert(rowHis);

		}
		ruleTableMapper.deleteByPrimaryKey(tableId);
		ruleTableHistoricMapper.insert(ruleTableHis);

		RuleDef ruleDef = ruleDefMapper.selectByPrimaryKey(ruleTable.getRuleId());
		// 删除的table缓存在其他发布的规则table
		if (tableList != null) {
			ruleDef.setDeployTime(new Date());
			ruleDef.setDeployUserId(String.valueOf(userId));

			ruleDef.setRuleVersion(getVersion(ruleDef.getRuleVersion(), ruleDef.getDeployTime()));

			String script = RuleRuntimeHelper.getDrl(ruleDef);
			ruleDef.setScript(script);
			RuleRuntimeHelper.cacheRuleBase(ruleDef.getRuleId(), script);
			ruleDefMapper.updateByPrimaryKey(ruleDef);

			// 挪到历史表，备份一份，用于历史查询
			HisRuleDef hisRuleDef = new HisRuleDef(ruleDef);
			hisRuleDefMapper.insert(hisRuleDef);
		} 
																	
	}
	
	/**
	 * 
	 * 描述：格式化条件
	 * @param tableId
	 * @return
	 *
	 */
	@Override
    public List<RuleRowViewerDTO> formatRuleConditions(Long tableId){
		RuleTable tableEdit = this.getRuleTable(tableId, RuleContants.TABLE_STATUS_EDIT);
		List<IRuleRow> ruleRows =new ArrayList<IRuleRow>();
		
		if(tableEdit != null){
			List<RuleRowEdit> ruleRowEditList=ruleRowEditMapper.selectByTableId(tableId);
			if(ruleRowEditList!=null){
				for(RuleRowEdit ruleRowEdit:ruleRowEditList){
					IRuleRow iRuleRow=(IRuleRow)ruleRowEdit;
					ruleRows.add(iRuleRow);
				}
			}
			
		}else{
			List<RuleRow> ruleRowList=ruleRowMapper.selectRuleRowByTableId(tableId);
			if(ruleRowList!=null){
				for(RuleRow ruleRow:ruleRowList){
					IRuleRow iRuleRow=(IRuleRow)ruleRow;
					ruleRows.add(iRuleRow);
				}
			}
		}		
		return RuleEditHelper.formatRuleConditionFromEdit(ruleRows);
	}

	private static String getVersion(String version , Date deployTime){
		SimpleDateFormat sdf = new SimpleDateFormat("yy.MM.dd");
		int i = 1;
		if(!StringUtils.isNullOrEmpty(version)){
			String index = version.substring(1, 4);
			i = Integer.valueOf(index) + 1;
		}
		String date = sdf.format(deployTime.getTime());
		String s = String.valueOf(i);
		while(s.length() < 3){
			s = "0" + s;
		}
		return "V" + s + "." + date;
		
	}
	
	@Override
    @Transactional
	public void addTableDetail(final RuleTable ruleTable,List<RuleFieldEntry> fieldList,
			Map<String,String[]> valuesMap, Map<String,String[]> operatersMap, Map<String,String[]> resultMap){
		
		Map<String,RuleFieldEntry> fieldMap = new HashMap<String, RuleFieldEntry>();
		for(Iterator<RuleFieldEntry> it=fieldList.iterator();it.hasNext();){
			RuleFieldEntry fieldEntry = it.next();
			fieldMap.put(fieldEntry.getId(), fieldEntry);
		}
		
		final List<RuleRowEdit> ruleRowList = RuleEditHelper.convertRuleRow(ruleTable.getTableId(), fieldMap, valuesMap, operatersMap, resultMap);
		
		RuleTable ruleTableEditOld=this.getRuleTable(ruleTable.getTableId(), RuleContants.TABLE_STATUS_EDIT);
		if(ruleTableEditOld==null){
			ruleTableMapper.insertEdit(ruleTable);
		}else{
			ruleTableMapper.updateEditByPrimaryKey(ruleTable);
		}
		
		
		ruleRowEditMapper.deleteByTableId(ruleTable.getTableId());
		for(RuleRowEdit ruleRowEdit:ruleRowList){
			ruleRowEditMapper.insert(ruleRowEdit);
		}
						
	}
	
	@Override
    @Transactional
	public void deploy(final Long tableId, final Long userId) {

		RuleTable tableEdit = this.getRuleTable(tableId, RuleContants.TABLE_STATUS_EDIT);
		RuleTable table = this.getRuleTable(tableId, RuleContants.TABLE_STATUS_PUBLISH);

		// 删除旧的规则
		if (table != null) {
			List<RuleRow> rowList = this.queryRuleRowByTableId(tableId);
			RuleTableHistoric tableHistoric = new RuleTableHistoric(table);
			tableHistoric.setRemoveUser(userId);
			ruleTableHistoricMapper.insert(tableHistoric);
			for (RuleRow ruleRow : rowList) {

				RuleRowHistoric rowHis = new RuleRowHistoric(ruleRow);
				ruleRowHistoricMapper.insert(rowHis);
			}
			ruleRowMapper.deleteByTableId(tableId);
			ruleTableMapper.deleteByPrimaryKey(tableId);
		}

		List<RuleRowEdit> rowEditList = ruleRowEditMapper.selectByTableId(tableId);

		// 转入rule_table
		table = tableEdit;
		table.setTableStatus(RuleContants.TABLE_STATUS_PUBLISH);
		table.setDeployTime(new Date());
		ruleTableMapper.insert(table);

		for (RuleRowEdit rowEdit : rowEditList) {
			RuleRow row = new RuleRow(rowEdit);
			ruleRowMapper.insert(row);
		}
		ruleRowEditMapper.deleteByTableId(tableId);

		ruleTableMapper.deleteEditByPrimaryKey(tableId);

		RuleDef ruleDef = ruleDefMapper.selectByPrimaryKey(table.getRuleId());
		ruleDef.setDeployTime(new Date());
		ruleDef.setDeployUserId(String.valueOf(userId));
		ruleDef.setRuleVersion(getVersion(ruleDef.getRuleVersion(), ruleDef.getDeployTime()));
		String script = RuleRuntimeHelper.getDrl(ruleDef);
		ruleDef.setScript(script);
		RuleRuntimeHelper.cacheRuleBase(ruleDef.getRuleId(), script);
		ruleDefMapper.updateByPrimaryKey(ruleDef);

		// 挪到历史表，备份一份，用于历史查询
		HisRuleDef hisRuleDef = new HisRuleDef(ruleDef);
		hisRuleDefMapper.insert(hisRuleDef);

	}
	
	/**
	 * 
	 * 查询规则日志
	 * @param log
	 * @param pageObjectDTO
	 * @return
	 */
	@Override
    public PageReturnDTO<RuleLogDTO> queryRuleLog(RuleLogCriteria log, PageObjectDTO pageObjectDTO ){
		PageReturnDTO<RuleLogDTO> pageResult = new PageReturnDTO<RuleLogDTO>();

		if (log != null) {
			int count = ruleLogMapper.countRuleLogSelective(log);
			if (count == 0) {
				pageResult.setRecordsTotal(0);
				pageResult.setData(new ArrayList<RuleLogDTO>());
			} else {
				
				//设置分页信息
				int start = 0;
				int size = 10;
				if (pageObjectDTO != null) {
					if (!StringUtils.isNullOrEmpty(pageObjectDTO.getStart())) {
						start = Integer.parseInt(pageObjectDTO.getStart());
					}

					if (!StringUtils.isNullOrEmpty(pageObjectDTO.getLength())) {
						size = Integer.parseInt(pageObjectDTO.getLength());
					}
				}
				log.setLength(size);
				log.setStart(start);				
				pageResult.setRecordsTotal(count);
				
				
				List<RuleLog> logList = ruleLogMapper.selectRuleLogSelective(log);
				
				//设置日志信息
				List<RuleLogDTO> logDTOList=new ArrayList<RuleLogDTO>();
				Set<Long> ruleIdSet = new HashSet<Long>();		
				for(RuleLog ruleLog:logList){
					ruleIdSet.add(ruleLog.getRuleId());					
				}
				List<Long> ruleIdList=new ArrayList<Long>(ruleIdSet);
				
				//查询历史信息
				List<HisRuleDef> ruleHisList=this.queryHisRuleDefByRuleIdList(ruleIdList);		
				Map<String,HisRuleDef> ruleHisMap=new HashMap<String,HisRuleDef>();
				if(ruleHisList!=null && ruleHisList.size()>0){
					for(HisRuleDef hisRule:ruleHisList){
						ruleHisMap.put(hisRule.getRuleId()+"_"+hisRule.getRuleVersion(), hisRule);
					}
				}
								
				for(RuleLog ruleLog:logList){
					RuleLogDTO logDTO=new RuleLogDTO();
					logDTO.setCustIdNo(ruleLog.getCustIdNo());
					logDTO.setCustName(ruleLog.getCustName());
					logDTO.setLogId(ruleLog.getLogId());
					logDTO.setRuleId(ruleLog.getRuleId());
					logDTO.setRuleRow(ruleLog.getRuleRow());
					logDTO.setRuleVersion(ruleLog.getRuleVersion());
					logDTO.setCreatedTime(ruleLog.getCreatedTime());
					HisRuleDef ruleDef=ruleHisMap.get(ruleLog.getRuleId()+"_"+ruleLog.getRuleVersion());
					
					if (ruleDef != null) {
						logDTO.setRuleName(ruleDef.getRuleName());
						logDTO.setRuleDesc(ruleDef.getRuleDesc());
						// 设置踩中条件和结果
						String ruleName = ruleDef.getRuleName();
						Map<String, RuleLogViewerDTO> conditionMap = formatRuleLogViwerDTO(ruleName, ruleDef.getScript());
						RuleLogViewerDTO conditionDesc = conditionMap.get(String.valueOf(ruleLog.getRuleRow()));
						if (conditionDesc != null) {
							logDTO.setCondition(conditionDesc.getCondition());
							logDTO.setResult(conditionDesc.getResult());
						}
					}
					logDTOList.add(logDTO);
				}
				pageResult.setData(logDTOList);

			}
			pageResult.setDraw(Integer.parseInt(pageObjectDTO.getDraw()));

		}
		return pageResult;
	}

	@Override
    public List<HisRuleDef> queryHisRuleDefByRuleIdList(List<Long> ruleIdList){
		List<HisRuleDef> ruleDefList=new ArrayList<HisRuleDef>();
		if(ruleIdList!=null && ruleIdList.size()>0){
			ruleDefList=hisRuleDefMapper.selectByRuleIdList(ruleIdList);
		}
		
		return ruleDefList;
	}

	@Override
	public RuleDef queryRuleByName(String ruleName) {
		if(!StringUtils.isNullOrEmpty(ruleName)){
			List<RuleDef> ruleList=ruleDefMapper.selectByName(ruleName);
			if(ruleList!=null && ruleList.size()>0){
				RuleDef rule=ruleList.get(0);
				return rule;
			}
		}
		return null;
	}

	@Override
	public void addRuleLogList(List<RuleLog> logList) {
		if(logList!=null && logList.size()>0){
			ruleLogMapper.batchInsert(logList);
		}
		
	}

	/**
	 * 
	 * 重载方法
	 * @param ruleName
	 * @param ruleScript
	 * @return  key为规则行数
	 */
	@Override
    public Map<String, RuleLogViewerDTO> formatRuleLogViwerDTO(String ruleName, String ruleScript) {
		Map<String , RuleLogViewerDTO> map = new HashMap<String, RuleLogViewerDTO>();
		Map<String, RuleBeanEntry> tempFieldMap=RuleConfigHelper.getRuleBeanEntities();
		Map<String, RuleResultEntry> ruleResultEntitiesMap = RuleConfigHelper.getRuleResultEntities();
		RuleBeanEntry ruleBeanEntry=tempFieldMap.get(ruleName);
		
		String ruleBeanObjecClasstName=ruleBeanEntry.getCls();
		String result=ruleBeanEntry.getResult();
		
		String ruleBeanObjectName=ruleBeanObjecClasstName.substring(ruleBeanObjecClasstName.lastIndexOf(".")+1);
		
		
		if(!StringUtils.isNullOrEmpty(ruleScript)){
		String [] scriptSplitArr = ruleScript.split("[\n|\t]");		
		int iBeg = -1;
		String key = "";	 
		String condition = "" ;
		RuleLogViewerDTO viewer = new RuleLogViewerDTO();
		for(String scriptRow : scriptSplitArr){
			if("salience".equals(scriptRow.split(" ")[0])){
				iBeg = 0;
				key = scriptRow.split(" ")[1];
				viewer = new RuleLogViewerDTO();
				continue;
			}
			if("when".equals(scriptRow)){
				iBeg = 1;
				continue;
			}else if("then".equals(scriptRow)){
				iBeg = 2;
				continue;
			}else if("end".equals(scriptRow)){
				viewer.getResult().remove(viewer.getResult().size() - 1);	//把最后的两个结果集删掉
				viewer.getResult().remove(viewer.getResult().size() - 1);	//把最后的两个结果集删掉
				map.put(key, viewer);
				iBeg = -1;
			}
			if(StringUtils.isNullOrEmpty(scriptRow)){
				continue;
			}
			if(iBeg == 1){ //解析条件
				condition = scriptRow.replace(ruleName+":"+ruleBeanObjectName+"(", "");					
					for(Iterator<RuleFieldEntry> itt = ruleBeanEntry.getFieldsMap().values().iterator() ; itt.hasNext() ;){
						RuleFieldEntry field = itt.next();
						String attr = field.getAttr();
						String showName = field.getShowName();
						if(condition.indexOf(attr) != -1){
							condition = condition.replace(attr, showName);
						}
					}
					if (!StringUtils.isNullOrEmpty(condition)) {
						viewer.addCondition(condition.substring(0, condition.length() - 1));
					}
			}
			if(iBeg == 2){ //解析结果
				String resultStr=scriptRow.replace(ruleName+".", "").replaceAll("[\"|;]", "");
				RuleResultEntry ruleResultEntry=ruleResultEntitiesMap.get(result);
				if(ruleResultEntry!=null){
					resultStr=resultStr.replace("setResult", ruleResultEntry.getShowName()+"=");
				}
				viewer.addResult(resultStr);    
			}
		}
		}
		return map;
	}
}
