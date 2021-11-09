package fertdt.helpers;

import org.mindrot.jbcrypt.BCrypt;

public class BcryptEncryptor implements PasswordEncryptor {
    public String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean checkPasswords(String password1, String password2) {
        return BCrypt.checkpw(password1, password2);
    }
}
