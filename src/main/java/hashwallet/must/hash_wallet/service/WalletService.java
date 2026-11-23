package hashwallet.must.hash_wallet.service;

import hashwallet.must.hash_wallet.config.HibernateUtil;
import hashwallet.must.hash_wallet.model.Wallet;
import hashwallet.must.hash_wallet.repository.WalletRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;

public class WalletService {

    public static boolean process(String walletID, BigDecimal amount, String process) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            try {
                Wallet wallet = WalletRepository.retrieveWallet(session, walletID);

                if (wallet == null) {
                    transaction.rollback();
                    return false;
                }

                if (process.equals("deposit")) {wallet.deposit(amount);}

                if (process.equals("withdraw")) {wallet.withdraw(amount);}

                transaction.commit();
                return true;

            } catch (Exception e) {

                if (transaction.isActive()) {
                    transaction.rollback();
                }

                e.printStackTrace();
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean transfer(String senderWalletID, String recipientWalletID, BigDecimal amount) {

        // Prevent invalid transfers
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        // Prevent self-transfers
        if (senderWalletID.equals(recipientWalletID)) {
            return false;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            try {
                Wallet sender = WalletRepository.retrieveWallet(session, senderWalletID);

                Wallet recipient = WalletRepository.retrieveWallet(session, recipientWalletID);

                if (sender == null || recipient == null) {
                    transaction.rollback();
                    return false;
                }

                sender.withdraw(amount);

                recipient.deposit(amount);

                transaction.commit();

                return true;

            } catch (Exception e) {

                if (transaction.isActive()) {
                    transaction.rollback();
                }

                e.printStackTrace();
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
