package com.Ipaisa;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@SpringBootApplication
@ComponentScan(basePackages = {
	    "com.Ipaisa.Jwt_Utils",
	    "com.Ipaisa.SecurityConfig",
	    "com.Ipaisa.UserController",
	    "com.Ipaisa.CustomExceptions",
	    "com.Ipaisa.filters",
	    "com.Ipaisa.Service",
	    "com.lpaisa",
	    "com.Ipaisa.config",
	    "com.Ipaisa.SprintService",
	    "com.Ipaisa.fina",
		"com.Ipaisa.config",
		"com.Ipaisa.axis",
		"com.Ipaisa.batchConfig",
		"com.Ipaisa.DeepLink",
		"com.Ipaisa.mplan"

	})
@Configuration
public class IpaisaBackendApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(IpaisaBackendApplication.class);
	}


	public static void main(String[] args) {
		System.out.println("here");
		SpringApplication.run(IpaisaBackendApplication.class, args);
		System.out.println("\n\t --------Application Start-----------");
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("https://api.ipaisa.co.in","https://ipaisa.co.in","https://www.test.ipaisa.co.in","https://www.ipaisa.co.in")
						.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE");
			}
		};
	}
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(12);

	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
