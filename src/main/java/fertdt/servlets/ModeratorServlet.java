package fertdt.servlets;

import fertdt.exceptions.DatabaseException;
import fertdt.exceptions.SessionException;
import fertdt.models.Algorithm;
import fertdt.models.Cuber;
import fertdt.services.IAlgorithmService;
import fertdt.services.ISessionService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/moderator")
public class ModeratorServlet extends HttpServlet {
    private ISessionService sessionService;
    private IAlgorithmService algorithmService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        sessionService = (ISessionService) context.getAttribute("sessionService");
        algorithmService = (IAlgorithmService) context.getAttribute("algorithmService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Cuber cuber = sessionService.getByCurrentSession(req);
            req.getSession().setAttribute("cuber", cuber);
            if (cuber.getStatus() == Cuber.Status.USER) resp.sendRedirect(req.getContextPath() + "/algorithms");
            List<Algorithm> algorithms = algorithmService.getNotVerifiedAlgorithms();
            req.getSession().setAttribute("algorithms", algorithms);
        } catch (DatabaseException e) {
            throw new ServletException("Problem with database", e);
        } catch (SessionException e) {
            throw new ServletException("Can't find session", e);
        }
        req.getRequestDispatcher("/WEB-INF/views/moderator.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String plus = req.getParameter("plus"), delete = req.getParameter("delete");
            if (plus != null) {
                String text = req.getParameter("plus-text");
                algorithmService.verify(Integer.parseInt(plus), text);
            } else if (delete != null) {
                algorithmService.delete(Integer.parseInt(delete));
            }
            resp.sendRedirect(req.getContextPath() + "/moderator");
        } catch (DatabaseException e) {
            throw new ServletException("Problem with database", e);
        }
    }
}

