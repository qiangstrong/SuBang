package com.subang.job;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.subang.service.BackAdminService;
import com.subang.service.BackStatService;
import com.subang.service.BackUserService;
import com.subang.service.FrontUserService;

public abstract class BaseJob extends QuartzJobBean {
	protected static final Logger LOG = Logger.getLogger(BaseJob.class.getName());

	@Autowired
	protected BackAdminService backAdminService;
	@Autowired
	protected BackUserService backUserService;
	@Autowired
	protected BackStatService backStatService;
	@Autowired
	protected FrontUserService frontUserService;
}
