package fertdt.repositories;

import fertdt.exceptions.DatabaseException;

public interface IScrambleRepository {
    String getScramble(int situationId) throws DatabaseException;
}
