package gr.aueb.cf.dao;

import gr.aueb.cf.model.Account;

import java.util.List;

public interface IAccountDAO {

    Account insert(Account account);

    Account update(long id, Account account);

    void delete(String iban);

    Account get(long id);

    Account get(String iban);

    List<Account> getAll();

    Account get(double balance);

    void delete(long id);

    boolean ibanExists(String iban);

    boolean userIdExists(long id);
}
