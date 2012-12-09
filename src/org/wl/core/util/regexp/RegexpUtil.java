package org.wl.core.util.regexp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.wl.core.util.common.StringUtil;
/**
 * 验证相关的方法
 */
public class RegexpUtil {
    
    private static final Log logger = LogFactory.getLog(RegexpUtil.class);

    private static ConcurrentHashMap<String,Pattern> patternCache = new ConcurrentHashMap<String,Pattern>();
    
	public RegexpUtil() {
	}
	
    /**
     * 
     * @param patternString 验证规则
     * @return
     */
	public static Pattern getPattern(String patternString) {
		if (StringUtil.isBlank(patternString)) {
			throw new RuntimeException("pattern string is space");
		}
		synchronized(patternCache){
			if(!patternCache.containsKey(patternString)){
				logger.debug("缓存表达式:"+patternString);
				patternCache.put(patternString,Pattern.compile(patternString, Pattern.CASE_INSENSITIVE));
			}
		}
		return patternCache.get(patternString);
	}

	public static Group[] parseGroups(String input, String regEx) {
		return parseGroups(input,getPattern(regEx));
	}
	
	public static Group[] parseGroups(String input,Pattern pattern) {
		List<Group> listGroup = new ArrayList<Group>();
		Matcher matcher = pattern.matcher(input);
		while (matcher.find()) {
			String[] g = new String[matcher.groupCount()+1];
			for (int i=0;i<g.length;i++) {
				g[i] = matcher.group(i);
			}
			listGroup.add(new Group(g));
		}
		return (Group[]) listGroup.toArray(new Group[0]);
	}
	
	public static Pattern unitePattern(Pattern[] patterns) {
		if (patterns == null) {
			throw new IllegalArgumentException("patterns can't is null.");
		}
		if (patterns.length == 0) {
			throw new IllegalArgumentException("the length of patterns can't is zero.");
		}
		StringBuffer buf = new StringBuffer("(" + patterns[0].pattern() + ")");
		for (int i = 1; i < patterns.length; i++) {
			buf.append("|(" + patterns[i].pattern() + ")");
		}
		return getPattern(buf.toString());
	}

//	public static Map getAttribute(String input) {
//		Map m = new HashMap();
//		Matcher matcher = RegexpCst.ATTR.matcher(input);
//		while (matcher.find()) {
//			m.put(matcher.group(1), matcher.group(2));
//		}
//		return m;
//	}
	
	public static Long parseNumber(String s) {
		String r = RegexpUtil.parseGroup(s,"(\\d+)", 1);
		if (r == null) {
			return null;
		}
		if ("".equals(r.trim())) {
			return null;
		}
		return new Long(r);
	}
	
    public static boolean isMatch(String input,String regEx) {
        Pattern pattern = RegexpUtil.getPattern(regEx);
        return isMatch(input, pattern);
    }

	private static boolean isMatch(String input, Pattern pattern) {
		Matcher matcher = pattern.matcher(input);
		return matcher.find();
	}
    
    public static String group(String regEx, String input, int i) {
        return RegexpUtil.parseGroup(input,regEx, i);
    }
    
    /**
     * 匹配正则表达式(可能有多个)
     * 只要匹配上一个既返回true
     * @param input
     * @param regExs
     * @return
     */
    public static boolean find(String input,String... regExs) {
    	if(regExs.length <= 1){
    		return regExs.length == 0 ? false : isMatch(input,regExs[0]);
    	}else{
    		for(String regEx : regExs){
    			if(isMatch(input,regEx)){
    				return true;
    			}
    		}
    		return false;
    	}
	}
    
	public static String parseFirst(String input, String regEx) {
		if(parseGroups(input,regEx).length > 0 && parseGroups(input,regEx)[0].groups.length > 0)
			return parseGroups(input,regEx)[0].$(0);
		return null;
	}
	
	public static String parseGroup(String input, String regEx,int group) {
		if(parseGroups(input,regEx).length > 0 && parseGroups(input,regEx)[0].groups.length > group)
			return parseGroups(input,regEx)[0].$(group);
		return null;
	}
	/**
	 * 替换匹配到的第一个元素
	 * @param input
	 * @param regEx
	 * @param replacement
	 * @return
	 */
	public static String replaceFirst(String input,String regEx,String replacement){		
		Pattern pattern = RegexpUtil.getPattern(regEx);
		return pattern.matcher(input).replaceFirst(replacement);
	}
	
	/**
	 * 替换匹配到的元素
	 * @param input
	 * @param regEx
	 * @param replacement
	 * @return
	 */
	public static String replace(String input,String regEx,String replacement){		
		Pattern pattern = RegexpUtil.getPattern(regEx);
		return pattern.matcher(input).replaceAll(replacement);
	}	

	/**
	 * 将String中的所有regex匹配的字串全部替换掉
	 * 
	 * @param string
	 *            代替换的字符串
	 * @param regex
	 *            替换查找的正则表达式
	 * @param replacement
	 *            替换函数
	 * @return
	 */
	public static String replace(String string, String regex,ReplaceCallBack replacement) {
		return replace(string, getPattern(regex), replacement);
	}

