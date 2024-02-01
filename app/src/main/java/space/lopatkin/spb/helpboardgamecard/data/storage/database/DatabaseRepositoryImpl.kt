package space.lopatkin.spb.helpboardgamecard.data.storage.database

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import space.lopatkin.spb.helpboardgamecard.data.storage.repository.DatabaseRepository
import space.lopatkin.spb.helpboardgamecard.data.storage.database.AppDatabase.Companion.getInstance
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard

class DatabaseRepositoryImpl(private val context: Context) : DatabaseRepository {
    private val helpcardDao: HelpcardDao
    private val allHelpcards: LiveData<List<Helpcard>>
    private val allFavoritesHelpcards: LiveData<List<Helpcard>>

    init {
        val database: AppDatabase = getInstance(context = context)
        helpcardDao = database.helpcardDao()
        allHelpcards = helpcardDao.getAllHelpcards()
        allFavoritesHelpcards = helpcardDao.getAllFavoritesHelpcards()
    }

    override fun getHelpcard(boardGameId: Int): LiveData<Helpcard> {
        return helpcardDao.getHelpcard(boardGameId = boardGameId)
    }

    override fun getAllHelpcards(): LiveData<List<Helpcard>> {
        return allHelpcards
    }

    override fun delete(helpcard: Helpcard) {
        DeleteHelpcardAsyncTask(helpcardDao = helpcardDao).execute(helpcard)
    }

    override fun delete(id: Int) {
        DeleteHelpcardByIdAsyncTask(helpcardDao = helpcardDao).execute(id)
    }

    override fun update(helpcard: Helpcard) {
        UpdateHelpcardAsyncTask(helpcardDao = helpcardDao).execute(helpcard)
    }

    override fun deleteAllUnlockHelpcards() {
        DeleteAllUnlockHelpcardsAsyncTask(helpcardDao = helpcardDao).execute()
    }

    override fun saveNewHelpcard(helpcard: Helpcard) {
        InsertHelpcardAsyncTask(helpcardDao = helpcardDao).execute(helpcard)
    }

    //todo: do work -------
    fun deleteAllHelpcards() {
        DeleteAllHelpcardsAsyncTask(helpcardDao = helpcardDao).execute()
    }

    fun getAllFavoritesHelpcards(): LiveData<List<Helpcard>> {
        return allFavoritesHelpcards
    }


    companion object {

        private class InsertHelpcardAsyncTask(private val helpcardDao: HelpcardDao) :
            AsyncTask<Helpcard, Void, Void>() {
            override fun doInBackground(vararg helpcards: Helpcard): Void? {
                helpcardDao.insert(helpcards[0])
                return null
            }
        }

        private class UpdateHelpcardAsyncTask(private val helpcardDao: HelpcardDao) :
            AsyncTask<Helpcard, Void, Void>() {
            override fun doInBackground(vararg helpcards: Helpcard): Void? {
                helpcardDao.update(helpcards[0])
                return null
            }
        }

        private class DeleteHelpcardAsyncTask(private val helpcardDao: HelpcardDao) :
            AsyncTask<Helpcard, Void, Void>() {
            override fun doInBackground(vararg helpcards: Helpcard): Void? {
                helpcardDao.delete(helpcards[0])
                return null
            }
        }

        private class DeleteHelpcardByIdAsyncTask(private val helpcardDao: HelpcardDao) :
            AsyncTask<Int, Void, Void>() {
            override fun doInBackground(vararg integers: Int?): Void? {
                helpcardDao.delete(integers[0]!!)
                return null
            }
        }

        private class DeleteAllHelpcardsAsyncTask(private val helpcardDao: HelpcardDao) :
            AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                helpcardDao.deleteAllHelpcards()
                return null
            }
        }

        private class DeleteAllUnlockHelpcardsAsyncTask(private val helpcardDao: HelpcardDao) :
            AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                helpcardDao.deleteAllUnlockHelpcards()
                return null
            }
        }

    }

}