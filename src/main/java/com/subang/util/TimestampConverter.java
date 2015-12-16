package com.subang.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

/**
 * @author Qiang 用于TicketType，Ticket中deadline的转换，把日期转换为日期时间
 */
public class TimestampConverter implements Converter<String, Timestamp> {

	public Timestamp convert(String arg0) {
		Timestamp ts = null;
		if (arg0.length() == 0) {
			return ts;
		}
		try {
			Date date = ComUtil.sdf_date.parse(arg0);
			ts = new Timestamp(date.getTime());
		} catch (ParseException e) {
			throw new IllegalArgumentException();
		}
		return ts;
	}

}
