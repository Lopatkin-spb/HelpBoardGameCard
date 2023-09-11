package space.lopatkin.spb.helpboardgamecard.dataRoom;


import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import space.lopatkin.spb.helpboardgamecard.data.repository.SettingsRepository;
import space.lopatkin.spb.helpboardgamecard.domain.repository.HelpcardRepository;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;

import java.util.List;


public class HelpcardRepositoryImpl implements HelpcardRepository {
    private HelpcardDao helpcardDao;
    private LiveData<List<Helpcard>> allHelpcards;
    private LiveData<List<Helpcard>> allFavoritesHelpcards;
    private SettingsRepository settingsRepository;


    public HelpcardRepositoryImpl(Application application, SettingsRepository settingsRepository) {
        HelpcardDatabase database = HelpcardDatabase.getInstance(application);
        helpcardDao = database.helpcardDao();
        allHelpcards = helpcardDao.getAllHelpcards();
        allFavoritesHelpcards = helpcardDao.getAllFavoritesHelpcards();

        this.settingsRepository = settingsRepository;
    }

    @Override
    public LiveData<Helpcard> getHelpcard(int boardGameId) {
        return helpcardDao.getHelpcard(boardGameId);
    }
    @Override
    public LiveData<List<Helpcard>> getAllHelpcards() {
        return allHelpcards;
    }
    @Override
    public void delete(Helpcard helpcard) {
        new DeleteHelpcardAsyncTask(helpcardDao).execute(helpcard);
    }
    @Override
    public void delete(int id) {
    }
    @Override
    public void update(Helpcard helpcard) {
        new UpdateHelpcardAsyncTask(helpcardDao).execute(helpcard);
    }
    @Override
    public void deleteAllUnlockHelpcards() {
        new DeleteAllUnlockHelpcardsAsyncTask(helpcardDao).execute();
    }
    @Override
    public void saveNewHelpcard(Helpcard helpcard) {
        new InsertHelpcardAsyncTask(helpcardDao).execute(helpcard);
    }
    @Override
    public void saveKeyboardVariant(int keyboardVariant) {
        settingsRepository.saveKeyboardVariant(keyboardVariant);
    }
    @Override
    public int getKeyboardVariant() {
        return settingsRepository.getKeyboardVariant();
    }


    //todo: do work -------
    public void deleteAllHelpcards() {
        new DeleteAllHelpcardsAsyncTask(helpcardDao).execute();
    }

    public LiveData<List<Helpcard>> getAllFavoritesHelpcards() {
        return allFavoritesHelpcards;
    }


    //todo: move to db repo -------
    private static class InsertHelpcardAsyncTask extends AsyncTask<Helpcard, Void, Void> {
        private HelpcardDao helpcardDao;

        private InsertHelpcardAsyncTask(HelpcardDao helpcardDao) {
            this.helpcardDao = helpcardDao;
        }

        @Override
        protected Void doInBackground(Helpcard... helpcards) {
            helpcardDao.insert(helpcards[0]);
            return null;
        }
    }

    private static class UpdateHelpcardAsyncTask extends AsyncTask<Helpcard, Void, Void> {
        private HelpcardDao helpcardDao;

        private UpdateHelpcardAsyncTask(HelpcardDao helpcardDao) {
            this.helpcardDao = helpcardDao;
        }

        @Override
        protected Void doInBackground(Helpcard... helpcards) {
            helpcardDao.update(helpcards[0]);
            return null;
        }
    }


    private static class DeleteHelpcardAsyncTask extends AsyncTask<Helpcard, Void, Void> {
        private HelpcardDao helpcardDao;

        private DeleteHelpcardAsyncTask(HelpcardDao helpcardDao) {
            this.helpcardDao = helpcardDao;
        }

        @Override
        protected Void doInBackground(Helpcard... helpcards) {
            helpcardDao.delete(helpcards[0]);
            return null;
        }
    }


    private static class DeleteAllHelpcardsAsyncTask extends AsyncTask<Void, Void, Void> {
        private HelpcardDao helpcardDao;

        private DeleteAllHelpcardsAsyncTask(HelpcardDao helpcardDao) {
            this.helpcardDao = helpcardDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            helpcardDao.deleteAllHelpcards();
            return null;
        }
    }

    private static class DeleteAllUnlockHelpcardsAsyncTask extends AsyncTask<Void, Void, Void> {
        private HelpcardDao helpcardDao;

        private DeleteAllUnlockHelpcardsAsyncTask(HelpcardDao helpcardDao) {
            this.helpcardDao = helpcardDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            helpcardDao.deleteAllUnlockHelpcards();
            return null;
        }
    }


}