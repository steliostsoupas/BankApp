package gr.aueb.cf;

import gr.aueb.cf.dao.AccountDAOImpl;
import gr.aueb.cf.dao.IAccountDAO;
import gr.aueb.cf.dto.AccountDTO;
import gr.aueb.cf.dto.UserDTO;
import gr.aueb.cf.model.Account;
import gr.aueb.cf.service.AccountServiceImpl;
import gr.aueb.cf.service.IAccountService;
import gr.aueb.cf.service.exceptions.*;

import java.util.List;

/**
 * The entry point for the application, demonstrating the usage of the account gr.aueb.cf.service.
 */
public class Main {

    // Wiring
    private final static IAccountDAO dao = new AccountDAOImpl();
    private final static IAccountService service = new AccountServiceImpl(dao);

    public static void main(String[] args) {
        try {
            // Create user and account data transfer objects
            UserDTO userDTO1 = new UserDTO(1L, "Alice", "Wonderland", "12345");
            AccountDTO accountDTO1 = new AccountDTO(1L, userDTO1, "GR123456", 100.0);

            UserDTO userDTO2 = new UserDTO(2L, "Bob", "M.", "12345");
            AccountDTO accountDTO2 = new AccountDTO(2L, userDTO2, "GR123457", 1000.0);

            // Insert the account DTOs into the system
            service.insertAccount(accountDTO1);
            service.insertAccount(accountDTO2);

            // Deposit 50.0 to the account with ID 1L
            Account depositedAccount = service.deposit(1L, 50.0);
            System.out.println("Deposited Account: " + depositedAccount);

            // Withdraw 900.0 from the account with ID 2L
            Account withdrawnAccount = service.withdraw(2L, 900.0, "12345");
            System.out.println("Withdrawn Account: " + withdrawnAccount);

            // Delete the account with ID 1L
            service.deleteAccount(1L);

            // Retrieve all accounts
            System.out.println("Retrieve all accounts");
            List<Account> accounts = service.getAllAccounts();
            for (Account account : accounts) {
                System.out.println(account);
            }
        } catch (AccountNotFoundException | IbanAlreadyExistsException |
                 UserIdAlreadyExistsException | InsufficientBalanceException |
                 SsnNotValidException | NegativeAmountException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
