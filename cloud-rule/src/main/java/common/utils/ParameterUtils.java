package common.utils;

import com.gzcb.creditcard.gykdh.common.contants.LoanContants;
import com.gzcb.creditcard.gykdh.common.context.SpringBeanLoader;
import com.gzcb.creditcard.gykdh.entity.system.Parameter;
import com.gzcb.creditcard.gykdh.service.system.ParameterService;

public class ParameterUtils {

	private static ParameterService parameterService=(ParameterService)SpringBeanLoader.getBean("parameterService");
	
	public static String getParameterValue(String name){
		String paramterValue=(String)JedisUtil.get(LoanContants.PARAMETER_CHACHE_NAME+ name);
		if(StringUtils.isNullOrEmpty(paramterValue)){
			Parameter parameter=parameterService.getParameter(name);
			if(parameter!=null){
				JedisUtil.set(LoanContants.PARAMETER_CHACHE_NAME,name, parameter.getParameterValue());
				paramterValue=parameter.getParameterValue();
			}
		}
		
		return paramterValue;
	}
		
}
