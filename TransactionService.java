import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TransactionService {
    // 1. Establish the starting balance
    private BigDecimal accountBalance = new BigDecimal("10000.00");
    private final List<Transaction> transactionLedger = new ArrayList<>();

    public void processTransaction(BigDecimal amount, Transaction.TransactionType type) {
        try {
            // Create the initial pending transaction object
            Transaction tx = new Transaction(amount, type);
            
            // 2. Business Logic Rule: Check if spending exceeds available funds
            if (type == Transaction.TransactionType.WITHDRAWAL || type == Transaction.TransactionType.INVESTMENT) {
                if (amount.compareTo(accountBalance) > 0) {
                    tx.setStatus(Transaction.Status.FAILED);
                    System.out.println("Rejected! " + tx + " -> Reason: Insufficient Funds!");
                    return; 
                } else {
                    accountBalance = accountBalance.subtract(amount);
                    tx.setStatus(Transaction.Status.SUCCESS);
                }
            } else if (type == Transaction.TransactionType.DEPOSIT) {
                accountBalance = accountBalance.add(amount);
                tx.setStatus(Transaction.Status.SUCCESS);
            }

            transactionLedger.add(tx);
            System.out.println("Processed: " + tx + " | Remaining Balance: $" + accountBalance);
            
        } catch (IllegalArgumentException e) {
            System.err.println("Transaction Rejected: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        TransactionService service = new TransactionService();
        System.out.println("Starting Bank Balance: $10000.00");
        System.out.println("------------------------------------------------");
        
        // Test Case 1: Valid Investment (Should succeed)
        service.processTransaction(new BigDecimal("5000.75"), Transaction.TransactionType.INVESTMENT);
        
        // Test Case 2: Insufficient Funds Withdrawal (Should fail)
        service.processTransaction(new BigDecimal("12523.23"), Transaction.TransactionType.WITHDRAWAL);

        // Test Case 3: Valid Deposit (Should succeed)
        service.processTransaction(new BigDecimal("3000.00"), Transaction.TransactionType.DEPOSIT);
    }
}
