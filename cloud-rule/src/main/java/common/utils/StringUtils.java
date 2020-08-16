package common.utils;


import common.LoanContants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class StringUtils {
	public static boolean isNullOrEmpty(String str){
		if(str == null) {
            return true;
        } else if("".equals(str)) {
            return true;
        } else {
            return false;
        }
	}
	
	public static String nullToChange(String str,String defaultStr){
		if ("null".equals(str)) {
			return defaultStr;
		} else if (StringUtils.isNullOrEmpty(str)) {
			return defaultStr;
		} else {
			return str;
		}
	}
	public static boolean isNullOrEmpty(String[] str){
		boolean flag = true;
		if(str == null){
			flag = true;
		}else if(str.length<=0){
			flag = true;
		}else{
			for(String s:str){
				if(!"".equals(s)){
					flag = false;
				}
			}
		}
		return flag;
	}
	public static String getUnderScoreCase(String camelCaseStr){
		if(!isNullOrEmpty(camelCaseStr)){
			String[] strs =camelCaseStr.split("(?=[A-Z])");
			String underScoreCaseStr="";
			for (int i = 0; i < strs.length; i++) {
				String string = strs[i];
				if(i==0){
					underScoreCaseStr+=string;
				}else{
					underScoreCaseStr+="_"+string;
				}
			}
			return underScoreCaseStr.toLowerCase();
		}
		return "";
	}
	
	/**
	 * 
	 * 描述：统计字符出现连续出现最高次数
	 * @param str
	 * @param chr
	 * @return
	 *
	 */
	public static int acountCharMaxNum(String str,char chr){	
		List<Integer> chrNumList = new ArrayList<Integer>();
		int firstIndex = str.indexOf(chr); // 第一次出现	
		String needFind = str;
		int needStrLen=needFind.length();
		while (firstIndex>=0) {			
			int charNum = 0;			
			for (int i = firstIndex; i < needStrLen; i++) {
				if (needFind.charAt(i) == chr) {
					charNum++;
					if(i==(needStrLen-1)){
						chrNumList.add(charNum);
						firstIndex =-1;
						break;
					}
				} else {
					chrNumList.add(charNum);
					needFind = needFind.substring(i);
					needStrLen=needFind.length();
					firstIndex = needFind.indexOf(chr);					
					break;
				}
			}
		}

		if(chrNumList.size()==0){
			return 0;
		}else{
			return Collections.max(chrNumList);
		}
				
	}
	
	/**
	 * 
	 * 描述：统计字符出现的次数
	 * @return
	 *
	 */
	public static int countChar(String str,char chr){
		int count=0;
		if(str !=null){
			for(int i=0;i<str.length();i++){
				if(str.charAt(i)==chr){
					count++;
				}
			}
		}
		return count;
	}
	
	public static boolean containsString(String[] strs,String str){
		for (String string : strs) {
			if (str.equals(string)) {
				return true;
			}
		}
		return false;
	}
	
	public static int getAgeFromCustIdNo(String custIdNo) {
		if (!StringUtils.isNullOrEmpty(custIdNo)&&custIdNo.length()==18) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			String birthday = custIdNo.substring(6, 10);
			System.out.println(birthday);
			int year = Integer.parseInt(birthday);
			Date today =new Date();
			int toYear=Integer.parseInt(sdf.format(today));
			return toYear-year;
		}
		return 0;
	}
//	public static String setFileName(String fileName){
//		String batchTimes=(String) JedisUtil.get( LoanContants.JOB_CHACHE_NAME+LoanContants.BATCH_CASE_TYPE_GYKDH_2T);
//		if (StringUtils.isNullOrEmpty(batchTimes)) {
//			batchTimes = LoanContants.BATCH_FIRST_OF_DAY;
//			JedisUtil.set(LoanContants.JOB_CHACHE_NAME,LoanContants.BATCH_CASE_TYPE_GYKDH_2T,batchTimes);
//		}
//		if (LoanContants.BATCH_NEXT_DAY.equals(batchTimes)) {
//			return null;
//		}
//		if (LoanContants.BATCH_FIRST_OF_DAY.equals(batchTimes)) {
//
//			fileName=fileName+"_01"+".csv";
//		}
//
//		if (LoanContants.BATCH_SEC_OF_DAY.equals(batchTimes)) {
//			fileName=fileName+"_02"+".csv";
//		}
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		Date batchDate =DateUtils.getBusinessDate();
//		fileName=fileName.replaceAll("yyyyMMdd", sdf.format(batchDate.getTime()));
//		return fileName;
//	}
//	public static void main(String[] args){
//		//String a="NGGGGGGNNNNNNNrrrNNNN";
//		//System.out.println(StringUtils.acountCharMaxNum(a, 'N'));
//		String num ="66789666";
//
//		System.out.println(StringUtils.setFileName("fasdfasfas.csv"));
//		System.out.println(StringUtils.countChar(num,'6'));
//
//	}
}
