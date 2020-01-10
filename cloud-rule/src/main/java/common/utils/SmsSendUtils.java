package common.utils;

import com.gzcb.creditcard.inteface.webservice.SmsInterfaceService;
import com.gzcb.creditcard.inteface.webservice.sms.SmsSenderDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmsSendUtils {

	private static Logger logger=LoggerFactory.getLogger(SmsSendUtils.class);
	
	/**
	 * 短信发送任务
	 * @param tels
	 * @param msg
	 */
	public static boolean sendSms(String []tels , String msg){
		logger.info("待发送短信数：{}" ,tels.length);
		boolean resultFlag=false;
		try{
			SmsSenderDelegate delegate = SmsInterfaceService.getInstance().getSmsSenderDelegate();
			String result = null;
			for (String tel : tels) {
				try {
					result = delegate.queryReport("GYKDH-SYSTEM", tel, msg);
					if ("00040000".equals(result)){
						resultFlag = true;
					}
				}catch(Exception e){
					logger.error("发送[ {}]失败",tel, e);
				}
				logger.info("发送[{}]结果：{}",tel ,result);
			}
		}catch(Exception e){
			logger.error(" 连接失败", e);
		}
		
		logger.info("短信发送任务结束。");
		return resultFlag;
	}
	
	public static void main(String[] args){
		String[] a={"18617320426"};
		boolean result = SmsSendUtils.sendSms(a,"测试测试测试");
		System.out.println(result);
	}
}
