package model;

import service.exceptions.NegativeAmountException;
import service.exceptions.SsnNotValidException;

public class OverdraftJointAccount extends JointAccount {

    public OverdraftJointAccount() {}

    public OverdraftJointAccount(User holder, String iban, double balance, User secondHolder) {
        super(holder, iban, balance, secondHolder);
    }

    public void withdraw(double amount,String ssn)
            throws SsnNotValidException, NegativeAmountException {
        try {
            if (amount < 0) throw new NegativeAmountException(amount);
            if (!isSsnValid(ssn)) throw new SsnNotValidException(ssn);

            setBalance(getBalance() - amount);
        } catch (SsnNotValidException | NegativeAmountException e) {
            System.err.println("Error: withdrawal");
            throw e;
        }
    }
}
