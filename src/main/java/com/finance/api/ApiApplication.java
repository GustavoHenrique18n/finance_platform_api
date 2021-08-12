package com.finance.api;

import com.finance.api.entity.Incomes;
import com.finance.api.entity.IncomesType;
import com.finance.api.entity.Users;
import com.finance.api.repository.IncomesRepository;
import com.finance.api.repository.IncomesTypeRepository;
import com.finance.api.repository.UsersRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class ApiApplication {



	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}



	@Bean
	CommandLineRunner commandLineRunner(
			IncomesRepository  incomesRepository ,
			UsersRepository usersRepository,
			IncomesTypeRepository incomesTypeRepository
	){
		return args -> {

			IncomesType type = new IncomesType();
			type.setIsActive(true);
			type.setCategorieName("casa");

			Users gustavo = new Users();
			gustavo.setId(6L);
			gustavo.setEmail("joao@");
			gustavo.setName("joao");
			gustavo.setPassword("a1212dad");

			Incomes income = new Incomes();
			income.setNameIncome("outro valor do 6");
			LocalDate local = LocalDate.now();
			income.setPreviewDate(local);
			income.setPreviewValue(100);
			income.setUser(gustavo);
			income.setCategorieIncome(type);

			System.out.println(type);
			System.out.println(gustavo);
			System.out.println(income);

		};
	}
}