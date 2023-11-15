package com.example.expenseTracker.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import org.springframework.ui.Model;


import com.example.expenseTracker.domain.CategoryRepository;
import com.example.expenseTracker.domain.Expense;
import com.example.expenseTracker.domain.ExpenseRepository;
import com.example.expenseTracker.domain.Category;


@Controller
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @RequestMapping(value = "/expenseList", method = RequestMethod.GET)
    public String demoPage(Model model){
        model.addAttribute("expenses", expenseRepository.findAll());
        return "expenseList";
    }

    @RequestMapping(value = "/addExpense", method = RequestMethod.GET)
    public String addExpense(Model model) {
        model.addAttribute("newExpense", new Expense());
        model.addAttribute("categorys", categoryRepository.findAll());
        return "addExpense";
    }
     @RequestMapping(value = "/addCategory", method = RequestMethod.GET)
    public String addCategory(Model model) {
        model.addAttribute("newCategory", new Category());
        return "addCategory";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveExpense(Expense expense) {
       expenseRepository.save(expense);
        return "redirect:expenseList";
    }
    @RequestMapping(value = "/saveCategory", method = RequestMethod.POST)
    public String saveCategory(Category category) {
       categoryRepository.save(category);
        return "redirect:addExpense";
    }

    
}
