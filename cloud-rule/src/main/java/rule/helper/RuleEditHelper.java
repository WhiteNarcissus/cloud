package rule.helper;


import common.utils.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class RuleEditHelper {

	private static Logger logger = LoggerFactory.getLogger(RuleEditHelper.class);

	/*
	 * 生成规则条件字段数据库字符串
	 */
	public static String orgConditionString(String[] conditions) {
		StringBuffer sb = buildXmlStringBuffer("root");
		for (int i = 0; i < conditions.length; i++) {
			String[] cons = conditions[i].split(",");
			String id = null;
			if (cons.length == 2) {
				id = cons[1];
			} else {
                id = "_" + UUID.randomUUID().toString().replace("-", "");
            }
			sb.append("<con id=\"" + id + "\" s=\"" + i + "\" attr=\"" + cons[0] + "\"/>");
		}
		finishXmlString(sb, "root");
		return sb.toString();
	}

	public static String orgResultString(String[] results) {
		StringBuffer sb = buildXmlStringBuffer("root");
		for (int i = 0; i < results.length; i++) {
			String id = "_" + UUID.randomUUID().toString().replace("-", "");
			sb.append("<result id=\"" + id + "\" s=\"" + i + "\" attr=\"" + results[i] + "\"/>");
		}
		finishXmlString(sb, "root");
		return sb.toString();
	}

	public static List<RuleFieldEntry> formateFieldFromString(String conditionStr) {
		try {
			List<RuleFieldEntry> selectedFieldList = new LinkedList<RuleFieldEntry>();

			if (StringUtils.isNullOrEmpty(conditionStr)) {
                return selectedFieldList;
            }

			Element element = getElement(conditionStr).getRootElement();
			for (Iterator<?> iter = element.elementIterator("con"); iter.hasNext();) {
				Element conEle = (Element) iter.next();
				String attrName = conEle.attributeValue("attr");
				String id = conEle.attributeValue("id");
				Integer sort = new Integer(conEle.attributeValue("s"));

				String[] fieldS = attrName.split("\\.");
				RuleFieldEntry fieldEntry = RuleConfigHelper.getRuleFieldEntry(fieldS[0], fieldS[1]);

				if (fieldEntry != null) {
					RuleFieldEntry ruleFieldSelected = new RuleFieldEntry();
					ruleFieldSelected.setAttr(fieldEntry.getAttr());
					ruleFieldSelected.setDescription(fieldEntry.getDescription());
					ruleFieldSelected.setBeanEntry(fieldEntry.getBeanEntry());
					ruleFieldSelected.setId(id);
					ruleFieldSelected.setSort(sort);
					ruleFieldSelected.setShowName(fieldEntry.getShowName());
					ruleFieldSelected.setUnit(fieldEntry.getUnit());

					selectedFieldList.add(ruleFieldSelected);
				}
			}

			return selectedFieldList;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new RuntimeException(ex);
		}
	}

	public static Map<String, RuleResultEntry> formateResultFromString(String resultStr) {
		
		try {
			Map<String, RuleResultEntry> selectedResultMap = new LinkedHashMap<String, RuleResultEntry>();
			if (StringUtils.isNullOrEmpty(resultStr)){
				return selectedResultMap;
			}
			Element element = getElement(resultStr).getRootElement();
			for (Iterator<?> iter = element.elementIterator("result"); iter.hasNext();) {
				
				Element conEle = (Element) iter.next();
				String attrName = conEle.attributeValue("attr");
				RuleResultEntry resultEntry = RuleConfigHelper.getRuleResultEntry(attrName);
				if (resultEntry != null) {
					selectedResultMap.put(resultEntry.getKey(), resultEntry);
				}
			}
			return selectedResultMap;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 生成规则内容行
	 * 
	 * @param ruleTable
	 * @param valuesMap
	 * @return
	 * @throws Exception
	 */
	public static List<RuleRowEdit> convertRuleRow(Long tableId, Map<String, RuleFieldEntry> fieldMap,
			Map<String, String[]> valuesMap, Map<String, String[]> operatersMap, Map<String, String[]> resultMap) {
		List<RuleRowEdit> rowList = new LinkedList<RuleRowEdit>();

		// 每行一共有多少个字段
		int rowSize = 0;
		for (Iterator<String> it = valuesMap.keySet().iterator(); it.hasNext();) {
			String key = it.next();
			String[] vals = valuesMap.get(key);
			rowSize = vals.length;
			break;
		}

		RuleRowViewerDTO[] rowViewerS = new RuleRowViewerDTO[rowSize];
		for (Iterator<String> it = valuesMap.keySet().iterator(); it.hasNext();) {
			
			String key = it.next();
			String[] vals = valuesMap.get(key);
			String[] operater = operatersMap.get(key);

			for (int j = 0; j < vals.length; j++) {
				if (rowViewerS[j] == null) {
					rowViewerS[j] = new RuleRowViewerDTO();
				}
				String v = vals[j].trim();
				String ope = operater[j].trim();
				/*
				 * if(StringUtils.isNullOrEmpty(v)) continue;
				 */
				RuleRowFieldViewerDTO condition = new RuleRowFieldViewerDTO();
				condition.setAttributeName(key);
				condition.setAttributeId(key);
				condition.setValue(v);
				// TODO
				condition.setOperator(ope);
				// TODO
				condition.setDataType(fieldMap.get(key).getUnit());
				rowViewerS[j].getRowConditions().put(condition.getAttributeId(), condition);
			}
		}

		// add result
		for (Iterator<String> it = resultMap.keySet().iterator(); it.hasNext();) {

			String key = it.next();
			String[] vals = resultMap.get(key);

			for (int j = 0; j < vals.length; j++) {
				if (rowViewerS[j] == null) {
					rowViewerS[j] = new RuleRowViewerDTO();
				}
				String v = vals[j].trim();
				/*
				 * if(StringUtils.isNullOrEmpty(v)) continue;
				 */

				RuleRowResultViewerDTO result = new RuleRowResultViewerDTO();
				result.setResultKey(key);
				result.setValue(v);
				rowViewerS[j].getRowResults().put(result.getResultKey(), result);
			}
		}

		for (int i = 0; i < rowViewerS.length; i++) {

			StringBuffer sb = buildXmlStringBuffer("root");

			sb.append("<condition>");

			Map<String, RuleRowFieldViewerDTO> fields = rowViewerS[i].getRowConditions();
			for (Iterator<RuleRowFieldViewerDTO> it = fields.values().iterator(); it.hasNext();) {
				RuleRowFieldViewerDTO field = it.next();

				RuleFieldEntry fieldEntry = fieldMap.get(field.getAttributeId());
				String name = fieldEntry.getBeanEntry().getName() + "." + fieldEntry.getAttr();

				sb.append("<con attr=\"" + field.getAttributeId() + "\" name=\"" + name + "\">");
				sb.append("<ope><![CDATA[" + field.getOperator() + "]]></ope>");
				sb.append("<val><![CDATA[" + field.getValue() + "]]></val>");
				sb.append("<type><![CDATA[" + field.getDataType() + "]]></type>");
				sb.append("</con>");
			}
			sb.append("</condition>");

			sb.append("<result>");
			Map<String, RuleRowResultViewerDTO> results = rowViewerS[i].getRowResults();
			for (Iterator<RuleRowResultViewerDTO> it = results.values().iterator(); it.hasNext();) {
				RuleRowResultViewerDTO result = it.next();

				sb.append("<res attr=\"" + result.getResultKey() + "\">");
				sb.append("<![CDATA[" + result.getValue() + "]]></res>");
			}
			sb.append("</result>");
			finishXmlString(sb, "root");

			RuleRowEdit ruleRow = new RuleRowEdit();
			ruleRow.setRuleCondition(sb.toString());
			ruleRow.setTableId(tableId);
			ruleRow.setSort(i);

			rowList.add(ruleRow);
		}

		return rowList;

	}

	public static List<RuleRowViewerDTO> formatRuleConditionFromEdit(List<IRuleRow> ruleRows) {
		try {
			List<RuleRowViewerDTO> rowsList = new LinkedList<RuleRowViewerDTO>();

			for (Iterator<IRuleRow> it = ruleRows.iterator(); it.hasNext();) {
				IRuleRow rowEdit = it.next();
				Element element = getElement(rowEdit.getRuleCondition()).getRootElement();

				RuleRowViewerDTO rowView = new RuleRowViewerDTO();
				rowView.setKey(rowEdit.getRowId());

				for (Iterator<?> iter = element.element("condition").elementIterator("con"); iter.hasNext();) {
					Element con = (Element) iter.next();

					String id = con.attributeValue("attr");
					String name = con.attributeValue("name");
					String ope = con.element("ope").getText();
					String type = con.element("type").getText();
					String val = con.element("val").getText();
					String beanName = name.substring(0, name.indexOf("."));
					String attr = name.substring(name.indexOf(".") + 1);

					RuleRowFieldViewerDTO fieldViewer = new RuleRowFieldViewerDTO();
					fieldViewer.setAttributeId(id);
					fieldViewer.setAttributeName(name);
					fieldViewer.setDataType(type);
					fieldViewer.setOperator(ope);
					fieldViewer.setValue(val);
					fieldViewer.setBeanName(beanName);
					fieldViewer.setAttribute(attr);

					rowView.getRowConditions().put(id, fieldViewer);
				}
				// result
				for (Iterator<?> iter = element.element("result").elementIterator("res"); iter.hasNext();) {
					Element res = (Element) iter.next();

					String attr = res.attributeValue("attr");
					String val = res.getText();

					RuleRowResultViewerDTO resultViewer = new RuleRowResultViewerDTO();
					resultViewer.setResultKey(attr);
					resultViewer.setValue(val);
					rowView.getRowResults().put(resultViewer.getResultKey(), resultViewer);
				}

				rowsList.add(rowView);
			}

			return rowsList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private static StringBuffer buildXmlStringBuffer(String rootName) {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<" + rootName + ">");
		return sb;
	}

	private static void finishXmlString(StringBuffer sb, String rootName) {
		sb.append("</" + rootName + ">");
	}

	private static Document getElement(String str) throws Exception {
		Document document = null;
		document = DocumentHelper.parseText(str.trim());
		return document;
	}
}