package fertdt.repositories;

import fertdt.exceptions.DatabaseException;
import fertdt.models.Cuber;
import fertdt.models.Method;

import java.util.List;

public interface IMethodRepository {
    List<Method> getMethodsByCubeId(int cubeId) throws DatabaseException;

    List<Method> getAllMethods() throws DatabaseException;

    void setState(Method method, Cuber cuber) throws DatabaseException;

    Method getMethodByName(String name) throws DatabaseException;

    void checkAll(Method method, Cuber cuber) throws DatabaseException;

    void plusAll(Method method, Cuber cuber) throws DatabaseException;

    void deleteAll(Method method, Cuber cuber) throws DatabaseException;

    void recalculateState(Method method, Cuber cuber, ISituationRepository situationRepository) throws DatabaseException;

    void setNumOfLearning(int methodId) throws DatabaseException;

    void setNumOfLearned(int methodId) throws DatabaseException;
}
