package fertdt.helpers;

public interface PasswordEncryptor {
    String encryptPassword(String password);

    boolean checkPasswords(String password1, String password2);
}
