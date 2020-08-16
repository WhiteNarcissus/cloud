//package rule.service.impl;
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import rule.service.CreditEvaluateRuleExeService;
//
//import java.math.BigDecimal;
//import java.util.*;
//
//@Service("creditEvaluateRuleExeService")
//public class CreditEvaluateRuleExeServiceImpl implements CreditEvaluateRuleExeService {
//	private static Logger logger=LoggerFactory.getLogger(CreditEvaluateRuleExeServiceImpl.class);
//
//	private static final String GUAR_MATE="mates";  //配偶
//	private static final String GUAR_GUAR="guar";  //担保人
//
//	private static final String GUAR_RULE_RESULT="guar";	//
//	private static final String MARKET_RULE_RESULT="market";	//细分市场
//	private static final String PAY_RULE_RESULT="pay";	//还款意愿
//	private static final String PAYAB_RULE_RESULT="payAb";  //还款能力
//
//
//	@Autowired
//	private GuarInfoService guarInfoService;
//
//	@Autowired
//	private GykAccountService gykAccountService;
//
//	@Autowired
//	private CreditAndDividedService creditAndDividedService;
//
//	@Autowired
//	private InternalService internalService;
//
//	@Autowired
//	private ExternalCreditReportService externalCreditReportService;
//
//	@Autowired
//	private CustInstallService custInstallService;
//
//
//	@Autowired
//	private RuleService ruleService;
//
//	@Autowired
//	private CustService custService;
//
//
//
//	@Override
//	public Map<String,CreditEvaluateRuleResultDTO> execute(String custIdNo) {
//		Map<String,CreditEvaluateRuleResultDTO> result = new HashMap<String,CreditEvaluateRuleResultDTO>();
//		List<RuleLog> ruleLogList=new ArrayList<RuleLog>();
//
//		if (!StringUtils.isNullOrEmpty(custIdNo)) {
//			CoreCustomer cust=custService.getCustInfoByCustId(custIdNo);
//
//			List<GuarInfo> guarList = guarInfoService.getCustGuarList(custIdNo); // 所有担保人
//			Map<String, List<String>> guarGroupMap = filterGuarGroup(guarList);
//
//			List<String> custIdList = new ArrayList<String>();
//			custIdList.add(custIdNo);
//			if (guarList != null && guarList.size() > 0) {
//				for (GuarInfo guar : guarList) {
//					custIdList.add(guar.getGuarIdNo());
//				}
//			}
//
//			List<String> mateGuarList = guarGroupMap.get(GUAR_MATE);
//			List<String> guarInfoList = guarGroupMap.get(GUAR_GUAR);
//			Map<String, CreditEvaluateRuleDTO> ruleDTOMap = fillRuleDTOValue(custIdList);
//
//			//保证人
//			CreditEvaluateRuleBean guarRuleBean=fillRuleValue(ruleDTOMap,custIdNo,mateGuarList,guarInfoList);
//
//			//细分市场
//			CreditEvaluateRuleBean marketRuleBean=fillRuleValue(ruleDTOMap,custIdNo,mateGuarList,guarInfoList);
//
//			//还款意愿
//			CreditEvaluateRuleBean payRuleBean=fillRuleValue(ruleDTOMap,custIdNo,mateGuarList,guarInfoList);
//
//			//还款能力
//			CreditEvaluateRuleBean payAbRuleBean=fillRuleValue(ruleDTOMap,custIdNo,mateGuarList,guarInfoList);
//
//
//			RuleDef ruleGuar=ruleService.queryRuleByName(RuleContants.CREDITEVALUATE_GUAR_RULE_NAME);
//			RuleDef rulePay=ruleService.queryRuleByName(RuleContants.CREDITEVALUATE_PAY_RULE_NAME);
//			RuleDef rulePayAb=ruleService.queryRuleByName(RuleContants.CREDITEVALUATE_PAYAB_RULE_NAME);
//			RuleDef ruleMarket=ruleService.queryRuleByName(RuleContants.CREDITEVALUATE_MARKET_RULE_NAME);
//
//			//保证人执行规则
//			if(ruleGuar!=null){
//				try {
//					RuleRuntimeHelper.execute(ruleGuar.getRuleId(), guarRuleBean);
//					CreditEvaluateRuleResultDTO guarResult = new CreditEvaluateRuleResultDTO();
//					guarResult.setRuleId(ruleGuar.getRuleId());
//					guarResult.setRuleName(ruleGuar.getRuleName());
//					guarResult.setBigoRow(guarRuleBean.getBigoRow());
//					guarResult.setVersion(ruleGuar.getRuleVersion());
//					Object ruleExeResult = guarRuleBean.getResult();
//					guarResult.setResult(ruleExeResult == null ? null : String.valueOf(ruleExeResult));
//
//					Map<String, RuleLogViewerDTO> ruleRowMap = ruleService.formatRuleLogViwerDTO(ruleGuar.getRuleName(), ruleGuar.getScript());
//					RuleLogViewerDTO ruleRowResult = ruleRowMap.get(String.valueOf(guarRuleBean.getBigoRow()));
//					if (ruleRowResult != null) {
//						guarResult.setMapRuleRow(ruleRowResult.getCondition());
//					}
//
//					result.put(GUAR_RULE_RESULT, guarResult);
//
//					RuleLog ruleLog = new RuleLog();
//					ruleLog.setCreatedTime(new Date());
//					ruleLog.setCustIdNo(custIdNo);
//					ruleLog.setCustName(cust.getCustName());
//					ruleLog.setRuleId(ruleGuar.getRuleId());
//					ruleLog.setRuleRow(guarRuleBean.getBigoRow());
//					ruleLog.setRuleVersion(ruleGuar.getRuleVersion());
//					ruleLog.setRuleName(ruleGuar.getRuleName());
//					ruleLogList.add(ruleLog);
//				} catch (Exception e) {
//					logger.error("执行保证人执行规则报错", e);
//				}
//			}
//
//
//			//还款意愿执行规则
//			if(rulePay!=null){
//				try {
//				RuleRuntimeHelper.execute(rulePay.getRuleId(), payRuleBean);
//				CreditEvaluateRuleResultDTO payResult=new CreditEvaluateRuleResultDTO();
//				payResult.setRuleId(rulePay.getRuleId());
//				payResult.setRuleName(rulePay.getRuleName());
//				payResult.setBigoRow(payRuleBean.getBigoRow());
//				payResult.setVersion(payRuleBean.getVersion());
//				Object ruleExeResult=payRuleBean.getResult();
//				payResult.setResult(ruleExeResult==null?null:String.valueOf(ruleExeResult));
//
//				Map<String,RuleLogViewerDTO> ruleRowMap=ruleService.formatRuleLogViwerDTO(payResult.getRuleName(),rulePay.getScript());
//				RuleLogViewerDTO ruleRowResult = ruleRowMap.get(String.valueOf(payRuleBean.getBigoRow()));
//				if (ruleRowResult != null) {
//					payResult.setMapRuleRow(ruleRowResult.getCondition());
//				}
//
//				result.put(PAY_RULE_RESULT, payResult);
//
//				RuleLog ruleLog = new RuleLog();
//				ruleLog.setCreatedTime(new Date());
//				ruleLog.setCustIdNo(custIdNo);
//				ruleLog.setCustName(cust.getCustName());
//				ruleLog.setRuleId(rulePay.getRuleId());
//				ruleLog.setRuleRow(payRuleBean.getBigoRow());
//				ruleLog.setRuleVersion(rulePay.getRuleVersion());
//				ruleLog.setRuleName(rulePay.getRuleName());
//				ruleLogList.add(ruleLog);
//				} catch (Exception e) {
//					logger.error("执行还款意愿执行规则报错", e);
//				}
//			}
//
//			//还款能力执行规则
//			if(rulePayAb!=null){
//				try {
//					RuleRuntimeHelper.execute(rulePayAb.getRuleId(), payAbRuleBean);
//					CreditEvaluateRuleResultDTO payAbResult = new CreditEvaluateRuleResultDTO();
//					payAbResult.setRuleId(rulePayAb.getRuleId());
//					payAbResult.setRuleName(rulePayAb.getRuleName());
//					payAbResult.setBigoRow(payAbRuleBean.getBigoRow());
//					payAbResult.setVersion(payAbRuleBean.getVersion());
//					Object ruleExeResult = payAbRuleBean.getResult();
//					payAbResult.setResult(ruleExeResult == null ? null : String.valueOf(ruleExeResult));
//					Map<String, RuleLogViewerDTO> ruleRowMap = ruleService.formatRuleLogViwerDTO(payAbResult.getRuleName(), rulePayAb.getScript());
//					RuleLogViewerDTO ruleRowResult = ruleRowMap.get(String.valueOf(payAbRuleBean.getBigoRow()));
//					if (ruleRowResult != null) {
//						payAbResult.setMapRuleRow(ruleRowResult.getCondition());
//					}
//
//					result.put(PAYAB_RULE_RESULT, payAbResult);
//
//					RuleLog ruleLog = new RuleLog();
//					ruleLog.setCreatedTime(new Date());
//					ruleLog.setCustIdNo(custIdNo);
//					ruleLog.setCustName(cust.getCustName());
//					ruleLog.setRuleId(rulePayAb.getRuleId());
//					ruleLog.setRuleRow(payAbRuleBean.getBigoRow());
//					ruleLog.setRuleVersion(rulePayAb.getRuleVersion());
//					ruleLog.setRuleName(rulePayAb.getRuleName());
//					ruleLogList.add(ruleLog);
//				} catch (Exception e) {
//					logger.error("执行还款能力执行报错", e);
//				}
//			}
//
//			//细分市场能力执行规则
//			if(ruleMarket!=null){
//				try {
//				RuleRuntimeHelper.execute(ruleMarket.getRuleId(), marketRuleBean);
//				CreditEvaluateRuleResultDTO marketResult=new CreditEvaluateRuleResultDTO();
//				marketResult.setRuleId(ruleMarket.getRuleId());
//				marketResult.setRuleName(ruleMarket.getRuleName());
//				marketResult.setBigoRow(marketRuleBean.getBigoRow());
//				marketResult.setVersion(marketRuleBean.getVersion());
//				Object ruleExeResult=marketRuleBean.getResult();
//				marketResult.setResult(ruleExeResult==null?null:String.valueOf(ruleExeResult));
//				Map<String,RuleLogViewerDTO> ruleRowMap=ruleService.formatRuleLogViwerDTO(marketResult.getRuleName(),ruleMarket.getScript());
//				RuleLogViewerDTO ruleRowResult = ruleRowMap.get(String.valueOf(marketRuleBean.getBigoRow()));
//				if (ruleRowResult != null) {
//					marketResult.setMapRuleRow(ruleRowResult.getCondition());
//				}
//
//				result.put(MARKET_RULE_RESULT, marketResult);
//
//				RuleLog ruleLog = new RuleLog();
//				ruleLog.setCreatedTime(new Date());
//				ruleLog.setCustIdNo(custIdNo);
//				ruleLog.setCustName(cust.getCustName());
//				ruleLog.setRuleId(ruleMarket.getRuleId());
//				ruleLog.setRuleRow(marketRuleBean.getBigoRow());
//				ruleLog.setRuleVersion( ruleMarket.getRuleVersion());
//				ruleLog.setRuleName(ruleMarket.getRuleName());
//				ruleLogList.add(ruleLog);
//			} catch (Exception e) {
//				logger.error("执行细分市场能力执行规则报错", e);
//			}
//			}
//
//			if(ruleLogList!=null && ruleLogList.size()>0){
//				ruleService.addRuleLogList(ruleLogList);
//			}
//		} else {
//			logger.error("执行信用评估规则失败，证件号为空");
//		}
//
//		return result;
//	}
//
//	private CreditEvaluateRuleBean fillRuleValue(Map<String, CreditEvaluateRuleDTO> ruleDTOMap,String custIdNo,List<String> mateGuarList,List<String> guarInfoList){
//		CreditEvaluateRuleBean ruleBean=new CreditEvaluateRuleBean();
//		CreditEvaluateRuleDTO custRuleDTO = ruleDTOMap.get(custIdNo);
//
//		// 家庭广赢卡当前逾期期数  MAX【客户的，配偶的】
//		int homeGykOverDueNum = 0;
//		homeGykOverDueNum = custRuleDTO.getGykOverDueNum()==null?0:custRuleDTO.getGykOverDueNum();
//
//		//家庭信用卡当前逾期期数 MAX【客户的，配偶的】
//		int homeCreditOverDueNum=0;
//		homeCreditOverDueNum = custRuleDTO.getCreditOverDueNum()==null?0:custRuleDTO.getCreditOverDueNum();
//
//		//家庭随心分当前逾期期数  MAX【客户的，配偶的】
//		int homeDivideOverDueNum=0;
//		homeDivideOverDueNum = custRuleDTO.getDivideOverDueNum()==null?0:custRuleDTO.getDivideOverDueNum();
//
//		//家庭个贷当前逾期期数MAX【客户的，配偶的】
//		int homeLoanOverDueNum=0;
//		homeLoanOverDueNum = custRuleDTO.getLoanOverDueNum()==null?0:custRuleDTO.getLoanOverDueNum();
//
//		//家庭人行当前逾期期数 MAX【客户的，配偶的】
//		int homeRHOverDueNum=0;
//		homeRHOverDueNum = custRuleDTO.getRhOverDueNum()==null?0:custRuleDTO.getRhOverDueNum();
//
//		//家庭人行近半年逾期总次数 ∑（客户的，配偶的）
//		int homeRH6MonthOverDueNum=0;
//		homeRH6MonthOverDueNum=custRuleDTO.getRh6MonthOverDueNum()==null?0:custRuleDTO.getRh6MonthOverDueNum();
//
//		//家庭保证人代偿笔数∑（客户的，配偶的）
//		int homeGuarPayNum=0;
//		homeGuarPayNum=custRuleDTO.getGuarPayNum()==null?0:custRuleDTO.getGuarPayNum();
//
//		//家庭资产处置笔数∑（客户的，配偶的）
//		int homeAssetAccessNum=0;
//		homeAssetAccessNum=custRuleDTO.getAssetAccessNum()==null?0:custRuleDTO.getAssetAccessNum();
//
//		//家庭法院执行笔数∑（客户的，配偶的）
//		int homeCourtAccessNum=0;
//		homeCourtAccessNum=custRuleDTO.getCourtAccessNum()==null?0:custRuleDTO.getCourtAccessNum();
//
//		//家庭是否有行政处罚
//		String homeHasAdmPenalty="否";
//		homeHasAdmPenalty=custRuleDTO.getHasAdmPenalty();
//		if(StringUtils.isNullOrEmpty(homeHasAdmPenalty)){
//			homeHasAdmPenalty="否";
//		}
//
//		//家庭人行当前逾期账户数∑（客户的，配偶的）
//		int homeRHOverDueAccountNum=0;
//		homeRHOverDueAccountNum=custRuleDTO.getRhOverDueAccountNum()==null?0:custRuleDTO.getRhOverDueAccountNum();
//
//		//家庭信用负债余额 信用负债余额=未结束大额分期余额+人行信用负债余额；∑（客户的，配偶的）
//		BigDecimal homeCreditDebtBalance=new BigDecimal(0);
//		BigDecimal creditDebtBalance=custRuleDTO.getCreditDebtBalance();
//		BigDecimal bigCloseBalance=custRuleDTO.getNotCloseBigBalance();
//		if(creditDebtBalance!=null){
//			homeCreditDebtBalance=homeCreditDebtBalance.add(creditDebtBalance);
//		}
//		if(bigCloseBalance!=null){
//			homeCreditDebtBalance=homeCreditDebtBalance.add(bigCloseBalance);
//		}
//
//		//家庭卡片透支余额∑（客户的，配偶的）
//		BigDecimal homeOverdrawBalance=new BigDecimal(0.0);
//		BigDecimal overdrawBalance=custRuleDTO.getOverdrawBalance();
//		if(overdrawBalance!=null){
//			homeOverdrawBalance=homeOverdrawBalance.add(overdrawBalance);
//		}
//
//		//家庭近半年最高延滞期数MAX【客户的，配偶的】
//		int home6mthMaxArrearangePeriod=0;
//		home6mthMaxArrearangePeriod=custRuleDTO.getHalfYearMaxArrearangePeriod()==null?0:custRuleDTO.getHalfYearMaxArrearangePeriod();
//
//		//家庭房产状态是否有二押
//		String homeHouseHasTwoMortgage="否";
//		homeHouseHasTwoMortgage=custRuleDTO.getHouseHasTwoMortgage();
//		if(StringUtils.isNullOrEmpty(homeHouseHasTwoMortgage)){
//			homeHouseHasTwoMortgage="否";
//		}
//
//		//家庭房产状态是否均为不一致
//		String homeHouseStaAllDiffer="否";
//		homeHouseStaAllDiffer=custRuleDTO.getHouseStaAllDiffer();
//		if(StringUtils.isNullOrEmpty(homeHouseStaAllDiffer)){
//			homeHouseStaAllDiffer="否";
//		}
//
//		//家庭房产是否有查封
//		String homeHouseHasClose="否";
//		homeHouseHasClose=custRuleDTO.getHouseHasClose();
//		if(StringUtils.isNullOrEmpty(homeHouseHasClose)){
//			homeHouseHasClose="否";
//		}
//
//		//家庭人行发卡机构数MAX【客户的，配偶的】
//		int homeRHCardIssuerNum=0;
//		homeRHCardIssuerNum=custRuleDTO.getRhCardIssuerNum()==null?0:custRuleDTO.getRhCardIssuerNum();
//
//		//家庭人行最高近半年查询次数MAX【客户的，配偶的】
//		int homeRHMaxQueryTime6Month=0;
//		homeRHMaxQueryTime6Month=custRuleDTO.getRhQueryTime6Month()==null?0:custRuleDTO.getRhQueryTime6Month();
//
//		//家庭人行最高近一年查询次数MAX【客户的，配偶的】
//		int homeRHMaxQueryTimeYear=0;
//		homeRHMaxQueryTimeYear=custRuleDTO.getRhQueryTimeYear()==null?0:custRuleDTO.getRhQueryTimeYear();
//
//
//		//家庭人行近半年查询次数总和∑（客户的，配偶的）
//		int homeRHQueryTime6Month=0;
//		homeRHQueryTime6Month=custRuleDTO.getRhQueryTime6Month()==null?0:custRuleDTO.getRhQueryTime6Month();
//
//		//家庭人行近一年查询次数总和∑（客户的，配偶的）
//		int homeRHQueryTimeYear=0;
//		homeRHQueryTimeYear=custRuleDTO.getRhQueryTimeYear()==null?0:custRuleDTO.getRhQueryTimeYear();
//
//		//客户广赢卡近3个月日均透支率
//		BigDecimal cust3mthOverdraftRate=custRuleDTO.getGyk3mthOverdraftRate();
//
//		//配偶广赢卡近3个月日均透支率
//		BigDecimal spouse3mthOverdraftRate=new BigDecimal(0);
//
//		//客户广赢卡近6个月日均透支率
//		BigDecimal cust6mthOverdraftRate=custRuleDTO.getGyk6mthOverdraftRate();
//
//		//配偶广赢卡近6个月日均透支率
//		BigDecimal spouse6mthOverdraftRate=new BigDecimal(0);
//
//		///客户广赢卡近一年日均透支率
//		BigDecimal custYearOverdraftRate=custRuleDTO.getGykYearOverdraftRate();
//
//		//配偶广赢卡近一年日均透支率
//		BigDecimal spouseYearOverdraftRate=new BigDecimal(0);
//
//		//家庭最高近6个月小贷或P2P次数 MAX【客户的，配偶的】
//		int homeMax6MthLoanAndP2pNum=0;
//		homeMax6MthLoanAndP2pNum=custRuleDTO.getSixMthLoanAndP2pNum()==null?0:custRuleDTO.getSixMthLoanAndP2pNum();
//
//		//家庭最高近12个月小贷或P2P次数 【客户的，配偶的】
//		int homeMaxYearLoanAndP2pNum=0;
//		homeMaxYearLoanAndP2pNum=custRuleDTO.getYearLoanAndP2pNum()==null?0:custRuleDTO.getYearLoanAndP2pNum();
//
//		//家庭同盾是否黑/灰名单
//		String homeHasBlackName="否";
//		homeHasBlackName=custRuleDTO.getHasBlackName();
//		if(StringUtils.isNullOrEmpty(homeHasBlackName)){
//			homeHasBlackName="否";
//		}
//
//		//家庭最高个贷违约比率 个贷违约比率=累计违约次数/已结清期数；MAX【客户的，配偶的】
//		BigDecimal homeMaxLoanBreakRate=new BigDecimal(0);
//		homeMaxLoanBreakRate=custRuleDTO.getLoanBreakRate();
//
//		if (mateGuarList != null) {
//			for (String custIdNoTemp : mateGuarList) {
//				CreditEvaluateRuleDTO mateRuleDTO = ruleDTOMap.get(custIdNoTemp);
//				if(mateRuleDTO!=null){
//				if(mateRuleDTO.getGykOverDueNum()!=null && homeGykOverDueNum < mateRuleDTO.getGykOverDueNum()){
//					homeGykOverDueNum=mateRuleDTO.getGykOverDueNum();
//				}
//
//				if(mateRuleDTO.getCreditOverDueNum()!=null && homeCreditOverDueNum < mateRuleDTO.getCreditOverDueNum()){
//					homeCreditOverDueNum=mateRuleDTO.getCreditOverDueNum();
//				}
//
//				if(mateRuleDTO.getDivideOverDueNum()!=null && homeDivideOverDueNum < mateRuleDTO.getDivideOverDueNum()){
//					homeDivideOverDueNum=mateRuleDTO.getDivideOverDueNum();
//				}
//
//				if(mateRuleDTO.getLoanOverDueNum()!=null && homeLoanOverDueNum < mateRuleDTO.getLoanOverDueNum()){
//					homeLoanOverDueNum=mateRuleDTO.getLoanOverDueNum();
//				}
//
//				if(mateRuleDTO.getRhOverDueNum()!=null && homeRHOverDueNum < mateRuleDTO.getRhOverDueNum()){
//					homeRHOverDueNum=mateRuleDTO.getRhOverDueNum();
//				}
//
//				int tempRh6MonthOverDueNum=mateRuleDTO.getRh6MonthOverDueNum()==null?0:mateRuleDTO.getRh6MonthOverDueNum();
//				homeRH6MonthOverDueNum=homeRH6MonthOverDueNum+tempRh6MonthOverDueNum;
//
//				int tempGuarPayNum=mateRuleDTO.getGuarPayNum()==null ?0:mateRuleDTO.getGuarPayNum();
//				homeGuarPayNum=homeGuarPayNum+tempGuarPayNum;
//
//				int tempAssetAccessNum=mateRuleDTO.getAssetAccessNum()==null?0:mateRuleDTO.getAssetAccessNum();
//				homeAssetAccessNum=homeAssetAccessNum+tempAssetAccessNum;
//
//				int tempCourtAccessNum=mateRuleDTO.getCourtAccessNum()==null?0:mateRuleDTO.getCourtAccessNum();
//				homeCourtAccessNum=homeCourtAccessNum+tempCourtAccessNum;
//
//				String tempHasAdmPenalty=mateRuleDTO.getHasAdmPenalty();
//				if("是".equals(tempHasAdmPenalty) && "否".equals(homeHasAdmPenalty)){
//					homeHasAdmPenalty="是";
//				}
//
//				int tempRhOverDueAccountNum=mateRuleDTO.getRhOverDueAccountNum()==null?0:mateRuleDTO.getRhOverDueAccountNum();
//				homeRHOverDueAccountNum=homeRHOverDueAccountNum+tempRhOverDueAccountNum;
//
//				BigDecimal creditDebtBalanceMate=mateRuleDTO.getCreditDebtBalance();
//				BigDecimal bigCloseBalanceMate=mateRuleDTO.getNotCloseBigBalance();
//				if(creditDebtBalanceMate!=null){
//					homeCreditDebtBalance=homeCreditDebtBalance.add(creditDebtBalanceMate);
//				}
//				if(bigCloseBalanceMate!=null){
//					homeCreditDebtBalance=homeCreditDebtBalance.add(bigCloseBalanceMate);
//				}
//
//				BigDecimal overdrawBalanceMate=mateRuleDTO.getOverdrawBalance();
//				if(overdrawBalanceMate!=null){
//					homeOverdrawBalance=homeOverdrawBalance.add(overdrawBalanceMate);
//				}
//
//				if(mateRuleDTO.getHalfYearMaxArrearangePeriod()!=null && home6mthMaxArrearangePeriod< mateRuleDTO.getHalfYearMaxArrearangePeriod()){
//					home6mthMaxArrearangePeriod=mateRuleDTO.getHalfYearMaxArrearangePeriod();
//				}
//
//
//				String tempHouseHasTwoMortgage=mateRuleDTO.getHouseHasTwoMortgage();
//				if("是".equals(tempHouseHasTwoMortgage) && "否".equals(homeHouseHasTwoMortgage)){
//					homeHouseHasTwoMortgage="是";
//				}
//
//				String tempHouseStaAllDiffer=mateRuleDTO.getHouseStaAllDiffer();
//				if("是".equals(tempHouseStaAllDiffer) && "否".equals(homeHouseStaAllDiffer)){
//					homeHouseStaAllDiffer="是";
//				}
//
//				String tempHouseHasClose=mateRuleDTO.getHouseHasClose();
//				if("是".equals(tempHouseHasClose) && "否".equals(homeHouseHasClose)){
//					homeHouseHasClose="是";
//				}
//
//				if(mateRuleDTO.getRhCardIssuerNum()!=null && homeRHCardIssuerNum<mateRuleDTO.getRhCardIssuerNum()){
//					homeRHCardIssuerNum=mateRuleDTO.getRhCardIssuerNum();
//				}
//
//				if(mateRuleDTO.getRhQueryTime6Month()!=null && homeRHMaxQueryTime6Month<mateRuleDTO.getRhQueryTime6Month()){
//					homeRHMaxQueryTime6Month=mateRuleDTO.getRhQueryTime6Month();
//				}
//
//				if(mateRuleDTO.getRhQueryTimeYear()!=null && homeRHMaxQueryTimeYear<mateRuleDTO.getRhQueryTimeYear()){
//					homeRHMaxQueryTimeYear=mateRuleDTO.getRhQueryTimeYear();
//				}
//
//				int tempRhQueryTime6Month=mateRuleDTO.getRhQueryTime6Month()==null?0:mateRuleDTO.getRhQueryTime6Month();
//				homeRHQueryTime6Month=homeRHQueryTime6Month+tempRhQueryTime6Month;
//
//				int tempRhQueryTimeYear=mateRuleDTO.getRhQueryTimeYear()==null?0:mateRuleDTO.getRhQueryTimeYear();
//				homeRHQueryTimeYear=homeRHQueryTimeYear+tempRhQueryTimeYear;
//
//
//				BigDecimal tempSpouse3mthOverdraftRate=mateRuleDTO.getGyk3mthOverdraftRate();
//				if(tempSpouse3mthOverdraftRate!=null){
//					spouse3mthOverdraftRate=spouse3mthOverdraftRate.max(tempSpouse3mthOverdraftRate);
//				}
//
//				BigDecimal  tempSpouse6mthOverdraftRate=mateRuleDTO.getGyk6mthOverdraftRate();
//				if(tempSpouse6mthOverdraftRate!=null){
//					spouse6mthOverdraftRate=spouse6mthOverdraftRate.max(tempSpouse6mthOverdraftRate);
//				}
//
//				BigDecimal tempSpouseYearOverdraftRate=mateRuleDTO.getGykYearOverdraftRate();
//				if(tempSpouse6mthOverdraftRate!=null){
//					spouseYearOverdraftRate=spouseYearOverdraftRate.max(tempSpouseYearOverdraftRate);
//				}
//
//				if(mateRuleDTO.getSixMthLoanAndP2pNum()!=null && homeMax6MthLoanAndP2pNum<mateRuleDTO.getSixMthLoanAndP2pNum()){
//					homeMax6MthLoanAndP2pNum=mateRuleDTO.getSixMthLoanAndP2pNum();
//				}
//
//				if(mateRuleDTO.getYearLoanAndP2pNum()!=null && homeMaxYearLoanAndP2pNum<mateRuleDTO.getYearLoanAndP2pNum()){
//					homeMaxYearLoanAndP2pNum=mateRuleDTO.getYearLoanAndP2pNum();
//				}
//
//
//				String tempHomeHasBlackName=mateRuleDTO.getHasBlackName();
//				if("黑名单".equals(tempHomeHasBlackName) && ("否".equals(homeHasBlackName) || "灰名单".equals(homeHasBlackName))){
//					homeHasBlackName="黑名单";
//				}else if("灰名单".equals(tempHomeHasBlackName) && "否".equals(homeHasBlackName)){
//					homeHasBlackName="灰名单";
//				}
//
//				BigDecimal loanBreakRateTemp=mateRuleDTO.getLoanBreakRate();
//				if(loanBreakRateTemp!=null &&homeMaxLoanBreakRate!=null ){
//					homeMaxLoanBreakRate=homeMaxLoanBreakRate.max(loanBreakRateTemp);
//				}
//				}
//			}
//		}
//
//		//担保人广赢卡当前逾期期数 MAX【非配偶的担保人的】
//		int guarGykOverDueNum=0;
//
//		//担保人信用卡当前逾期期数MAX【非配偶的担保人的】
//		int guarCreditOverDueNum=0;
//
//		//担保人随心分当前逾期期数 MAX【非配偶的担保人的】
//		int guarDivideOverDueNum=0;
//
//		//担保人个贷当前逾期期数 MAX【非配偶的担保人的】
//		int guarLoanOverDueNum=0;
//
//		//担保人人行当前逾期期数 MAX【非配偶的担保人的】
//		int guarRHOverDueNum=0;
//
//		//担保人人行近半年逾期总次数 MAX【非配偶的担保人的】
//		int guarRH6MonthOverDueNum=0;
//
//		//担保人保证人代偿笔数 MAX【非配偶的担保人的】
//		int guarGuarPayNum=0;
//
//		//担保人资产处置笔数 MAX【非配偶的担保人的】
//		int guarAssetAccessNum=0;
//
//		//担保人法院执行笔数  MAX【非配偶的担保人的】
//		int guarCourtAccessNum=0;
//
//		//担保人是否有行政处罚
//		String guarHasAdmPenalty="否";
//
//		//担保人人行当前逾期账户数   MAX【非配偶的担保人的】
//		int guarRHOverDueAccountNum=0;
//		if(guarInfoList!=null){
//			for(String guarIdNo:guarInfoList){
//				CreditEvaluateRuleDTO guarRuleDTO = ruleDTOMap.get(guarIdNo);
//				if(guarRuleDTO!=null){
//					if(guarRuleDTO.getGykOverDueNum()!=null && guarGykOverDueNum< guarRuleDTO.getGykOverDueNum()){
//						guarGykOverDueNum=guarRuleDTO.getGykOverDueNum();
//					}
//
//
//					if(guarRuleDTO.getCreditOverDueNum()!=null && guarCreditOverDueNum<guarRuleDTO.getCreditOverDueNum()){
//						guarCreditOverDueNum=guarRuleDTO.getCreditOverDueNum();
//					}
//
//					if(guarRuleDTO.getDivideOverDueNum()!=null && guarDivideOverDueNum<guarRuleDTO.getDivideOverDueNum()){
//						guarDivideOverDueNum=guarRuleDTO.getDivideOverDueNum();
//					}
//
//					if(guarRuleDTO.getLoanOverDueNum()!=null && guarLoanOverDueNum<guarRuleDTO.getLoanOverDueNum()){
//						guarLoanOverDueNum=guarRuleDTO.getLoanOverDueNum();
//					}
//
//					if(guarRuleDTO.getRhOverDueNum()!=null && guarRHOverDueNum<guarRuleDTO.getRhOverDueNum()){
//						guarRHOverDueNum=guarRuleDTO.getRhOverDueNum();
//					}
//
//					if(guarRuleDTO.getRh6MonthOverDueNum()!=null && guarRH6MonthOverDueNum<guarRuleDTO.getRh6MonthOverDueNum()){
//						guarRH6MonthOverDueNum=guarRuleDTO.getRh6MonthOverDueNum();
//					}
//
//					if(guarRuleDTO.getGuarPayNum()!=null && guarGuarPayNum<guarRuleDTO.getGuarPayNum()){
//						guarGuarPayNum=guarRuleDTO.getGuarPayNum();
//					}
//
//					if(guarRuleDTO.getAssetAccessNum()!=null && guarAssetAccessNum<guarRuleDTO.getAssetAccessNum()){
//						guarAssetAccessNum=guarRuleDTO.getAssetAccessNum();
//					}
//
//					if(guarRuleDTO.getCourtAccessNum()!=null && guarCourtAccessNum<guarRuleDTO.getCourtAccessNum()){
//						guarCourtAccessNum=guarRuleDTO.getCourtAccessNum();
//					}
//
//					if("是".equals(guarRuleDTO.getHasAdmPenalty()) && "否".equals(guarHasAdmPenalty)){
//						guarHasAdmPenalty="是";
//					}
//
//
//					if(guarRuleDTO.getRhOverDueAccountNum()!=null && guarRHOverDueAccountNum<guarRuleDTO.getRhOverDueAccountNum()){
//						guarRHOverDueAccountNum=guarRuleDTO.getRhOverDueAccountNum();
//					}
//				}
//
//			}
//		}
//
//
//		ruleBean.setHomeGykOverDueNum(homeGykOverDueNum);
//		ruleBean.setHomeCreditOverDueNum(homeCreditOverDueNum);
//		ruleBean.setHomeCreditOverDueNum(homeDivideOverDueNum);
//		ruleBean.setHomeCreditOverDueNum(homeLoanOverDueNum);
//		ruleBean.setHomeCreditOverDueNum(homeRHOverDueNum);
//		ruleBean.setHomeRH6MonthOverDueNum(homeRH6MonthOverDueNum);
//		ruleBean.setHomeGuarPayNum(homeGuarPayNum);
//		ruleBean.setHomeAssetAccessNum(homeAssetAccessNum);
//		ruleBean.setHomeCourtAccessNum(homeCourtAccessNum);
//		ruleBean.setHomeHasAdmPenalty(homeHasAdmPenalty);
//		ruleBean.setHomeRHOverDueAccountNum(homeRHOverDueAccountNum);
//		ruleBean.setHomeCreditDebtBalance(homeCreditDebtBalance);
//		ruleBean.setHomeOverdrawBalance(homeOverdrawBalance);
//		ruleBean.setHome6mthMaxArrearangePeriod(home6mthMaxArrearangePeriod);
//		ruleBean.setHomeHouseHasTwoMortgage(homeHouseHasTwoMortgage);
//		ruleBean.setHomeHouseStaAllDiffer(homeHouseStaAllDiffer);
//		ruleBean.setHomeHouseHasClose(homeHouseHasClose);
//		ruleBean.setHomeRHCardIssuerNum(homeRHCardIssuerNum);
//		ruleBean.setHomeRHMaxQueryTime6Month(homeRHMaxQueryTime6Month);
//		ruleBean.setHomeRHMaxQueryTimeYear(homeRHMaxQueryTimeYear);
//		ruleBean.setHomeRHQueryTime6Month(homeRHQueryTime6Month);
//		ruleBean.setHomeRHQueryTimeYear(homeRHQueryTimeYear);
//		ruleBean.setCust3mthOverdraftRate(cust3mthOverdraftRate);
//		ruleBean.setSpouse3mthOverdraftRate(spouse3mthOverdraftRate);
//		ruleBean.setCust6mthOverdraftRate(cust6mthOverdraftRate);
//		ruleBean.setSpouse6mthOverdraftRate(spouse6mthOverdraftRate);
//		ruleBean.setCustYearOverdraftRate(custYearOverdraftRate);
//		ruleBean.setSpouseYearOverdraftRate(spouseYearOverdraftRate);
//		ruleBean.setHomeMax6MthLoanAndP2pNum(homeMax6MthLoanAndP2pNum);
//		ruleBean.setHomeMaxYearLoanAndP2pNum(homeMaxYearLoanAndP2pNum);
//		ruleBean.setHomeHasBlackName(homeHasBlackName);
//		ruleBean.setHomeMaxLoanBreakRate(homeMaxLoanBreakRate);
//		ruleBean.setGuarGykOverDueNum(guarGykOverDueNum);
//		ruleBean.setGuarCreditOverDueNum(guarCreditOverDueNum);
//		ruleBean.setGuarDivideOverDueNum(guarDivideOverDueNum);
//		ruleBean.setGuarLoanOverDueNum(guarLoanOverDueNum);
//		ruleBean.setGuarRHOverDueNum(guarRHOverDueNum);
//		ruleBean.setGuarRH6MonthOverDueNum(guarRH6MonthOverDueNum);
//		ruleBean.setGuarGuarPayNum(guarGuarPayNum);
//		ruleBean.setGuarAssetAccessNum(guarAssetAccessNum);
//		ruleBean.setGuarCourtAccessNum(guarCourtAccessNum);
//		ruleBean.setGuarHasAdmPenalty(guarHasAdmPenalty);
//		ruleBean.setGuarRHOverDueAccountNum(guarRHOverDueAccountNum);
//
//		return ruleBean;
//	}
//
//	/**
//	 *
//	 * 描述：将担保人拆分为配偶和担保人
//	 * @param guarList
//	 * @return
//	 *
//	 */
//	private Map<String,List<String>> filterGuarGroup(List<GuarInfo> guarList){
//		String matesStr = PropertiesUtils.getProperty("MATES_LIST");
//		List<String> mateGuarList=new ArrayList<String>(); //配偶
//		List<String> guarInfoList=new ArrayList<String>() ; //担保人
//
//		for(GuarInfo guar:guarList){
//			String relation=guar.getGuarRelation();
//			if(matesStr!=null && matesStr.indexOf(relation)>=0){
//				mateGuarList.add(guar.getGuarIdNo());
//			}else{
//				guarInfoList.add(guar.getGuarIdNo());
//			}
//		}
//
//		Map<String,List<String>> guarGroupMap=new HashMap<String,List<String>>();
//		guarGroupMap.put(GUAR_MATE, mateGuarList);
//		guarGroupMap.put(GUAR_GUAR, guarInfoList);
//		return guarGroupMap;
//	}
//
//	/**
//	 *
//	 * 描述：填充规则需要的值
//	 * @param custIdList
//	 * @return
//	 *
//	 */
//	private Map<String, CreditEvaluateRuleDTO> fillRuleDTOValue(List<String> custIdList) {
//		Map<String, CreditEvaluateRuleDTO> ruleDTOMap = new HashMap<String, CreditEvaluateRuleDTO>();
//
//		//广赢卡
////		List<GykAccount> gykAccountList = gykAccountService.queryGykAccountList(custIdList);
//		Map<String, GykAccount> gykAccountMap = new HashMap<String, GykAccount>();
//		List<GykAccount> gykAccountList = null;
//		if (gykAccountList != null) {
//			for (GykAccount acount : gykAccountList) {
//				gykAccountMap.put(acount.getCustIdNo(), acount);
//			}
//		}
//
//		// 需要分随心分或者信用卡
//		List<Credit> creditList = creditAndDividedService.selectLastCreditByCustIdList(custIdList);
//		Map<String, Credit> creditCardMap = new HashMap<String, Credit>();
//		Map<String, Credit> dividedMap = new HashMap<String, Credit>();
//		if (creditList != null && creditList.size() > 0) {
//			for (Credit credit : creditList) {
//				if (CreditCoreContants.CREDIT_CARD_TYPE_CODE.equals(credit.getCardType())) {
//					creditCardMap.put(credit.getCardIdNo(), credit);
//				}
//
//				if (CreditCoreContants.DIVIDED_CARD_TYPE_CODE.equals(credit.getCardType())) {
//					dividedMap.put(credit.getCardIdNo(), credit);
//				}
//			}
//		}
//
//		//个贷
//		List<PersonLoan> personLoanList = null;
//				//internalService.getPersonLoanInfos(custIdList);
//		Map<String, List<PersonLoan>> personLoanMap = new HashMap<String, List<PersonLoan>>();
//		if (personLoanList != null) {
//			for (PersonLoan loan : personLoanList) {
//				List<PersonLoan> custPersonLoanList=personLoanMap.get(loan.getCustIdNo());
//				if(custPersonLoanList==null){
//					custPersonLoanList=new ArrayList<PersonLoan>();
//					custPersonLoanList.add(loan);
//					personLoanMap.put(loan.getCustIdNo(), custPersonLoanList);
//				}else{
//					custPersonLoanList.add(loan);
//				}
//			}
//		}
//
//		//人行
//		Map<String, CreditReportHis> creditReportMap = new HashMap<String, CreditReportHis>();
//	/*	List<CreditReportHis> creditReportList = externalCreditReportService.queryCreditReportHisList(custIdList);
//		if (creditReportList != null && creditReportList.size() > 0) {
//			for (CreditReportHis report : creditReportList) {
//				creditReportMap.put(report.getCustIdNo(), report);
//			}
//		}*/
//
//		//同盾
//		List<TongdunReportHis> tongdunReportHisList = externalCreditReportService.queryTongdunReportHisList(custIdList);
//		Map<String, TongdunReportHis> tongdunReportMap = new HashMap<String, TongdunReportHis>();
//		if (tongdunReportHisList != null && tongdunReportHisList.size() > 0) {
//			for (TongdunReportHis report : tongdunReportHisList) {
//				tongdunReportMap.put(report.getCustIdNo(), report);
//			}
//		}
//
//		//大额分期
//		Map<String, CustInstallDTO> installMap=getInstall(custIdList);
//
//
//
//		for (String custIdNo : custIdList) {
//			CreditEvaluateRuleDTO ruleDTO=new CreditEvaluateRuleDTO();
//			ruleDTO.setCustIdNo(custIdNo);
//			GykAccount gykAccount=gykAccountMap.get(custIdNo);
//			Credit creditCard=creditCardMap.get(custIdNo);
//			Credit dividedCard=dividedMap.get(custIdNo);
//			List<PersonLoan> loanList=personLoanMap.get(custIdNo);
//			CreditReportHis creditReport=creditReportMap.get(custIdNo);
//			TongdunReportHis tongdunReport=tongdunReportMap.get(custIdNo);
//			CustInstallDTO install=installMap.get(custIdNo);
//
//
//
//			if(gykAccount!=null){
//				ruleDTO.setGykOverDueNum(gykAccount.getOverdueNum());
//				String last3mthOverdraftRate=gykAccount.getLast3mthOverdraftRate();
//				last3mthOverdraftRate=StringUtils.isNullOrEmpty(last3mthOverdraftRate)?"0":last3mthOverdraftRate;
//				ruleDTO.setGyk3mthOverdraftRate(new BigDecimal(last3mthOverdraftRate));
//
//				String last6mthOverdraftRate=gykAccount.getLast6mthOverdraftRate();
//				last6mthOverdraftRate=StringUtils.isNullOrEmpty(last6mthOverdraftRate)?"0":last6mthOverdraftRate;
//				ruleDTO.setGyk6mthOverdraftRate(new BigDecimal(last6mthOverdraftRate));
//
//				String lastyearOverdraftRate=gykAccount.getLastyearOverdraftRate();
//				lastyearOverdraftRate=StringUtils.isNullOrEmpty(lastyearOverdraftRate)?"0":lastyearOverdraftRate;
//				ruleDTO.setGykYearOverdraftRate(new BigDecimal(lastyearOverdraftRate));
//			}
//
//			if(creditCard!=null){
//				BigDecimal overDueNum=creditCard.getAgeday();
//				ruleDTO.setCreditOverDueNum(overDueNum==null?0:overDueNum.intValue());
//
//			}
//
//			if(dividedCard!=null){
//				BigDecimal overDueNumDivided=dividedCard.getAgeday();
//				ruleDTO.setDivideOverDueNum(overDueNumDivided==null?0:overDueNumDivided.intValue());
//			}
//
//			if(loanList   !=null && loanList.size()>0){
//				//取最大的
//				int maxLoanOverDueNum=0;
//				BigDecimal maxloanBreakRate=new BigDecimal("0");
//				//个贷违约比率=累计违约次数/已结清期数
//				for(PersonLoan loan:loanList){
//					String loanOverDueNum=loan.getOverdueNum();
//					String breakruleNum=loan.getBreakruleNum();
//					String closedPeriod=loan.getClosedPeriod();
//
//					int tempMaxLoanOverDueNum=StringUtils.isNullOrEmpty(loanOverDueNum)?0:Integer.parseInt(loanOverDueNum);
//					if(tempMaxLoanOverDueNum > maxLoanOverDueNum){
//						maxLoanOverDueNum=tempMaxLoanOverDueNum;
//					}
//
//					if(!StringUtils.isNullOrEmpty(closedPeriod) && !StringUtils.isNullOrEmpty(breakruleNum) ){
//						int closedPeriodInt=Integer.parseInt(closedPeriod);
//						int breakruleNumInt=Integer.parseInt(breakruleNum);
//						if(breakruleNumInt!=0){
//							double tempLoanBreakRate=(breakruleNumInt+0.0)/(closedPeriodInt+0.0);
//							maxloanBreakRate=maxloanBreakRate.max(new BigDecimal(tempLoanBreakRate));
//
//						}
//					}
//
//				}
//
//				ruleDTO.setLoanOverDueNum(maxLoanOverDueNum);
//				ruleDTO.setLoanBreakRate(maxloanBreakRate);
//			}
//
////			if(houseInfoList!=null && houseInfoList.size()>0){
////				String hasHouseHasTwoMortgage="否";
////				String houseStaAllDiffer="是";
////				String houseHasClose="否";
////				for(HouseInfo house:houseInfoList){
////					String houseStatus=house.getHouseStatus();
////					String seizedStatus=house.getSeizedStatus();
////					if("二押".equals(houseStatus) && "否".equals(hasHouseHasTwoMortgage)){
////						hasHouseHasTwoMortgage="是";
////					}
////
////					if(("查封".equals(seizedStatus) || "轮封" .equals(seizedStatus))&& "否".equals(houseHasClose)){
////						houseHasClose="是";
////					}
////
////					if(!"不一致".equals(houseStatus)){
////						houseStaAllDiffer="否";
////					}
////				}
////				ruleDTO.setHouseHasClose(houseHasClose); //房产是否有查封
////				ruleDTO.setHouseHasTwoMortgage(hasHouseHasTwoMortgage); //房产状态是否有二押
////				ruleDTO.setHouseStaAllDiffer(houseStaAllDiffer); //所有登记的房产状态是否均为“不一致”
////			}
//
//			if (creditReport != null) {
//				// 设置人行
//				String currentOverduePeriod = creditReport.getCurOverduePeriod();
//				ruleDTO.setRhOverDueNum(StringUtils.isNullOrEmpty(currentOverduePeriod) ? 0 : Integer.parseInt(currentOverduePeriod));
//
//				String lastHalfYearOverdueTime = creditReport.getHalfyearOverdueTimes();
//				ruleDTO.setRh6MonthOverDueNum(StringUtils.isNullOrEmpty(lastHalfYearOverdueTime) ? 0 : Integer.parseInt(lastHalfYearOverdueTime));
//
//				String bondsmanCompensatory = creditReport.getBondsmanCompensatory();
//				ruleDTO.setGuarPayNum(StringUtils.isNullOrEmpty(bondsmanCompensatory) ? 0 : Integer.parseInt(bondsmanCompensatory));
//
//				String assetDisposalCounts = creditReport.getAssetDisposalCounts();
//				ruleDTO.setAssetAccessNum(StringUtils.isNullOrEmpty(assetDisposalCounts) ? 0 : Integer.parseInt(assetDisposalCounts));
//
//				String courtEnforcementCounts = creditReport.getCourtEnforcementCounts();
//				ruleDTO.setCourtAccessNum(StringUtils.isNullOrEmpty(courtEnforcementCounts) ? 0 : Integer.parseInt(courtEnforcementCounts));
//
//				ruleDTO.setHasAdmPenalty(creditReport.getHasAdministrativePenalty());
//
//				String currentOverdueAccountNum = creditReport.getCurOverdueAccountNum();
//				ruleDTO.setRhOverDueAccountNum(StringUtils.isNullOrEmpty(currentOverdueAccountNum) ? 0 : Integer.parseInt(currentOverdueAccountNum));
//
//				String creditDebtRemain = creditReport.getCreditDebtRemain();
//				ruleDTO.setCreditDebtBalance(StringUtils.isNullOrEmpty(creditDebtRemain) ? new BigDecimal("0") : new BigDecimal(creditDebtRemain));
//
//				String cardOverdrawRemain = creditReport.getCardOverdrawRemain();
//				ruleDTO.setOverdrawBalance(StringUtils.isNullOrEmpty(cardOverdrawRemain) ? new BigDecimal("0") : new BigDecimal(cardOverdrawRemain));
//
//				String lastHalfYearMaxArrearangePeriod = creditReport.getHalfyearMaxarrearangePeriod();
//				ruleDTO.setHalfYearMaxArrearangePeriod(StringUtils.isNullOrEmpty(lastHalfYearMaxArrearangePeriod) ? 0 : Integer.parseInt(lastHalfYearMaxArrearangePeriod));
//
//				String cardIssuerCounts = creditReport.getCardIssuerCounts();
//				ruleDTO.setRhCardIssuerNum(StringUtils.isNullOrEmpty(cardIssuerCounts) ? 0 : Integer.parseInt(cardIssuerCounts));
//
//				String lastHalfYearQueryTimes = creditReport.getLastHalfyearQueryTimes();
//				ruleDTO.setRhQueryTime6Month(StringUtils.isNullOrEmpty(lastHalfYearQueryTimes) ? 0 : Integer.parseInt(lastHalfYearQueryTimes));
//
//				String lastYearQueryTimes = creditReport.getLastYearQueryTimes();
//				ruleDTO.setRhQueryTimeYear(StringUtils.isNullOrEmpty(lastYearQueryTimes) ? 0 : Integer.parseInt(lastYearQueryTimes));
//			}
//			// 同盾
//			if (tongdunReport != null) {
//				String last6MonthSmallLoanAndP2PTimes = tongdunReport.getLast6monthSmallLoanandp2pTimes();
//				ruleDTO.setSixMthLoanAndP2pNum(StringUtils.isNullOrEmpty(last6MonthSmallLoanAndP2PTimes) ? 0 : Integer.parseInt(last6MonthSmallLoanAndP2PTimes));
//
//				String last12MonthSmallLoanAndP2PTimes = tongdunReport.getLast12monthSmallLoanandp2pTimes();
//				ruleDTO.setYearLoanAndP2pNum(StringUtils.isNullOrEmpty(last12MonthSmallLoanAndP2PTimes) ? 0 : Integer.parseInt(last12MonthSmallLoanAndP2PTimes));
//
//				//设置是否为黑名单
//				ruleDTO.setHasBlackName(tongdunReport.getBlackGray());
//
//			}
//
//			//未结束大额分期
//			if(install!=null){
//				String notCloseBigBalance = install.getNoteCloseBigBalance();
//				ruleDTO.setNotCloseBigBalance(StringUtils.isNullOrEmpty(notCloseBigBalance) ? new BigDecimal("0") : new BigDecimal(notCloseBigBalance));
//			}
//			ruleDTOMap.put(custIdNo, ruleDTO);
//		}
//
//		return ruleDTOMap;
//	 }
//
//	/**
//	 *
//	 * 描述：大额分期
//	 * @param
//	 * @return
//	 *
//	 */
//	private Map<String,CustInstallDTO> getInstall(List<String> custIdNoList){
//		Map<String, CustInstallDTO> installMap = new HashMap<String, CustInstallDTO>();
//		for (String custIdNo : custIdNoList) {
//			installMap.put(custIdNo, custInstallService.queryInstall(custIdNo));
//		}
//		return installMap;
//	}
//
//
//
//
//
//}