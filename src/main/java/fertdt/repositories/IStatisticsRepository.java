package fertdt.repositories;

import fertdt.exceptions.DatabaseException;
import fertdt.models.Cuber;
import fertdt.models.Statistics;

import java.util.List;

public interface IStatisticsRepository {

    void updateStatistics(Cuber cuber, String action) throws DatabaseException;

    List<Statistics> getAllStatistics(Cuber cuber) throws DatabaseException;
}
