package fertdt.services;

import fertdt.exceptions.DatabaseException;
import fertdt.models.Cuber;
import fertdt.models.Setting;

public interface ISettingsService {
    void saveSetting(Setting setting, Cuber cuber) throws DatabaseException;

    Setting getSettingById(int settingId, Cuber cuber) throws DatabaseException;

    void resetAll(Cuber cuber) throws DatabaseException;

    void reset(Cuber cuber, int settingId) throws DatabaseException;
}
