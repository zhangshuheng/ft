package org.wl.core.util.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import org.wl.core.util.regexp.RegexpUtil;

/**
 * 字符串处理集合类 主要功能：判断是否为null或者空,字符串是否在数组中,截取，字符在字符串中出现的次数等方法
 */
@SuppressWarnings("unchecked")
public class StringUtil {
	public static int PASSWORD_LEVEL_LOW = 1;

	public static int PASSWORD_LEVEL_MIDDLE = 2;

	public static int PASSWORD_LEVEL_HIGH = 3;

	public static String ellipsis(String value, int len, String word) {
		if (length(value) <= len) {
			return value;
		}
		len = len - length(word);
		do {
			value = value.substring(0, value.length() - 1);
		} while (length(value) > len);
		return value + word;
	}

	public static int length(String str) {
		return str.replaceAll("[^\\x00-\\xff]", "rr").length();
	}

	/**
	 * 字符串中是否含有中文
	 * 
	 * @param s
	 *            字符串
	 * @return
	 */
	public static boolean isChinese(String input) {
		return RegexpUtil.isMatch(input, "[^\\x00-\\xff]");
	}

	public static void main(String[] args) throws Exception {

		System.out.println(replaceStrByIndex(0, 4,
				"上海市人民政府办公厅关于加强全市性重要经济社会统计数据管理与发布工作的通知", "*"));

		System.out.println(isChinese("sss"));

		String ts = "上海市人民政府办公厅关于加强全市性重要经济社会统计数据管理与发布工作的通知";
		String en = "Rights and Interests of the Counterpart of Administrative Law Enforcement of the Statistics Organs";
		String c_ts = StringUtil.ellipsis(ts, 39, "...");
		String c_en = StringUtil.ellipsis(en, 39, "...");
		System.out.println(c_ts);
		System.out.println(c_en);
		System.out.println(StringUtil.ellipsis(ts, 39, "..."));
		System.out.println(StringUtil.ellipsis(en, 39, "..."));
		System.out.println(StringUtil.ellipsis("海市人民政府办公厅关于于rb", 25, "..."));
		System.out.println(StringUtil.ellipsis("海市人民政府办公厅关于于o", 25, "..."));

		// String[] strs = new String[]{"上","1","a","A",",","h，"};
		//
		// for(String str:strs){
		// System.out.println("================:"+str+":=============");
		// System.out.println(RegexpUtil.isMatch(str,
		// "[\u4E00-\u9FA5\uF900-\uFA2D]"));
		// System.out.println(RegexpUtil.isMatch(str, "[^\\x00-\\xff]"));
		// System.out.println(RegexpUtil.isMatch(str, "[\u4e00-\u9fa5]"));
		// System.out.println(strNum(str));
		// }
	}

	/**
	 * 去除字符串空格
	 * 
	 * @param s
	 *            字符串
	 * @return
	 */
	public static String trim(String s) {
		if (s == null) {
			return "";
		}

		s = s.replaceAll("　", " ");
		s = s.replaceAll("\\s+", " ");

		return s.trim();
	}

	/**
	 * 以\s+格式截取字符串返回list
	 * 
	 * @param s
	 *            字符串
	 * @return
	 */
	public static List split(String s) {
		List list = new ArrayList();

		s = trim(s);
		if (s == null) {
			return list;
		}
		String[] rs = s.split("\\s+");
		for (int i = 0; i < rs.length; i++) {
			if (rs[i].trim().length() > 0) {
				list.add(rs[i]);
			}
		}
		return list;
	}

	/**
	 * 截取字符串返回string数组
	 * 
	 * @param s
	 *            字符串
	 * @param delim
	 *            截取方式
	 * @return
	 */
	public static String[] split2Array(String s, String delim) {
		List list = split(s, delim);
		return (String[]) list.toArray(new String[0]);
	}

	/**
	 * 截取字符串
	 * 
	 * @param s
	 *            字符串
	 * @param delim
	 *            截取方式
	 * @return
	 */
	public static List split(String s, String delim) {
		List list = new ArrayList();

		s = trim(s);
		if (s == null) {
			return list;
		}
		String[] rs = s.split(delim);

		for (int i = 0; i < rs.length; i++) {
			if (rs[i].trim().length() > 0) {
				list.add(rs[i]);
			}
		}

		return list;
	}

