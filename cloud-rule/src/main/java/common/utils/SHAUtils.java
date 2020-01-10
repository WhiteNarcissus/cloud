package common.utils;

import jxl.common.Logger;

import java.security.MessageDigest;

/**
 * 
 * 项目：gykdh
 * 版    权: Copyright(C) 2011-2020,  gzcb. All rights reserved
 * 类名：SHAUtils
 * 描述：加密
 * 创建人：liting    创建日期：2016年10月28日   版本：
 * 历史
 * <修改人>   <修改日期 >   <修改内容>
 */
public class SHAUtils {

	private static final String SHA = "SHA";
	private static Logger logger=Logger.getLogger(SHAUtils.class);

	public static String getSHACode(String originalString) {
		try {
			if (originalString == null || originalString.length() == 0) {
                throw new Exception("需要加密的明文为空");
            }
			byte[] originalByte = originalString.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance(SHA);
			md.update(originalByte);
			originalByte = md.digest();			
			String rtv = "";
			for (int i = 0; i < originalByte.length; i++) {
				int temp = originalByte[i] & 0xFF;
				String s = Integer.toHexString(temp);
				if (s.length() == 1) {
                    s += "0";
                }
				rtv += s;
			}
			return rtv;
		} catch (Exception ex) {
			logger.error("密码加密失败", ex);
			return null;
		}
	}
	
	public static void main(String[] args) throws Exception{
		System.out.println(SHAUtils.getSHACode("admin123456"));
	}
}
