package fertdt.services;

import fertdt.exceptions.DatabaseException;

public interface IScrambleService {
    String getScramble(int situationId) throws DatabaseException;
}
