package fertdt.services;

import fertdt.exceptions.DatabaseException;
import fertdt.models.Cube;
import fertdt.models.Cuber;
import fertdt.models.Method;
import fertdt.repositories.IMethodRepository;

import java.util.List;

public class MethodService implements IMethodService {
    private final IMethodRepository methodRepository;

    public MethodService(IMethodRepository methodRepository) {
        this.methodRepository = methodRepository;
    }

    @Override
    public List<Method> getMethodsByCube(Cube cube, Cuber cuber) throws DatabaseException {
        List<Method> list = methodRepository.getMethodsByCubeId(cube.getId());
        for (Method el : list) {
            setState(el, cuber);
        }
        return list;
    }

    @Override
    public List<Method> getAllMethods(Cuber cuber) throws DatabaseException {
        List<Method> list = methodRepository.getAllMethods();
        for (Method el : list) {
            setState(el, cuber);
        }
        return list;
    }

    @Override
    public IMethodRepository getMethodRepository() {
        return methodRepository;
    }

    @Override
    public void setState(Method method, Cuber cuber) throws DatabaseException {
        methodRepository.setState(method, cuber);
    }

    @Override
    public Method getMethodByName(String name, Cuber cuber) throws DatabaseException {
        Method method = methodRepository.getMethodByName(name);
        setState(method, cuber);
        return method;
    }

    @Override
    public void checkAll(Method method, Cuber cuber) throws DatabaseException {
        methodRepository.checkAll(method, cuber);
    }

    @Override
    public void plusAll(Method method, Cuber cuber) throws DatabaseException {
        methodRepository.plusAll(method, cuber);
    }

    @Override
    public void deleteAll(Method method, Cuber cuber) throws DatabaseException {
        methodRepository.deleteAll(method, cuber);
    }

    @Override
    public void recalculateState(Method method, Cuber cuber, ISituationService situationService) throws DatabaseException {
        methodRepository.recalculateState(method, cuber, situationService.getSituationRepository());
    }

    @Override
    public void setNumOfLearning(int methodId) throws DatabaseException {
        methodRepository.setNumOfLearning(methodId);
    }

    @Override
    public void setNumOfLearned(int methodId) throws DatabaseException {
        methodRepository.setNumOfLearned(methodId);
    }
}
