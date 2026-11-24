package com.expensetracker.config;
import com.expensetracker.repository.AppUserRepository;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class SecurityConfig {
 @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
 @Bean UserDetailsService userDetailsService(AppUserRepository users){
  return email -> users.findByEmailIgnoreCase(email).map(u -> User.withUsername(u.getEmail()).password(u.getPassword()).roles(u.getRole()).build()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
 }
 @Bean SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
  http.authorizeHttpRequests(a -> a.requestMatchers("/login","/register","/css/**","/error").permitAll().requestMatchers("/admin/**").hasRole("ADMIN").anyRequest().authenticated())
   .formLogin(f -> f.loginPage("/login").defaultSuccessUrl("/",true).permitAll())
   .logout(l -> l.logoutSuccessUrl("/login?logout").permitAll());
  return http.build();
 }
}