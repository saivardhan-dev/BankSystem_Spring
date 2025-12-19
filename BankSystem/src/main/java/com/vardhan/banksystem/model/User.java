package com.vardhan.banksystem.model;

public class User {
    private String accountNumber;
    private String name;
    private double balance;

    public User(String accountNumber, String name, double balance){
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber=accountNumber;
    }

    public String getName() {
        return name;
    }
    public void setName(){
        this.name=name;
    }


    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance){
        this.balance=balance;
    }
}
