package com.expensetracker.model;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
@Entity @Table(name="transactions")
public class Transaction {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 private String title; @Column(precision=12,scale=2) private BigDecimal amount;
 private String type; private String category; private LocalDate transactionDate;
 private String description;
 @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="user_id") private AppUser owner;
 public Long getId(){return id;} public void setId(Long v){id=v;}
 public String getTitle(){return title;} public void setTitle(String v){title=v;}
 public BigDecimal getAmount(){return amount;} public void setAmount(BigDecimal v){amount=v;}
 public String getType(){return type;} public void setType(String v){type=v;}
 public String getCategory(){return category;} public void setCategory(String v){category=v;}
 public LocalDate getTransactionDate(){return transactionDate;} public void setTransactionDate(LocalDate v){transactionDate=v;}
 public String getDescription(){return description;} public void setDescription(String v){description=v;}
 public AppUser getOwner(){return owner;} public void setOwner(AppUser v){owner=v;}
}