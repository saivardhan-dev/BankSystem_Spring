package com.vardhan.banksystem.model;

public class User {
    private int accountNumber;
    private String name;
    private double balance;

    public User(int accountNumber, String name, double balance){
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(int accountNumber) {
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
