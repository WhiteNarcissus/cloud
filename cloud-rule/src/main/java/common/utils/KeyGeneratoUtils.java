package common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class KeyGeneratoUtils {
	//生成随机数的算法
	private static final String ALGORITHM="SHA1PRNG";
	//算法提供商
	private static final String PROVIDER="SUN";
	//日志打印
	private static Logger logger = LoggerFactory.getLogger(KeyGeneratoUtils.class);
	/**
	 * 生产20位的建
	 * 描述：<功能简述>
	 * @return
	 *
	 */
	
	public static String getKeyOld(){
		StringBuffer key=new StringBuffer();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		key.append(sdf.format(new Date()));
		//设置安全随机数
		SecureRandom random = null;
		try {
			random = SecureRandom.getInstance(ALGORITHM,PROVIDER);
			List<Integer> tempKey=new ArrayList<Integer>();
			for(int i=0;i<100;i++){
				tempKey.add(random.nextInt(9));
			}
			key.append(tempKey.get(random.nextInt(100)));
			key.append(tempKey.get(random.nextInt(100)));
			key.append(tempKey.get(random.nextInt(100)));
		} catch (NoSuchAlgorithmException e) {
			logger.error("SecureRandom发生异常:NoSuchAlgorithmException_"+e.getMessage(),e);
		} catch (NoSuchProviderException e) {
			logger.error("SecureRandom发生异常:NoSuchProviderException_"+e.getMessage(),e);
		} catch (Exception e){
			logger.error("其他异常:",e);
		}
		
		return key.toString();
	}
		
	public static String getKey(){
		StringBuffer key=new StringBuffer();
		String uuidStr=UUID.randomUUID().toString();
		uuidStr=uuidStr.replaceAll("-", "");
		uuidStr=uuidStr.toLowerCase();
		uuidStr=uuidStr.replaceAll("[a-z]", ""); //替换掉所有字母
		int length=uuidStr.length();
		if(length<20){
			key.append(uuidStr);
			SecureRandom random = null;
			try{
				random = SecureRandom.getInstance(ALGORITHM,PROVIDER);	
				for(int i=0;i<20 && length<20;i++){
					key.append(random.nextInt(9));
					length++;
				}
			}catch (NoSuchAlgorithmException e) {
				logger.error("SecureRandom发生异常:NoSuchAlgorithmException_"+e.getMessage(),e);
			} catch (NoSuchProviderException e) {
				logger.error("SecureRandom发生异常:NoSuchProviderException_"+e.getMessage(),e);
			} catch (Exception e){
				logger.error("其他异常:",e);
			}
			uuidStr=key.toString();
		}
		
		if(length>20){
			uuidStr=uuidStr.substring(0,20);
		}
		
		return uuidStr;
	}
	
	
}
