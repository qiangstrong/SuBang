package com.subang.controller.app;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.subang.bean.Identity;
import com.subang.bean.RecordDetail;
import com.subang.controller.BaseController;
import com.subang.domain.Worker;
import com.subang.util.SuUtil;

@Controller("recordController_app")
@RequestMapping("/app/record")
public class RecordController extends BaseController {

	@RequestMapping("/workerlist")
	public void workerList(Identity identity, @RequestParam("type") Integer stateType,
			@RequestParam(value = "filter", required = false) String filter,
			HttpServletResponse response) {
		Worker worker = getWorker(identity);
		List<RecordDetail> recordDetails = recordService.searchRecordByWorkeridAndState(
				worker.getId(), stateType);
		SuUtil.doFilter(filter, recordDetails, RecordDetail.class);
		SuUtil.outputJson(response, recordDetails);
	}

	@RequestMapping("/get")
	public void get(@RequestParam("recordid") Integer recordid, HttpServletResponse response) {
		RecordDetail recordDetail = recordDao.getDetail(recordid);
		SuUtil.outputJson(response, recordDetail);
	}

	@RequestMapping("/deliver")
	public void deliver(@RequestParam("recordid") Integer recordid, HttpServletResponse response) {
		recordService.deliverRecord(recordid);
		SuUtil.outputJsonOK(response);
	}
}
