package gr.aueb.cf.service;

import gr.aueb.cf.dto.AccountDTO;
import gr.aueb.cf.model.Account;
import gr.aueb.cf.service.exceptions.*;

import java.util.List;

public interface IAccountService {

    Account insertAccount(AccountDTO accountDTO)
        throws IbanAlreadyExistsException, UserIdAlreadyExistsException, SsnNotValidException;

    Account updateAccount(long id, AccountDTO accountDTO)
        throws AccountNotFoundException, IbanAlreadyExistsException, UserIdAlreadyExistsException;

    void deleteAccount(String iban) throws AccountNotFoundException;

    void deleteAccount(long id) throws AccountNotFoundException;

    Account getAccount(String iban) throws AccountNotFoundException;

    Account getAccount(long  id) throws AccountNotFoundException;

    List<Account> getAllAccounts();

    Account withdraw(long id, double amount, String ssn) throws InsufficientBalanceException, SsnNotValidException, NegativeAmountException, AccountNotFoundException;

    Account deposit(long id, double amount) throws NegativeAmountException;
}
