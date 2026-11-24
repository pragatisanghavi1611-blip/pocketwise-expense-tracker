package com.expensetracker.service;
import com.expensetracker.model.Transaction;
import com.expensetracker.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import java.util.List;
@Service public class TransactionService{
 private final TransactionRepository repo;
 public TransactionService(TransactionRepository repo){this.repo=repo;}
 public List<Transaction> findAll(){return repo.findAllByOrderByTransactionDateDescIdDesc();}
 public Transaction save(Transaction t){if(t.getAmount()==null||t.getAmount().signum()<=0)throw new IllegalArgumentException("Amount must be positive");if(!"INCOME".equals(t.getType())&&!"EXPENSE".equals(t.getType()))throw new IllegalArgumentException("Invalid type");return repo.save(t);}
 public Transaction findById(Long id){return repo.findById(id).orElseThrow();}
 public void delete(Long id){repo.deleteById(id);}
}