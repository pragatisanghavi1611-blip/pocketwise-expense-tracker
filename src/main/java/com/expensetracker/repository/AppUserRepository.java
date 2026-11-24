package com.expensetracker.repository;
import com.expensetracker.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface AppUserRepository extends JpaRepository<AppUser,Long>{
 Optional<AppUser> findByEmailIgnoreCase(String email);
 boolean existsByEmailIgnoreCase(String email);
 long countByRole(String role);
}