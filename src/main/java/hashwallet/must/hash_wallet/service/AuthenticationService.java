package hashwallet.must.hash_wallet.service;

import hashwallet.must.hash_wallet.model.User;
import static hashwallet.must.hash_wallet.repository.UserRepository.retrieveUser;

public class AuthenticationService {

    public static boolean authenticate(String email, String password) {

        User user = retrieveUser(email);

        if (user != null) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) return true;
        }

        return false;
    }
}
