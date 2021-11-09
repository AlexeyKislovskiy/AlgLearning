package fertdt.servlets;

import fertdt.exceptions.DatabaseException;
import fertdt.exceptions.SessionException;
import fertdt.models.*;
import fertdt.services.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/algorithms/method")
public class MethodServlet extends HttpServlet {
    private ISessionService sessionService;
    private IMethodService methodService;
    private ISituationService situationService;
    private IAlgorithmService algorithmService;
    private ISettingsService settingsService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        sessionService = (ISessionService) context.getAttribute("sessionService");
        methodService = (IMethodService) context.getAttribute("methodService");
        situationService = (ISituationService) context.getAttribute("situationService");
        algorithmService = (IAlgorithmService) context.getAttribute("algorithmService");
        settingsService = (ISettingsService) context.getAttribute("settingsService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Cuber cuber = sessionService.getByCurrentSession(req);
            req.getSession().setAttribute("cuber", cuber);
            req.getSession().setAttribute("userStatus", Cuber.Status.USER);
            req.getSession().setAttribute("moderatorStatus", Cuber.Status.MODERATOR);
            req.getSession().setAttribute("adminStatus", Cuber.Status.ADMIN);
            req.getSession().setAttribute("learning", Situation.State.LEARNING);
            req.getSession().setAttribute("learned", Situation.State.LEARNED);
            String methodName = req.getParameter("name");
            Method method = methodService.getMethodByName(methodName, cuber);
            List<Integer> num = situationService.getNumberOfAllTypes(method, cuber);
            req.setAttribute("num", num);
            String situationName = req.getParameter("situation");
            if (situationName != null) {
                Situation situation = situationService.getSituationByName(situationName, cuber, method);
                req.getSession().setAttribute("situation", situation);
                getAnotherSituations(situation, cuber, req);
                req.getSession().setAttribute("algorithms", algorithmService.getAlgorithmsBySituation(situation, cuber));
                req.getSession().setAttribute("using", Algorithm.State.USING);
                req.getSession().setAttribute("notUsing", Algorithm.State.NOT_USING);
                Setting setting9 = settingsService.getSettingById(9, cuber);
                Setting setting11 = settingsService.getSettingById(11, cuber);
                Setting setting12 = settingsService.getSettingById(12, cuber);
                if (setting9 == null || setting9.getValue().equals("popularity"))
                    req.getSession().setAttribute("setting9", "popularity");
                else {
                    req.getSession().setAttribute("setting9", "length");
                    boolean interceptionFlag = setting11 != null && setting11.getValue().equals("yes");
                    boolean doubleMoveFlag = setting12 != null && setting12.getValue().equals("yes");
                    req.getSession().setAttribute("algorithms", algorithmService.getAlgorithmsSortedByLength(situation, cuber, interceptionFlag, doubleMoveFlag));
                }
            } else {
                req.getSession().removeAttribute("situation");
            }
            req.getSession().setAttribute("method", method);
            String search = req.getParameter("search");
            List<Situation> allSituations = situationService.getSituationsByMethod(method, cuber);
            if (search != null) {
                req.getSession().setAttribute("situations", situationService.filterSituations(allSituations, search));
            } else req.getSession().setAttribute("situations", allSituations);
        } catch (DatabaseException e) {
            throw new ServletException("Problem with database", e);
        } catch (SessionException e) {
            throw new ServletException("Can't find session", e);
        }
        req.getRequestDispatcher("/WEB-INF/views/method.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String methodName = req.getParameter("name");
        try {
            Cuber cuber = sessionService.getByCurrentSession(req);
            Method method = methodService.getMethodByName(methodName, cuber);
            req.getSession().setAttribute("method", method);
            String situationName = req.getParameter("situation");
            if (req.getParameter("check-all") != null) methodService.checkAll(method, cuber);
            else if (req.getParameter("plus-all") != null) methodService.plusAll(method, cuber);
            else if (req.getParameter("delete-all") != null) methodService.deleteAll(method, cuber);
            if (situationName != null) {
                Situation situation = situationService.getSituationByName(situationName, cuber, method);
                if (req.getParameter("check") != null) situationService.check(situation, cuber);
                else if (req.getParameter("plus") != null) situationService.plus(situation, cuber);
                else if (req.getParameter("delete") != null) situationService.delete(situation, cuber);
                getAnotherSituations(situation, cuber, req);
                methodService.recalculateState(method, cuber, situationService);
                methodService.setNumOfLearning(method.getId());
                methodService.setNumOfLearned(method.getId());
                situation = situationService.getSituationByName(situationName, cuber, method);
                req.getSession().setAttribute("situation", situation);
                if (req.getParameter("use-flag") != null) {
                    int algorithmId = Integer.parseInt(req.getParameter("use-flag"));
                    if (req.getParameter("use") != null) algorithmService.addUseAlgorithm(algorithmId, cuber);
                    else algorithmService.deleteUseAlgorithm(algorithmId, cuber);
                    req.getSession().setAttribute("algorithms", algorithmService.getAlgorithmsBySituation(situation, cuber));
                }
                if (req.getParameter("new-alg") != null) {
                    algorithmService.addAlgorithm(situation.getId(), req.getParameter("new-alg"), cuber);
                }
                Setting setting9 = settingsService.getSettingById(9, cuber);
                if (req.getParameter("sort") != null && req.getParameter("sort").equals("length") || req.getParameter("sort") == null && setting9 != null && setting9.getValue().equals("length")) {
                    Setting setting11 = settingsService.getSettingById(11, cuber);
                    Setting setting12 = settingsService.getSettingById(12, cuber);
                    boolean interceptionFlag = setting11 != null && setting11.getValue().equals("yes");
                    boolean doubleMoveFlag = setting12 != null && setting12.getValue().equals("yes");
                    req.getSession().setAttribute("algorithms", algorithmService.getAlgorithmsSortedByLength(situation, cuber, interceptionFlag, doubleMoveFlag));
                } else {
                    req.getSession().setAttribute("algorithms", algorithmService.getAlgorithmsBySituation(situation, cuber));
                }
            }
            String search = req.getParameter("search");
            method = methodService.getMethodByName(methodName, cuber);
            req.getSession().setAttribute("method", method);
            List<Situation> allSituations = situationService.getSituationsByMethod(method, cuber);
            if (search != null) {
                req.getSession().setAttribute("situations", situationService.filterSituations(allSituations, search));
            } else req.getSession().setAttribute("situations", allSituations);
            List<Integer> num = situationService.getNumberOfAllTypes(method, cuber);
            req.setAttribute("num", num);
            if (req.getParameter("sort") != null)
                req.getRequestDispatcher("/WEB-INF/views/method.jsp").forward(req, resp);
            else if (situationName != null)
                resp.sendRedirect(req.getContextPath() + "/algorithms/method?name=" + methodName + "&situation=" + situationName);
            else resp.sendRedirect(req.getContextPath() + "/algorithms/method?name=" + methodName);
        } catch (DatabaseException e) {
            throw new ServletException("Problem with database", e);
        } catch (SessionException e) {
            throw new ServletException("Can't find session", e);
        }
    }

    private void getAnotherSituations(Situation situation, Cuber cuber, HttpServletRequest req) throws DatabaseException {
        Situation mirrorSituation = situationService.getSituationById(situation.getMirrorId(), cuber);
        Situation reverseSituation = situationService.getSituationById(situation.getReverseId(), cuber);
        Situation mirrorReverseSituation = situationService.getSituationById(situation.getMirrorReverseId(), cuber);
        req.getSession().setAttribute("mirrorSituation", mirrorSituation);
        req.getSession().setAttribute("reverseSituation", reverseSituation);
        req.getSession().setAttribute("mirrorReverseSituation", mirrorReverseSituation);
    }
}
