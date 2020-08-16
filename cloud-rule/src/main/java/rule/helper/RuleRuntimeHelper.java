package rule.helper;



import common.LoanContants;
import common.utils.StringUtils;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.core.common.DroolsObjectInputStream;
import org.drools.core.common.DroolsObjectOutputStream;
import org.drools.core.definitions.impl.KnowledgePackageImpl;
import org.drools.definition.KnowledgePackage;
import org.drools.impl.adapters.KnowledgePackageAdapter;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import rule.dao.RuleDefMapper;
import rule.dao.RuleRowMapper;
import rule.dao.RuleTableMapper;
import rule.dto.config.RuleBeanEntry;
import rule.dto.config.RuleResultEntry;
import rule.dto.viewer.RuleRowFieldViewerDTO;
import rule.dto.viewer.RuleRowResultViewerDTO;
import rule.dto.viewer.RuleRowViewerDTO;
import rule.entity.IRuleRow;
import rule.entity.RuleDef;
import rule.entity.RuleRow;
import rule.entity.RuleTable;

import java.io.*;
import java.util.*;

public class RuleRuntimeHelper {
	private static Logger logger=LoggerFactory.getLogger(RuleRuntimeHelper.class);

	@Autowired
	private static RuleTableMapper ruleTableMapper ;//=(RuleTableMapper)SpringBeanLoader.getBean("ruleTableMapper");
	@Autowired
	private static RuleRowMapper ruleRowMapper; //=(RuleRowMapper)SpringBeanLoader.getBean("ruleRowMapper");
	@Autowired
	private  static RuleDefMapper ruleDefMapper; //=(RuleDefMapper)SpringBeanLoader.getBean("ruleDefMapper");






	public static void execute(Long ruleId,List<Object> entityBeans){
		 KnowledgeBase knowledgeBase= KnowledgeBaseFactory.newKnowledgeBase();
		 Collection<KnowledgePackage> knowledgePackage = getRuleBase(ruleId);
		if(knowledgePackage != null){
			knowledgeBase.addKnowledgePackages(knowledgePackage);
			StatefulKnowledgeSession kSession=knowledgeBase.newStatefulKnowledgeSession();
			for (Iterator<Object> iter = entityBeans.iterator(); iter.hasNext();) {
				Object obj = iter.next();
				kSession.insert(obj);
			}
			try{
				kSession.fireAllRules();

			}finally{
				kSession.dispose();
			}
		}
	}

	public static void execute(Long ruleId,Object entityBean){
		List<Object> entityBeans=new ArrayList<Object>();
		entityBeans.add(entityBean);
		execute(ruleId,entityBeans);
	}

	@SuppressWarnings("unchecked")
	private static Collection<KnowledgePackage> getRuleBase(final Long ruleId){

//		List<String> list = (List<String>) JedisUtil.get(LoanContants.RULE_CHACHE_NAME, ruleId.toString());
		List<String> list = null ;
		if (list == null) {
			KnowledgeBuilder knoledgeBuilder= KnowledgeBuilderFactory.newKnowledgeBuilder();
			RuleDef ruleDef = ruleDefMapper.selectByPrimaryKey(ruleId);
			if (ruleDef == null) {
				logger.error("Rule[" + ruleId + "] not found!");
				throw new RuntimeException("Rule[" + ruleId + "] not found!");
			}

			String script = ruleDef.getScript();
			if (script != null) {
				Reader reader = new StringReader(ruleDef.getScript());
				Resource ruleResource = ResourceFactory.newReaderResource(reader);
				knoledgeBuilder.add(ruleResource, ResourceType.DRL);
				if (knoledgeBuilder.hasErrors()) {
					logger.error("编译规则失败");
					throw new RuntimeException("knoledgeBuilder ruleId:" + ruleId + " ruleName:"
							+ ruleDef.getRuleName());
				}
				Collection<KnowledgePackage> knowledgePackage = knoledgeBuilder.getKnowledgePackages();

				addRuleToCache(ruleId,knowledgePackage);

				return knowledgePackage;
			}else{
				logger.error("Rule[{}] script not found!",ruleId);
				throw new RuntimeException("Rule[" + ruleId + "] script not found!");
			}
		} else {

			return getRuleFormCache(ruleId,list);

		}
	}

