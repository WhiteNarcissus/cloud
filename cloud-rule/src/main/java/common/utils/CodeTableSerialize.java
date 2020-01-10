package common.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.gzcb.creditcard.gykdh.common.annotation.CodeTypeAnnotation;
import com.gzcb.creditcard.gykdh.common.contants.LoanContants;
import com.gzcb.creditcard.gykdh.entity.system.CodeTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class CodeTableSerialize extends StdSerializer<String> {
	Logger logger = LoggerFactory.getLogger(CodeTableSerialize.class);
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CodeTableSerialize(){
    	this(null);
    }
	public CodeTableSerialize(Class<String> s) {
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
			
			Map<String, Map<String, CodeTable>> map =  (Map<String, Map<String, CodeTable>>) JedisUtil.get(LoanContants.CODETYPE_CHACHE_NAME,LoanContants.CONTEXT_PARAM_CODE_TABLE);
			String key=paramJsonGenerator.getOutputContext().getCurrentName();
			String value = "";
			try {
				key=paramJsonGenerator.getOutputContext().getCurrentValue().getClass().getDeclaredField(key).getAnnotation(CodeTypeAnnotation.class).codeType();
				if (map.containsKey(key)) {
					CodeTable code=map.get(key).get(paramT);
					if(code!=null){
						value =code.getCodeValue();
					}else{
						value=paramT;
					}
					
				}
			} catch (NoSuchFieldException e) {
				logger.error("key",e);
			} catch (SecurityException e) {
				e.printStackTrace();
			}
			if (StringUtils.isNullOrEmpty(value)) {
				value=paramT;
			}
			paramJsonGenerator.writeString(value);
		}
	}

}
