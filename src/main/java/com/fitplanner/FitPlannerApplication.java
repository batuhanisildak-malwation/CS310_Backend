package com.fitplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class FitPlannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitPlannerApplication.class, args);
	}

	// @Bean
	// CommandLineRunner runner(UserRepository repository) {
	// return args -> {
	// User user = new User(
	// "John Doe",
	// "johndoe@su.edu",
	// "password",
	// Gender.MALE,
	// 20,
	// 180,
	// 80,
	// 5,
	// 75,
	// 2000);
	// repository.save(user);
	// };
	// }

}
