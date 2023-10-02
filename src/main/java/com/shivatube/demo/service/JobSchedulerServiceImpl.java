package com.shivatube.demo.service;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shivatube.demo.job.EmailJob;
import com.shivatube.demo.listener.JobListenerImpl;
import com.shivatube.demo.listener.TriggerListenerImpl;
import com.shivatube.demo.payload.SchedulerRequest;
import com.shivatube.demo.payload.SchedulerResponse;

@Service
public class JobSchedulerServiceImpl implements JobSchedulerService{

	@Autowired
	private Scheduler scheduler;
	
	@Override
	public SchedulerResponse scheduleJob(SchedulerRequest scheduleRequest) {
		
		try {
			
			ZonedDateTime dateTime = ZonedDateTime.of(scheduleRequest.getDateTime(), scheduleRequest.getTimeZone());
			if(dateTime.isBefore(ZonedDateTime.now())) {
				
				return new SchedulerResponse("Please provide correct date/time for scheduling job", false);
			}
			
			JobDetail jobDetail = buildJobDetail(scheduleRequest);
			Trigger trigger = buildTrigger(jobDetail, dateTime);
			
			scheduler.getListenerManager().addTriggerListener(new TriggerListenerImpl());
			scheduler.getListenerManager().addJobListener(new JobListenerImpl());
			scheduler.scheduleJob(jobDetail, trigger);
			
			
			return new SchedulerResponse(trigger.getJobKey().getName(), trigger.getJobKey().getGroup(), "Successfully scheduled", true);
			
		}catch(SchedulerException ex) {
			System.out.println("Exception occured");
			return new SchedulerResponse("Exception occured", false);
		}
	}

	private Trigger buildTrigger(JobDetail jobDetail, ZonedDateTime dateTime) {
		
		return TriggerBuilder.newTrigger()
				.forJob(jobDetail)
				.withIdentity("email-triggers")
				.withDescription("Send Email Trigger")
				.startAt(Date.from(dateTime.toInstant()))
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
				.build();
	}

	private JobDetail buildJobDetail(SchedulerRequest scheduleRequest) {
		
		JobDataMap jobDataMap = new JobDataMap();
		
		jobDataMap.put("email", scheduleRequest.getEmail());
		jobDataMap.put("subject", scheduleRequest.getSubject());
		jobDataMap.put("body", scheduleRequest.getBody());
		
		return JobBuilder.newJob(EmailJob.class)
				.withIdentity(UUID.randomUUID().toString(), "email-jobs	")
				.withDescription("Send mail job")
				.usingJobData(jobDataMap)
				.storeDurably()
				.build();
	}

}
