package com.finance.api;

import com.finance.api.entity.Expenses;
import com.finance.api.entity.Incomes;
import com.finance.api.entity.IncomesType;
import com.finance.api.entity.Users;
import com.finance.api.repository.ExpensesRepository;
import com.finance.api.repository.IncomesRepository;
import com.finance.api.repository.IncomesTypeRepository;
import com.finance.api.repository.UsersRepository;
import com.finance.api.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import java.util.List;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}




}