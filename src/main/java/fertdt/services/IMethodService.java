package fertdt.services;

import fertdt.exceptions.DatabaseException;
import fertdt.models.Cube;
import fertdt.models.Cuber;
import fertdt.models.Method;
import fertdt.repositories.IMethodRepository;

import java.util.List;

public interface IMethodService {
    List<Method> getMethodsByCube(Cube cube, Cuber cuber) throws DatabaseException;

    List<Method> getAllMethods(Cuber cuber) throws DatabaseException;

    IMethodRepository getMethodRepository();

    void setState(Method method, Cuber cuber) throws DatabaseException;

    Method getMethodByName(String name, Cuber cuber) throws DatabaseException;

    void checkAll(Method method, Cuber cuber) throws DatabaseException;

    void plusAll(Method method, Cuber cuber) throws DatabaseException;

    void deleteAll(Method method, Cuber cuber) throws DatabaseException;

    void recalculateState(Method method, Cuber cuber, ISituationService situationService) throws DatabaseException;

    void setNumOfLearning(int methodId) throws DatabaseException;

    void setNumOfLearned(int methodId) throws DatabaseException;
}
