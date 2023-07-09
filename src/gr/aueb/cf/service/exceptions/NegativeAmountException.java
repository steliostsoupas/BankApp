package gr.aueb.cf.service.exceptions;

public class NegativeAmountException extends Exception {
    private static final long serialVersionUID = 1L;

    public NegativeAmountException(){}

    public NegativeAmountException(double amount) {
        super("Amount " + amount + " is negative");
    }
}
