package org.wl.core.util.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.wl.core.util.ognl.OgnlUtil;
import org.wl.core.util.reflect.ClassUtil;
import org.wl.core.util.reflect.Property;

/**
 * 
 * 对象处理集合类 主要功能：排序，克隆，比较属性，拷贝等方法
 * 
 */
@SuppressWarnings("unchecked")
public class ObjectUtil {

	private static final ConcurrentMap<String, Comparator<?>> comparatorMap = new ConcurrentHashMap<String, Comparator<?>>();

	/**
	 * 调整list的顺序
	 * 
	 * @param list
	 *            数组
	 * @param field
	 *            字段
	 * @param value
	 *            值
	 * @param seqField
	 *            排序字段
	 * @param seqOffset
	 *            调换位置
	 * @return
	 */
	public static List adjustSeq(List list, String field, Object value, String seqField, int seqOffset) {
		List modifiedList = new ArrayList();
		if (seqOffset == 0) {
			return modifiedList;
		}

		int m = -1;
		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i);
			Object prop = ClassUtil.getValue(obj, field);
			if (prop == null) {
				continue;
			}
			if (prop.equals(value)) {
				m = i;
				break;
			}
		}

		Object target = list.remove(m);
		int newPos = m + seqOffset;
		if (newPos >= list.size()) {
			newPos = list.size() - 1;
		}
		if (newPos < 0) {
			newPos = 0;
		}
		System.out.println(newPos + "");
		list.add(newPos, target);

		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i);
			setProperties(obj, seqField, new Integer(i));
			modifiedList.add(obj);
		}
		return modifiedList;
	}

	/**
	 * 克隆对象
	 * 
	 * @param o1
	 *            对象
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static <T> T clone(T o1) throws IOException, ClassNotFoundException {
	  ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
	  ObjectOutputStream out=new ObjectOutputStream(byteOut);
	  out.writeObject(o1);
	  ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
	  ObjectInputStream in = new ObjectInputStream(byteIn);
	  return (T)in.readObject();
	}

	
	
	/**
	 * 比较对象中的字段
	 * 
	 * @param o1
	 *            对象1
	 * @param o2
	 *            对象2
	 * @param orderField
	 *            字段
	 * @return
	 */
	private static int compareField(Object o1, Object o2, final String orderField) {
		try {
			Object f1 = OgnlUtil.getInstance().getValue(orderField,o1);
			Object f2 = OgnlUtil.getInstance().getValue(orderField,o2);
			Object[] ary = new Object[] { f1, f2 };
			if (f1 instanceof String && f2 instanceof String) {
				Comparator cmp = Collator.getInstance(java.util.Locale.CHINA);
				Arrays.sort(ary, cmp);
			} else {
				Arrays.sort(ary);
			}
			if (ary[0].equals(f1)) {
				return -1;
			} else {
				return 1;
			}
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 数组中对象字段名转成对象数组
	 * 
	 * @param objs
	 *            数组
	 * @param fieldName
	 *            字段名
	 * @return
	 */
	public static <T> List<T> toFieldArray(List objs, String fieldName, Class<T> fieldType) {
		List<T> array = new ArrayList<T>();
		for (Object object : objs) {
			array.add((T) ClassUtil.getValue(object, fieldName));
		}
		return array;
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		 * List list = new ArrayList(); BlogArticle a1 = new BlogArticle();
		 * a1.setTitle("title-1"); a1.setId(new Long(1)); BlogArticle a2 = new
		 * BlogArticle(); a2.setTitle("title-2"); a2.setId(new Long(2));
		 * list.add(a1); list.add(a2);
		 * 
		 * Object[] s = toFieldArray(list, "id",CallBackUtil.TOSTRING);
		 * System.out.println(toString(s)); s = toFieldArray(new
		 * Object[]{a1,a2}, "id"); System.out.println(toString(s));
		 */
	}

	/**
	 * 获取数组中最大的对象
	 * 
	 * @param c
	 *            数组
	 * @param fieldName
	 *            字段名
	 * @return
	 */
	public static <T> T getMaxObject(Collection<T> c, String fieldName) {
		T maxObject = null;
		for (Iterator<T> iter = c.iterator(); iter.hasNext();) {
			T element = iter.next();
			if (maxObject == null) {
				maxObject = element;
			} else {
				Object maxValue = ClassUtil.getValue(maxObject, fieldName);
				Object theValue = ClassUtil.getValue(element, fieldName);
				if (compareField(maxValue, theValue, fieldName) == 1) {
					maxObject = element;
				}
			}
		}
		return maxObject;
	}

	/**
	 * 获取数组中最小的对象
	 * 
	 * @param c
	 *            数组
	 * @param fieldName
	 *            字段名
	 * @return
	 */
	public static <T> T getMinObject(Collection<T> c, String fieldName) {
		T minObject = null;
		for (Iterator<T> iter = c.iterator(); iter.hasNext();) {
			T element = iter.next();
			if (minObject == null) {
				minObject = element;
			} else {
				Object minValue = ClassUtil.getValue(minObject, fieldName);
				Object theValue = ClassUtil.getValue(element, fieldName);
				if (compareField(minValue, theValue, fieldName) == -1) {
					minObject = element;
				}
			}
		}
		return minObject;
	}

	/**
	 * 验证对象是否中数组对象中并返回此对象
	 * 
	 * @param list
	 *            数组
	 * @param field
	 *            字段
	 * @param value
	 *            对象
	 * @return
	 */
	public static <T> int indexOf(List<T> list, String field, Object value) {
		for (int i = 0; i < list.size(); i++) {
			T obj = list.get(i);
			Object prop = ClassUtil.getValue(obj, field);
			if (prop == null) {
				continue;
			}
			if (prop.equals(value)) {
				return i;
			}
		}
		return -1;
	}

	public static <T> int indexOf(T[] objs, T o) {
		for (int i = 0; i < objs.length; i++) {
			if (objs[i].equals(o))
				return i;
		}
		return -1;
	}

	public static <T> int indexOf(List<T> objs, T o) {
		for (int i = 0; i < objs.size(); i++) {
			if (isNotNull(objs.get(i)) && objs.get(i).equals(o))
				return i;
		}
		return -1;
	}

	/**
	 * 验证对象是否中数组对象中并返回此对象在数组中的位置
	 * 
	 * @param obj
	 *            对象
	 * @param list
	 *            数组
	 * @param property
	 *            属性名
	 * @return
	 * @throws Exception
	 */
	public static <T> int indexOf(List<T> list, T obj, String property) {
		for (int i = 0; i < list.size(); i++) {
			Object value = ClassUtil.getValue(list.get(i), property);
			if (value.equals(ClassUtil.getValue(obj, property))) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 不验证大小写判断数组中是否有此字符串
	 * 
	 * @param list
	 *            数组
	 * @param field
	 *            字段名
	 * @param value
	 *            字符串
	 * @return
	 */
	public static <T> T indexOfString(List<T> list, String field, String value) {
		for (int i = 0; i < list.size(); i++) {
			T obj = list.get(i);
			Object prop = ClassUtil.getValue(obj, field);
			if (prop == null) {
				continue;
			}
			if (value.equalsIgnoreCase(StringUtil.nullValue(prop))) {
				return obj;
			}
		}
		return null;
	}

	/**
	 * 为对象设置属性
	 * 
	 * @param obj
	 *            对象
	 * @param fieldName
	 *            字段名
	 * @param value
	 *            值
	 */
	public static void setProperties(Object obj, String fieldName, Object value) {
		try {
			BeanUtil.setValue(obj, fieldName, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> List<T> sort(Collection<T> collectoin, final String orderField) {
		return sort(collectoin, orderField, "asc");
	}

	/**
	 * 为数组以desc属性排序如asc,desc 例如sor(List<T>, "createTime","desc");
	 * 
	 * @param collectoin
	 *            数组
	 * @param orderBy
	 *            排序字段名
	 * @param order
	 *            排序方式
	 * @return
	 */
	public static <T> List<T> sort(Collection<T> collectoin, String orderBy, String order) {
		List<T> list = new ArrayList<T>();
		if (collectoin == null || collectoin.isEmpty()) {
			return list;
		}
		String key = collectoin.iterator().next().getClass().toString().concat("|").concat(orderBy);
		if (!comparatorMap.containsKey(key)) {
			final String _orderBy = orderBy;
			comparatorMap.put(key, new Comparator<T>() {
				public int compare(Object o1, Object o2) {
					return compareField(o1, o2, _orderBy);
				}
			});
		}
		list.addAll(collectoin);
		Collections.sort(list, (Comparator<T>) comparatorMap.get(key));
		if ("desc".equalsIgnoreCase(order)) {
			Collections.reverse(list);
		}
		return list;
	}

	/**
	 * 根据list中某个属性的值返回新的列表
	 * 
	 * @param list
	 * @param field
	 *            属性名
	 * @param value
	 *            条件的值
	 * @return
	 */
	public static <T> List<T> sublist(List<T> list, String field, Object... values) {
		List<T> l = new ArrayList<T>();
		for (int i = 0; i < list.size(); i++) {
			T obj = list.get(i);
			Object prop = ClassUtil.getValue(obj, field);
			if (prop == null)
				continue;
			for (int j = 0; j < values.length; j++) {
				if (prop.equals(values[j])) {
					l.add(obj);
					break;
				}
			}
		}
		return l;
	}

	/**
	 * 重载 sublist方法
	 * 
	 * @param list
	 *            数组
	 * @param from
	 *            开始位置
	 * @param to
	 *            结束位置
	 * @return
	 */
	public static <T> List<T> subList(List<T> list, int from, int to) {
		if (from > list.size() || from < 0) {
			from = 0;
		}
		if (to > list.size() || to < 0) {
			to = list.size();
		}
		return list.subList(from, to);
	}

	/**
	 * 判断对象是否未NULL
	 * 
	 * @param object
	 * @return
	 */
	public static boolean isNull(Object object) {
		return object == null;
	}

	public static boolean isNotNull(Object object) {
		return !isNull(object);
	}

	public static boolean toBoolean(Object object) {
		if (!ObjectUtil.isNull(object)) {
			try {
				return (boolean) new Boolean(StringUtil.nullValue(object));
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	/**
	 * 判断source是否为空,如果为NULL 返回def
	 * 
	 * @param source
	 * @param def
	 *            为NULL时的默认值
	 * @return
	 */
	public static <T> T defaultValue(T source, T def) {
		return isNull(source) ? def : source;
	}

	/**
	 * 返回集合或者数组的长度
	 * 
	 * @param value
	 * @return
	 */
	public static int length(Object value) {
		if (ClassUtil.isArray(value)) {
			return Array.getLength(value);
		} else if (ClassUtil.isList(value)) {
			return ((List<?>) value).size();
		} else
			return 0;
	}

	/**
	 * 返回集合或者数组对应索引的值
	 * 
	 * @param value
	 * @param i
	 * @return
	 */
	public static Object get(Object value, int i) {
		if (ClassUtil.isArray(value)) {
			return Array.get(value, i);
		} else if (ClassUtil.isList(value)) {
			return ((List<?>) value).get(i);
		} else
			return null;
	}

	public static Map<String, Object> toMap(Object data) {
		if (ClassUtil.isMap(data)) {
			return (Map<String, Object>) data;
		}
		Map<String, Object> rootMap = new HashMap<String, Object>();
		Property[] properties = ClassUtil.getPropertys(data);
		for (Property property : properties) {
			if (property.isRead())
				rootMap.put(property.getName(), property.getValue(data));
		}
		return rootMap;
	}

	public final static boolean isEmpty(String str) {
		if (str == null)
			return true;

		if (str.trim().equals(""))
			return true;

		return false;
	}

	public static <T> void add(List<T> dest, List<T> orig) {
		add(dest, orig, null);
	}

	public static <T> void add(List<T> dest, List<T> orig, String property) {
		List<T> news = new ArrayList<T>();
		for (T o : orig) {
			if (ObjectUtil.isNotNull(property) && ObjectUtil.indexOf(dest, o, property) == -1) {
				news.add(o);
			} else if (dest.indexOf(o) == -1) {
				news.add(o);
			}
		}
		dest.addAll(news);
	}

	public static <T> void add(List<T> dest, T t, String property) {
		if (ObjectUtil.indexOf(dest, t, property) == -1)
			dest.add(t);
	}

	public static <T> T copy(T dest, Object orig, String... excludeProperties) {
		return BeanUtil.copy(dest, orig, excludeProperties);
	}

	public static <T> Boolean exists(List<T> list, Object object) {
		for (T t : list) {
			if (t.getClass().isEnum()) {
				if (t.toString().equals(object))
					return true;
			} else {
				if (t.equals(object))
					return true;
			}
		}
		return false;
	}

	public static <T> List<T> remove(List<T> orig, String property, Object value) {
		for (int i = indexOf(orig, property, value); i != -1; i = indexOf(orig, property, value))
			orig.remove(i);
		return orig;
	}

	public static <T> T[] remove(T[] dest, T orig) {
		List<T> array = Arrays.asList(dest);
		array.remove(orig);
		return (T[]) array.toArray(new Object[0]);
	}

	public static List<Map<String, String>> sortBydesc(List<Map<String, String>> list, final String orderField) {
		if (list == null || list.isEmpty()) {
			return list;
		}
		Collections.sort(list, new Comparator<Map<String, String>>() {
			public int compare(Map<String, String> o1, Map<String, String> o2) {
				int ret = 0;
				try {
					ret = o1.get(orderField).compareTo(o2.get(orderField));
				} catch (Exception e) {

				}
				return ret;
			}
		});
		return list;
	}
}
