package fertdt.servlets;

import fertdt.exceptions.DatabaseException;
import fertdt.exceptions.SessionException;
import fertdt.models.Cube;
import fertdt.models.Cuber;
import fertdt.models.Method;
import fertdt.services.ICubeService;
import fertdt.services.IMethodService;
import fertdt.services.ISessionService;
import fertdt.services.ISituationService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet({"/algorithms", "/training", "/learning"})
public class AlgorithmsServlet extends HttpServlet {
    private ICubeService cubeService;
    private IMethodService methodService;
    private ISituationService situationService;
    private ISessionService sessionService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        cubeService = (ICubeService) context.getAttribute("cubeService");
        methodService = (IMethodService) context.getAttribute("methodService");
        sessionService = (ISessionService) context.getAttribute("sessionService");
        situationService = (ISituationService) context.getAttribute("situationService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            setStatus(req);
            req.getSession().setAttribute("cubes", cubeService.getAllCubes());
            String cubeName = req.getParameter("cube");
            Cuber cuber = sessionService.getByCurrentSession(req);
            req.getSession().setAttribute("cuber", cuber);
            if (cubeName == null || cubeName.equals("all")) {
                req.getSession().setAttribute("methods", methodService.getAllMethods(cuber));
            } else {
                Cube cube = cubeService.getCubeByName(cubeName);
                req.getSession().setAttribute("methods", methodService.getMethodsByCube(cube, cuber));
            }
            String path = req.getRequestURI().substring(req.getContextPath().length());
            if (path.equals("/algorithms"))
                req.getRequestDispatcher("/WEB-INF/views/algorithms.jsp").forward(req, resp);
            else if (path.equals("/learning")) {
                situationService.updateLearningState(cuber);
                List<Method> methods = (List<Method>) req.getSession().getAttribute("methods");
                req.getSession().setAttribute("methods", situationService.getAllLearningMethods(cuber, methods));
                req.getRequestDispatcher("/WEB-INF/views/learning.jsp").forward(req, resp);
            } else req.getRequestDispatcher("/WEB-INF/views/training.jsp").forward(req, resp);

        } catch (DatabaseException e) {
            throw new ServletException("Problem with database", e);
        } catch (SessionException e) {
            throw new ServletException("Can't find session", e);
        }
    }

    private void setStatus(HttpServletRequest req) {
        req.getSession().setAttribute("userStatus", Cuber.Status.USER);
        req.getSession().setAttribute("moderatorStatus", Cuber.Status.MODERATOR);
        req.getSession().setAttribute("adminStatus", Cuber.Status.ADMIN);
        req.getSession().setAttribute("learning", Method.State.LEARNING);
        req.getSession().setAttribute("learned", Method.State.LEARNED);
    }
}
