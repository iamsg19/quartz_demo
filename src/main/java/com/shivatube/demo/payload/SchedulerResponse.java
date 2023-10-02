package com.shivatube.demo.payload;

public class SchedulerResponse {

	private String jobId;
	private String jobGroup;
	private String message;
	private boolean success;
	
	public SchedulerResponse(String message, boolean success) {
		super();
		this.message = message;
		this.success = success;
	}

	public SchedulerResponse(String jobId, String jobGroup, String message, boolean success) {
		super();
		this.jobId = jobId;
		this.jobGroup = jobGroup;
		this.message = message;
		this.success = success;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	
}