	/**
	 * 将String中的所有pattern匹配的字串替换掉
	 * 
	 * @param string
	 *            代替换的字符串
	 * @param pattern
	 *            替换查找的正则表达式对象
	 * @param replacement
	 *            替换函数
	 * @return
	 */
	public static String replace(String string, Pattern pattern,ReplaceCallBack replacement) {
		if (string == null) {
			return null;
		}
		Matcher m = pattern.matcher(string);
		if (m.find()) {
			StringBuffer sb = new StringBuffer();
			int index = 0;
			while (true) {
				String st = replacement.replace(m.group(0),index++,m);
				m.appendReplacement(sb, st);
				if (!m.find()){
					break;
				}
			}
			m.appendTail(sb);
			return sb.toString();
		}
		return string;
	}

	/**
	 * 将String中的regex第一次匹配的字串替换掉
	 * 
	 * @param string
	 *            代替换的字符串
	 * @param regex
	 *            替换查找的正则表达式
	 * @param replacement
	 *            替换函数
	 * @return
	 */
	public static String replaceFirst(String string, String regex,ReplaceCallBack replacement) {
		return replaceFirst(string, Pattern.compile(regex), replacement);
	}

	/**
	 * 将String中的pattern第一次匹配的字串替换掉
	 * 
	 * @param string
	 *            代替换的字符串
	 * @param pattern
	 *            替换查找的正则表达式对象
	 * @param replacement
	 *            替换函数
	 * @return
	 */
	public static String replaceFirst(String string, Pattern pattern,ReplaceCallBack replacement) {
		if (string == null) {
			return null;
		}
		Matcher m = pattern.matcher(string);
		StringBuffer sb = new StringBuffer();
		if (m.find()) {
			m.appendReplacement(sb, replacement.replace(m.group(0), 0, m));
		}
		m.appendTail(sb);
		return sb.toString();
	}

	public static interface ReplaceCallBack {

		String replace(String group, int i, Matcher m);
		
	}
	
	/**
	 * 抽象的字符串替换接口
	 * 主要是添加了$(group)方法来替代matcher.group(group)
	 */
	public static abstract class AbstractReplaceCallBack implements ReplaceCallBack {
		protected Matcher matcher;

		final public String replace(String text, int index, Matcher matcher) {
			this.matcher = matcher;
			try {
				return doReplace(text, index, matcher);
			} finally {
				this.matcher = null;
			}
		}

		/**
		 * 将text转化为特定的字串返回
		 * @param text
		 *            指定的字符串
		 * @param index
		 *            替换的次序
		 * @param matcher
		 *            Matcher对象
		 * @return
		 */
		public abstract String doReplace(String text, int index, Matcher matcher);

		/**
		 * 获得matcher中的组数据
		 * 等同于matcher.group(group)
		 * 该函数只能在{@link #doReplace(String, int, Matcher)} 中调用
		 * @param group
		 * @return
		 */
		protected String $(int group) {
			String data = matcher.group(group);
			return data == null ? "" : data;
		}
	}
	
	public static class Group{
		private String[] groups;
		
		protected Group(String[] groups){
			this.groups = groups;
		}
		
		public String $(int group) {
			return this.groups[group];
		}

		@Override
		public String toString() {
			return "Group [groups=" + Arrays.toString(groups) + "]";
		}
		
	}
	
	/*		通配符		*/
	
	/*
	private static boolean wildMatch(String pattern, String str) {
        pattern = toJavaPattern(pattern);
        return java.util.regex.Pattern.matches(pattern, str);
    }

    private static String toJavaPattern(String pattern) {
        String result = "^";
        char metachar[] = { '$', '^', '[', ']', '(', ')', '{', '|', '*', '+', '?', '.', '//' };
        for (int i = 0; i < pattern.length(); i++) {
            char ch = pattern.charAt(i);
            boolean isMeta = false;
            for (int j = 0; j < metachar.length; j++) {
                if (ch == metachar[j]) {
                    result += "/" + ch;
                    isMeta = true;
                    break;
                }
            }
            if (!isMeta) {
                if (ch == '*') {
                    result += ".*";
                } else {
                    result += ch;
                }

            }
        }
        result += "$";
        return result;
    }
    
    public static void main(String[] args) {
          test("*", "toto");
          test("toto.java", "tutu.java");
          test("12345", "1234");
          test("1234", "12345");
          test("*f", "");
          test("***", "toto");
          test("*.java", "toto.");
          test("*.java", "toto.jav");
          test("*.java", "toto.java");
          test("abc*", "");
          test("a*c", "abbbbbccccc");
          test("abc*xyz", "abcxxxyz");
          test("*xyz", "abcxxxyz");
          test("abc**xyz", "abcxxxyz");
          test("abc**x", "abcxxx");
          test("*a*b*c**x", "aaabcxxx");
          test("abc*x*yz", "abcxxxyz");
          test("abc*x*yz*", "abcxxxyz");
          test("a*b*c*x*yf*z*", "aabbccxxxeeyffz");
          test("a*b*c*x*yf*zze", "aabbccxxxeeyffz");
          test("a*b*c*x*yf*ze", "aabbccxxxeeyffz");
          test("a*b*c*x*yf*ze", "aabbccxxxeeyfze");
          test("*LogServerInterface*.java", "_LogServerInterfaceImpl.java");
          test("abc*xyz", "abcxyxyz");
    }

    private static void test(String pattern, String str) {
        System.out.println(pattern+" " + str + " =>> " + wildMatch(pattern, str));        
    }
    */
}
