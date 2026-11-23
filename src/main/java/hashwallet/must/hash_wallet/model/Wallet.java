package hashwallet.must.hash_wallet.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "wallets")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    private String walletNumber;
    private BigDecimal balance;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    public Wallet() {}

    public Wallet(BigDecimal balance) {
        this.balance = balance;
        this.walletNumber = generateWalletNum();
    }

    private String generateWalletNum() {
        return "SPA"+ LocalTime.now().format(DateTimeFormatter.ofPattern("ssSSS"));
    }

    public long getID() {
        return ID;
    }

    public String getWalletNumber() {
        return walletNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {

        this.user = user;
    }
}
