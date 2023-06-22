package com.exam.examportal;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.exam.examportal.helper.UserFoundException;
import com.exam.examportal.model.Role;
import com.exam.examportal.model.User;
import com.exam.examportal.model.UserRole;
import com.exam.examportal.service.UserService;

@SpringBootApplication
public class ExamportalApplication implements CommandLineRunner{

	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(ExamportalApplication.class, args);
	}
	@Configuration
	public class CorsConfig implements WebMvcConfigurer {

	    @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**") // Configure CORS for all endpoints
	                .allowedOrigins("*") // Allow requests from any origin
	                .allowedMethods("*"); // Allow all HTTP methods
	    }
	}
	
	@Override
    public void run(String... args) throws Exception {
		try {
		System.out.println("Starting code");
		
		User user = new User();
		user.setFirstName("Sri");
		user.setLastName("Priya");
		user.setUsername("sripriya");
		user.setPassword(this.bCryptPasswordEncoder.encode("gagana123"));
		user.setEmail("sripriya@gmail.com");
		
		Role role1 = new Role();
		role1.setRoleId(44L);
		role1.setRoleName("ADMIN");
		
		Set<UserRole> userRoleSet = new HashSet<>();
		UserRole userRole = new UserRole();
		userRole.setRole(role1);
		userRole.setUser(user);
		
		userRoleSet.add(userRole);
		
		User user1 = this.userService.createUser(user, userRoleSet);
		System.out.println(user1.getUsername());
	}catch(UserFoundException e) {
		e.printStackTrace();
	}
	}

}
