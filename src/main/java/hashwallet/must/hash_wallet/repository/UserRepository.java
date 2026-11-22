package hashwallet.must.hash_wallet.repository;

import hashwallet.must.hash_wallet.config.HibernateUtil;
import hashwallet.must.hash_wallet.model.User;
import org.hibernate.Session;


public class UserRepository {


    public static User retrieveUser(String email) {
        Session session = (Session) HibernateUtil.getSessionFactory().openSession();



        try (session) {
            return session.createQuery("FROM User WHERE email = :email", User.class)
                    .setParameter("email", email)
                    .uniqueResult();  // returns the User or null
        }
    }
}
