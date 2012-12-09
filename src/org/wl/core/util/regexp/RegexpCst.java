package org.wl.core.util.regexp;

import java.util.regex.Pattern;
/**
 * 验证常量定义类
 * 
 */
public class RegexpCst {
	//匹配空行的正则表达式：\n[\s| ]*\r
	
	private static String ATTR_STR = "\\s+(\\w+)\\s*=\\s*(\"[^\"]*\"|\'[^\']*\'|[^\\s>]+)";

	public static Pattern A = getPattern("<a(" + ATTR_STR + ")*\\s*>[^(<\\s*/\\s*a\\s*>)]*<\\s*/\\s*a\\s*>");

	public static Pattern A_START = getPattern("<\\s*a[^>]*>");

	public static Pattern A_END = getPattern("<\\s*/a\\s*>");

	public static Pattern HREF_URL = getPattern("href\\s*=\\s*(\"[^\"]*\"|'[^']*'|[^\\s>])");

	public static Pattern ATTR = getPattern(ATTR_STR);

	public static Pattern TAG_NAME = getPattern("<\\s*(\\w+)");

	public static Pattern SRC = getPattern("<\\s*img\\s+[^>]*src\\s*=\\s*(\"[^\"]*\"|'[^']*'|[^\\s>]+)[^>]*>");

	public static Pattern SIMPLE_TAG = getPattern("<[^<>]+>");
	
	public static Pattern HTML_CHAR = getPattern("(&[^;]+;)");

	public static Pattern NON_TAG = getPattern(">([^<>]+)<");

	public static Pattern TABLE = getPattern("<\\s*[/]?\\s*table[^>]*>");

	public static Pattern TABLE_START = getPattern("<\\s*\\s*table[^>]*>");

	public static Pattern TABLE_END = getPattern("<\\s*[/]+\\s*table\\s*>");

	public static Pattern TR = getPattern("<\\s*[/]?\\s*tr[^>]*>");

	public static Pattern TR_START = getPattern("<\\s*tr[^>]*>");

	public static Pattern TR_END = getPattern("<\\s*[/]+\\s*tr[^>]*>");

	public static Pattern TD = getPattern("<\\s*[/]?\\s*td[^>]*>");

	public static Pattern TD_START = getPattern("<\\s*td[^>]*>");

	public static Pattern TD_END = getPattern("<\\s*[/]+\\s*td[^>]*>");

	public static Pattern DIV_START = getPattern("<\\s*div[^>]*>");

	public static Pattern DIV_END = getPattern("<\\s*[/]+\\s*div[^>]*>");

	public static Pattern WORD = getPattern("\\w+");

	public static Pattern VALUE = getPattern("value\\s*=\\s*(\"[^\"]*\"|'[^']*'|[^\\s>]+)");
	

	/**
	 * 整数
	 */
	public static Pattern VALIDATOR_INTEGE = getPattern("^-?[1-9]\\d*$");
	/**
	 * 数字
	 */
	public static Pattern VALIDATOR_NUMBER = getPattern("^([+-]?)\\d*\\.?\\d+$");
	/**
	 * 浮点数
	 */
	public static Pattern VALIDATOR_DECMAL = getPattern("^([+-]?)\\d*\\.\\d+$");
	/**
	 * 电子邮箱
	 */
	public static Pattern VALIDATOR_EMAIL = getPattern("^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$");
	/**
	 * URL地址
	 */
	public static Pattern VALIDATOR_URL = getPattern("^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&=]*)?$");
	/**
	 * 仅中文
	 */
	public static Pattern VALIDATOR_CHINESE = getPattern("^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$");
	/**
	 * 仅ACSII字符
	 */
	public static Pattern VALIDATOR_ASCII = getPattern("^[\\x00-\\xFF]+$");
	/**
	 * 邮编
	 */
	public static Pattern VALIDATOR_ZIPCODE = getPattern("^\\d{6}$");
	/**
	 * 手机
	 */
	public static Pattern VALIDATOR_MOBILE =  getPattern("^(13|15)[0-9]{9}$");
	/**
	 * ip4  地址
	 */
	public static Pattern VALIDATOR_IP4 =  getPattern("^(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)$");
	/**
	 * 非空
	 */
	public static Pattern VALIDATOR_NOTEMPTY =  getPattern("^\\S+$");
	/**
	 * 日期
	 */
	public static Pattern VALIDATOR_DATE =  getPattern("^\\d{4}(-|/|.)\\d{1,2}\\1\\d{1,2}$");
	/**
	 * QQ号码
	 */
	public static Pattern VALIDATOR_QQ =  getPattern("^[1-9]*[1-9][0-9]*$");
	/**
	 * 电话号码的函数(包括验证国内区号,国际区号,分机号)
	 */
	public static Pattern VALIDATOR_TEL =  getPattern("^(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)?(\\d{7,8})(-(\\d{3,}))?$");
	/**
	 * 用来用户注册。匹配由数字、26个英文字母或者下划线组成的字符串
	 */
	public static Pattern VALIDATOR_USERNAME = getPattern("^\\w+$");
	/**
	 * 字母
	 */
	public static Pattern VALIDATOR_LETTER = getPattern("^[A-Za-z]+$");	
	/**
	 * 身份证
	 */
	public static Pattern VALIDATOR_IDCARD = getPattern("^[1-9]([0-9]{14}|[0-9]{17})$");
	/**
	 * 匹配标签内容	如果内容中嵌套标签过多会出现异常
	 */
	public static String htmlTag = "<HtmlTag [^>]*id=fontzoom[^>]*>(<HtmlTag[^>]*>(<HtmlTag[^>]*>(<HtmlTag[^>]*>.*?</HtmlTag>|.)*?</HtmlTag>|.)*?</HtmlTag>|.)*?</HtmlTag>";
	
	public RegexpCst() {
	}
	
	/**
	 * 
	 * @param patternString 验证规则
	 * @return
	 */
	private static Pattern getPattern(String patternString) {
		Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
		return pattern;
	}
	
	public static void main(String[] strs){
//		System.out.println(RegexpUtil.isMatch("limaofeng@msn.com", RegexpCst.VALIDATOR_EMAIL));
	}
	
}
