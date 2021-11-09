package fertdt.services;

import fertdt.exceptions.*;
import fertdt.models.Cuber;

public interface ICuberService {
    void register(Cuber cuber, String confirmPassword) throws MatchingNicknamesException, MatchingPasswordsException, WeakPasswordException, DatabaseException, IncorrectEmailException, SendEmailException, MatchingEmailException;

    Cuber getByNickname(String nickname) throws DatabaseException;

    Cuber getByEmail(String nickname) throws DatabaseException;

    void confirmEmail(Cuber cuber, String cuberCode) throws IncorrectEmailCodeException, DatabaseException;

    void login(Cuber cuber) throws DatabaseException, NoSuchUserException, IncorrectPasswordException;

    void recoveryPassword(String email) throws IncorrectEmailException, DatabaseException, MatchingEmailException, SendEmailException;

    void recoveryConfirmation(String cuberCode) throws IncorrectEmailCodeException;

    int numOfLearning(Cuber cuber) throws DatabaseException;

    int numOfLearned(Cuber cuber) throws DatabaseException;

    void updateVisit(Cuber cuber) throws DatabaseException;

    void delete(Cuber cuber) throws DatabaseException;
}
