package fertdt.services;

import fertdt.exceptions.DatabaseException;
import fertdt.models.Cuber;
import fertdt.models.Setting;
import fertdt.repositories.ISettingsRepository;

public class SettingsService implements ISettingsService {
    private final ISettingsRepository settingsRepository;

    public SettingsService(ISettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    @Override
    public void saveSetting(Setting setting, Cuber cuber) throws DatabaseException {
        settingsRepository.saveSetting(setting, cuber);
    }

    @Override
    public Setting getSettingById(int settingId, Cuber cuber) throws DatabaseException {
        return settingsRepository.getSettingById(settingId, cuber);
    }

    @Override
    public void resetAll(Cuber cuber) throws DatabaseException {
        settingsRepository.resetAll(cuber);
    }

    @Override
    public void reset(Cuber cuber, int settingId) throws DatabaseException {
        settingsRepository.reset(cuber, settingId);
    }
}
