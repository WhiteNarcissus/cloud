package common.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;


public class JsonObjectMapper extends ObjectMapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JsonObjectMapper() {
		super();
		//空值处理为空串
		this.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>(){


			@Override
			public void serialize(Object arg0, JsonGenerator jgen,
					SerializerProvider arg2) throws IOException,
                    JsonProcessingException {
				jgen.writeString("");
				
			}
			
		});
	

	}	
}
