/*
 * $Id$
 * 版权所有 2014 Matrix。
 * 保留所有权利。
 */
package net.matrix.sql.hibernate.type;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.jadira.usertype.spi.shared.AbstractStringColumnMapper;

/**
 * 映射字符串列表值到字符串字段。
 */
public class StringColumnStringListMapper
	extends AbstractStringColumnMapper<List<String>> {
	private static final long serialVersionUID = -3448788221055335510L;

	private String separator;

	private Pattern pattern;

	public void setSeparator(String separator) {
		this.separator = separator;
		this.pattern = Pattern.compile(separator);
	}

	@Override
	public List<String> fromNonNullValue(String value) {
		List<String> result = new ArrayList<String>();
		String[] list = pattern.split(value);
		for (String item : list) {
			result.add(item);
		}
		return result;
	}

	@Override
	public String toNonNullValue(List<String> value) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < value.size(); i++) {
			if (i != 0) {
				sb.append(separator);
			}
			sb.append(value.get(i));
		}
		return sb.toString();
	}
}
