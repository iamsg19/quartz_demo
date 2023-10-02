package com.shivatube.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shivatube.demo.payload.SchedulerRequest;
import com.shivatube.demo.payload.SchedulerResponse;
import com.shivatube.demo.service.JobSchedulerService;

@RestController
public class JobSchedulerController {

	@Autowired
	private JobSchedulerService jobSchedulerService;
	
	@PostMapping("/scheduleEmail")
	public ResponseEntity<SchedulerResponse> scheduleJob(@RequestBody SchedulerRequest schedulerRequest){
		
		return ResponseEntity.ok(jobSchedulerService.scheduleJob(schedulerRequest));
	}
}
