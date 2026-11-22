package hashwallet.must.hash_wallet.config;

import hashwallet.must.hash_wallet.model.User;
import hashwallet.must.hash_wallet.model.Wallet;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {

    // Creates and manages sessions
    private static final SessionFactory sessionFactory = new Configuration()
                                                            .configure()
                                                            .addAnnotatedClass(User.class)
                                                            .addAnnotatedClass(Wallet.class)
                                                            .buildSessionFactory();


    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
