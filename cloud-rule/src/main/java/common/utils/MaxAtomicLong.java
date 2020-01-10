/**
 * ==================================================================
 * 广州银行信用卡中心
 * Copyright © All rights reserved.
 * ==================================================================
 */
package common.utils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 功能描述： 自增最大值循环原子操作<br>
 * 创建日期: 2016年9月29日 <br>
 * 创建人: XYL <br>
 * 程序版本: Collection <1.0> <br>
 * 修改历史 <br>
 *  date           | author  | desc <br>
 */
public class MaxAtomicLong {
	
	private AtomicLong count =new AtomicLong(0);
	private Object lock =new Object();
	private long max;

	/**
	 * 构造函数
	 */
	public MaxAtomicLong(long max) {
		this.max=max;
	}

	public long getCount(){
    	long num=count.incrementAndGet();
		while(num>max){
			synchronized (lock) {
				if(count.get()>max) {
                    count.set(0);
                }
			}
			num=count.incrementAndGet();
		}
		return num;
    }
}
