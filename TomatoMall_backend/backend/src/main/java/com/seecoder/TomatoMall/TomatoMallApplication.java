package com.seecoder.TomatoMall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TomatoMallApplication {

	public static void main(String[] args) {
		SpringApplication.run(TomatoMallApplication.class, args);
	}

}
