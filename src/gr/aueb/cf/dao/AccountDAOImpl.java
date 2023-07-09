package dao;

import model.Account;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AccountDAOImpl implements IAccountDAO{
    private static final List<Account> accounts = new ArrayList<>();

    @Override
    public Account insert(Account account) {
        if (account == null) return null;
        accounts.add(account);
        return account;
    }

    @Override
    public Account update(long id, Account account) {
        if (id != account.getId()) return null;
        int positionToUpdate = getIndexById(id);
        return accounts.set(positionToUpdate, account);
    }

    @Override
    public void delete(String iban) {
        accounts.removeIf(account -> account.getIban().equals(iban));
    }

    @Override
    public Account get(long id) {
        int pos = getIndexById(id);
        if (pos == -1) return null;
        return accounts.get(pos);
    }

    @Override
    public Account get(String iban) {
        int pos = getIndexByIban(iban);
        if (pos == -1) return null;
        return accounts.get(pos);
    }

    @Override
    public List<Account> getAll() {
        return Collections.unmodifiableList(accounts);
    }

    @Override
    public Account get(double balance) {
        int pos = getIndexByBalance(balance);
        if (pos == -1) return null;
        return accounts.get(pos);
    }

    @Override
    public void delete(long id) {
        accounts.removeIf(account -> account.getId() == id);
    }

    @Override
    public boolean ibanExists(String iban) {
        return getIndexByIban(iban) != -1;
    }

    @Override
    public boolean userIdExists(long id) {
        return getIndexById(id) != -1;
    }

    private int getIndexById(long id) {
        int pos = -1;
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getId() == id) {
                pos = i;
                break;
            }
        }

        return pos;
    }

    private int getIndexByIban(String iban) {
        int pos = -1;
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getIban().equals(iban)) {
                pos = i;
                break;
            }
        }

        return pos;
    }

    private int getIndexByBalance(double balance) {
        int pos = -1;
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getBalance() == balance) {
                pos = i;
                break;
            }
        }

        return pos;
    }
}
