package fertdt.services;

import fertdt.exceptions.DatabaseException;
import fertdt.models.Cuber;
import fertdt.models.Method;
import fertdt.models.Situation;
import fertdt.repositories.ISituationRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ISituationService {
    List<Situation> getSituationsByMethod(Method method, Cuber cuber) throws DatabaseException;

    void setState(Situation situation, Cuber cuber) throws DatabaseException;

    Situation getSituationById(int id, Cuber cuber) throws DatabaseException;

    Situation getSituationByName(String name, Cuber cuber, Method method) throws DatabaseException;

    List<Integer> getNumberOfAllTypes(Method method, Cuber cuber) throws DatabaseException;

    void check(Situation situation, Cuber cuber) throws DatabaseException;

    void plus(Situation situation, Cuber cuber) throws DatabaseException;

    void delete(Situation situation, Cuber cuber) throws DatabaseException;

    ISituationRepository getSituationRepository();

    List<Situation> filterSituations(List<Situation> allSituations, String search);

    List<Situation> getAllTrainingSituationsByMethod(int methodId, Cuber cuber) throws DatabaseException;

    void updateAllTraining(int methodId, Cuber cuber, HttpServletRequest req) throws DatabaseException;

    List<Method> getAllLearningMethods(Cuber cuber, List<Method> methods) throws DatabaseException;

    List<Situation> getAllLearning(Cuber cuber, Method method) throws DatabaseException;

    void setLearningStatus(Situation situation, Cuber cuber, String status) throws DatabaseException;

    void updateNextLearning(Situation situation, Cuber cuber, double multiplier) throws DatabaseException;

    void updateLearningState(Cuber cuber) throws DatabaseException;
}
