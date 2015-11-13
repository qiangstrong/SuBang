package com.subang.job;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

public class SpringBeanJobFactory extends
		org.springframework.scheduling.quartz.SpringBeanJobFactory {
	@Autowired
	private AutowireCapableBeanFactory beanFactory;

	@Override
	protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
		Object jobInstance = super.createJobInstance(bundle);
		beanFactory.autowireBean(jobInstance);
		return jobInstance;
	}
}
