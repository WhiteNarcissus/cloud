package common.utils;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @author liufei Date:2017-12-04 Decription:AES加密解密工具类
 * 
 *         说明:因为java只支持128密钥加密,所以如果需要扩大key_size须从官网下载local_policy.
 *         jar和US_export_policy.jar替换掉%JAVA_HOME%\jre\lib\security目录下的jar包,
 *         不然就改为128bit密钥
 */
public class AESUtils {
	// 密钥
	private static final String KEY = "abcdefgabcdefgfg";
	// 采用的算法,pkcs5和pkcs7一样
	private static final String ALGORITHM = "AES/ECB/PKCS5Padding";
	//日志
	private static Logger logger = LoggerFactory.getLogger(AESUtils.class);
	//key位数
	private static final short KEY_SIZE=128;
	/**
	 * aes解密
	 * 
	 * @param encrypt
	 *            解密的密文
	 * @return
	 */
	public static String aesDecrypt(String encrypt) throws Exception{
		
		return aesDecrypt(encrypt, KEY);
		
	}

	/**
	 * aes加密
	 * 
	 * @param content
	 *            加密明文
	 * @return
	 */
	public static String aesEncrypt(String content) {
		try {
			return aesEncrypt(content, KEY);

		} catch (Exception e) {
			logger.error("加密失败:",e);
			return "";
		}
	}

	/**
	 * base64编码
	 * 
	 * @param bytes
	 *            待编码的byte[]
	 * @return
	 */
	private static String base64Encode(byte[] bytes) {
		return Base64.encodeBase64String(bytes);
	}

	/**
	 * base64解码
	 * 
	 * @param base64Code
	 *            待解码的字符串
	 * @return
	 * @throws Exception
	 */
	private static byte[] base64Decode(String base64Code)  {
		try {
			return StringUtils.isEmpty(base64Code) ? null : new BASE64Decoder().decodeBuffer(base64Code);
		} catch (IOException e) {
			logger.error("解密报错",e);
		}
		return null;
	}

	/**
	 * AES加密
	 * 
	 * @param content
	 *            明文
	 * @param encryptKey
	 *            密钥
	 * @return 加密后的byte[]
	 * @throws Exception
	 */
	private static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception{
		KeyGenerator kgen;
		try {
			kgen = KeyGenerator.getInstance("AES");
			kgen.init(KEY_SIZE);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
			return cipher.doFinal(content.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			logger.error("NoSuchAlgorithmException:",e);
			throw e;
		} catch (NoSuchPaddingException e) {
			logger.error("NoSuchPaddingException:",e);
			throw e;
		} catch (IllegalBlockSizeException e) {
			logger.error("IllegalBlockSizeException:",e);
			throw e;
		} catch (BadPaddingException e) {
			logger.error("BadPaddingException:",e);
			throw e;
		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException:",e);
			throw e;
		} catch (InvalidKeyException e) {
			logger.error("InvalidKeyException:",e);
			throw e;
			
		}
		
	}

	/**
	 * AES解密
	 * 
	 * @param encryptBytes
	 *            待解密的byte[]
	 * @param decryptKey
	 *            密钥
	 * @return 解密后的string
	 * @throws Exception
	 */
	private static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey)  throws Exception {
		KeyGenerator kgen;
		try {
			kgen = KeyGenerator.getInstance("AES");
			kgen.init(KEY_SIZE);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
			byte[] decryptBytes = cipher.doFinal(encryptBytes);
			return new String(decryptBytes);
		} catch (NoSuchAlgorithmException e) {
			logger.error("NoSuchAlgorithmException:",e);
			throw e;
		} catch (NoSuchPaddingException e) {
			logger.error("NoSuchPaddingException:",e);
			throw e;
		} catch (InvalidKeyException e) {
			logger.error("InvalidKeyException:",e);
			throw e;
		} catch (IllegalBlockSizeException e) {
			logger.error("IllegalBlockSizeException:",e);
			throw e;
		} catch (BadPaddingException e) {
			logger.error("BadPaddingException:",e);
			throw e;
		}		
	}

	/**
	 * AES加密为base64 code
	 * 
	 * @param content
	 * @param encryptKey
	 * @return
	 * @throws Exception
	 */
	private static String aesEncrypt(String content, String encryptKey) throws Exception {
		return base64Encode(aesEncryptToBytes(content, encryptKey));
	}

	/**
	 * 将base64 code AES解密
	 * 
	 * @param encrypt
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private static String aesDecrypt(String encrypt, String key) throws Exception {
		return StringUtils.isEmpty(encrypt) ? null : aesDecryptByBytes(base64Decode(encrypt), key);
	}

	/**
	 * 测试AES加密解密
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String content = "123456";
		String encrypt = aesEncrypt(content, KEY);
		System.out.println(encrypt);
		String decrypt = aesDecrypt("C8SwY8yXp798DhhvAWGOxw==", KEY);
		System.out.println(decrypt);
	}
}