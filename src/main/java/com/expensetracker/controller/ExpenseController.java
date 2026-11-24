package com.expensetracker.controller;
import com.expensetracker.model.Transaction;
import com.expensetracker.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
@Controller public class ExpenseController{
 private final TransactionService service;
 private final List<String> categories=List.of("Food","Travel","Bills","Shopping","Education","Health","Salary","Other");
 public ExpenseController(TransactionService service){this.service=service;}
 @GetMapping("/") public String home(@RequestParam(required=false) String type,@RequestParam(required=false) String category,Model m){
 List<Transaction> all=service.findAll();
 List<Transaction> filtered=all.stream().filter(t->type==null||type.isBlank()||type.equals(t.getType())).filter(t->category==null||category.isBlank()||category.equals(t.getCategory())).toList();
 BigDecimal income=all.stream().filter(t->"INCOME".equals(t.getType())).map(Transaction::getAmount).filter(v->v!=null).reduce(BigDecimal.ZERO,BigDecimal::add);
 BigDecimal expenses=all.stream().filter(t->"EXPENSE".equals(t.getType())).map(Transaction::getAmount).filter(v->v!=null).reduce(BigDecimal.ZERO,BigDecimal::add);
 BigDecimal total=income.add(expenses);
 m.addAttribute("transactions",filtered);m.addAttribute("income",income);m.addAttribute("expenses",expenses);m.addAttribute("balance",income.subtract(expenses));
 m.addAttribute("incomePercent",total.signum()==0?0:income.multiply(BigDecimal.valueOf(100)).divide(total,0,java.math.RoundingMode.HALF_UP).intValue());
 m.addAttribute("categories",categories);m.addAttribute("selectedType",type==null?"":type);m.addAttribute("selectedCategory",category==null?"":category);m.addAttribute("today",LocalDate.now());return "index";
 }
 @PostMapping("/transactions") public String add(@ModelAttribute Transaction t,RedirectAttributes r){if(t.getTransactionDate()==null)t.setTransactionDate(LocalDate.now());service.save(t);r.addFlashAttribute("success","Transaction added.");return "redirect:/";}
 @PostMapping("/transactions/{id}/delete") public String delete(@PathVariable Long id,RedirectAttributes r){service.delete(id);r.addFlashAttribute("success","Transaction deleted.");return "redirect:/";}
 @GetMapping("/transactions/{id}/edit") public String edit(@PathVariable Long id,Model m){m.addAttribute("transaction",service.findById(id));m.addAttribute("categories",categories);return "edit";}
 @PostMapping("/transactions/{id}/edit") public String update(@PathVariable Long id,@ModelAttribute Transaction t,RedirectAttributes r){t.setId(id);service.save(t);r.addFlashAttribute("success","Transaction updated.");return "redirect:/";}
}