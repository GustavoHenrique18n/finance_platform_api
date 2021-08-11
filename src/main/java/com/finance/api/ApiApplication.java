package com.finance.api;

import com.finance.api.entity.Expenses;
import com.finance.api.entity.Incomes;
import com.finance.api.entity.Report;
import com.finance.api.entity.Users;
import com.finance.api.repository.UsersRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}
}
