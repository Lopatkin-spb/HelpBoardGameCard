package space.lopatkin.spb.helpboardgamecard.dataRoom;
import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;
import space.lopatkin.spb.helpboardgamecard.model.Helpcard;
//import space.lopatkin.spb.testnavdrawer.model.Note;

import java.util.List;

public class HelpcardRepository {

    private HelpcardDao helpcardDao;
    private LiveData<List<Helpcard>> allHelpcards;

    public HelpcardRepository(Application application) {
        HelpcardDatabase database = HelpcardDatabase.getInstance(application);
        helpcardDao = database.helpcardDao();
        allHelpcards = helpcardDao.getAllHelpcards();
    }

    public void insert(Helpcard helpcard) {
        new InsertHelpcardAsyncTask(helpcardDao).execute(helpcard);
    }
    public void update(Helpcard helpcard) {
        new UpdateHelpcardAsyncTask(helpcardDao).execute(helpcard);
    }
    public void delete(Helpcard helpcard) {
        new DeleteHelpcardAsyncTask(helpcardDao).execute(helpcard);
    }
    public void deleteAllHelpcards() {
        new DeleteAllHelpcardsAsyncTask(helpcardDao).execute();
    }
    public LiveData<List<Helpcard>> getAllHelpcards() {
        return allHelpcards;
    }

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
}