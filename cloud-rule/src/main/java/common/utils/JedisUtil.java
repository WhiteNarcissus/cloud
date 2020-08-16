//package common.utils;
//
//import com.gzcb.creditcard.gykdh.common.contants.LoanContants;
//import com.gzcb.creditcard.gykdh.common.context.SpringBeanLoader;
//import com.gzcb.creditcard.gykdh.dto.system.UserRoleDTO;
//import com.gzcb.creditcard.gykdh.entity.system.User;
//import com.gzcb.creditcard.gykdh.service.system.UserService;
//import common.LoanContants;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import redis.clients.jedis.exceptions.JedisException;
//
//import java.nio.ByteBuffer;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
//public class JedisUtil {
//	private static Logger logger = LoggerFactory.getLogger(JedisUtil.class);
//
//	private static  RedisTemplate<String,Object> redisTemplate=((RedisTemplate) SpringBeanLoader.getBean("redisTemplate"));
//
//	/**
//	 * 获取缓存getCodeTba
//	 * @param key 键
//	 * @return 值
//	 */
//	public static String get(String key) {
//		String value = null;
//		try {
//			if (existsObject(key)) {
//				value = redisTemplate.opsForValue().get(key).toString();
//				value = StringUtils.isNullOrEmpty(value) && !"nil".equalsIgnoreCase(value) ? value : null;
//				logger.debug("get {} = {}", key, value);
//			}
//		} catch (Exception e) {
//			logger.warn("get {} = {}", key, value, e);
//		}
//		return value;
//	}
//
//	/**
//	 * 获取缓存
//	 * @param key 键
//	 * @return 值
//	 */
//	public static Object getObject(String key) {
//		Object value = null;
//		try {
//			if (existsObject(key)) {
//				value = redisTemplate.opsForValue().get(key);
//				logger.debug("getObject {} = {}", key, value);
//			}
//		} catch (Exception e) {
//			logger.warn("getObject {} = {}", key, value, e);
//		}
//		return value;
//	}
//
//
//	/**
//	 * 设置缓存
//	 * @param key 键
//	 * @param value 值
//	 * @return
//	 */
//	public static String set(String key, Object value) {
//		String result = null;
//		try {
//			redisTemplate.opsForValue().set(key,value);
//
//			logger.debug("set {} = {}", key, value);
//		} catch (Exception e) {
//			logger.warn("set {} = {}", key, value, e);
//		}
//		return result;
//	}
//	/**
//	 * 设置缓存
//	 * @param key 键
//	 * @param hashkey hash键
//	 * @param value 值
//	 * @return
//	 */
//	public static String set(String key, String hashkey, Object value) {
//		String result = null;
//		try {
//			redisTemplate.opsForHash().put(key,hashkey,value);
//			logger.debug("set {}--{} = {}", key,hashkey, value);
//		} catch (Exception e) {
//			logger.warn("set {}--{} = {}", key,hashkey, value, e);
//		}
//		return result;
//	}
//
//	/**
//	 * 设置缓存
//	 * @param key 键
//	 * @param hashkey hash键
//	 * @return
//	 */
//	public static Object get(String key, String hashkey) {
//		Object result = null;
//		try {
//			result=redisTemplate.opsForHash().get(key, hashkey);
//			logger.debug("getObject {}--{} = {}", key,hashkey, result);
//		} catch (Exception e) {
//			result=null;
//			logger.warn("getObject {}--{} = {}", key, hashkey,result, e);
//		}
//		return result;
//	}
//	/**
//	 * 删除缓存
//	 * @param key 键
//	 * @param hashkey hash键
//	 * @return
//	 */
//	public static Object del(String key, String hashkey) {
//		Object result = null;
//		try {
//			result=redisTemplate.opsForHash().delete(key, hashkey);
//			logger.debug("getObject {}--{} = {}", key,hashkey, result);
//		} catch (Exception e) {
//			result=null;
//			logger.warn("getObject {}--{} = {}", key, hashkey,result, e);
//		}
//		return result;
//	}
//
//
//
//	/**
//	 * 自增
//	 * @param key 键
//	 * @return
//	 */
//	public static Long incr(String key) {
//		Long result = null;
//		try {
//			result = redisTemplate.opsForValue().increment(key,1);
//			logger.debug("incr {} = {}", key, result);
//		} catch (Exception e) {
//			logger.warn("incr {} = {}", key, result, e);
//		}
//		return result;
//	}
//
//	/**
//	 * 设置缓存
//	 * @param key 键
//	 * @param value 值
//	 * @param cacheSeconds 超时时间，0为不超时
//	 * @return
//	 */
//	public static String setObject(String key, Object value, int cacheSeconds) {
//		String result = "success";
//		try {
//
//			if (cacheSeconds != 0) {
//				redisTemplate.opsForValue().set(key,value,cacheSeconds,TimeUnit.MILLISECONDS);
//			}else {
//				redisTemplate.opsForValue().set(key,value);
//			}
//			logger.debug("setObject {} = {}", key, value);
//		} catch (Exception e) {
//			result="error";
//			logger.warn("setObject {} = {}", key, value, e);
//		}
//		return result;
//	}
//
//
//	public static  String setMapUser(String key,Map<Long,User> value,int cacheSeconds){
//		String result = null;
//		try{
//			redisTemplate.opsForHash().putAll(key,value);
//			if(cacheSeconds!=0){
//				redisTemplate.expire(key, cacheSeconds,TimeUnit.SECONDS);
//			}
//			result="success";
//		}catch (Exception e) {
//			result="error";
//			logger.warn("setMapList {} = {}", key, value, e);
//		}
//
//		return result;
//	}
//
//	/**
//	 * 设置Map缓存
//	 * @param key 键
//	 * @param value 值
//	 * @param cacheSeconds 超时时间，0为不超时
//	 * @return
//	 */
//	public static <T> String setObjectMap(String key, Map<String, T> value, int cacheSeconds) {
//		String result = null;
//		try{
//			redisTemplate.opsForHash().putAll(key,value);
//			if(cacheSeconds!=0){
//				redisTemplate.expire(key, cacheSeconds,TimeUnit.SECONDS);
//			}
//			result="success";
//		}catch (Exception e) {
//			result="error";
//			logger.warn("setMapList {} = {}", key, value, e);
//		}
//
//		return result;
//	}
//
//	/**
//	 * 获取Map缓存
//	 * @param key 键
//	 * @return
//	 */
//	public static Map getObjectMap(String key) {
//		Map result = null;
//		try{
//			result=redisTemplate.opsForHash().entries(key);
//
//		}catch (Exception e) {
//			logger.warn("getMapList {} = {}", key, e);
//		}
//
//		return result;
//	}
//
//
//
//	/**
//	 * 删除缓存
//	 * @param key 键
//	 * @return
//	 */
//	public static long del(String key) {
//		long result = 0;
//		try {
//			if (existsObject(key)){
//				redisTemplate.delete(key);
//				result=1;
//				logger.debug("del {}", key);
//			}else{
//				logger.debug("del {} not exists", key);
//			}
//		} catch (Exception e) {
//			logger.warn("del {}", key, e);
//		}
//		return result;
//	}
//
//
//
//	/**
//	 * 缓存是否存在
//	 * @param key 键
//	 * @return
//	 */
//	public static boolean exists(String key) {
//		boolean result = false;
//		try {
//			result = redisTemplate.hasKey(key);
//			logger.debug("exists {}", key);
//		} catch (Exception e) {
//			logger.warn("exists {}", key, e);
//		}
//		return result;
//	}
//
//	/**
//	 * 缓存是否存在
//	 * @param key 键
//	 * @return
//	 */
//	public static boolean existsObject(String key) {
//		boolean result = false;
//		try {
//			result = redisTemplate.hasKey(key);
//			logger.debug("existsObject {}", key);
//		} catch (Exception e) {
//			logger.warn("existsObject {}", key, e);
//		}
//		return result;
//	}
//
//	/**
//	 * 获取资源
//	 * @return
//	 * @throws JedisException
//	 */
//	public static RedisTemplate<String,Object> getResource() {
//		try {
//			redisTemplate =((RedisTemplate) SpringBeanLoader.getBean("redisTemplate"));
//			logger.debug("getResource.", redisTemplate);
//		} catch (JedisException e) {
//			logger.warn("getResource.", e);
//			throw e;
//		}
//		return redisTemplate;
//	}
//
//
//
//
//
//	public static synchronized void updateUserCache(){
//		UserService userService = (UserService) SpringBeanLoader.getBean("userService");
//		Map<String,List<UserRoleDTO>>  userRoleMap= userService.queryRoleUserMap(null);
//
//		List<User> userList=userService.queryAllUserNoCache();
//		if(userList!=null){
//			Map<Long,User> userMap=new LinkedHashMap<Long,User>();
//			for(User user:userList){
//				userMap.put(user.getUserId(), user);
//				JedisUtil.set(LoanContants.USER_CHACHE_NAME,user.getUserId().toString(),user);
//			}
//
//			JedisUtil.set( LoanContants.USER_CHACHE_NAME,LoanContants.CONTEXT_PARAM_USER_LIST,userMap);
//		}
//
//		JedisUtil.set(LoanContants.USER_CHACHE_NAME,LoanContants.CONTEXT_PARAM_USER_ROLE,userRoleMap);
//
//	}
//
//	/**
//	 * 获取byte[]类型Key
//	 * @param object
//	 * @return
//	 */
//	public static byte[] getBytesKey(Object object){
//		if(object instanceof String){
//    		return ((String) object).getBytes();
//    	}else{
//    		return ObjectUtils.serialize(object);
//    	}
//	}
//
//	/**
//	 * Object转换byte[]类型
//	 * @param object
//	 * @return
//	 */
//	public static byte[] toBytes(Object object){
//    	return ObjectUtils.serialize(object);
//	}
//
//	/**
//	 * byte[]型转换Object
//	 * @param bytes
//	 * @return
//	 */
//	public static Object toObject(byte[] bytes){
//		return ObjectUtils.unserialize(bytes);
//	}
//	/**
//	 * Long转换为byte[]
//	 * @param number
//	 * @return
//	 */
//	public static byte[] longToByte(Long number){
//        ByteBuffer buffer = ByteBuffer.allocate(8);
//        buffer.putLong(0,number);
//        return buffer.array();
//    }
//	/**
//	 * byte[]转Long
//	 * @param b
//	 * @return
//	 */
//    public static Long byteToLong(byte[] b){
//        ByteBuffer buffer = ByteBuffer.allocate(8);
//        buffer.put(b,0,b.length);
//        buffer.flip();
//        return buffer.getLong();
//    }
//}
