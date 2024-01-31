package space.lopatkin.spb.helpboardgamecard.data;


import androidx.lifecycle.LiveData;
import space.lopatkin.spb.helpboardgamecard.data.repository.DatabaseRepository;
import space.lopatkin.spb.helpboardgamecard.data.repository.SettingsRepository;
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard;
import space.lopatkin.spb.helpboardgamecard.domain.repository.AppRepository;

import java.util.List;


public class AppRepositoryImpl implements AppRepository {

    private DatabaseRepository databaseRepository;
    private SettingsRepository settingsRepository;


    public AppRepositoryImpl(DatabaseRepository databaseRepository, SettingsRepository settingsRepository) {
        this.databaseRepository = databaseRepository;
        this.settingsRepository = settingsRepository;
    }

    @Override
    public LiveData<Helpcard> getHelpcard(int boardGameId) {
        return databaseRepository.getHelpcard(boardGameId);
    }
    @Override
    public LiveData<List<Helpcard>> getAllHelpcards() {
        return databaseRepository.getAllHelpcards();
    }
    @Override
    public void delete(Helpcard helpcard) {
        databaseRepository.delete(helpcard);
    }
    @Override
    public void delete(int id) {
        databaseRepository.delete(id);
    }
    @Override
    public void update(Helpcard helpcard) {
        databaseRepository.update(helpcard);
    }
    @Override
    public void deleteAllUnlockHelpcards() {
        databaseRepository.deleteAllUnlockHelpcards();
    }
    @Override
    public void saveNewHelpcard(Helpcard helpcard) {
        databaseRepository.saveNewHelpcard(helpcard);
    }
    @Override
    public void saveKeyboardType(int type) {
        settingsRepository.saveKeyboardType(type);
    }
    @Override
    public int getKeyboardType() {
        return settingsRepository.getKeyboardType();
    }

}