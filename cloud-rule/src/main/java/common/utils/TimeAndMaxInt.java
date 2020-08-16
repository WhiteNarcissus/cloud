/**
 * ==================================================================
 * 广州银行信用卡中心
 * Copyright © All rights reserved.
 * ==================================================================
 */
package common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TimeAndMaxInt {
	
	private String pattern;//时间格式
	private String format;//数字格式
	
	private MaxAtomicLong count;
	
	/**
	 * 构造函数
	 * @param pattern	时间格式
	 * @param maxNum	数字最大值
	 */
	public TimeAndMaxInt(String pattern, long maxNum) {
		super();
		this.pattern = pattern;
		this.count=new MaxAtomicLong(maxNum);
		this.format = "%0"+String.valueOf(maxNum).length()+"d";
	}


	public String next(){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String dateString = sdf.format(new Date());
		return dateString+String.format(format, count.getCount());
	}
	
	public static void main(String[] args) {
		TimeAndMaxInt max=new TimeAndMaxInt("yyyyMMddHHmmssSSS", 9999999999999L);
		System.out.println(max.next());
	}
}
