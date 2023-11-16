package com.example.expenseTracker.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    
}
