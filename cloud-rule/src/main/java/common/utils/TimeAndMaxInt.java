/**
 * ==================================================================
 * 广州银行信用卡中心
 * Copyright © All rights reserved.
 * ==================================================================
 */
package common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能描述： <br>
 * 创建日期: 2016年11月10日 <br>
 * 创建人: XYL <br>
 * 程序版本: CreditUnion <1.0> <br>
 * 修改历史 <br>
 *  date           | author  | desc <br>
 */
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

	/**
	 * 返回下一个值
	 * @return
	 * @date 2016年11月10日
	 * @author XYL
	 */
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
