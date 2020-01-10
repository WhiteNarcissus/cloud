package common.utils;

/**
 * 征信平台查询ID生成器
 * @description
 * @author: chenglj
 * @date: 2018/10/15 10:33
 */
public class QueryIdGenerator {

    private static TimeAndMaxInt queryIdContants = new TimeAndMaxInt("yyyyMMddHHmmssSSS", 9999999999999L);


    public static String next(){
        return queryIdContants.next();
    }


}
