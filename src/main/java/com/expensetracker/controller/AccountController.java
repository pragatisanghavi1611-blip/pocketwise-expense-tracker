package com.expensetracker.controller;
import com.expensetracker.model.AppUser;
import com.expensetracker.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller
public class AccountController {
 private final AppUserRepository users; private final PasswordEncoder encoder;
 public AccountController(AppUserRepository users,PasswordEncoder encoder){this.users=users;this.encoder=encoder;}
 @GetMapping("/login") public String login(){return "login";}
 @GetMapping("/register") public String register(){return "register";}
 @PostMapping("/register") public String create(@RequestParam String name,@RequestParam String email,@RequestParam String password,Model model,RedirectAttributes redirect){
  String cleanEmail=email.trim().toLowerCase();
  if(name.isBlank()||!cleanEmail.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")||password.length()<10){
   model.addAttribute("error","Enter a name, valid email and password of at least 10 characters.");return "register";
  }
  if(users.existsByEmailIgnoreCase(cleanEmail)){model.addAttribute("error","An account with that email already exists.");return "register";}
  AppUser u=new AppUser();u.setName(name.trim());u.setEmail(cleanEmail);u.setPassword(encoder.encode(password));u.setRole("USER");users.save(u);
  redirect.addFlashAttribute("success","Account created. Please log in.");return "redirect:/login";
 }
}