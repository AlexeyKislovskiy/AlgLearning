package fertdt.servlets;

import fertdt.exceptions.DatabaseException;
import fertdt.exceptions.SessionException;
import fertdt.models.Cuber;
import fertdt.models.Statistics;
import fertdt.services.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/account")
public class AccountServlet extends HttpServlet {
    private ICuberService cuberService;
    private ISessionService sessionService;
    private IStatisticsService statisticsService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        cuberService = (ICuberService) context.getAttribute("cuberService");
        sessionService = (ISessionService) context.getAttribute("sessionService");
        statisticsService = (IStatisticsService) context.getAttribute("statisticsService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Cuber cuber = sessionService.getByCurrentSession(req);
            req.getSession().setAttribute("cuber", cuber);
            req.getSession().setAttribute("userStatus", Cuber.Status.USER);
            req.getSession().setAttribute("moderatorStatus", Cuber.Status.MODERATOR);
            req.getSession().setAttribute("adminStatus", Cuber.Status.ADMIN);
            req.getSession().setAttribute("numOfLearning", cuberService.numOfLearning(cuber));
            req.getSession().setAttribute("numOfLearned", cuberService.numOfLearned(cuber));
            List<Statistics> statistics = statisticsService.getAllStatistics(cuber);
            List<Integer> day = new ArrayList<>(), week = new ArrayList<>(), month = new ArrayList<>(),
                    year = new ArrayList<>(), all = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                day.add(0);
                week.add(0);
                month.add(0);
                year.add(0);
                all.add(0);
            }
            for (Statistics el : statistics) {
                if (el.getDate().compareTo(Date.valueOf(LocalDate.now())) == 0) {
                    day.set(0, day.get(0) + el.getNumberForgot());
                    day.set(1, day.get(1) + el.getNumberRepeat());
                    day.set(2, day.get(2) + el.getNumberNew());
                    day.set(3, day.get(3) + el.getNumberTrained());
                }
                if (el.getDate().compareTo(Date.valueOf(LocalDate.now().minusDays(7))) >= 0) {
                    week.set(0, week.get(0) + el.getNumberForgot());
                    week.set(1, week.get(1) + el.getNumberRepeat());
                    week.set(2, week.get(2) + el.getNumberNew());
                    week.set(3, week.get(3) + el.getNumberTrained());
                }
                if (el.getDate().compareTo(Date.valueOf(LocalDate.now().minusDays(30))) >= 0) {
                    month.set(0, month.get(0) + el.getNumberForgot());
                    month.set(1, month.get(1) + el.getNumberRepeat());
                    month.set(2, month.get(2) + el.getNumberNew());
                    month.set(3, month.get(3) + el.getNumberTrained());
                }
                if (el.getDate().compareTo(Date.valueOf(LocalDate.now().minusDays(365))) >= 0) {
                    year.set(0, year.get(0) + el.getNumberForgot());
                    year.set(1, year.get(1) + el.getNumberRepeat());
                    year.set(2, year.get(2) + el.getNumberNew());
                    year.set(3, year.get(3) + el.getNumberTrained());
                }
                all.set(0, all.get(0) + el.getNumberForgot());
                all.set(1, all.get(1) + el.getNumberRepeat());
                all.set(2, all.get(2) + el.getNumberNew());
                all.set(3, all.get(3) + el.getNumberTrained());
            }
            req.getSession().setAttribute("day", day);
            req.getSession().setAttribute("week", week);
            req.getSession().setAttribute("month", month);
            req.getSession().setAttribute("year", year);
            req.getSession().setAttribute("all", all);
        } catch (DatabaseException e) {
            throw new ServletException("Problem with database", e);
        } catch (SessionException e) {
            throw new ServletException("Can't find session", e);
        }
        req.getRequestDispatcher("/WEB-INF/views/account.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Cuber cuber = sessionService.getByCurrentSession(req);
            req.getSession().setAttribute("cuber", null);
            req.getSession().setAttribute("registrationDone", null);
            req.getSession().setAttribute("upload", null);
            sessionService.exit(req, resp);
            if (req.getParameter("delete") != null) cuberService.delete(cuber);
            resp.sendRedirect(req.getContextPath() + "/login");
        } catch (DatabaseException e) {
            throw new ServletException("Problem with database", e);
        } catch (SessionException e) {
            throw new ServletException("Can't find session", e);
        }

    }
}
