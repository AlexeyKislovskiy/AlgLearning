package fertdt.services;

import fertdt.exceptions.DatabaseException;
import fertdt.models.Cuber;
import fertdt.models.Method;
import fertdt.models.Situation;
import fertdt.repositories.ISituationRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class SituationService implements ISituationService {
    private final ISituationRepository situationRepository;

    public SituationService(ISituationRepository situationRepository) {
        this.situationRepository = situationRepository;
    }

    @Override
    public ISituationRepository getSituationRepository() {
        return situationRepository;
    }

    @Override
    public List<Situation> getSituationsByMethod(Method method, Cuber cuber) throws DatabaseException {
        List<Situation> list = situationRepository.getSituationsByMethodId(method.getId());
        for (Situation el : list) {
            setState(el, cuber);
        }
        return list;
    }

    @Override
    public void setState(Situation situation, Cuber cuber) throws DatabaseException {
        situationRepository.setState(situation, cuber);
    }

    @Override
    public Situation getSituationById(int id, Cuber cuber) throws DatabaseException {
        Situation situation = situationRepository.getSituationById(id);
        setState(situation, cuber);
        return situation;
    }

    @Override
    public Situation getSituationByName(String name, Cuber cuber, Method method) throws DatabaseException {
        Situation situation = situationRepository.getSituationByName(name, method.getId());
        setState(situation, cuber);
        return situation;
    }

    @Override
    public List<Integer> getNumberOfAllTypes(Method method, Cuber cuber) throws DatabaseException {
        return situationRepository.getNumberOfAllTypes(method.getId(), cuber);
    }

    @Override
    public void check(Situation situation, Cuber cuber) throws DatabaseException {
        situationRepository.check(situation, cuber);
    }

    @Override
    public void plus(Situation situation, Cuber cuber) throws DatabaseException {
        situationRepository.plus(situation, cuber);
    }

    @Override
    public void delete(Situation situation, Cuber cuber) throws DatabaseException {
        situationRepository.delete(situation, cuber);
    }

    @Override
    public List<Situation> filterSituations(List<Situation> allSituations, String search) {
        List<Situation> ans = new ArrayList<>();
        List<String> excluded = new ArrayList<>();
        search = search.toLowerCase().strip();
        for (Situation el : allSituations) {
            String lowerName = el.getName().toLowerCase();
            String[] g = search.split("\\|");
            for (int i = 0; i < g.length; i++) {
                g[i] = g[i].strip();
                if (g[i].equals("")) continue;
                else if (g[i].equals("*")) ans = new ArrayList<>(allSituations);
                else if (g[i].startsWith("~")) excluded.add(g[i].substring(1));
                else if (g[i].charAt(0) == '^' && g[i].charAt(g[i].length() - 1) == '$') {
                    if (g[i].substring(1, g[i].length() - 1).equals(lowerName)) {
                        ans.add(el);
                        break;
                    } else continue;
                } else if (g[i].charAt(0) == '^') {
                    if (lowerName.startsWith(g[i].substring(1))) {
                        ans.add(el);
                        break;
                    } else continue;
                } else if (g[i].charAt(g[i].length() - 1) == '$') {
                    if (lowerName.endsWith(g[i].substring(0, g[i].length() - 1))) {
                        ans.add(el);
                        break;
                    } else continue;
                }
                if (lowerName.contains(g[i])) {
                    ans.add(el);
                    break;
                }
            }
        }
        List<Situation> realAns = new ArrayList<>(ans);
        for (Situation el : ans) {
            String lowerName = el.getName().toLowerCase();
            for (String ex : excluded) {
                if (ex.equals("*")) return new ArrayList<>();
                if (ex.charAt(0) == '^' && ex.charAt(ex.length() - 1) == '$') {
                    if (ex.substring(1, ex.length() - 1).equals(lowerName)) {
                        realAns.remove(el);
                        break;
                    } else continue;
                } else if (ex.charAt(0) == '^') {
                    if (lowerName.startsWith(ex.substring(1))) {
                        realAns.remove(el);
                        break;
                    } else continue;
                } else if (ex.charAt(ex.length() - 1) == '$') {
                    if (lowerName.endsWith(ex.substring(0, ex.length() - 1))) {
                        realAns.remove(el);
                        break;
                    } else continue;
                }
                if (lowerName.contains(ex)) {
                    realAns.remove(el);
                    break;
                }
            }
        }
        return realAns;
    }

    @Override
    public List<Situation> getAllTrainingSituationsByMethod(int methodId, Cuber cuber) throws DatabaseException {
        List<Situation> list = situationRepository.getAllTrainingSituationsByMethod(methodId, cuber);
        for (Situation el : list) {
            setState(el, cuber);
        }
        return list;
    }

    @Override
    public void updateAllTraining(int methodId, Cuber cuber, HttpServletRequest req) throws DatabaseException {
        situationRepository.updateAllTraining(methodId, cuber, req);
    }

    @Override
    public List<Method> getAllLearningMethods(Cuber cuber, List<Method> methods) throws DatabaseException {
        List<Method> learningMethods = new ArrayList<>();

        for (Method el : methods) {
            if (el.getState() != Method.State.LEARNING) continue;
            int forgot = 0, newS = 0, repeat = 0;
            List<Situation> situations = getSituationsByMethod(el, cuber);
            for (Situation sit : situations) {
                if (sit.getLearningState() == Situation.LearningState.NEW) newS++;
                else if (sit.getLearningState() == Situation.LearningState.REPEAT) repeat++;
                else if (sit.getLearningState() == Situation.LearningState.FORGOT) forgot++;
            }
            el.setNumOfForgot(forgot);
            el.setNumOfNew(newS);
            el.setNumOfRepeat(repeat);
            learningMethods.add(el);
        }
        return learningMethods;
    }

    @Override
    public List<Situation> getAllLearning(Cuber cuber, Method method) throws DatabaseException {
        List<Situation> learningSituations = new ArrayList<>();
        int forgot = 0, newS = 0, repeat = 0;
        List<Situation> situations = getSituationsByMethod(method, cuber);
        for (Situation sit : situations) {
            if (sit.getLearningState() == Situation.LearningState.NEW) {
                newS++;
                learningSituations.add(sit);
            } else if (sit.getLearningState() == Situation.LearningState.REPEAT) {
                repeat++;
                learningSituations.add(sit);
            } else if (sit.getLearningState() == Situation.LearningState.FORGOT) {
                forgot++;
                learningSituations.add(sit);
            }
        }
        method.setNumOfForgot(forgot);
        method.setNumOfNew(newS);
        method.setNumOfRepeat(repeat);
        return learningSituations;
    }

    @Override
    public void setLearningStatus(Situation situation, Cuber cuber, String status) throws DatabaseException {
        situationRepository.setLearningStatus(situation, cuber, status);
    }

    @Override
    public void updateNextLearning(Situation situation, Cuber cuber, double multiplier) throws DatabaseException {
        situationRepository.updateNextLearning(situation, cuber, multiplier);
    }

    @Override
    public void updateLearningState(Cuber cuber) throws DatabaseException {
        situationRepository.updateLearningState(cuber);
    }
}
