package common.utils;


import com.gzcb.creditcard.inteface.core.request.CoreDataRequest;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * 项目：gykdh
 * 版    权: Copyright(C) 2011-2020,  gzcb. All rights reserved
 * 类名：CoreRequestUtils
 * 描述：调用核心接口查询类
 * 创建人：liting    创建日期：2016年11月3日   版本：
 * 历史
 * <修改人>   <修改日期 >   <修改内容>
 */
public class CoreRequestUtils {

	private static Logger logger=LoggerFactory.getLogger(CoreRequestUtils.class);
	
	/**
	 * 
	 * 描述：<功能简述>
	 * @param coreInterFaceName 核心接口名：3004,3001
	 * @param
	 * @return
	 * @throws Exception 
	 *
	 */
	public static Object callCore(String coreInterFaceName ,Object requestObject) throws Exception{	
		long startTime=System.currentTimeMillis();
		logger.info("卡核心接口调用开始 coreInterFaceName:{}",coreInterFaceName);
		Object result=null;
		try {
			Map<String,String> paramsMap=objectToMap(requestObject);
			
			result = CoreDataRequest.getInstance().request(coreInterFaceName, paramsMap);
		} catch (Exception e) {
			logger.error("query core has a wrong :{}",coreInterFaceName, e);
			throw e;
		}
		long endTime=System.currentTimeMillis();
		logger.info("卡核心接口调用结束 coreInterFaceName:{} sumTime:{}",coreInterFaceName,(endTime-startTime));
		return result;
	}
	
	/**
	 * 
	 * 描述：查询公积金接口
	 * @param coreInterFaceName
	 * @param requestObject
	 * @return
	 * @throws Exception
	 *
	 */
	public static Object callFund(String coreInterFaceName ,Object requestObject) throws Exception{
		long startTime=System.currentTimeMillis();
		logger.info("公积金接口调用开始 coreInterFaceName:{}",coreInterFaceName);
		Object result=null;
		try {
			Map<String,String> paramsMap=objectToMap(requestObject);
			
			result = CoreDataRequest.getInstance().requestFund(coreInterFaceName, paramsMap);
		} catch (Exception e) {
			logger.error("query core has a wrong :{}",coreInterFaceName, e);
			throw e;
		}
		long endTime=System.currentTimeMillis();
		logger.info("公积金接口调用结束 coreInterFaceName:{} sumTime:{}",coreInterFaceName,(endTime-startTime));
		return result;
		
	}
	
	public static Map<String,String> objectToMap(Object object) throws Exception{
		Map<String,String> params=new LinkedHashMap<String,String>();
		PropertyUtilsBean utilsBean=new PropertyUtilsBean();
		PropertyDescriptor[]  descriptor=utilsBean.getPropertyDescriptors(object);
		for(int i=0;i<descriptor.length;i++){
			String name=descriptor[i].getName();
			if(!"class".equals(name)){
				Object value=utilsBean.getNestedProperty(object, name);
				if(value!=null){
					name=name.replaceAll("_", "-");
					params.put(name, value.toString());
				}
			}
		}
		
		return params;
		
	}
	
	
	/**
	 * 
	 * 描述：获取期逾期月数
	 * @param cupaym24  24期还款状态
	 * @param month 月数
	 * @return
	 *
	 */
	public static  int getOverdueNum(String cupaym24,int month){
		int result=0;
		String cupaym24Sub=cupaym24.substring(0,month);		
		for(int i=0;i<cupaym24Sub.length();i++){
			int cupaym=(int)cupaym24Sub.charAt(i);
			if(cupaym<=90 && cupaym>=65){
				result++;
			}
		}
		return result;
	}
	
	/**
	 * 
	 * 描述：获取最高逾期期数
	 * @param cupaym24
	 * @param month
	 * @return
	 *
	 */
	public static  int getHigestOverdueNum(String cupaym24){
		int result=0;
		char max='0';
		//找出最大的字母
		for(int i=0;i<cupaym24.length();i++){
			char cupaym=cupaym24.charAt(i);
			if(cupaym<='Q' && cupaym>='A'){
				if(cupaym>max){
					max=cupaym;
				}
			}
		}
		//A-M，按照字母顺序，I不不使用
		//N:13-18
		//P:19-24
		//Q:24以上		
		int maxNum=0;
		if(max>='A' && max<='M'){
			if(max>='A' && max<='H'){
				result=max-64;
			}else{
				result=max-64-1;  //跳过字母 I
			}
		}else if(max=='N'){
			maxNum=StringUtils.acountCharMaxNum(cupaym24,max);
			result=13+(maxNum-1);			
		}else if(max=='P'){
			maxNum=StringUtils.acountCharMaxNum(cupaym24,max);
			result=19+(maxNum-1);			
		}else if(max=='Q'){
			maxNum=StringUtils.acountCharMaxNum(cupaym24,max);
			result=25+(maxNum-1);			
		}
		
		
		return result;
	}
	
