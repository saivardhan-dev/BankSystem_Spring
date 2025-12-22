package com.vardhan.banksystem.Web;

import com.vardhan.banksystem.model.User;
import com.vardhan.banksystem.Service.BankService;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
@Validated
public class BankController {
    @Autowired
    private BankService bank;
//    public BankController(BankService bank) {
//        this.bank = bank;
//    }

    @GetMapping("/")
    public String home() {
        return "Banking API is running. Try /api/users";
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return bank.listUsers();
    }

    @PostMapping("/users")
    public ResponseEntity<User> create(@RequestBody User user) {
        User created = bank.addUser(user);
        return ResponseEntity.created(URI.create("/api/users/" + created.getAccountNumber()))
                .body(created);
    }

    @GetMapping("/users/{accountNumber}")
    public ResponseEntity<User> get(@PathVariable String accountNumber) {
        return bank.getUser(accountNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/users/{accountNumber}/balance")
    public ResponseEntity<Double> balance(@PathVariable String accountNumber) {
        return bank.getUser(accountNumber)
                .map(u -> ResponseEntity.ok(u.getBalance()))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/users/{accountNumber}/deposit")
    public ResponseEntity<?> deposit(@PathVariable String accountNumber,
                                     @RequestParam @Min(1) double amount) {
        try {
            double newBalance = bank.deposit(accountNumber, amount);
            return ResponseEntity.ok(newBalance);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/users/{accountNumber}/withdraw")
    public ResponseEntity<?> withdraw(@PathVariable String accountNumber,
                                      @RequestParam @Min(1) double amount) {
        try {
            double newBalance = bank.withdraw(accountNumber, amount);
            return ResponseEntity.ok(newBalance);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
