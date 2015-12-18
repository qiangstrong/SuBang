package com.subang.controller.app;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.subang.bean.Identity;
import com.subang.bean.Result;
import com.subang.controller.BaseController;
import com.subang.domain.Worker;
import com.subang.tool.SuException;
import com.subang.util.SuUtil;

@Controller("workerController_app")
@RequestMapping("/app/worker")
public class WorkerController extends BaseController {

	@RequestMapping("/get")
	public void get(Identity identity, HttpServletResponse response) {
		Worker worker = getWorker(identity);
		SuUtil.outputJson(response, worker);
	}

	@RequestMapping("/login")
	public void login(Worker worker, HttpServletResponse response) {
		Worker matchWorker = workerDao.findByWorker(worker);
		Result result = new Result();
		if (matchWorker == null) {
			result.setCode(Result.ERR);
			result.setMsg("手机号或密码错误。");
		} else {
			result.setCode(Result.OK);
		}
		SuUtil.outputJson(response, result);
	}

	// 使用验证过的手机号登录
	@RequestMapping("/logincellnum")
	public void loginCellnum(Worker worker, HttpServletResponse response) {
		Worker matchWorker = workerDao.getByCellnum(worker.getCellnum());
		if (matchWorker == null) {
			matchWorker = new Worker();
		}
		SuUtil.outputJson(response, matchWorker);
	}

	// 工作人员不需要注册，访问这个url需要认证信息
	@RequestMapping("/chkcellnum")
	public void chkCellnum(@RequestParam("cellnum") String cellnum, HttpServletResponse response) {
		Result result = new Result();
		if (!workerService.checkCellnum(cellnum)) {
			result.setCode(Result.ERR);
			result.setMsg("该手机号码已经被注册。");
			SuUtil.outputJson(response, result);
		} else {
			result.setCode(Result.OK);
			SuUtil.outputJson(response, result);
		}
	}

	@RequestMapping("/chgcellnum")
	public void chgCellnum(Identity identity, @RequestParam("cellnum") String cellnum,
			HttpServletResponse response) {
		Result result = new Result();
		if (!workerService.checkCellnum(cellnum)) {
			result.setCode(Result.ERR);
			result.setMsg("该手机号码已经被注册。");
			SuUtil.outputJson(response, result);
			return;
		}
		Worker worker = getWorker(identity);
		worker.setCellnum(cellnum);
		try {
			workerService.modifyWorker(worker);
		} catch (SuException e) {
		}
		result.setCode(Result.OK);
		SuUtil.outputJson(response, result);
	}

	@RequestMapping("/chgpassword")
	public void chgPassword(Identity identity, @RequestParam("password") String password,
			HttpServletResponse response) {
		Worker worker = getWorker(identity);
		worker.setPassword(password);
		try {
			workerService.modifyWorker(worker);
		} catch (SuException e) {
		}
		SuUtil.outputJsonOK(response);
	}
}
