package fertdt.listeners;

import fertdt.exceptions.SendEmailException;
import fertdt.helpers.*;
import fertdt.repositories.*;
import fertdt.services.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.util.Properties;

@WebListener
public class ResourceInitializer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        PasswordEncryptor passwordEncryptor = new BcryptEncryptor();
        IEmailValidator emailValidator = new EmailValidator(org.apache.commons.validator.routines.EmailValidator.getInstance());
        ISessionIDGenerator sessionIDGenerator = new SessionIDGenerator();
        IAlgorithmLengthCalculator algorithmLengthCalculator = new AlgorithmLengthCalculator();
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getResourceAsStream("/emailSettings.ini"));
        } catch (IOException e) {
            throw new IllegalStateException("Can't read email settings", new SendEmailException());
        }
        IEmailSender emailSender = new EmailSender(properties.getProperty("username"), properties.getProperty("password"));
        DBConnector dbConnector = new PostgresqlDBConnector();
        ICuberRepository cuberRepository = new CuberRepository(dbConnector, passwordEncryptor);
        ICuberService cuberService = new CuberService(cuberRepository, emailValidator, emailSender, passwordEncryptor);
        ISessionRepository sessionRepository = new SessionRepository(dbConnector);
        ISessionService sessionService = new SessionService(sessionRepository, sessionIDGenerator);
        ICubeRepository cubeRepository = new CubeRepository(dbConnector);
        ICubeService cubeService = new CubeService(cubeRepository);
        IMethodRepository methodRepository = new MethodRepository(dbConnector);
        IMethodService methodService = new MethodService(methodRepository);
        ISituationRepository situationRepository = new SituationRepository(dbConnector);
        ISituationService situationService = new SituationService(situationRepository);
        IAlgorithmRepository algorithmRepository = new AlgorithmRepository(dbConnector);
        IAlgorithmService algorithmService = new AlgorithmService(algorithmRepository, algorithmLengthCalculator);
        IScrambleRepository scrambleRepository = new ScrambleRepository(dbConnector);
        IScrambleService scrambleService = new ScrambleService(scrambleRepository);
        IStatisticsRepository statisticsRepository = new StatisticsRepository(dbConnector);
        IStatisticsService statisticsService = new StatisticsService(statisticsRepository);
        ISettingsRepository settingsRepository = new SettingsRepository(dbConnector);
        ISettingsService settingsService = new SettingsService(settingsRepository);
        servletContext.setAttribute("cuberService", cuberService);
        servletContext.setAttribute("sessionService", sessionService);
        servletContext.setAttribute("cubeService", cubeService);
        servletContext.setAttribute("methodService", methodService);
        servletContext.setAttribute("situationService", situationService);
        servletContext.setAttribute("algorithmService", algorithmService);
        servletContext.setAttribute("scrambleService", scrambleService);
        servletContext.setAttribute("statisticsService", statisticsService);
        servletContext.setAttribute("settingsService", settingsService);
    }
}