	/**
	 * 字符在字符串中出现的次数
	 * 
	 * @param string
	 * @param a
	 * @return
	 */
	public static int occurTimes(String string, String a) {
		int pos = -2;
		int n = 0;

		while (pos != -1) {
			if (pos == -2) {
				pos = -1;
			}
			pos = string.indexOf(a, pos + 1);
			if (pos != -1) {
				n++;
			}
		}
		return n;
	}

	/**
	 * 是否为空 null 或 ""
	 * 
	 * @param s
	 *            字符串
	 * @return
	 */
	public static boolean nullOrSpace(String s) {
		if (s == null) {
			return true;
		}
		if (s.trim().length() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 是否为空
	 * 
	 * @param s
	 *            字符串
	 * @return
	 */
	public static boolean isNull(Object s) {
		return isBlank(nullValue(s));
	}

	public static boolean isNotNull(Object s) {
		return !isNull(s);
	}

	/**
	 * 返回字符串如果为null 则返回""
	 * 
	 * @param s
	 *            字符串
	 * @return
	 */
	public static String nullValue(String s) {
		return s == null ? "" : s.trim();
	}

	/**
	 * 返回字符串 如果为null 返回defaultValue
	 * 
	 * @param s
	 *            字符串
	 * @param defaultValue
	 *            默认值
	 * @return
	 */
	public static String nullValue(Object s, String defaultValue) {
		return s == null ? defaultValue : s.toString();
	}

	public static String defaultValue(String s, String defaultValue) {
		return isNull(s) ? defaultValue : s;
	}

	/**
	 * 重载方法
	 * 
	 * @param s
	 *            对象
	 * @return
	 */
	public static String nullValue(Object s) {
		return s == null ? "" : s.toString();
	}

	public static String LongValue(Long s) {
		return s == null || s.intValue() <= 0 ? "" : s.toString();
	}

	public static String LongValueZero(Long s) {
		return s == null || s.intValue() <= 0 ? "0" : s.toString();
	}

	/**
	 * 重载方法
	 * 
	 * @param s
	 * @return
	 */
	public static String nullValue(long s) {
		return s < 0 ? "" : String.valueOf(s);
	}

	/**
	 * 重载方法
	 * 
	 * @param s
	 * @return
	 */
	public static String nullValue(int s) {
		return s < 0 ? "" : "" + s;
	}

	/**
	 * 是否selected
	 * 
	 * @param arg
	 *            值
	 * @param selectedValue
	 *            值
	 * @return
	 */
	public static String isSelected(String arg, String selectedValue) {
		return (arg != null && arg.equals(selectedValue)) ? "selected" : "";
	}

	/**
	 * 是否checked
	 * 
	 * @param arg
	 *            值
	 * @param selectedValue
	 *            值
	 * @return
	 */
	public static String isChecked(String arg, String checkedValue) {
		return (arg != null && arg.equals(checkedValue)) ? "checked" : "";
	}

	/**
	 * 返回字符串如为null返回空
	 * 
	 * @param s
	 *            字符串
	 * @return
	 */
	public static String noNull(String s) {
		return s == null ? "" : s;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param s
	 *            字符串
	 * @return
	 */
	public static boolean isBlank(Object s) {
		return s == null || nullValue(s).trim().length() == 0;
	}

	/**
	 * @param queryString
	 * @return
	 */
	public static boolean isNotBlank(String s) {
		return !isBlank(s);
	}

	/**
	 * 首字母大写
	 * 
	 * @param s
	 *            字符串
	 * @return
	 */
	public static String upperCaseFirst(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	/**
	 * 首字母小写
	 * 
	 * @param s
	 * @return
	 */
	public static String lowerCaseFirst(String s) {
		return s.substring(0, 1).toLowerCase() + s.substring(1);
	}

	/**
	 * 字符串是否在字符串数组中 忽略大小写
	 * 
	 * @param url
	 *            字符串
	 * @param allUrl
	 *            字符串数组
	 * @return
	 */
	public static boolean in(String url, String[] allUrl) {
		for (int i = 0; i < allUrl.length; i++) {
			if (allUrl[i].equalsIgnoreCase(url)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 字符串是否在字符串数组中 不忽略大小写
	 * 
	 * @param url
	 *            字符串
	 * @param allUrl
	 *            字符串数组
	 * @return
	 */
	public static boolean inWithCase(String url, String[] allUrl) {
		for (int i = 0; i < allUrl.length; i++) {
			if (allUrl[i].equals(url)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 返回字符
	 * 
	 * @param n
	 *            第几个
	 * @return
	 */
	public static String getChar(int n) {
		return String.valueOf((char) n);
	}

	/**
	 * 返回字符
	 * 
	 * @param n
	 *            第几个
	 * @return
	 */
	public static String getCol(int n) {
		return String.valueOf((char) (n + 65));
	}

	/**
	 * 格式化sql
	 * 
	 * @param sql
	 *            字符串
	 * @return
	 */
	public static String escapeSql(String sql) {
		if (sql == null) {
			return null;
		}
		// TODO 非常重要，需要进一步丰富
		sql = sql.replaceAll("'", "''");
		sql = sql.replaceAll("_", "\\_");
		sql = sql.replaceAll("%", "\\%");
		sql = sql.replaceAll("\\(", "\\\\(");
		sql = sql.replaceAll("\\)", "\\\\)");
		return sql;
	}

	/**
	 * 格式化字符串并在前台加上空格
	 * 
	 * @param text
	 *            字符串
	 * @return
	 */
	public static String firstIndent(String text) {
		text = text.trim();
		text = text.replaceAll("&nbsp;", "");
		text = text.replaceAll("　", "");
		text = text.replaceAll("　", "");
		text = text.replaceAll("\\s+", "");
		return "&nbsp;&nbsp;&nbsp;&nbsp;" + text;
	}

	/**
	 * 
	 * @param password
	 * @param intensity
	 * @param pwdLength
	 * @return 密码强度 1 为低等强度 2为中等强度 3为高等强度
	 */
	public static int validPassword(String password, String intensity,
			int pwdLength) {
		String charGroup[][] = {
				{ "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
						"m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
						"x", "y", "z" },
				{ "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
						"M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
						"X", "Y", "Z" },
				{ "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" } };
		int level = 0;
		boolean flagGroup[] = { false, false, false };
		for (int i = 0; i < charGroup.length; i++) {
			String charSmallGroup[] = charGroup[i];
			for (int j = 0; j < password.length(); j++) {
				if (flagGroup[i] == true) {
					continue;
				}
				String str = password.substring(j, j + 1);
				if (inWithCase(str, charSmallGroup)) {
					level++;
					flagGroup[i] = true;
				}
			}
		}

		return level;
	}

	/**
	 * 左边补零以满足长度要求
	 * 
	 * resultLength 最终长度
	 * */
	public static String addZeroLeft(String arg, int resultLength) {
		if (arg == null)
			return "";
		String result = arg;
		if (result.length() < resultLength) {
			for (int i = result.length(); i < resultLength; i++) {
				result = "0" + result;
				if (result.length() == resultLength)
					break;
			}
		}
		return result;
	}

	/**
	 * 获取相同字符的个数；
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static int getSameCharCount(String str1, String str2) {
		int count = 0;
		int start = str2.length();
		int i = 0;
		if (str1 != null && str1 != "") {
			while (i < str1.length() && count < 5) {
				if (str1.indexOf(str2, i) != -1) {
					count++;
					i = i + start;
				} else {
					i++;
					if (count == 0 && i >= 50) {
						break;
					}
				}
			}
		}
		return count;
	}

	/**
	 * 判断字符是否以指定字符开头
	 * 
	 * @param source
	 * @param prefix
	 * @return
	 */
	public static boolean startsWith(String source, String prefix) {
		return source.startsWith(prefix);
	}

	public static boolean endsWith(String source, String prefix) {
		return source.endsWith(prefix);
	}

	public static String replaceFirst(String source, String regex, String prefix) {
		return source.replaceFirst(regex, prefix);
	}

	public static String replaceLast(String source, String regex, String prefix) {
		int index = lastIndexOf(source, regex);
		if (index <= 0) {
			return source;
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append(source.substring(0, index - 1)).append(prefix)
				.append(source.substring(index - 1 + regex.length()));
		return buffer.toString();
	}

	static int lastIndexOf(String source, String str) {
		int i = 0, indexof = 0;
		while (indexof >= 0) {
			i = indexof + 1;
			indexof = source.indexOf(str, i);
		}
		return i;
	}

	public static boolean isNumber(String curPage) {
		return RegexpUtil.isMatch(curPage, "^[0-9]+$");
	}

	public static Object equals(String s1, String s2, Object v1, Object v2) {
		return s1.equals(s2) ? v1 : v2;
	}


	public static String capitalize(String field) {
		return field.substring(0, 1).toUpperCase().concat(field.substring(1));
	}

	public static String escapeHtml(String html) {
		String htmlStr = html; // 含html标签的字符串
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;

		java.util.regex.Pattern p_html1;
		java.util.regex.Matcher m_html1;

		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
			// }
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
			// }
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
			String regEx_html1 = "<[^>]+";
			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签

			p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
			m_html1 = p_html1.matcher(htmlStr);
			htmlStr = m_html1.replaceAll(""); // 过滤html标签

			htmlStr = htmlStr.replace("\"", "“");
			htmlStr = htmlStr.replaceAll("\\s", "&nbsp;");
			textStr = htmlStr;

		} catch (Exception e) {
			textStr = html;
			System.err.println("Html2Text: " + e.getMessage());
		}

		return textStr;// 返回文本字符串
	}

	public static String encodeURI(String s, String enc)
			throws UnsupportedEncodingException {
		return StringUtil.isBlank(s) ? s : URLEncoder.encode(s, enc);
	}

	public static String decodeURI(String s, String enc)
			throws UnsupportedEncodingException {
		return StringUtil.isBlank(s) ? s : URLDecoder.decode(s, enc);
	}

	/**
	 * 转义 特殊符
	 */
	public static String escapeSpecialSign(String condition) {
		String bb = StringUtils.replace(condition, "/", "//");
		bb = StringUtils.replace(bb, "%", "/%");
		bb = StringUtils.replace(bb, "_", "/_");
		return bb;
	}

	/**
	 * 解决中文乱码问题
	 * 
	 * @param name
	 * @param defaultVal
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getStrParam(String name)
			throws UnsupportedEncodingException {
		String str = new String(name.getBytes("8859_1"), "UTF-8");
		return str;
	}

	/**
	 * 替换指定位置字符串
	 * 
	 * @param beginIndex
	 *            开始索引
	 * @param endIndex
	 *            结束索引
	 * @param replacStr
	 *            替换字符串
	 * @param replaceValue
	 *            替换值
	 * @return
	 */
	public static String replaceStrByIndex(int beginIndex, int endIndex,
			String replacStr, String replaceValue)
			throws UnsupportedEncodingException {
		// 判断替换的字符串是否为空
		if (isNull(replacStr)) {
			return "";
		}
		// 判断开始索引是否超过字符串的长度
		if (beginIndex >= replacStr.length()) {
			return replacStr;
		}
		// 结束索引是否超过字符串的长度
		if (endIndex >= replacStr.length() || 0 == endIndex) {
			endIndex = replacStr.length() - 1;
		}
		// 进行字符串替换
		char[] ch = replacStr.toCharArray();
		for (int j = beginIndex; j <= endIndex; j++) {
			ch[j] = replaceValue.charAt(0);
		}
		replacStr = String.valueOf(ch);
		return replacStr;
	}
	
	public static final String full2HalfChange(String QJstr)  
	throws UnsupportedEncodingException {  
		if(QJstr == null) return "";
	    StringBuffer outStrBuf = new StringBuffer("");  
	    String Tstr="";  
	    byte[] b=null;  
	    for(int i=0;i < QJstr.length();i++ ) {  
	    	Tstr=QJstr.substring(i,i+1);  
	    	// 全角空格转换成半角空格  
	    	if ( Tstr.equals("　") ){  
		      outStrBuf.append( " " );  
		      continue;  
	    	}  
	    	b=Tstr.getBytes("unicode"); // 得到 unicode 字节数据  
	    	if (b[3]==-1) { // 表示全角？  
	    		b[2]=(byte)(b[2]+32);  
	    		b[3]=0;  
	    		outStrBuf.append( new String(b,"unicode") );  
	    	}else {  
	    		outStrBuf.append( Tstr );  
	    	}  
	    } // end for.  
	   return outStrBuf.toString();   
	} 
	
	public static String instead(String str){
		String e = "";
		String l = "";
		if (StringUtil.isNotNull(str)) {
			str = str.replace(" ", "");
			if (str.indexOf("银行") != -1) {
				String a[] = str.split("银行");
				str = str.replace(a[0], "**");
				e = a[1].substring(1, a[1].length() - 1);
			} else {
				e = str.substring(1, str.length() - 1);
			}

			for (int i = 0; i < e.length(); i++) {
				l = l + "*";
			}
			str = str.replace(e, l);
		}
        
		return str;
	}
	
	
}
