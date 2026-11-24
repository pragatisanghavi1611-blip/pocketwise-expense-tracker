package com.expensetracker.repository;
import com.expensetracker.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface TransactionRepository extends JpaRepository<Transaction,Long>{
 List<Transaction> findAllByOrderByTransactionDateDescIdDesc();
 List<Transaction> findAllByOwnerIdOrderByTransactionDateDescIdDesc(Long ownerId);
 Optional<Transaction> findByIdAndOwnerId(Long id,Long ownerId);
 void deleteByIdAndOwnerId(Long id,Long ownerId);
}