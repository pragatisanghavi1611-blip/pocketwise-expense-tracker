package com.expensetracker.controller;
import com.expensetracker.repository.AppUserRepository;
import com.expensetracker.repository.TransactionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class AdminController {
 private final AppUserRepository users; private final TransactionRepository transactions;
 public AdminController(AppUserRepository users,TransactionRepository transactions){this.users=users;this.transactions=transactions;}
 @GetMapping("/admin") public String dashboard(Model model){
  model.addAttribute("users",users.findAll());model.addAttribute("userCount",users.countByRole("USER"));
  model.addAttribute("adminCount",users.countByRole("ADMIN"));model.addAttribute("transactionCount",transactions.count());
  model.addAttribute("transactions",transactions.findAll());return "admin";
 }
}