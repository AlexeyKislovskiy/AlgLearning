package fertdt.filters;

import fertdt.exceptions.DatabaseException;
import fertdt.exceptions.SessionException;
import fertdt.helpers.SessionChecker;
import fertdt.models.Cuber;
import fertdt.models.Setting;
import fertdt.services.ICuberService;
import fertdt.services.ISessionService;
import fertdt.services.ISettingsService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter("/*")
public class LoginFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        List<String> validAddresses = Arrays.asList("/login", "/algorithms", "/algorithms/method", "/training", "/moderator",
                "/training/method-training", "/learning", "/learning/method-learning", "/account", "/settings", "/upload");
        boolean logged;
        try {
            logged = SessionChecker.check(req, res, (ISessionService) getServletContext().getAttribute("sessionService"));
        } catch (DatabaseException e) {
            throw new ServletException("Problem with database", e);
        }
        String path = req.getRequestURI().substring(req.getContextPath().length());
        if (logged) {
            ICuberService cuberService = (ICuberService) getServletContext().getAttribute("cuberService");
            ISessionService sessionService = (ISessionService) getServletContext().getAttribute("sessionService");
            ISettingsService settingsService = (ISettingsService) getServletContext().getAttribute("settingsService");
            try {
                Cuber cuber = sessionService.getByCurrentSession(req);
                cuberService.updateVisit(cuber);
                Setting setting20 = settingsService.getSettingById(20, cuber);
                if (setting20 != null) req.getSession().setAttribute("upload", setting20.getValue());
                else req.getSession().setAttribute("upload", null);
            } catch (DatabaseException e) {
                throw new ServletException("Problem with database", e);
            } catch (SessionException e) {
                throw new ServletException("Can't find session", e);
            }
        }
        if (path.contains("static")) {
            chain.doFilter(req, res);
        } else if (!path.equals("/login") && !logged) {
            res.sendRedirect(req.getContextPath() + "/login");
        } else if (!validAddresses.contains(path)) res.sendRedirect(req.getContextPath() + "/algorithms");
        else {
            chain.doFilter(req, res);
        }
    }
}
