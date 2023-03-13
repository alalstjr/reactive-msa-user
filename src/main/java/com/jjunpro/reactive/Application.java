package com.jjunpro.reactive;

import java.io.FilterInputStream;
import java.util.ResourceBundle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.blockhound.BlockHound;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		BlockHound.install(b -> b.allowBlockingCallsInside(FilterInputStream.class.getName(), "read") // OpenApi Blocking Call 허용
														 .allowBlockingCallsInside(ResourceBundle.class.getName(), "getBundle")); // OpenApi
//		BlockHound.install();
		SpringApplication.run(Application.class, args);
	}

}
