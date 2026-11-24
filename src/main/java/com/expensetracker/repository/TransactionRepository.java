package com.expensetracker.repository;
import com.expensetracker.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface TransactionRepository extends JpaRepository<Transaction,Long>{
 List<Transaction> findAllByOrderByTransactionDateDescIdDesc();
}