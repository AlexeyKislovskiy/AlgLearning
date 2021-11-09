package fertdt.services;

import fertdt.exceptions.DatabaseException;
import fertdt.models.Algorithm;
import fertdt.models.Cuber;
import fertdt.models.Situation;

import java.util.List;

public interface IAlgorithmService {
    List<Algorithm> getAlgorithmsBySituation(Situation situation, Cuber cuber) throws DatabaseException;

    void setState(Algorithm algorithm, Cuber cuber) throws DatabaseException;

    void addUseAlgorithm(int algorithmId, Cuber cuber) throws DatabaseException;

    void deleteUseAlgorithm(int algorithmId, Cuber cuber) throws DatabaseException;

    List<Algorithm> getAlgorithmsSortedByLength(Situation situation, Cuber cuber, boolean interceptionFlag, boolean doubleMoveFlag) throws DatabaseException;

    List<Algorithm> getUsingAlgorithms(Situation situation, Cuber cuber) throws DatabaseException;

    void addAlgorithm(int situationId, String text, Cuber cuber) throws DatabaseException;

    List<Algorithm> getNotVerifiedAlgorithms() throws DatabaseException;

    void verify(int algorithmId, String text) throws DatabaseException;

    void delete(int algorithmId) throws DatabaseException;
}
