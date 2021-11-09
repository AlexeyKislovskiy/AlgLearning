package fertdt.services;

import fertdt.exceptions.*;
import fertdt.helpers.*;
import fertdt.models.Cuber;
import fertdt.repositories.ICuberRepository;

public class CuberService implements ICuberService {
    private final ICuberRepository cuberRepository;
    private final IEmailValidator emailValidator;
    private final IEmailSender emailSender;
    private final PasswordEncryptor passwordEncryptor;
    private String correctCode;

    public CuberService(ICuberRepository cuberRepository, IEmailValidator emailValidator,
                        IEmailSender emailSender, PasswordEncryptor passwordEncryptor) {
        this.cuberRepository = cuberRepository;
        this.emailValidator = emailValidator;
        this.emailSender = emailSender;
        this.passwordEncryptor = passwordEncryptor;
    }

    public void register(Cuber cuber, String confirmPassword) throws MatchingNicknamesException, MatchingPasswordsException, WeakPasswordException, DatabaseException, IncorrectEmailException, SendEmailException, MatchingEmailException {
        if (cuberRepository.findByNickname(cuber.getNickname()) != null) {
            throw new MatchingNicknamesException("User with this nickname already exists");
        } else if (!cuber.getPassword().equals(confirmPassword)) {
            throw new MatchingPasswordsException("Passwords don't match");
        } else if (!cuber.getEmail().isEmpty() && !emailValidator.isValid(cuber.getEmail())) {
            throw new IncorrectEmailException("Incorrect email address");
        } else if (!cuber.getEmail().isEmpty() && cuberRepository.findByEmail(cuber.getEmail()) != null) {
            throw new MatchingEmailException("User with this email already exists");
        } else {
            String password = cuber.getPassword();
            boolean uppercaseLetter = false, lowercaseLetter = false, digit = false;
            for (int i = 0; i < password.length(); i++) {
                char current = password.charAt(i);
                if (current >= 'A' && current <= 'Z') uppercaseLetter = true;
                else if (current >= 'a' && current <= 'z') lowercaseLetter = true;
                else if (current >= '0' && current <= '9') digit = true;
            }
            if (!(password.length() >= 8 && uppercaseLetter && lowercaseLetter && digit)) {
                throw new WeakPasswordException("Password should be at least 8 characters with 1 uppercase, 1 " +
                        "lowercase and 1 digit character");
            } else if (cuber.getEmail().isEmpty()) cuberRepository.save(cuber);
            else registerWithEmail(cuber);
        }
    }

    private void registerWithEmail(Cuber cuber) throws SendEmailException {
        String[] message = ConfirmationMessageGenerator.generateMessage();
        String content = message[0];
        correctCode = message[1];
        emailSender.send("AlgLearning", content, cuber.getEmail());
    }

    @Override
    public void confirmEmail(Cuber cuber, String cuberCode) throws IncorrectEmailCodeException, DatabaseException {
        if (!cuberCode.equals(correctCode)) throw new IncorrectEmailCodeException("Incorrect code");
        else cuberRepository.save(cuber);
    }

    @Override
    public void recoveryConfirmation(String cuberCode) throws IncorrectEmailCodeException {
        if (!cuberCode.equals(correctCode)) throw new IncorrectEmailCodeException("Incorrect code");
//        else{
//
//        }
    }

    @Override
    public void login(Cuber cuber) throws DatabaseException, NoSuchUserException, IncorrectPasswordException {
        Cuber realCuber = cuberRepository.findByNickname(cuber.getNickname());
        if (realCuber == null) throw new NoSuchUserException("No user with this nickname");
        else if (!passwordEncryptor.checkPasswords(cuber.getPassword(), realCuber.getPassword()))
            throw new IncorrectPasswordException("Incorrect password");
//        else{
//
//        }
    }

    @Override
    public Cuber getByNickname(String nickname) throws DatabaseException {
        return cuberRepository.findByNickname(nickname);
    }

    @Override
    public Cuber getByEmail(String email) throws DatabaseException {
        return cuberRepository.findByEmail(email);
    }

    @Override
    public void recoveryPassword(String email) throws IncorrectEmailException, DatabaseException, MatchingEmailException, SendEmailException {
        if (!emailValidator.isValid(email)) {
            throw new IncorrectEmailException("Incorrect email address");
        } else if (cuberRepository.findByEmail(email) == null) {
            throw new MatchingEmailException("No user with this email");
        } else {
            String[] message = RecoveryMessageGenerator.generateMessage();
            String content = message[0];
            correctCode = message[1];
            emailSender.send("AlgLearning", content, email);
        }
    }

    @Override
    public int numOfLearning(Cuber cuber) throws DatabaseException {
        return cuberRepository.numOfLearning(cuber);
    }

    @Override
    public int numOfLearned(Cuber cuber) throws DatabaseException {
        return cuberRepository.numOfLearned(cuber);
    }

    @Override
    public void updateVisit(Cuber cuber) throws DatabaseException {
        cuberRepository.updateVisit(cuber);
    }

    @Override
    public void delete(Cuber cuber) throws DatabaseException {
        cuberRepository.delete(cuber);
    }
}