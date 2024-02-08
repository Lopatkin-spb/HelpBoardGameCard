package space.lopatkin.spb.helpboardgamecard.data.storage.database

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import space.lopatkin.spb.helpboardgamecard.data.storage.repository.DatabaseRepository
import space.lopatkin.spb.helpboardgamecard.data.storage.database.AppDatabase.Companion.getInstance
import space.lopatkin.spb.helpboardgamecard.domain.model.*

class DatabaseRepositoryImpl(private val context: Context) : DatabaseRepository {
    private val helpcardDao: HelpcardDao
    private val allBoardgamesInfo: LiveData<List<BoardgameInfo>>

    init {
        val database: AppDatabase = getInstance(context = context)
        helpcardDao = database.helpcardDao()
        allBoardgamesInfo = helpcardDao.getAllBoardgamesInfo()
    }

    override fun getAllBoardgamesInfo(): LiveData<List<BoardgameInfo>> {
        return allBoardgamesInfo
    }

    override fun getHelpcardBy(boardgameId: Long): LiveData<Helpcard> {
        return helpcardDao.getHelpcardBy(boardgameId)
    }

    override fun getBoardgameRawBy(boardgameId: Long): LiveData<BoardgameRaw> {
        return helpcardDao.getBoardgameRawBy(boardgameId)
    }

    override fun saveNewBoardgameBy(boardgameRaw: BoardgameRaw) {
        AddBoardgameInfoAndHelpcardAsyncTask(helpcardDao = helpcardDao).execute(boardgameRaw)
    }

    override fun deleteBoardgameBy(boardgameId: Long) {
        DeleteBoardgameInfoAndHelpcardByIdAsyncTask(helpcardDao = helpcardDao).execute(boardgameId)
    }

    override fun update(boardgameInfo: BoardgameInfo) {
        UpdateBoardgameInfoAsyncTask(helpcardDao = helpcardDao).execute(boardgameInfo)
    }

    override fun updateBoardgameBy(boardgameRaw: BoardgameRaw) {
        UpdateBoardgameInfoAndHelpcardByIdAsyncTask(helpcardDao = helpcardDao).execute(boardgameRaw)
    }

    override fun deleteUnlockBoardgames() {
        DeleteUnlockBoardgamesAsyncTask(helpcardDao = helpcardDao).execute()
    }

    companion object {

        private class AddBoardgameInfoAndHelpcardAsyncTask(private val helpcardDao: HelpcardDao) :
            AsyncTask<BoardgameRaw, Void, Void>() {
            override fun doInBackground(vararg data: BoardgameRaw): Void? {
                val boardgameRaw: BoardgameRaw = data[0]
                val boardgameInfo: BoardgameInfo = boardgameRaw.toBoardgameInfo()
                val boardgameId: Long = helpcardDao.add(boardgameInfo)
                val helpcard: Helpcard = boardgameRaw.toHelpcard(boardgameId)
                helpcardDao.add(helpcard)
                return null
            }
        }

        private class UpdateBoardgameInfoAndHelpcardByIdAsyncTask(private val helpcardDao: HelpcardDao) :
            AsyncTask<BoardgameRaw, Void, Void>() {
            override fun doInBackground(vararg data: BoardgameRaw): Void? {
                val boardgameRaw: BoardgameRaw = data[0]
                if (boardgameRaw.id != null) {
                    val boardgameInfo: BoardgameInfo = boardgameRaw.toBoardgameInfo()
                    helpcardDao.update(boardgameInfo)
                    val helpcardDbId: Long = helpcardDao.getHelpcardIdBy(boardgameRaw.id!!)
                    val helpcard: Helpcard = boardgameRaw.toHelpcard(helpcardDbId, boardgameRaw.id!!)
                    helpcardDao.update(helpcard)
                }
                return null
            }
        }

        private class UpdateBoardgameInfoAsyncTask(private val helpcardDao: HelpcardDao) :
            AsyncTask<BoardgameInfo, Void, Void>() {
            override fun doInBackground(vararg boardgameInfos: BoardgameInfo): Void? {
                helpcardDao.update(boardgameInfos[0])
                return null
            }
        }

        private class DeleteBoardgameInfoAndHelpcardByIdAsyncTask(private val helpcardDao: HelpcardDao) :
            AsyncTask<Long, Void, Void>() {
            override fun doInBackground(vararg params: Long?): Void? {
                val boardgameId: Long? = params[0]
                if (boardgameId != null) {
                    helpcardDao.deleteBoardgameInfoBy(boardgameId)
                    helpcardDao.deleteHelpcardBy(boardgameId)
                }
                return null
            }
        }

        private class DeleteUnlockBoardgamesAsyncTask(private val helpcardDao: HelpcardDao) :
            AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                val listIdUnlock: Array<Long> = helpcardDao.getBoardgameIdsByUnlock()

                for (index in 0 until listIdUnlock.size) {
                    helpcardDao.deleteBoardgameInfoBy(listIdUnlock.get(index))
                    helpcardDao.deleteHelpcardBy(listIdUnlock.get(index))
                }
                return null
            }
        }

    }

}