	public static void cacheRuleBase(final Long ruleId, final String ruleScript){
		KnowledgeBuilder knoledgeBuilder= KnowledgeBuilderFactory.newKnowledgeBuilder();
		if (ruleScript != null) {
			Reader reader = new StringReader(ruleScript);
			Resource ruleResource = ResourceFactory.newReaderResource(reader);
			Collection<KnowledgePackage> knowledgePackage = null;
			knoledgeBuilder.add(ruleResource, ResourceType.DRL);
			if (knoledgeBuilder.hasErrors()) {
				KnowledgeBuilderErrors erros = knoledgeBuilder.getErrors();

				logger.error("编译规则失败 {}" , erros.toString());
				throw new RuntimeException("knoledgeBuilder ruleId:" + ruleId);
			}
			knowledgePackage = knoledgeBuilder.getKnowledgePackages();

			addRuleToCache(ruleId,knowledgePackage);

		}else{
			logger.error("Rule[{}] script not found!",ruleId);
//			JedisUtil.set(LoanContants.RULE_CHACHE_NAME, ruleId.toString());
		}
	}

	private static void addRuleToCache(Long ruleId,Collection<KnowledgePackage> knowledgePackage) {

		if(knowledgePackage != null && knowledgePackage.size() > 0){
			List<String> list = new ArrayList<>();
			knowledgePackage.forEach(k->{

				KnowledgePackageAdapter knowledgePackageAdapter = (KnowledgePackageAdapter) k;
				KnowledgePackageImpl knowledgePackageImp = (KnowledgePackageImpl)knowledgePackageAdapter.getDelegate();

				ByteArrayOutputStream bos = null;
				DroolsObjectOutputStream dops = null;
				try {
					bos = new ByteArrayOutputStream();
					dops = new DroolsObjectOutputStream(bos);
					knowledgePackageImp.writeExternal(dops);
					byte[] bytes = bos.toByteArray();

					Base64.Encoder encoder = Base64.getEncoder();
					String byteStr = encoder.encodeToString(bytes);
					list.add(byteStr);
				} catch (Exception e) {
					logger.error("将规则[{}]写入缓存异常",ruleId,e);
				} finally {
					if(bos != null){
						try {
							bos.close();
						} catch (Exception e) {
							//ignore
						}
					}

					if(dops != null){
						try {
							dops.close();
						} catch (Exception e) {
							//ignore
						}
					}

				}
			});

//			JedisUtil.set(LoanContants.RULE_CHACHE_NAME, ruleId.toString(), list);
		}

	}

	private static Collection<KnowledgePackage> getRuleFormCache(Long ruleId,List<String> list){
		if(list == null || list.size() == 0){
			return null;
		}
		List<KnowledgePackage> resultList = new ArrayList<>();
		for (String str : list) {
			ByteArrayInputStream bis = null;
			DroolsObjectInputStream dois = null;
			try{
				Base64.Decoder decoder = Base64.getDecoder();
				byte[] bytes = decoder.decode(str);
				bis = new ByteArrayInputStream(bytes);
				dois = new DroolsObjectInputStream(bis);

				KnowledgePackageImpl knowledgePackageImp = new KnowledgePackageImpl();
				knowledgePackageImp.readExternal(dois);

				KnowledgePackage knowledgePackageAdapter = new KnowledgePackageAdapter(knowledgePackageImp);

				resultList.add(knowledgePackageAdapter);
			} catch (Exception e){
				logger.error("在缓存中获取规则[{}]异常",ruleId,e);
			} finally {
				if(bis != null){
					try {
						bis.close();
					} catch (Exception e) {
						//ignore
					}
				}

				if(dois != null){
					try {
						dois.close();
					} catch (IOException e) {
						//ignore
					}
				}

			}

		}

		return resultList;
	}

