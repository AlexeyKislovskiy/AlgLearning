package fertdt.servlets;

import fertdt.exceptions.*;
import fertdt.helpers.Action;
import fertdt.helpers.ActionIdentifier;
import fertdt.models.Cuber;
import fertdt.services.ICubeService;
import fertdt.services.ICuberService;
import fertdt.services.IMethodService;
import fertdt.services.ISessionService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")

public class LoginServlet extends HttpServlet {

    private ICuberService cuberService;
    private ICubeService cubeService;
    private ISessionService sessionService;
    private IMethodService methodService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        cuberService = (ICuberService) context.getAttribute("cuberService");
        cubeService = (ICubeService) context.getAttribute("cubeService");
        sessionService = (ISessionService) context.getAttribute("sessionService");
        methodService = (IMethodService) context.getAttribute("methodService");
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cuber cuber;
        try {
            cuber = cuberService.getByNickname("unknown");
            req.getSession().setAttribute("cubes", cubeService.getAllCubes());
            req.getSession().setAttribute("methods", methodService.getAllMethods(cuber));
        } catch (DatabaseException e) {
            throw new ServletException("Problem with database", e);
        }
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Action action;
        try {
            action = ActionIdentifier.identify(req);
        } catch (IncorrectRequestException e) {
            throw new ServletException("Incorrect request", e);
        }
        if (action == Action.REGISTRATION || action == Action.REGISTRATION_WITH_EMAIL) {
            setAttributesFalse(req);
            String nickname = req.getParameter("nickname");
            String email = req.getParameter("mail");
            String password = req.getParameter("password-1");
            String confirmPassword = req.getParameter("password-2");
            Cuber cuber = new Cuber(nickname, email, password);
            try {
                req.getSession().setAttribute("cuberLogin", cuber);
                req.getSession().setAttribute("confirmPassword", confirmPassword);
                cuberService.register(cuber, confirmPassword);
                req.getSession().setAttribute("exception", null);
                if (action == Action.REGISTRATION_WITH_EMAIL) {
                    req.getSession().setAttribute("emailConfirmation", true);
                } else {
                    setRegDefault(req);
                    req.getSession().setAttribute("intoLogin", true);
                    req.getSession().setAttribute("registrationDone", true);
                }
            } catch (MatchingNicknamesException e) {
                req.getSession().setAttribute("exception", "nicknameMatch");
            } catch (MatchingPasswordsException e) {
                req.getSession().setAttribute("exception", "passwordMatch");
            } catch (WeakPasswordException e) {
                req.getSession().setAttribute("exception", "weakPassword");
            } catch (DatabaseException e) {
                req.getSession().setAttribute("exception", "dataBaseProblem");
            } catch (IncorrectEmailException e) {
                req.getSession().setAttribute("exception", "incorrectEmail");
            } catch (SendEmailException e) {
                req.getSession().setAttribute("exception", "sendEmailProblem");
            } catch (MatchingEmailException e) {
                req.getSession().setAttribute("exception", "emailMatch");
            } finally {
                resp.sendRedirect(req.getContextPath() + "/login");
            }

        } else if (action == Action.EMAIL_CONFIRMATION) {
            setAttributesFalse(req);
            req.getSession().setAttribute("emailConfirmation", true);
            Cuber cuber = (Cuber) req.getSession().getAttribute("cuberLogin");
            String cuberCode = req.getParameter("confirm-mail");
            try {
                cuberService.confirmEmail(cuber, cuberCode);
                req.getSession().setAttribute("exception", null);
                setRegDefault(req);
                req.getSession().setAttribute("intoLogin", true);
                req.getSession().setAttribute("registrationDone", true);
            } catch (IncorrectEmailCodeException e) {
                req.getSession().setAttribute("exception", "incorrectCode");
            } catch (DatabaseException e) {
                req.getSession().setAttribute("exception", "dataBaseProblem");
            } finally {
                resp.sendRedirect(req.getContextPath() + "/login");
            }

        } else if (action == Action.LOGIN) {
            setAttributesFalse(req);
            req.getSession().setAttribute("intoLogin", true);
            String nickname = req.getParameter("nickname-login");
            String password = req.getParameter("password-login");
            String remember = req.getParameter("remember");
            Cuber cuber = new Cuber(nickname, "", password);
            try {
                req.getSession().setAttribute("cuber", cuber);
                cuberService.login(cuber);
                cuber = cuberService.getByNickname(nickname);
                sessionService.saveSession(cuber, remember, resp);
                req.getSession().setAttribute("exception", null);
                setLoginDefault(req);
                resp.sendRedirect(req.getContextPath() + "/algorithms");
            } catch (DatabaseException e) {
                req.getSession().setAttribute("exception", "dataBaseProblem");
                req.getSession().setAttribute("registrationDone", false);
                req.getSession().setAttribute("intoLogin", true);
                resp.sendRedirect(req.getContextPath() + "/login");
            } catch (NoSuchUserException e) {
                req.getSession().setAttribute("exception", "noSuchUser");
                req.getSession().setAttribute("registrationDone", false);
                req.getSession().setAttribute("intoLogin", true);
                resp.sendRedirect(req.getContextPath() + "/login");
            } catch (IncorrectPasswordException e) {
                req.getSession().setAttribute("exception", "incorrectPassword");
                req.getSession().setAttribute("registrationDone", false);
                req.getSession().setAttribute("intoLogin", true);
                resp.sendRedirect(req.getContextPath() + "/login");
            }
        } else if (action == Action.RECOVERY) {
            setAttributesFalse(req);
            String email = req.getParameter("forgot-mail");
            req.getSession().setAttribute("email", email);
            try {
                cuberService.recoveryPassword(email);
                req.getSession().setAttribute("exception", null);
                req.getSession().setAttribute("recoveryCode", true);
            } catch (IncorrectEmailException e) {
                req.getSession().setAttribute("exception", "incorrectEmail-r");
                req.getSession().setAttribute("intoRecovery", true);
            } catch (DatabaseException e) {
                req.getSession().setAttribute("exception", "dataBaseProblem-r");
                req.getSession().setAttribute("intoRecovery", true);
            } catch (MatchingEmailException e) {
                req.getSession().setAttribute("exception", "emailMatch-r");
                req.getSession().setAttribute("intoRecovery", true);
            } catch (SendEmailException e) {
                req.getSession().setAttribute("exception", "sendEmailProblem-r");
                req.getSession().setAttribute("intoRecovery", true);
            } finally {
                resp.sendRedirect(req.getContextPath() + "/login");
            }
        } else if (action == Action.RECOVERY_CONFIRMATION) {
            setAttributesFalse(req);
            req.getSession().setAttribute("recoveryCode", true);
            String cuberCode = req.getParameter("confirm-mail2");
            try {
                cuberService.recoveryConfirmation(cuberCode);
                req.getSession().setAttribute("exception", null);
                Cuber cuber = cuberService.getByEmail((String) req.getSession().getAttribute("email"));
                sessionService.saveSession(cuber, null, resp);
                setLoginDefault(req);
                resp.sendRedirect(req.getContextPath() + "/algorithms");
            } catch (IncorrectEmailCodeException e) {
                req.getSession().setAttribute("recoveryCode", true);
                req.getSession().setAttribute("exception", "incorrectCode-r");
                resp.sendRedirect(req.getContextPath() + "/login");
            } catch (DatabaseException e) {
                req.getSession().setAttribute("recoveryCode", true);
                req.getSession().setAttribute("exception", "dataBaseProblem-r");
                resp.sendRedirect(req.getContextPath() + "/login");
            }
        }
    }

    private void setRegDefault(HttpServletRequest req) {
        req.getSession().setAttribute("cuberLogin", null);
        req.getSession().setAttribute("confirmPassword", null);
    }

    private void setLoginDefault(HttpServletRequest req) {
        req.getSession().setAttribute("cuber", null);
    }

    private void setAttributesFalse(HttpServletRequest req) {
        req.getSession().setAttribute("intoRecovery", null);
        req.getSession().setAttribute("intoLogin", null);
        req.getSession().setAttribute("emailConfirmation", null);
        req.getSession().setAttribute("recoveryCode", null);
    }
}
