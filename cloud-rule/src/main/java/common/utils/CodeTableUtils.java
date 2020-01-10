package common.utils;

import com.gzcb.creditcard.gykdh.common.contants.LoanContants;
import com.gzcb.creditcard.gykdh.entity.system.CodeTable;

import java.util.Map;

public class CodeTableUtils {
	public static String getCodeDesc(String codeType,String codeName){
		String desc=codeName;
		Map<String, Map<String, CodeTable>> codeTableAllMap=(Map<String, Map<String, CodeTable>>)JedisUtil.get( LoanContants.CODETYPE_CHACHE_NAME,LoanContants.CONTEXT_PARAM_CODE_TABLE);
		Map<String, CodeTable> codeTableType=codeTableAllMap.get(codeType);
		if(codeTableType!=null){
			CodeTable  codeTable=codeTableType.get(codeName);
			if(codeTable!=null){
				desc=codeTable.getCodeDesc();
			}
		}
		return desc;
	}
	
	public static Map<String, CodeTable> getCodeTableByType(String codeType){
		Map<String, Map<String, CodeTable>> codeTableAllMap=(Map<String, Map<String, CodeTable>>)JedisUtil.get(LoanContants.CODETYPE_CHACHE_NAME,LoanContants.CONTEXT_PARAM_CODE_TABLE);
		return codeTableAllMap.get(codeType);
	}
	
	public static CodeTable getCodeTable(String codeType,String codeName){
		Map<String, Map<String, CodeTable>> codeTableAllMap=(Map<String, Map<String, CodeTable>>)JedisUtil.get(LoanContants.CODETYPE_CHACHE_NAME,LoanContants.CONTEXT_PARAM_CODE_TABLE);
		Map<String, CodeTable> codeTableType=codeTableAllMap.get(codeType);
		if(codeTableType!=null){
			CodeTable  codeTable=codeTableType.get(codeName);
			return codeTable;
		}
		
		return null;
	}
}
