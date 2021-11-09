package fertdt.repositories;

import fertdt.exceptions.DatabaseException;
import fertdt.models.Cuber;
import fertdt.models.Situation;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ISituationRepository {
    List<Situation> getSituationsByMethodId(int methodId) throws DatabaseException;

    void setState(Situation situation, Cuber cuber) throws DatabaseException;

    Situation getSituationById(int id) throws DatabaseException;

    Situation getSituationByName(String name, int methodId) throws DatabaseException;

    List<Integer> getNumberOfAllTypes(int methodId, Cuber cuber) throws DatabaseException;

    void check(Situation situation, Cuber cuber) throws DatabaseException;

    void plus(Situation situation, Cuber cuber) throws DatabaseException;

    void delete(Situation situation, Cuber cuber) throws DatabaseException;

    List<Situation> getAllTrainingSituationsByMethod(int methodId, Cuber cuber) throws DatabaseException;

    void updateAllTraining(int methodId, Cuber cuber, HttpServletRequest req) throws DatabaseException;

    void setLearningStatus(Situation situation, Cuber cuber, String status) throws DatabaseException;

    void updateNextLearning(Situation situation, Cuber cuber, double multiplier) throws DatabaseException;

    void updateLearningState(Cuber cuber) throws DatabaseException;
}
