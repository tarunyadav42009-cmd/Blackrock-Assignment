import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public final class Transaction {
    
    public enum TransactionType {
        DEPOSIT, WITHDRAWAL, INVESTMENT
    }

    public enum Status {
        PENDING, SUCCESS, FAILED
    }

    private final String transactionId;
    private final LocalDateTime timestamp; 
    private final BigDecimal amount;
    private final TransactionType type;
    private Status status; 

    public Transaction(BigDecimal amount, TransactionType type) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive.");
        }
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null.");
        }
        
        this.transactionId = UUID.randomUUID().toString();
        this.timestamp = LocalDateTime.now(); // Captures the exact execution time
        this.amount = amount;
        this.type = type;
        this.status = Status.PENDING; 
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public BigDecimal getAmount() { return amount; }
    public TransactionType getType() { return type; }

    // Updated to the String to include the Timestamp anchor
    @Override
    public String toString() {
        return String.format("Transaction[ID=%s, Type=%s, Amount=$%s, Status=%s, Time=%s]", 
                transactionId, type, amount, status, timestamp);
    }
}
