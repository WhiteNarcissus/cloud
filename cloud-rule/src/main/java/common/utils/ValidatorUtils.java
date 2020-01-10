package common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtils {

	/**
	 * 验证是否为空
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNullOrEmpty(String value) {
		if (value == null || "".equals(value)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 验证中文
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	/**
	 * 计算字符串长度包括中文
	 * 
	 * @param str
	 * @param step 一个中文代表多少字符
	 * @return
	 */
	public static int countString(String str, int step) {
		int count = 0;
		char[] chars = str.toCharArray();
		for (char c : chars) {
			if (isChinese(c)) {
				count += step;
			} else {
				count++;
			}
		}
		return count;
	}

	/**
	 * 验证数字
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNum(String value) {
		return regex(value, "^([+-]?)\\d*\\.?\\d+$");
	}

	/**
	 * 验证整数
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isInteger(String value) {
		return regex(value, "^-?[0-9]\\d*$");
	}

	/**
	 * 验证浮点数
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isDecimal(String value) {
		return regex(value, "^([+-]?)\\d*\\.\\d+$");
	}

	/**
	 * 正则表达式验证
	 * 
	 * @param value
	 * @param pattern
	 * @return
	 */
	public static boolean regex(String value, String pattern) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(value);
		return m.find();
	}
}