	public static String [] getListFromSchoolEn(String schoolen){
		String [] strs = null;
		if (schoolen.contains("；")) {
			strs = schoolen.split("；");
		}
		if (schoolen.contains("：")) {
			strs = schoolen.split("：");
		}
		if (schoolen.contains(";")) {
			strs = schoolen.split(";");
		}
		if (schoolen.contains(":")) {
			strs = schoolen.split(":");
		}
		if (strs!=null&&strs.length==1) {
			String [] strs1 = new String[2];
			strs1[0]=strs[0];
			strs1[1]=" ";
			return strs1;
		}
		return strs;
	}

	/**
	 *
	 * 描述：获取人行2.0最高逾期期数
	 * * 	当月不需要还款且之前没有拖欠
	 * N	正常还款
	 * D	担保人代还
	 * Z	以资抵债
	 * M	约定还款日后月底前还款
	 * 1	逾期 1-30 天
	 * 2	逾期 31-60 天
	 * 3	逾期 61-90 天
	 * 4	逾期 91-120 天
	 * 5	逾期 121-150 天
	 * 6	逾期 151-180 天
	 * 7	逾期 180 天以上
	 * B	呆账
	 * C	结清
	 * G	结束
	 * #	未知

	 * @param cupaym24
	 * @param month
	 * @return
	 *
	 */
	public static  int getCreditHigestOverdueNum(String cupaym24,int month){
		int result=0;
		char max='0';
		if(cupaym24==null ||cupaym24.length()==0){
			return result;
		}
		int length=cupaym24.length();
		String newCupaym24="";
		//如果长度小于月数，在前面补0
		if(length<month){
			for(int i=0;i<(month-length);i++){
				cupaym24="0"+cupaym24;
			}
			length=cupaym24.length();
		}

		//如果字母不是1-7,把他们替换为0,没有逾期
		for(int i=0;i<length;i++){
			char cupaym=cupaym24.charAt(i);
			if(cupaym<='7' && cupaym>='1'){
				newCupaym24=newCupaym24+cupaym;
			}else{
				newCupaym24=newCupaym24+"0";
			}
		}

		String[] newCupaym24Arr=newCupaym24.split("");
		if(newCupaym24.charAt(length-month)=='7'){
			//找出第一次查询7的位置
			int firstEvenIndex=month;
			for(int i=length-month;i>0;i--){
				if(newCupaym24.charAt(i)=='7'){
					firstEvenIndex=i;
				}else{
					break;
				}
			}

			for(int i=0;i<firstEvenIndex;i++){
				newCupaym24Arr[i]="0";
			}
		}else{
			for(int i=0;i<length-month;i++){
				newCupaym24Arr[i]="0";
			}
		}

		//重新拼接24期
		newCupaym24="";
		for(int i=0;i<length;i++){
			newCupaym24=newCupaym24+newCupaym24Arr[i];
		}

		//找出最大的字母
		for(int i=0;i<newCupaym24.length();i++){
			char cupaym=newCupaym24.charAt(i);
			if(cupaym<='7' && cupaym>='1'){
				if(cupaym>max){
					max=cupaym;
				}
			}
		}

		int maxNum=0;
		if(max>='1' && max<='6'){
			result=max-48;
		}else if(max=='7'){
			maxNum=StringUtils.acountCharMaxNum(newCupaym24,max);
			result=7+(maxNum-1);
		}


		return result;
	}
	
	
	public static void main(String[] args) throws Exception{		
		//System.out.println(CoreRequestUtils.getHigestOverdueNum("929999999999920*********"));
		System.out.println(CoreRequestUtils.getHigestOverdueNum(""));
		String [] aa = getListFromSchoolEn(":22");
		String [] bb = getListFromSchoolEn("111:");
		System.out.println(aa[0]+"---"+aa[1]);
		System.out.println(bb[0]+"---"+bb[1]);
	}
}
