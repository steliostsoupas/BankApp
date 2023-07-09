package service.exceptions;

import model.Account;

public class AccountNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public AccountNotFoundException(Account account) {
        super("The account with iban" + account.getIban() + " was not found");
    }

    public AccountNotFoundException(String  iban) {
        super("The account with iban" + iban + " was not found");
    }

    public AccountNotFoundException(long id) {
        super("The account with id " + id + " was not found");
    }

}
