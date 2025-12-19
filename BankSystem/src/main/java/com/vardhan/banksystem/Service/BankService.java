package com.vardhan.banksystem.Service;

import org.springframework.stereotype.Service;

import com.vardhan.banksystem.model.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BankService {
    private final Map<String,User> users = new ConcurrentHashMap<>();

    public List<User> listUsers(){
        return new ArrayList<>(users.values());
    }
    public Optional<User> getUser(String id){
        return Optional.ofNullable(users.get(id));
    }
    public User addUser(User u){
        if(users.containsKey(u.getAccountNumber())){
            throw new IllegalArgumentException("Account already exists");
        }
        users.put(u.getAccountNumber(), u);
        return u;
    }

    public double deposit(String accountNumber, double amount){
        if(amount<0) throw new IllegalArgumentException("Invalid deposit amount: Amount should be greater than 0");
        User u = users.get(accountNumber);
        if (u == null) throw new NoSuchElementException("User not found");
        synchronized (u){
            u.setBalance(u.getBalance() + amount);
            return u.getBalance();
        }
    }
    public double withdraw(String accountNumber, double amount){
        if(amount<0) throw new IllegalArgumentException("Invalid withdraw amount: Amount should be greater than 0");
        User u = users.get(accountNumber);
        if (u == null) throw new NoSuchElementException("User not found");
        synchronized (u){
            if (u.getBalance() < amount) throw new IllegalStateException("Insufficient balance");
            u.setBalance(u.getBalance() - amount);
            return u.getBalance();
        }
    }
}
