package common.utils;


import com.gzcb.creditcard.gykdh.annotation.NullAble;
import com.gzcb.creditcard.inteface.cert.common.CreditReportCondition;
import org.apache.shiro.codec.Base64;
import org.joda.time.DateTime;

import java.lang.reflect.Field;
import java.security.MessageDigest;

/**
 * 证信服务平台接口调用工具类
 * @author chengl
 * @date 2016年10月10日 下午5:52:04
 */
public class CreditReportUtils {
	
	/**
	 * 系统ID：系统申请接入征信平台时分配的系统ID
	 * Y
	 */
	public static final String systemId = PropertiesUtils.getProperty("CREDIT_UNION_SYSTEMID");
//	public static final String systemId = "TEST";
	
	/**
	 * 系统申请接入征信平台时分配的key
	 * Y
	 */
	public static final String key = PropertiesUtils.getProperty("CREDIT_UNION_KEY");
	
//	public static final String key = "TEST";
	
	public static final String credit_union_url = PropertiesUtils.getProperty("CREDIT_UNION_URL");
	
	//public static final String credit_union_url = "http://10.1.88.18:8180/csp/service/creditReport";
	
	/**
	 * 请求系统自定义唯一请求ID：推荐生成方式是：3
	 *	0 位，（其中 17 位时
		间值（精确到毫秒）：
		yyyyMMddHHmmssS
		SS）加上（13 位自增
		数字：123456789012
		3）
	 *@see  默认值为当前时间的毫秒数
		 如：20161011150426906
		 @param 规定方式为时间加自定义数，可为空
	 */
	private static String getQueryId(String queryId) {
		
		if(StringUtils.isNotEmpty(queryId)){
			return queryId;
		}
		return new DateTime().toString("yyyyMMddHHmmssSSS")+"123456789012";
		
	}
	
	/**
	 * 系统申请接入征信平台时分配的key，然后通过组合
	 * pk=key + "," + systemId + "," + userId  
	 * 成一个pk字符串，通过字符串md5加密得到byte数组，
	 * 对这个数组进行base64编码即是 pKey
	 * @return
	 */
	private static String getPKey(String userId) {
		try {
			
			if(!StringUtils.isNotEmpty(userId)){
				return null;
			}
			String str = key+","+systemId+","+userId;
			MessageDigest md5;
			
			md5 = MessageDigest.getInstance("MD5");
			md5.update(str.getBytes("utf-8"));
			byte[] md5Bytes = md5.digest();
			return new String(Base64.encode(md5Bytes));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取信用报告（人行，同盾）接口查询统一条件
	 * @param userId 查询的用户ID
	 * @param queryReports 单个接口请求报文
	 * @param queryId  系统自定义查询唯一ID，默认为当前时间年月日时分秒毫秒的组合
	 * @return
	 * @throws Exception 
	 */
	public static CreditReportCondition getCondition(String userId,
			Object[] queryReports,String queryId) throws Exception{
		CreditReportCondition con = new CreditReportCondition();
		con.setSystemId(systemId);
		con.setUserId(userId);
		con.setpKey(getPKey(userId));
//		con.setpKey("ENDCYsbodi78yQyyDen7pw==");
		con.setQueryId(getQueryId(queryId));
//		con.setQueryId(getQueryId("a2bn32bss233d1f21w1251"));
		con.setQueryReports(queryReports);
		checkNullField(queryReports[0]);
		return con;
	}
	
	public static void checkNullField(Object obj) throws Exception{
		Field[] files = obj.getClass().getDeclaredFields();
		for (Field field : files) {
			field.setAccessible(true);
			NullAble nullAble = field.getAnnotation(NullAble.class);
			
			//没有标示nullAble属性，默认为必输
			//标示nullAble属性的值为true的，需要必输
			if((nullAble == null && field.get(obj) == null) || ( nullAble != null && !nullAble.value() && field.get(obj) == null) ){
				throw new Exception(obj.getClass()+"参数："+field.getName()+"为空");
			}
			
		}
	}
	
}
