package fertdt.services;

import fertdt.exceptions.DatabaseException;
import fertdt.helpers.IAlgorithmLengthCalculator;
import fertdt.models.Algorithm;
import fertdt.models.Cuber;
import fertdt.models.Situation;
import fertdt.repositories.IAlgorithmRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AlgorithmService implements IAlgorithmService {
    private final IAlgorithmRepository algorithmRepository;
    private final IAlgorithmLengthCalculator algorithmLengthCalculator;

    public AlgorithmService(IAlgorithmRepository algorithmRepository, IAlgorithmLengthCalculator algorithmLengthCalculator) {
        this.algorithmRepository = algorithmRepository;
        this.algorithmLengthCalculator = algorithmLengthCalculator;
    }

    @Override
    public List<Algorithm> getAlgorithmsBySituation(Situation situation, Cuber cuber) throws DatabaseException {
        List<Algorithm> list = algorithmRepository.getAlgorithmsBySituationId(situation.getId());
        List<Algorithm> ans = new ArrayList<>();
        for (Algorithm el : list) {
            setState(el, cuber);
            if (el.isVerified() || el.getAddCuberId() == cuber.getId()) ans.add(el);
        }
        return ans;
    }

    @Override
    public void setState(Algorithm algorithm, Cuber cuber) throws DatabaseException {
        algorithmRepository.setState(algorithm, cuber);
    }

    @Override
    public void addUseAlgorithm(int algorithmId, Cuber cuber) throws DatabaseException {
        algorithmRepository.addUseAlgorithm(algorithmId, cuber);
    }

    @Override
    public void deleteUseAlgorithm(int algorithmId, Cuber cuber) throws DatabaseException {
        algorithmRepository.deleteUseAlgorithm(algorithmId, cuber);
    }

    @Override
    public List<Algorithm> getAlgorithmsSortedByLength(Situation situation, Cuber cuber, boolean interceptionFlag, boolean doubleMoveFlag) throws DatabaseException {
        List<Algorithm> list = getAlgorithmsBySituation(situation, cuber);
        list.sort(Comparator.comparingInt(a -> algorithmLengthCalculator.length(interceptionFlag, doubleMoveFlag, a.getText())));
        return list;
    }

    @Override
    public List<Algorithm> getUsingAlgorithms(Situation situation, Cuber cuber) throws DatabaseException {
        List<Algorithm> list = getAlgorithmsBySituation(situation, cuber);
        List<Algorithm> ans = new ArrayList<>();
        for (Algorithm el : list) {
            if (el.getState() == Algorithm.State.USING) ans.add(el);
        }
        return ans;
    }

    @Override
    public void addAlgorithm(int situationId, String text, Cuber cuber) throws DatabaseException {
        algorithmRepository.addAlgorithm(situationId, text, cuber);
    }

    @Override
    public List<Algorithm> getNotVerifiedAlgorithms() throws DatabaseException {
        return algorithmRepository.getNotVerifiedAlgorithms();
    }

    @Override
    public void verify(int algorithmId, String text) throws DatabaseException {
        algorithmRepository.verify(algorithmId, text);
    }

    @Override
    public void delete(int algorithmId) throws DatabaseException {
        algorithmRepository.delete(algorithmId);
    }
}
