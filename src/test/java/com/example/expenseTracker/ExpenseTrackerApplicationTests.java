package com.example.expenseTracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.expenseTracker.web.ExpenseController;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ExpenseTrackerApplicationTests {

	@Autowired
	private ExpenseController controller;

	@Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	};

}
