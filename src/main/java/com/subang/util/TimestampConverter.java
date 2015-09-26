package com.subang.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class TimestampConverter implements Converter<String, Timestamp> {

	public Timestamp convert(String arg0) {
		Timestamp ts = null;
		try {
			Date date = ComUtil.sdf_date.parse(arg0);
			ts = new Timestamp(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ts;
	}

}
