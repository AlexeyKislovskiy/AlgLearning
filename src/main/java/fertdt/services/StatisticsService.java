package fertdt.services;

import fertdt.exceptions.DatabaseException;
import fertdt.models.Cuber;
import fertdt.models.Statistics;
import fertdt.repositories.IStatisticsRepository;

import java.util.List;

public class StatisticsService implements IStatisticsService {
    private final IStatisticsRepository statisticsRepository;

    public StatisticsService(IStatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    @Override
    public void updateStatistics(Cuber cuber, String action) throws DatabaseException {
        statisticsRepository.updateStatistics(cuber, action);
    }

    @Override
    public List<Statistics> getAllStatistics(Cuber cuber) throws DatabaseException {
        return statisticsRepository.getAllStatistics(cuber);
    }
}