	public static String getDrl(RuleDef ruleDef){
		Map<String, RuleBeanEntry> ruleBeanMap = RuleConfigHelper.getRuleBeanEntities();
		Map<String, RuleResultEntry> resultMap = RuleConfigHelper.getRuleResultEntities();
		
		StringBuffer sb = new StringBuffer();
		sb.append("package _" + ruleDef.getRuleId() + ";\n");
		//后发布的规则优先级更高，所以需要放到前面入堆栈
		RuleTable ruleTable = new RuleTable();
		ruleTable.setRuleId(ruleDef.getRuleId());
		List<RuleTable> ruleTableList=ruleTableMapper.select(ruleTable);
		
		
		StringBuffer sbCondition = new StringBuffer();
		int i=0,index = 10000;
		
		Map<String,String> classMap = new HashMap<String, String>();
		
		for(RuleTable table:ruleTableList){
			i++;
			RuleRow rr = new RuleRow();
			rr.setTableId(table.getTableId());

			List<RuleRow> ruleRows = ruleRowMapper.select(rr);
			List<IRuleRow> iRuleRows=new ArrayList<IRuleRow>();
			for(RuleRow ruleRow:ruleRows){
				IRuleRow iruleRow=ruleRow;
				iRuleRows.add(iruleRow);
			}
			
			List<RuleRowViewerDTO> rowViewerList = RuleEditHelper.formatRuleConditionFromEdit(iRuleRows);
			int j=0;
			for(Iterator<?> it2=rowViewerList.iterator();it2.hasNext();){
				j++;
				index --;
				RuleRowViewerDTO row = (RuleRowViewerDTO)it2.next();
				
				sbCondition.append("rule \"TB_"+i+"_TR_"+j +"\"\n");
				sbCondition.append("\tsalience "+index+"\n"); //序号从大到小
				sbCondition.append("\twhen\n");
				
				//将规则字段按类分开，key:beanName, value：字段列表
				Map<String,List<RuleRowFieldViewerDTO>> fieldMap = new HashMap<String, List<RuleRowFieldViewerDTO>>();
				
				for(Iterator<RuleRowFieldViewerDTO> itc = row.getRowConditions().values().iterator();itc.hasNext();){
					RuleRowFieldViewerDTO field = itc.next();
					String[] name = field.getAttributeName().split("\\.");
					
					List<RuleRowFieldViewerDTO> fieldList = fieldMap.get(name[0]);
					if(fieldList == null){
						fieldList = new LinkedList<RuleRowFieldViewerDTO>();
						fieldMap.put(name[0], fieldList);
					}
					fieldList.add(field);
				}
				String resultBean = null;
				for(Iterator<String> itor=fieldMap.keySet().iterator();itor.hasNext();){
					String beanName = itor.next();
					resultBean =beanName;
					RuleBeanEntry ruleBean = ruleBeanMap.get(beanName);
					String className = ruleBean.getCls().substring(ruleBean.getCls().lastIndexOf(".")+1);
					classMap.put(ruleBean.getName(), ruleBean.getCls());
					
					String express = getExpress(fieldMap.get(beanName));
					if(StringUtils.isNullOrEmpty(express)) {
                        continue;
                    }
					sbCondition.append("\t\t"+beanName+":"+className+"(" +express + ")\n");
				}

				sbCondition.append("\tthen\n");
				for(Iterator<RuleRowResultViewerDTO> itr = row.getRowResults().values().iterator(); itr.hasNext();){
					RuleRowResultViewerDTO result = itr.next();
					
					RuleResultEntry resultEntry = resultMap.get(result.getResultKey());
					
					String script = resultEntry.getScript();
					sbCondition.append("\t\t"+script.replaceAll("@param", result.getValue())+"\n");
				}
				sbCondition.append("\t\t"+resultBean+".setBigoRow(" + index + ");\n");
				sbCondition.append("\t\t"+resultBean+".setVersion(\"" + ruleDef.getRuleVersion() + "\");\n");
				sbCondition.append("\tend\n");
				
			}
		}
	
		for(Iterator<String> it=classMap.values().iterator();it.hasNext();){
			sb.append("import "+it.next() + ";\n");
		}
		sb.append(sbCondition);
		return sb.toString();
	}
	
	private static String getExpress(List<RuleRowFieldViewerDTO> fieldList){

		StringBuffer sb = new StringBuffer();
		for(Iterator<RuleRowFieldViewerDTO> itc = fieldList.iterator();itc.hasNext();){
			RuleRowFieldViewerDTO field = itc.next();
			String[] name = field.getAttributeName().split("\\.");
			
			String val = field.getValue();
			if(StringUtils.isNullOrEmpty(val)){
				continue;
			}
			
			String ope=field.getOperator();
			if("between".equals(ope)){
				String [] valArr = val.replace("，", ",").split(",");
				if(valArr.length==2){
					
					sb.append("&&" + getCondition(name[1],">",field.getDataType(),valArr[0]));
					sb.append("&&" + getCondition(name[1],"<=",field.getDataType(),valArr[1]));
				}
								
			}else{				
				sb.append("&&" + getCondition(name[1],field.getOperator(),field.getDataType(),val));
			}
		}
		String express = sb.toString();
		if(express.length() > 2) {
            express = express.substring(2);
        }
		return express;
	}
	
	private static String getCondition(String field, String ope,String type, String val){
		String condition = null;
		type = type.toUpperCase();
		if("in".equals(ope) || "not in".equals(ope)){
			String []v = val.replace("，", ",").split(",");
			String value = "";
			if("STRING".equals(type)){
				for(String s : v){
					value += ("\"" +  s + "\",");
				}
			}else{
				for(String s : v){
					value += (s + ",");
				}
			}
			
			val = "(" + value.substring(0, value.length() - 1) + ")";
		}else{
			if("STRING".equals(type)){
				val = "\"" + val + "\"";
			}
		}
		condition = field + " " + ope + " " + val;
		
		return condition;
	}
}
