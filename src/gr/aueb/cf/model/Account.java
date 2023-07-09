package model;

import service.exceptions.InsufficientBalanceException;
import service.exceptions.NegativeAmountException;
import service.exceptions.SsnNotValidException;

public class Account extends AbstractEntity {
    private User user = new User();
    private String iban;
    private double balance;

    public Account(){}

    public Account(User user, String iban, double balance) {
        this.user = user;
        this.iban = iban;
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "user=" + user +
                ", iban='" + iban + '\'' +
                ", balance=" + balance +
                '}';
    }

    public boolean isSsnValid(String ssn) {
        if (ssn == null || getUser().getSsn() == null) {
            return false;
        }

        return user.getSsn().equals(ssn);
    }
}
