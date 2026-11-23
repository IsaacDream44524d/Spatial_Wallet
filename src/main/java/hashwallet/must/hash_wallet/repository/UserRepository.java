package hashwallet.must.hash_wallet.repository;

import hashwallet.must.hash_wallet.config.HibernateUtil;
import hashwallet.must.hash_wallet.model.User;
import hashwallet.must.hash_wallet.model.Wallet;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;

public class UserRepository {

    public static User retrieveUser(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM User WHERE email = :email", User.class)
                    .setParameter("email", email)
                    .uniqueResult();
        }
    }

    public static boolean save(String email, String username, String password, BigDecimal initialBalance) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            User user = new User(username, email, password);
            Wallet wallet = new Wallet(initialBalance);

            // Bidirectional relationship (important)
            user.setWallet(wallet);
            wallet.setUser(user);

            session.persist(user);         // relies on cascade = CascadeType.ALL (or PERSIST)

            transaction.commit();
            return true;

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
}