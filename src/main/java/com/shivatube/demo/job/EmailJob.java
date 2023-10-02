package com.shivatube.demo.job;

import java.nio.charset.StandardCharsets;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.quartz.QuartzJobBean;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

public class EmailJob extends QuartzJobBean{

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private MailProperties mailProperties;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		JobDataMap jobDataMap = context.getMergedJobDataMap();
		String subject = jobDataMap.getString("subject");
		String email = jobDataMap.getString("email");
		String body = jobDataMap.getString("body");
		
		try {
			sendEmail(mailProperties.getUsername(), email, subject, body);
		}catch(MessagingException ex) {
			
			System.out.println("Exception occured"+ex);
		}
	}

	private void sendEmail(String username, String email, String subject, String body) throws MessagingException {
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, StandardCharsets.UTF_8.toString());
		messageHelper.setSubject(subject);
		
		messageHelper.setText(body);
		messageHelper.setTo(email);
		messageHelper.setFrom(username);
		
		mailSender.send(message);
	}

}
