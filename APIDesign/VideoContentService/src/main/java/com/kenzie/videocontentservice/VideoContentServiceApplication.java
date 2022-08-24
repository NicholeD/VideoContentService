package com.kenzie.videocontentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Provides inversion of control for the SNS MLP by instantiating all of the
 * dependencies needed by the SubscriptionDebugUtil and its dependency classes.
 */
@SpringBootApplication
public class VideoContentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideoContentServiceApplication.class, args);
	}

}