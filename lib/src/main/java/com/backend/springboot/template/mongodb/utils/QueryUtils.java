package com.backend.springboot.template.mongodb.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public final class QueryUtils {

	@SuppressWarnings("serial")
	private static final Map<Character, String>	DIACRITICS	= Collections
																	.unmodifiableMap(new HashMap<Character, String>() {
																		{
																			put('a', "aàâàáâãäå");
																			put('c', "cç");
																			put('e', "eèéêë");
																			put('i', "iìíîï");
																			put('n', "nñ");
																			put('o', "oòóôõö");
																			put('u', "uùúûü");
																			put('y', "yýÿ");
																		}
																	});

	private QueryUtils() {
		super();
	}


	/**
	 * Transform each valu {@link Pattern} regex to do an sql 'like value%' command
	 *
	 * @param keyWords
	 * @return {@link Pattern} regex
	 */
	public static Pattern convertToRegex(List<String> values) {
		if (values == null) {
			return null;
		}
		StringBuilder regexKeyWords = new StringBuilder();
		String current = null;
		for (int i = 0; i < values.size(); i++) {
			current = values.get(i);
			if (current == null) {
				throw new NullPointerException("param null");
			}
			if (i > 0) {
				regexKeyWords.append("|");
			}
			regexKeyWords.append(manageDiacritic(current)).append(".*$");
		}
		return Pattern.compile(regexKeyWords.toString(), Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
	}

	private static String manageDiacritic(String value) {
		StringBuilder result = new StringBuilder();

		// Suppression des accents + mise en minuscule
		String valueWithoutAccents = StringUtils.stripAccents(value).toLowerCase();

		for (int i = 0; i < valueWithoutAccents.length(); i++) {
			char c = valueWithoutAccents.charAt(i);
			result.append('[');
			result.append(DIACRITICS.getOrDefault(c, String.valueOf(c)));
			result.append(']');
		}

		return result.toString();
	}


}
