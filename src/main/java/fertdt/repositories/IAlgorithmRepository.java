package fertdt.repositories;

import fertdt.exceptions.DatabaseException;
import fertdt.models.Algorithm;
import fertdt.models.Cuber;

import java.util.List;

public interface IAlgorithmRepository {
    List<Algorithm> getAlgorithmsBySituationId(int situationId) throws DatabaseException;

    void setState(Algorithm algorithm, Cuber cuber) throws DatabaseException;

    void addUseAlgorithm(int algorithmId, Cuber cuber) throws DatabaseException;

    void deleteUseAlgorithm(int algorithmId, Cuber cuber) throws DatabaseException;

    Algorithm getAlgorithmById(int algorithmId) throws DatabaseException;

    void addAlgorithm(int situationId, String text, Cuber cuber) throws DatabaseException;

    List<Algorithm> getNotVerifiedAlgorithms() throws DatabaseException;

    void verify(int algorithmId, String text) throws DatabaseException;

    void delete(int algorithmId) throws DatabaseException;
}
