package fertdt.servlets;

import fertdt.exceptions.DatabaseException;
import fertdt.exceptions.SessionException;
import fertdt.models.Cuber;
import fertdt.models.Setting;
import fertdt.services.ISessionService;
import fertdt.services.ISettingsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

@WebServlet("/upload")
@MultipartConfig
public class UploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ISessionService sessionService = (ISessionService) getServletContext().getAttribute("sessionService");
        ISettingsService settingsService = (ISettingsService) getServletContext().getAttribute("settingsService");
        try {
            Cuber cuber = sessionService.getByCurrentSession(req);
            String uploadPath = getServletContext().getRealPath("") + "static\\img\\backgrounds\\" + cuber.getId();
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();
            Part filePart = req.getPart("background-img");
            String fileName = filePart.getSubmittedFileName();
            if (!fileName.isEmpty()) {
                for (Part part : req.getParts()) {
                    part.write(uploadPath + File.separator + fileName);
                }
                fileName = cuber.getId() + "/" + fileName;
                req.getSession().setAttribute("upload", fileName);
                Setting setting = new Setting(20, fileName);
                settingsService.saveSetting(setting, cuber);
            }
            resp.sendRedirect(req.getContextPath() + "/settings");
        } catch (DatabaseException e) {
            throw new ServletException("Problem with database", e);
        } catch (SessionException e) {
            throw new ServletException("Can't find session", e);
        }
    }
}
