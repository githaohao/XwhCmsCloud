package com.xwh.core.utils;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName: BlankUtils
 * @Description: 判断对象是否为空
 */
public class BlankUtils {
	/**
	 * 判断字符串是否为空
	 * @param string 字符串
	 * @return 返回布尔值，true表示字符串为null或空字符串
	 */
	public static boolean isBlank(final String string) {
		return string == null || string.trim().length() <= 0;
	}

	public static boolean isNotBlank(final String string) {
		return !isBlank(string);
	}

	/**
	 * 判断字符是否为空
	 * @param character字符
	 * @return 返回布尔值，true表示字符为null或空字符
	 */
	public static boolean isBlank(final Character character) {
		return character == null || Character.isWhitespace(character.charValue());
	}

	public static boolean isNotBlank(final Character character) {
		return !isBlank(character);
	}

	/**
	 * 判断字符是否为空
	 * @param character 字符
	 * @return 返回布尔值，true表示字符为null或空字符
	 */
	public static boolean isBlank(final StringBuilder stringBuilder) {
		return stringBuilder == null || stringBuilder.length() <= 0;
	}

	public static boolean isNotBlank(final StringBuilder stringBuilder) {
		return !isBlank(stringBuilder);
	}

	/**
	 * 判断字符是否为空
	 * @param character 字符
	 * @return 返回布尔值，true表示字符为null或空字符
	 */
	public static boolean isBlank(final StringBuffer stringBuffer) {
		return stringBuffer == null || stringBuffer.length() <= 0;
	}

	public static boolean isNotBlank(final StringBuffer stringBuffer) {
		return !isBlank(stringBuffer);
	}

	/**
	 * 判断对象是否为空
	 * @param object 对象
	 * @return 返回布尔值，true表示对象为null
	 */
	public static boolean isBlank(final Object object) {
		return object == null;
	}

	public static boolean isNotBlank(final Object object) {
		return !isBlank(object);
	}

	/**
	 * 判断数组是否为空
	 * @param objects 数组
	 * @return 返回布尔值，true表示数组为null或空数组
	 */
	public static boolean isBlank(final Object[] objects) {
		return objects == null || objects.length <= 0;
	}

	public static boolean isNotBlank(final Object[] objects) {
		return !isBlank(objects);
	}

	/**
	 * 判断集合是否为空
	 * @param collection 集合对象
	 * @return 返回布尔值，true表示集合为null或空集合
	 */
	public static boolean isBlank(final Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}

	public static boolean isNotBlank(final Collection<?> collection) {
		return !isBlank(collection);
	}

	/**
	 * 判断散列是否为空
	 * @param set 散列
	 * @return 返回布尔值，true表示散列为null或空散列
	 */
	public static boolean isBlank(final Set<?> set) {
		return set == null || set.isEmpty();
	}

	public static boolean isNotBlank(final Set<?> set) {
		return !isBlank(set);
	}

	/**
	 * 判断键值对是否为空
	 * @param map 键值对
	 * @return 返回布尔值，true表示键值对为null或空键值对
	 */
	public static boolean isBlank(final Map<?, ?> map) {
		return map == null || map.isEmpty();
	}

	public static boolean isNotBlank(final Map<?, ?> map) {
		return !isBlank(map);
	}
}
