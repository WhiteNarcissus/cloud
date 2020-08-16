//package rule.service;
//
//import com.gzcb.creditcard.gykdh.rule.ruleBean.*;
//
///***
// * 权限提交规则
// * @author  songshuai
// */
//public interface SubmitPrivilegeService {
//
//	/**
//	 * 执行止付与止付开通规则
//	 * @param stopPayRuleBean
//	 * @return
//	 */
//	 String executeStopPayRule(StopPayRuleBean stopPayRuleBean, String taskId, String custIdNo, String customerName);
//
//	/**
//	 * 执行调额规则
//	 * @param changeQuotaRuleBean
//	 * @return
//	 */
//	String executeChangeQuotaRule(ChangeQuotaRuleBean changeQuotaRuleBean, String taskId, String custIdNo, String customerName);
//
//	/***
//	 *执行债务重组规则
//	 * @param debtAgainRuleBean
//	 * @return
//	 */
//	String executeDebtAgainRule(DebtAgainRuleBean debtAgainRuleBean, String taskId, String custIdNo, String customerName);
//
//	/***
//	 *执行账单分期规则
//	 * @param billInstallRuleBean
//	 * @return
//	 */
//	String executeBillInstallRule(BillInstallRuleBean billInstallRuleBean, String taskId, String custIdNo, String customerName);
//
//
//	/***
//	 *催收措施权限规则
//	 * @param collectMethodRuleBean
//	 * @return
//	 */
//	String executeCollectMethodRule(CollectMethodRuleBean collectMethodRuleBean, String taskId, String custIdNo, String customerName);
//	/***
//	 *提前还款权限规则
//	 * @param prePayRuleBean
//	 * @return
//	 */
//	String executePrePayRule(PrePayRuleBean prePayRuleBean, String taskId, String custIdNo, String customerName);
//	/***
//	 *还款后止付/降额 权限规则
//	 * @param repaidFlagRuleBean
//	 * @return
//	 */
//	String executeRepaidFlagRule(RepaidFlagRuleBean repaidFlagRuleBean, String taskId, String custIdNo, String customerName);
//	/***
//	 *贷后措施 权限规则
//	 * @param postLoanMeasureRuleBean
//	 * @return
//	 */
//	String executePostLoanMeasureRule(PostLoanMeasureRuleBean postLoanMeasureRuleBean, String taskId, String custIdNo, String customerName);
//	/***
//	 *担保人变更 权限规则
//	 * @param guarChangeRuleBean
//	 * @return
//	 */
//	String executeGuarChangeRule(GuarChangeRuleBean guarChangeRuleBean, String taskId, String custIdNo, String customerName);
//
//}
