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

@WebServlet("/training/method-training")
public class MethodTrainingServlet extends HttpServlet {
    private ISessionService sessionService;
    private IMethodService methodService;
    private ISituationService situationService;
    private IAlgorithmService algorithmService;
    private IScrambleService scrambleService;
    private IStatisticsService statisticsService;
    private ISettingsService settingsService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        sessionService = (ISessionService) context.getAttribute("sessionService");
        methodService = (IMethodService) context.getAttribute("methodService");
        situationService = (ISituationService) context.getAttribute("situationService");
        algorithmService = (IAlgorithmService) context.getAttribute("algorithmService");
        scrambleService = (IScrambleService) context.getAttribute("scrambleService");
        statisticsService = (IStatisticsService) context.getAttribute("statisticsService");
        settingsService = (ISettingsService) context.getAttribute("settingsService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Cuber cuber = sessionService.getByCurrentSession(req);
            req.getSession().setAttribute("cuber", cuber);
            setStatus(req);
            statisticsService.updateStatistics(cuber, "trained");
            String methodName = req.getParameter("name");
            Method method = methodService.getMethodByName(methodName, cuber);
            req.getSession().setAttribute("method", method);
            List<Situation> situations = situationService.getAllTrainingSituationsByMethod(method.getId(), cuber);
            req.getSession().setAttribute("situations", situations);
            String search = req.getParameter("search");
            List<Situation> allSituations = situationService.getSituationsByMethod(method, cuber);
            if (search != null) {
                req.getSession().setAttribute("allSituations", situationService.filterSituations(allSituations, search));
            } else req.getSession().setAttribute("allSituations", allSituations);
            int randomPosition = (int) (Math.random() * situations.size());
            Situation situation = situations.get(randomPosition);
            req.getSession().setAttribute("situation", situation);
            String scramble = scrambleService.getScramble(situation.getId());
            req.getSession().setAttribute("scramble", scramble);
            List<Algorithm> algorithms = algorithmService.getUsingAlgorithms(situation, cuber);
            req.getSession().setAttribute("algorithms", algorithms);
            Setting setting6 = settingsService.getSettingById(6, cuber);
            if (setting6 == null || setting6.getValue().equals("no")) req.getSession().setAttribute("setting6", "no");
            else req.getSession().setAttribute("setting6", "yes");
            Setting setting7 = settingsService.getSettingById(7, cuber);
            if (setting7 == null || setting7.getValue().equals("no")) req.getSession().setAttribute("setting7", "no");
            else req.getSession().setAttribute("setting7", "yes");
        } catch (DatabaseException e) {
            throw new ServletException("Problem with database", e);
        } catch (SessionException e) {
            throw new ServletException("Can't find session", e);
        }
        req.getRequestDispatcher("/WEB-INF/views/method-training.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Cuber cuber = sessionService.getByCurrentSession(req);
            Method method = (Method) req.getSession().getAttribute("method");
            situationService.updateAllTraining(method.getId(), cuber, req);
        } catch (DatabaseException e) {
            throw new ServletException("Problem with database", e);
        } catch (SessionException e) {
            throw new ServletException("Can't find session", e);
        }
        resp.sendRedirect(req.getContextPath() + "/training/method-training?name=" + req.getParameter("name"));
    }

    private void setStatus(HttpServletRequest req){
        req.getSession().setAttribute("userStatus", Cuber.Status.USER);
        req.getSession().setAttribute("moderatorStatus", Cuber.Status.MODERATOR);
        req.getSession().setAttribute("adminStatus", Cuber.Status.ADMIN);
        req.getSession().setAttribute("situationLearning", Situation.State.LEARNING);
        req.getSession().setAttribute("situationLearned", Situation.State.LEARNED);
        req.getSession().setAttribute("methodLearning", Method.State.LEARNING);
        req.getSession().setAttribute("methodLearned", Method.State.LEARNED);
        req.getSession().setAttribute("situationTraining", Situation.TrainingState.TRAINING);
    }
}
