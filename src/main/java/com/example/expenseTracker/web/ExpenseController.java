package com.example.expenseTracker.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    //@PreAuthorize("hasRole('ADMIN')")
    public String deleteExpense(@PathVariable("id") Long expenseId, Model model) {
    expenseRepository.deleteById(expenseId);
        
    return "redirect:../expenseList";
    }
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editBook(@PathVariable("id") Long id,Model model) {
        Optional<Expense> optionalExpense = expenseRepository.findById(id);
        Expense expense = optionalExpense.get();
        model.addAttribute("categorys", categoryRepository.findAll());
        model.addAttribute("editedExpense",expense);
        
        expenseRepository.deleteById(id);
        return "editExpense";
    }
    @RequestMapping(value = "/saveEdit", method = RequestMethod.POST)
    public String saveExpenseEdit(@ModelAttribute("editedExpense") Expense editedExpense) {
       expenseRepository.save(editedExpense);
        return "redirect:expenseList";
    }
}
