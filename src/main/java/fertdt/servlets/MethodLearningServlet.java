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

@WebServlet("/learning/method-learning")
public class MethodLearningServlet extends HttpServlet {
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
            String methodName = req.getParameter("name");
            situationService.updateLearningState(cuber);
            Method method = methodService.getMethodByName(methodName, cuber);
            req.getSession().setAttribute("method", method);
            List<Situation> situations = situationService.getAllLearning(cuber, method);
            req.getSession().setAttribute("situations", situations);
            if (situations.size() == 0) resp.sendRedirect(req.getContextPath() + "/learning");
            else {
                int randomPosition = (int) (Math.random() * situations.size());
                Situation situation = situations.get(randomPosition);
                req.getSession().setAttribute("situation", situation);
                String scramble = scrambleService.getScramble(situation.getId());
                req.getSession().setAttribute("scramble", scramble);
                List<Algorithm> algorithms = algorithmService.getUsingAlgorithms(situation, cuber);
                req.getSession().setAttribute("algorithms", algorithms);
                Setting setting1 = settingsService.getSettingById(1, cuber);
                if (setting1 == null || setting1.getValue().equals("no"))
                    req.getSession().setAttribute("setting1", "no");
                else req.getSession().setAttribute("setting1", "yes");
                Setting setting2 = settingsService.getSettingById(2, cuber);
                if (setting2 == null || setting2.getValue().equals("no"))
                    req.getSession().setAttribute("setting2", "no");
                else req.getSession().setAttribute("setting2", "yes");
                req.getRequestDispatcher("/WEB-INF/views/method-learning.jsp").forward(req, resp);
            }
        } catch (DatabaseException e) {
            throw new ServletException("Problem with database", e);
        } catch (SessionException e) {
            throw new ServletException("Can't find session", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Situation situation = (Situation) req.getSession().getAttribute("situation");
        try {
            Cuber cuber = sessionService.getByCurrentSession(req);
            if (situation.getLearningState() == Situation.LearningState.NEW)
                statisticsService.updateStatistics(cuber, "new");
            else if (situation.getLearningState() == Situation.LearningState.FORGOT)
                statisticsService.updateStatistics(cuber, "forgot");
            else statisticsService.updateStatistics(cuber, "repeat");
            String again = req.getParameter("again"), hard = req.getParameter("hard"), medium = req.getParameter("medium"),
                    easy = req.getParameter("easy");
            if (situation.getLearningState() != Situation.LearningState.REPEAT && again == null) {
                situationService.setLearningStatus(situation, cuber, "repeat");
            } else if (again != null) {
                situationService.setLearningStatus(situation, cuber, "forgot");
            } else if (hard != null) {
                situationService.updateNextLearning(situation, cuber, 1);
                situationService.setLearningStatus(situation, cuber, "await");
            } else if (medium != null) {
                situationService.updateNextLearning(situation, cuber, 1.5);
                situationService.setLearningStatus(situation, cuber, "await");
            } else if (easy != null) {
                situationService.updateNextLearning(situation, cuber, 2);
                situationService.setLearningStatus(situation, cuber, "await");
            }
        } catch (DatabaseException e) {
            throw new ServletException("Problem with database", e);
        } catch (SessionException e) {
            throw new ServletException("Can't find session", e);
        }
        resp.sendRedirect(req.getContextPath() + "/learning/method-learning?name=" + req.getParameter("name"));
    }

    private void setStatus(HttpServletRequest req) {
        req.getSession().setAttribute("userStatus", Cuber.Status.USER);
        req.getSession().setAttribute("moderatorStatus", Cuber.Status.MODERATOR);
        req.getSession().setAttribute("adminStatus", Cuber.Status.ADMIN);
        req.getSession().setAttribute("newS", Situation.LearningState.NEW);
        req.getSession().setAttribute("forgot", Situation.LearningState.FORGOT);
        req.getSession().setAttribute("repeat", Situation.LearningState.REPEAT);
    }
}
