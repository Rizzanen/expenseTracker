package com.example.expenseTracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.Arrays;

import com.example.expenseTracker.domain.AppUser;
import com.example.expenseTracker.domain.AppUserRepository;
import com.example.expenseTracker.domain.Category;
import com.example.expenseTracker.domain.CategoryRepository;
import com.example.expenseTracker.domain.Expense;
import com.example.expenseTracker.domain.ExpenseRepository;

@SpringBootApplication
public class ExpenseTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseTrackerApplication.class, args);
	}

	private static final Logger log = LoggerFactory.getLogger(ExpenseTrackerApplication.class);

	 @Bean
    public CommandLineRunner expenceDemo(ExpenseRepository expenceRepository, CategoryRepository categoryRepository,
	AppUserRepository appUserRepository) {
        return(args) -> {
			log.info("save a couple of books");
			categoryRepository.save(new Category("Groceries"));
			categoryRepository.save(new Category("Car"));
			categoryRepository.save(new Category("Public transportation"));

			
            expenceRepository.save(new Expense("Gas", new BigDecimal("80.90") , "20.10.2023", categoryRepository.findByName("Car").get(0)));
			expenceRepository.save(new Expense("ES", new BigDecimal("1.45") , "21.10.2023", categoryRepository.findByName("Groceries").get(0)));
			expenceRepository.save(new Expense("HSL kklippu", new BigDecimal("45.90") , "30.10.2023", categoryRepository.findByName("Public transportation").get(0)));
           

            
			//log.info("fetch all books");
			//for (Book book : brepository.findAll()) {
			//	log.info(book.toString());
			//}

			// Create users with BCrypt encoded password (user/user, admin/admin)
			AppUser user1 = new AppUser("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER");
			 AppUser user2 = new AppUser("admin", "$2a$08$bCCcGjB03eulCWt3CY0AZew2rVzXFyouUolL5dkL/pBgFkUH9O4J2", "ADMIN");
			appUserRepository.saveAll(Arrays.asList(user1, user2));
        };
    }

}
