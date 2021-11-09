package fertdt.repositories;

import fertdt.exceptions.DatabaseException;
import fertdt.models.Cuber;

public interface ICuberRepository {
    void save(Cuber cuber) throws DatabaseException;

    Cuber findById(int id) throws DatabaseException;

    Cuber findByNickname(String name) throws DatabaseException;

    Cuber findByEmail(String email) throws DatabaseException;

    int numOfLearning(Cuber cuber) throws DatabaseException;

    int numOfLearned(Cuber cuber) throws DatabaseException;

    void updateVisit(Cuber cuber) throws DatabaseException;

    void delete(Cuber cuber) throws DatabaseException;
}
