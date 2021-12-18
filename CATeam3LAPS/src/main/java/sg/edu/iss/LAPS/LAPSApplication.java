package sg.edu.iss.LAPS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import sg.edu.iss.LAPS.model.User;
import sg.edu.iss.LAPS.repo.UserRepository;

@SpringBootApplication
public class LAPSApplication {

	public static void main(String[] args) {
		SpringApplication.run(LAPSApplication.class, args);
	}
	
	@Autowired
	UserRepository urepo;
	
	@Bean
	CommandLineRunner runner() {
		return args -> { 
			User u1 = new User("Esther", "esther@nus.edu.sg", "password", "Esther", "Tan", 0);
			urepo.save(u1);
			User u2 = new User("Tin", "tin@nus.edu.sg", "password", "Tin", "Nguyen", 1);
			urepo.save(u2);
			User u3 = new User("Cherwah", "tin@nus.edu.sg", "password", "CherWah", "Tan", 1);
			urepo.save(u3);
			};
	}

}
