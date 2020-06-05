package com.who.read.reading.who.util;

import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

/**
 * @Classname UUID
 * @date 2020/6/3 11:15 AM
 * @Created by fengjiadong
 */
public class UUID {
	private static final Pattern UUID_PATTERN = Pattern.compile("^[a-z0-9A-Z]{32}$");

	public UUID() {
	}

	public static String generateUUID(char separator) {
		return separator != '-' ? java.util.UUID.randomUUID().toString().replace('-', separator) : java.util.UUID.randomUUID().toString();
	}

	public static String generateUUID() {
		return java.util.UUID.randomUUID().toString().replace("-", "");
	}

	public static boolean likeUUID(String uuid) {
		return StringUtils.isEmpty(uuid) ? false : UUID_PATTERN.matcher(uuid).matches();
	}
}
