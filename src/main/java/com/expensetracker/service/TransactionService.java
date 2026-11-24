package com.expensetracker.service;
import com.expensetracker.model.Transaction;
import com.expensetracker.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import java.util.List;
@Service public class TransactionService{
 private final TransactionRepository repo;
 public TransactionService(TransactionRepository repo){this.repo=repo;}
 public List<Transaction> findAllForUser(Long userId){return repo.findAllByOwnerIdOrderByTransactionDateDescIdDesc(userId);}
 public Transaction saveForUser(Transaction t,Long userId){if(t.getAmount()==null||t.getAmount().signum()<=0)throw new IllegalArgumentException("Amount must be positive");if(!"INCOME".equals(t.getType())&&!"EXPENSE".equals(t.getType()))throw new IllegalArgumentException("Invalid type");t.setOwner(new com.expensetracker.model.AppUser());t.getOwner().setId(userId);return repo.save(t);}
 public Transaction findForUser(Long id,Long userId){return repo.findByIdAndOwnerId(id,userId).orElseThrow();}
 public void deleteForUser(Long id,Long userId){repo.deleteByIdAndOwnerId(id,userId);}
}