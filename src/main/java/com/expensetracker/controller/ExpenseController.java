package com.expensetracker.controller;
import com.expensetracker.model.Transaction;
import com.expensetracker.repository.AppUserRepository;
import com.expensetracker.service.TransactionService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
@Controller public class ExpenseController{
 private final TransactionService service; private final AppUserRepository users;
 private final List<String> categories=List.of("Food","Travel","Bills","Shopping","Education","Health","Salary","Other");
 public ExpenseController(TransactionService service,AppUserRepository users){this.service=service;this.users=users;}
 private Long userId(Authentication auth){return users.findByEmailIgnoreCase(auth.getName()).orElseThrow().getId();}
 @GetMapping("/") public String home(@RequestParam(required=false) String type,@RequestParam(required=false) String category,Model m,Authentication auth){
 List<Transaction> all=service.findAllForUser(userId(auth));
 List<Transaction> filtered=all.stream().filter(t->type==null||type.isBlank()||type.equals(t.getType())).filter(t->category==null||category.isBlank()||category.equals(t.getCategory())).toList();
 BigDecimal income=all.stream().filter(t->"INCOME".equals(t.getType())).map(Transaction::getAmount).filter(v->v!=null).reduce(BigDecimal.ZERO,BigDecimal::add);
 BigDecimal expenses=all.stream().filter(t->"EXPENSE".equals(t.getType())).map(Transaction::getAmount).filter(v->v!=null).reduce(BigDecimal.ZERO,BigDecimal::add);
 BigDecimal total=income.add(expenses);
 m.addAttribute("transactions",filtered);m.addAttribute("income",income);m.addAttribute("expenses",expenses);m.addAttribute("balance",income.subtract(expenses));
 m.addAttribute("incomePercent",total.signum()==0?0:income.multiply(BigDecimal.valueOf(100)).divide(total,0,java.math.RoundingMode.HALF_UP).intValue());
 m.addAttribute("categories",categories);m.addAttribute("selectedType",type==null?"":type);m.addAttribute("selectedCategory",category==null?"":category);m.addAttribute("today",LocalDate.now());m.addAttribute("admin",auth.getAuthorities().stream().anyMatch(a->a.getAuthority().equals("ROLE_ADMIN")));return "index";
 }
 @PostMapping("/transactions") public String add(@ModelAttribute Transaction t,RedirectAttributes r,Authentication auth){if(t.getTransactionDate()==null)t.setTransactionDate(LocalDate.now());service.saveForUser(t,userId(auth));r.addFlashAttribute("success","Transaction added.");return "redirect:/";}
 @PostMapping("/transactions/{id}/delete") public String delete(@PathVariable Long id,RedirectAttributes r,Authentication auth){service.deleteForUser(id,userId(auth));r.addFlashAttribute("success","Transaction deleted.");return "redirect:/";}
 @GetMapping("/transactions/{id}/edit") public String edit(@PathVariable Long id,Model m,Authentication auth){m.addAttribute("transaction",service.findForUser(id,userId(auth)));m.addAttribute("categories",categories);return "edit";}
 @PostMapping("/transactions/{id}/edit") public String update(@PathVariable Long id,@ModelAttribute Transaction t,RedirectAttributes r,Authentication auth){Transaction old=service.findForUser(id,userId(auth));t.setId(id);t.setOwner(old.getOwner());service.saveForUser(t,userId(auth));r.addFlashAttribute("success","Transaction updated.");return "redirect:/";}
}