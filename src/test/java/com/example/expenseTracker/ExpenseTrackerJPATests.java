package com.example.expenseTracker;

import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.example.expenseTracker.domain.AppUser;
import com.example.expenseTracker.domain.AppUserRepository;
import com.example.expenseTracker.domain.Category;
import com.example.expenseTracker.domain.CategoryRepository;
import com.example.expenseTracker.domain.Expense;
import com.example.expenseTracker.domain.ExpenseRepository;

@DataJpaTest
public class ExpenseTrackerJPATests {
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    
    @Test
    public void createNewExpenseTest() {
        Expense expense = new Expense("Milk", new BigDecimal(0.80), "10.10.2023",  categoryRepository.findByName("Groceries").get(0));
        expenseRepository.save(expense);
        assertThat(expense.getId()).isNotNull();
    }

    @Test
    public void createNewCategoryTest() {
        Category category = new Category("Vacation");
        categoryRepository.save(category);
        assertThat(category.getId()).isNotNull();
    }

    @Test
    public void createNewUserTest() {
        AppUser user = new AppUser("bobi", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER");
        appUserRepository.save(user);
        assertThat(user.getId()).isNotNull();
    }

    @Test
    public void deleteExpenseTest() {
        expenseRepository.deleteById(1L);
        boolean expenseExists = expenseRepository.existsById(1L);
        assertThat(expenseExists).isFalse();
    }

     @Test
    public void deleteCategoryTest() {
        Category category = new Category("Movies");
        categoryRepository.save(category);
        categoryRepository.deleteById(0L);
        boolean categoryExists = categoryRepository.existsById(0L);
        assertThat(categoryExists).isFalse();
    }
}