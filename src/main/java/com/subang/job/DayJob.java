package com.subang.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.subang.util.ComUtil;
import com.subang.util.StratUtil;

public class DayJob extends BaseJob {

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		LOG.info("调度器执行");
		StratUtil.reset();
		ComUtil.reset();

		userService.reset();
		regionService.check();
		priceService.check();
		workerService.check();

		activityService.clear();
	}

}
