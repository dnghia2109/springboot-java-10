package com.foodei.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@SpringBootApplication
public class ProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
		String current = System.getProperty("user.dir");
		Path staticStr = Paths.get("static");
		System.out.println(staticStr);
		System.out.println("Current working directory in Java : " + current);
	}



}
