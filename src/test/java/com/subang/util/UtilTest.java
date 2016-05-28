package com.subang.util;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class UtilTest {

	private static final Logger LOG = Logger.getLogger("sdfas");

	@Test
	public void test() throws Exception {
		String path = "C:\\Users\\lenovo\\Desktop\\临时\\1.xls";
		List<String> data = new ArrayList<String>();
		FileInputStream fis = new FileInputStream(path);
		Workbook wk = Workbook.getWorkbook(fis);
		// 获取第一张Sheet表
		Sheet sheet = wk.getSheet(0);
		// 获取总行数
		int rowNum = sheet.getRows();
		for (int i = 0; i < rowNum; i++) {
			String string = sheet.getCell(0, i).getContents();
			if (string.length() != 0) {
				data.add(string);
			}

		}
		fis.close();
		wk.close();
		pause();
	}

	public void pause() {
	}
}
