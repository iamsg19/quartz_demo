package com.shivatube.demo.service;

import com.shivatube.demo.payload.SchedulerRequest;
import com.shivatube.demo.payload.SchedulerResponse;

public interface JobSchedulerService {

	public SchedulerResponse scheduleJob(SchedulerRequest scheduleRequest);
}
