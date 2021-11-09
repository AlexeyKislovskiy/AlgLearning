package fertdt.servlets;

import fertdt.exceptions.DatabaseException;
import fertdt.exceptions.SessionException;
import fertdt.models.Cuber;
import fertdt.models.Setting;
import fertdt.services.ISessionService;
import fertdt.services.ISettingsService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/settings")
public class SettingsServlet extends HttpServlet {
    private ISessionService sessionService;
    private ISettingsService settingsService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        sessionService = (ISessionService) context.getAttribute("sessionService");
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
            for (int i = 1; i <= 20; i++) {
                Setting setting = settingsService.getSettingById(i, cuber);
                String s = "setting" + i;
                req.getSession().setAttribute(s, setting);
            }
        } catch (DatabaseException e) {
            throw new ServletException("Problem with database", e);
        } catch (SessionException e) {
            throw new ServletException("Can't find session", e);
        }
        req.getRequestDispatcher("/WEB-INF/views/settings.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Cuber cuber = sessionService.getByCurrentSession(req);
            if (req.getParameter("reset-all") != null) {
                settingsService.resetAll(cuber);
            }
            String reset = req.getParameter("reset");
            if (reset != null) {
                switch (reset) {
                    case "learning":
                        for (int i = 1; i <= 5; i++) {
                            settingsService.reset(cuber, i);
                        }
                        break;
                    case "training":
                        for (int i = 6; i <= 8; i++) {
                            settingsService.reset(cuber, i);
                        }
                        break;
                    case "algorithms":
                        for (int i = 9; i <= 19; i++) {
                            settingsService.reset(cuber, i);
                        }
                        break;
                    default:
                        settingsService.reset(cuber, 20);
                        break;
                }
            }
            for (int i = 1; i <= 20; i++) {
                Setting setting = settingsService.getSettingById(i, cuber);
                String s = "setting" + i;
                req.getSession().setAttribute(s, setting);
            }
            for (int i = 1; i <= 20; i++) {
                if (req.getParameter("setting" + i) != null) {
                    Setting setting = new Setting(i, req.getParameter("setting" + i));
                    req.getSession().setAttribute("setting" + i, setting);
                    settingsService.saveSetting(setting, cuber);
                }
            }
        } catch (DatabaseException e) {
            throw new ServletException("Problem with database", e);
        } catch (SessionException e) {
            throw new ServletException("Can't find session", e);
        }
        resp.sendRedirect(req.getContextPath() + "/settings");
    }
}
