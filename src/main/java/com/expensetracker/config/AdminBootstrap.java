package com.expensetracker.config;
import com.expensetracker.model.AppUser;
import com.expensetracker.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
@Component
public class AdminBootstrap implements ApplicationRunner {
 private final AppUserRepository users; private final PasswordEncoder encoder;
 @Value("${POCKETWISE_ADMIN_EMAIL:}") private String email;
 @Value("${POCKETWISE_ADMIN_PASSWORD:}") private String password;
 public AdminBootstrap(AppUserRepository users,PasswordEncoder encoder){this.users=users;this.encoder=encoder;}
 public void run(ApplicationArguments args){
  if(email.isBlank()||password.length()<12)return;
  users.findByEmailIgnoreCase(email.trim()).ifPresentOrElse(u->{if(!"ADMIN".equals(u.getRole())){u.setRole("ADMIN");users.save(u);}},()->{
   AppUser u=new AppUser();u.setName("Pocketwise Admin");u.setEmail(email.trim().toLowerCase());u.setPassword(encoder.encode(password));u.setRole("ADMIN");users.save(u);
  });
 }
}