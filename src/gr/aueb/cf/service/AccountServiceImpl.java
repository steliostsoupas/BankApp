package gr.aueb.cf.service;

import gr.aueb.cf.dao.IAccountDAO;
import gr.aueb.cf.dto.AccountDTO;
import gr.aueb.cf.dto.UserDTO;
import gr.aueb.cf.model.Account;
import gr.aueb.cf.model.User;
import gr.aueb.cf.service.exceptions.*;

import java.util.List;

public class AccountServiceImpl implements IAccountService {

    private final IAccountDAO dao;

    public AccountServiceImpl(IAccountDAO dao) {
        this.dao = dao;
    }

    @Override
    public Account insertAccount(AccountDTO accountDTO) throws IbanAlreadyExistsException, UserIdAlreadyExistsException {
        Account account = null;
        try {
            account = new Account();
            if (dao.ibanExists(accountDTO.getIban())) {
                throw new IbanAlreadyExistsException(account);
            }
            if (dao.userIdExists(accountDTO.getId())) {
                throw new UserIdAlreadyExistsException(account);
            }
            mapAccount(account, accountDTO);

            account = dao.insert(account);
        } catch (IbanAlreadyExistsException | UserIdAlreadyExistsException e) {
            e.printStackTrace();
            throw e;
        }

        return account;
    }

    @Override
    public Account updateAccount(long id, AccountDTO accountDTO)
            throws AccountNotFoundException, IbanAlreadyExistsException,
            UserIdAlreadyExistsException {
        Account account;
        try {
            account = new Account();
            mapAccount(account, accountDTO);

            if (id != accountDTO.getId() || dao.userIdExists(id)) {
                throw new AccountNotFoundException(id);
            }

            if (dao.ibanExists(accountDTO.getIban())) {
                if (!dao.get(accountDTO.getId()).equals(dao.get(accountDTO.getIban()))) {
                    throw new IbanAlreadyExistsException(account);
                }
            }

            if (!dao.userIdExists(accountDTO.getId())) {
                throw new UserIdAlreadyExistsException(account);
            }

            mapAccount(account, accountDTO);

            account = dao.update(id, account);
        } catch (IbanAlreadyExistsException | UserIdAlreadyExistsException e) {
            e.printStackTrace();
            throw e;
        }

        return account;
    }

    @Override
    public void deleteAccount(String iban) throws AccountNotFoundException {
        Account account;
        try {
            account = new Account();
            if (!dao.ibanExists(iban)) {
                throw new AccountNotFoundException(account);
            }

            dao.delete(iban);
        } catch (AccountNotFoundException e) {
            e.printStackTrace();
            throw e;
        }

    }

    @Override
    public void deleteAccount(long id) throws AccountNotFoundException {
        Account account;
        try {
            account = new Account();
            if (!dao.userIdExists(id)) {
                throw new AccountNotFoundException(account);
            }

            dao.delete(id);
        } catch (AccountNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Account getAccount(String iban) throws AccountNotFoundException {
        Account account;
        try {
            account = dao.get(iban);
            if (account == null) {
                throw new AccountNotFoundException(iban);
            }
            return account;
        } catch (AccountNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Account getAccount(long id) throws AccountNotFoundException {
        Account account;
        try {
            account = dao.get(id);
            if (account == null) {
                throw new AccountNotFoundException(id);
            }
            return account;
        } catch (AccountNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Account> getAllAccounts() {
        return dao.getAll();
    }

    @Override
    public Account withdraw(long id, double amount, String ssn) throws InsufficientBalanceException, SsnNotValidException, NegativeAmountException, AccountNotFoundException {
        Account account = null;
        try {
            account = dao.get(id);

            if (amount < 0) throw new NegativeAmountException(amount);
            if (!account.isSsnValid(ssn)) throw new SsnNotValidException(ssn);
            if (account == null) throw new AccountNotFoundException(id);
            if (amount > account.getBalance()) throw new InsufficientBalanceException(account.getBalance(), amount);

            account.setBalance(account.getBalance() - amount);
            dao.update(account.getId(), account);

        } catch (InsufficientBalanceException | SsnNotValidException | NegativeAmountException |
                 AccountNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
        return account;
    }

    @Override
    public Account deposit(long id, double amount) throws NegativeAmountException {
        Account account = null;
        try {
            account = dao.get(id);

            if (amount < 0) {
                throw new NegativeAmountException(amount);
            }

            double newBalance = account.getBalance() + amount;
            account.setBalance(newBalance);
            dao.update(account.getId(), account);
        } catch (NegativeAmountException e) {
            System.err.println("Error: Negative amount");
            throw e;
        }
        return account;
    }

    private void mapAccount(Account account, AccountDTO accountDTO) {
        account.setId(accountDTO.getId());
        account.setIban(accountDTO.getIban());
        account.setBalance(accountDTO.getBalance());
        User user = new User();
        mapUser(user, accountDTO.getUser());
        account.setUser(user);
    }

    private void mapUser(User user, UserDTO userDTO) {
        user.setId(userDTO.getId());
        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setSsn(userDTO.getSsn());
    }
}
