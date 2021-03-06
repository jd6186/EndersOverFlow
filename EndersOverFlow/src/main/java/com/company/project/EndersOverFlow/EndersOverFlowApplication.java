package com.company.project.EndersOverFlow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.company.project.EndersOverFlow.model.Member;
import com.company.project.EndersOverFlow.service.MemberService;

import ch.qos.logback.classic.Logger;

@SpringBootApplication
public class EndersOverFlowApplication {

	Logger logger = (Logger) org.slf4j.LoggerFactory.getLogger(this.getClass());

	public static void main(String[] args) {
		SpringApplication.run(EndersOverFlowApplication.class, args);
	}

}
