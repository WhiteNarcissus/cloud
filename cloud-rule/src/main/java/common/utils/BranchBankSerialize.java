package common.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.gzcb.creditcard.gykdh.common.contants.LoanContants;
import com.gzcb.creditcard.gykdh.common.context.SpringBeanLoader;
import com.gzcb.creditcard.gykdh.dao.system.BankBranchMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class BranchBankSerialize extends StdSerializer<String> {
	Logger logger = LoggerFactory.getLogger(BranchBankSerialize.class);
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public BranchBankSerialize(){
    	this(null);
    }
	public BranchBankSerialize(Class<String> s) {
		super(s);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void serialize(String paramT,
			JsonGenerator paramJsonGenerator,
			SerializerProvider paramSerializerProvider) throws IOException,
            JsonProcessingException {
		if (StringUtils.isNullOrEmpty(paramT)) {
			paramJsonGenerator.writeString("");
		}else{
			Map<String,Map<String,String>> map = (Map<String,Map<String,String>>) JedisUtil.get(LoanContants.CODETYPE_CHACHE_NAME, LoanContants.CONTEXT_PARAM_BRANCH_BANK);
			if (map==null){
				BankBranchMapper bankBranchMapper= (BankBranchMapper) SpringBeanLoader.getBean("bankBranchMapper");
				map = bankBranchMapper.selectAllBranchOffice();
				JedisUtil.set( LoanContants.CODETYPE_CHACHE_NAME,LoanContants.CONTEXT_PARAM_BRANCH_BANK,map);
			}
			String value = "";
			if (map.containsKey(paramT)) {
				value=map.get(paramT).get("bank_name");
			}
			if (StringUtils.isNullOrEmpty(value)) {
				value=paramT;
			}
			paramJsonGenerator.writeString(value);
		}
	}

}
