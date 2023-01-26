package com.share.my_todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MyTodoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyTodoApplication.class, args);
	}

}
