package com.example.expenseTracker.web;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import com.example.expenseTracker.domain.CategoryRepository;
import com.example.expenseTracker.domain.Expense;
import com.example.expenseTracker.domain.ExpenseRepository;
import com.example.expenseTracker.exception.NotFoundException;

import jakarta.validation.Valid;
import com.example.expenseTracker.domain.Category;

@Controller
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    //When NotFoundException  occurs, spring will call this method to handle the exception. It creates HttpStatus 404 error and puts the set message into the body of the Response. 
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException exeption) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exeption.getMessage());
    }

    //Endpoint for /expenseList. Give all expenses found it the expenseRepository as parameter for thymeleaf template.
    @RequestMapping(value = "/expenseList", method = RequestMethod.GET)
    public String expenseList(Model model){
        model.addAttribute("expenses", expenseRepository.findAll());
        return "expenseList"; //expenseList.html
    }

    //Endpoint for /addExpense. Give a empty expense object and all categorys as attribute so expense object can be filled and categorys can be selected from in thymeleaf template.
    @RequestMapping(value = "/addExpense", method = RequestMethod.GET)
    public String addExpense(Model model) {
        model.addAttribute("newExpense", new Expense());
        model.addAttribute("categorys", categoryRepository.findAll());
        return "addExpense"; //addExpense.html
    }

    //POST Endpoint for /saveExpense called after submitting at /addExpense. Takes Expense as parameter and saves it to expenseRepository.
    @RequestMapping(value = "/saveExpense", method = RequestMethod.POST)
    public String saveExpense(Expense expense) {
       expenseRepository.save(expense);
        return "redirect:expenseList"; //expenseList.html
    }

    //Endpoint for /addCategory. Give empty category object as attribute so it can be filled.
     @RequestMapping(value = "/addCategory", method = RequestMethod.GET)
    public String addCategory(Model model) {
        model.addAttribute("newCategory", new Category());
        return "addCategory"; //addCategory.html
    }

    //POST Endpoint for /saveCategory. Called after submitting form at /addCategory. Takes Category as parameter and saves it to categoryRepository. 
    @RequestMapping(value = "/saveCategory", method = RequestMethod.POST)
    public String saveCategory(Category category) {
       categoryRepository.save(category);
        return "redirect:addExpense"; //addExpense.html
    }

    //Endpoint for /delete/{id}. Takes id as PathVariable and then deletes from expenseRepository with the given id. Also restricts this functionality from other than admin users.
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteExpense(@PathVariable("id") Long expenseId, Model model) {
    expenseRepository.deleteById(expenseId); 
    return "redirect:../expenseList"; //expenseList.html
    }

    //Endpoint for /edit/{id}. Takes id as PathVariable and then searches by it from expenseRepository. searches all categorys and adds as attributes with possibly found expense to be used in thymeleaf template. Also deletes the expenseObject which will be modified.
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public String editExpense(@PathVariable("id") @Valid Long id,Model model) {
        Optional<Expense> optionalExpense = expenseRepository.findById(id);
        Expense expense = optionalExpense.get();
        model.addAttribute("categorys", categoryRepository.findAll());
        model.addAttribute("expense",expense);
        
        expenseRepository.deleteById(id);
        return "editExpense"; //editExpense.html
    }
    //POST Endpoint for /saveEdit. Takes the edited expense as attribute and saves it to expenseRepository as a new one. 
    @RequestMapping(value = "/saveEdit", method = RequestMethod.POST)
    public String saveExpenseEdit(@ModelAttribute("expense") @Valid Expense expense) {
       expenseRepository.save(expense);
        return "redirect:expenseList"; //expenseList.html
    }
    @RequestMapping("/login")
	public String login() {
		return "login";
	}

    //REST endpoint for calling expense by id as json. Also includes calling of NotFoundException if findById is empty after searching with given id.
    @RequestMapping(value="/expense/{id}", method = RequestMethod.GET)
    public ResponseEntity <Expense> findExpenseRest(@PathVariable("id")Long expenseId){
        Expense expense = expenseRepository.findById(expenseId)
        .orElseThrow(()->new NotFoundException("Expense not found with ID: " + expenseId));
        return ResponseEntity.ok().body(expense);
    }

    //REST endpoint for calling all expenses by id as json. Also call exception handling and set exception message if no expenses is found from db
    @RequestMapping(value="/expenses", method = RequestMethod.GET)
    public @ResponseBody List <Expense> allExpensesRest(){
        List <Expense> expenses = (List<Expense>) expenseRepository.findAll();
        if(expenses.isEmpty()) {
            throw new NotFoundException("No expenses found from database");
        }
        return expenses;
    }

    //REST endpoint for calling all expenses by id as json. Also call exception handling and set exception message if no categorys is found from db
    @RequestMapping(value="/categorys", method = RequestMethod.GET)
    public @ResponseBody List <Category> allCategorysRest(){
        List <Category> categorys = (List<Category>) categoryRepository.findAll();
        if(categorys.isEmpty()) {
            throw new NotFoundException("No categorys found from database");
        }
        return categorys;
    }

     //REST endpoint for calling category by id as json. Also includes calling of NotFoundException if findById is empty after searching with given id.
    @RequestMapping(value="/category/{id}", method = RequestMethod.GET)
    public  ResponseEntity<Category> findCategoryRest(@PathVariable("id")Long categoryId){
        Category category = categoryRepository.findById(categoryId)
        .orElseThrow(()->new NotFoundException("Category not found with ID: " + categoryId));
        return ResponseEntity.ok().body(category);
    }
    
}
