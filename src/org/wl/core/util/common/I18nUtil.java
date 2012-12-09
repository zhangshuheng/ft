package org.wl.core.util.common;


public class I18nUtil {

	public static String unicode(String value) {
		return gbEncoding(value);
	}

	private static String gbEncoding(final String gbString) {
		char[] utfBytes = gbString.toCharArray();
		String unicodeBytes = "";
		for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {
			String hexB = Integer.toHexString(utfBytes[byteIndex]);
			if (hexB.length() <= 2) {
				hexB = "00" + hexB;
			}
			unicodeBytes = unicodeBytes + (StringUtil.isChinese(new String(new char[]{utfBytes[byteIndex]})) ? ("\\u" + hexB) : new String(new char[]{utfBytes[byteIndex]}));
		}
		return unicodeBytes;
	}

	/** */
	/*****************************************************
	 * 功能介绍:将unicode字符串转为汉字 输入参数:源unicode字符串 输出参数:转换后的字符串
	 *****************************************************/
	public String decodeUnicode(final String dataStr) {
		int start = 0;
		int end = 0;
		final StringBuffer buffer = new StringBuffer();
		while (start > -1) {
			end = dataStr.indexOf("\\\\u", start + 2);
			String charStr = "";
			if (end == -1) {
				charStr = dataStr.substring(start + 2, dataStr.length());
			} else {
				charStr = dataStr.substring(start + 2, end);
			}
			char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
			buffer.append(new Character(letter).toString());
			start = end;
		}
		return buffer.toString();
	}
	
	public static void main(String[] args){
		System.out.print(unicode("成功导入{0}条,新的资源!"));
	}
	
}
