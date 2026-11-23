package hashwallet.must.hash_wallet.repository;

import hashwallet.must.hash_wallet.config.HibernateUtil;
import hashwallet.must.hash_wallet.model.Wallet;
import org.hibernate.Session;

import java.math.BigDecimal;

public class WalletRepository {

    public static Wallet retrieveWallet(String walletID) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.createQuery("FROM Wallet WHERE walletNumber = :walletNumber", Wallet.class)
                    .setParameter("walletNumber", walletID)
                    .uniqueResult();
        }
    }

    public static Wallet retrieveWallet(Session session, String walletID) {

        return session.createQuery(
                        "FROM Wallet WHERE walletNumber = :walletNumber",
                        Wallet.class
                )
                .setParameter("walletNumber", walletID)
                .uniqueResult();
    }

    public static BigDecimal getTotalBalance() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            BigDecimal total = session.createQuery(
                    "SELECT SUM(w.balance) FROM Wallet w",
                    BigDecimal.class
            ).uniqueResult();

            return total != null ? total : BigDecimal.ZERO;
        }
    }
}
