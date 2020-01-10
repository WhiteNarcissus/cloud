package rule.service.impl;


import com.gzcb.creditcard.gykdh.dao.rule.PaichaRuleFieldMapper;
import com.gzcb.creditcard.gykdh.dao.rule.PaichaRuleResultMapper;
import com.gzcb.creditcard.gykdh.rule.RuleContants;
import com.gzcb.creditcard.gykdh.rule.entity.*;
import com.gzcb.creditcard.gykdh.rule.helper.RuleRuntimeHelper;
import com.gzcb.creditcard.gykdh.rule.ruleBean.PaichaRuleBean;
import com.gzcb.creditcard.gykdh.rule.service.PaichaRuleExecuteService;
import com.gzcb.creditcard.gykdh.rule.service.RuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 
 * 项目：gykdh1.10
 * 版    权: Copyright(C) 2011-2020,  gzcb. All rights reserved
 * 类名：PaichaRuleExecuteServiceImpl
 * 描述：排查规则执行服务
 * 创建人：liting    创建日期：2017年5月19日   版本：
 * 历史
 * <修改人>   <修改日期 >   <修改内容>
 */
@Service("paichaRuleExecuteService")
public class PaichaRuleExecuteServiceImpl implements PaichaRuleExecuteService {
	
	private static Logger logger=LoggerFactory.getLogger(PaichaRuleExecuteServiceImpl.class);
	
	@Autowired
	private PaichaRuleFieldMapper paichaRuleFieldMapper;
	
	@Autowired
	private PaichaRuleResultMapper paichaRuleResultMapper;
	
	@Autowired
	private RuleService ruleService;
	
	@Override
    @Transactional
	public void execute() {			
		long startTime = System.currentTimeMillis();				
		logger.info("排查规则开始执行");
				
		List<PaichaRuleField> ruleFieldList = paichaRuleFieldMapper.selectByAll();
		if (ruleFieldList != null && ruleFieldList.size() > 0) {
			RuleDef rule = ruleService.queryRuleByName(RuleContants.PAICHA_RULE_NAME);
			if (rule != null) {
				
				int size = ruleFieldList.size();
				int unitNum = 100;
				int startIndex = 0;
				int endIndex = 0;
				while (size > 0) {
					if (size > unitNum) {
						endIndex = startIndex + unitNum;
					} else {
						endIndex = startIndex + size;
					}
					List<PaichaRuleField> subList = ruleFieldList.subList(startIndex, endIndex);
					executeRule(subList, rule);

					size = size - unitNum;
					startIndex = endIndex;
				}
			}else{
				logger.error("规则不存在");
			}
		}

		long endTime = System.currentTimeMillis();
		logger.info("排查规则结束执行   sumTime:{}" , (endTime - startTime));
	}

	private void executeRule(List<PaichaRuleField> ruleList,RuleDef rule){		
		List<String> custIdList=new ArrayList<String>();
		List<PaichaRuleResult> addRuleResultList=new ArrayList<PaichaRuleResult>();
		List<PaichaRuleResult> updateRuleResultList=new ArrayList<PaichaRuleResult>();
		List<Object> ruleBeanList=new ArrayList<Object>();
		List<RuleLog> ruleLogList=new ArrayList<RuleLog>();
				
		for(PaichaRuleField field:ruleList){
			custIdList.add(field.getCustIdNo());			
			//创建规则Bean
			PaichaRuleBean paichaRuleBean=new PaichaRuleBean();
			BeanUtils.copyProperties(field, paichaRuleBean);
			ruleBeanList.add(paichaRuleBean);
		}
		
		//执行规则
		RuleRuntimeHelper.execute(rule.getRuleId(), ruleBeanList);	
		
		
		List<PaichaRuleResult> ruleResultList=paichaRuleResultMapper.selectByCustIdList(custIdList);
		Map<String,PaichaRuleResult> ruleResultMap=new HashMap<String,PaichaRuleResult>();		
		if(ruleResultList!=null && ruleResultList.size()>0){
			for(PaichaRuleResult result:ruleResultList){
				ruleResultMap.put(result.getCustIdNo(),result);				
			}
		}
		
		for(Object ruleBean:ruleBeanList){
			PaichaRuleBean paichaRuleBean=(PaichaRuleBean)ruleBean;
			String custIdNo=paichaRuleBean.getCustIdNo();
			RuleLog ruleLog = new RuleLog();
			ruleLog.setCreatedTime(new Date());
			ruleLog.setCustIdNo(custIdNo);
			ruleLog.setCustName(paichaRuleBean.getCustName());
			ruleLog.setRuleId(rule.getRuleId());
			ruleLog.setRuleRow(paichaRuleBean.getBigoRow());
			if(paichaRuleBean.getVersion()!=null){
				ruleLog.setRuleVersion( paichaRuleBean.getVersion());
			}else{
				ruleLog.setRuleVersion(rule.getRuleVersion());
			}
			ruleLog.setRuleName(RuleContants.PAICHA_RULE_NAME);
			ruleLogList.add(ruleLog);
			
			 
			PaichaRuleResult ruleResult=ruleResultMap.get(custIdNo)	;
			if(ruleResult==null){
				ruleResult=new PaichaRuleResult();
				ruleResult.setCreatedTime(new Date());
				ruleResult.setCustIdNo(custIdNo);
				ruleResult.setCustName(paichaRuleBean.getCustName());
				
				if(paichaRuleBean.getResult()==null){
					ruleResult.setRuleResult("N");
				}else{
					ruleResult.setRuleResult(String.valueOf(paichaRuleBean.getResult()));
				}
				ruleResult.setUpdatedTime(new Date());
				addRuleResultList.add(ruleResult);
				
			}else{
				ruleResult.setCustIdNo(custIdNo);
				ruleResult.setCustName(paichaRuleBean.getCustName());
				if(paichaRuleBean.getResult()==null){
					ruleResult.setRuleResult("N");
				}else{
					ruleResult.setRuleResult(String.valueOf(paichaRuleBean.getResult()));
				}
				ruleResult.setUpdatedTime(new Date());
				updateRuleResultList.add(ruleResult);
			}			
		}
		
		if(updateRuleResultList!=null && updateRuleResultList.size()>0){
			paichaRuleResultMapper.batchUpdate(updateRuleResultList);
		}
		
		if(addRuleResultList!=null && addRuleResultList.size()>0){
			paichaRuleResultMapper.batchInsert(addRuleResultList);
		}
		if(ruleLogList.size()>0){
			ruleService.addRuleLogList(ruleLogList);
		}
		
	}

	@Override
	public List<PaichaRuleResult> queryRuleResultList(PaichaRuleResultCriteria paichaRuleResultCriteria) {	
		if(paichaRuleResultCriteria!=null){
			return paichaRuleResultMapper.selectBySelective(paichaRuleResultCriteria);
		}
		return null;
	}

	@Override
	public int countRuleResultList(PaichaRuleResultCriteria paichaRuleResultCriteria) {	
		if(paichaRuleResultCriteria!=null){
			return paichaRuleResultMapper.countBySelective(paichaRuleResultCriteria);
		}		
		return 0;
	}
	
	
}
