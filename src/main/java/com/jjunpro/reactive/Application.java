package com.jjunpro.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
//		BlockHound.install(b -> b.allowBlockingCallsInside(FilterInputStream.class.getName(), "read") // OpenApi Blocking Call 허용
//														 .allowBlockingCallsInside(ResourceBundle.class.getName(), "getBundle")); // OpenApi
//		BlockHound.install();
		SpringApplication.run(Application.class, args);
	}

}
