package space.lopatkin.spb.helpboardgamecard.data.local.room

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import space.lopatkin.spb.helpboardgamecard.data.local.data.source.BoardgameLocalDataSource
import space.lopatkin.spb.helpboardgamecard.data.local.room.RoomDb.Companion.getInstance
import space.lopatkin.spb.helpboardgamecard.domain.model.*

class RoomBoardgameLocalDataSource(private val context: Context) : BoardgameLocalDataSource {
    private val boardgameDao: BoardgameDao
    private val allBoardgamesInfo: LiveData<List<BoardgameInfo>>

    init {
        val database: RoomDb = getInstance(context = context)
        boardgameDao = database.boardgameDao()
        allBoardgamesInfo = boardgameDao.getAllBoardgamesInfo()
    }

    override fun getAllBoardgamesInfo(): LiveData<List<BoardgameInfo>> {
        return allBoardgamesInfo
    }

    override fun getHelpcardBy(boardgameId: Long): LiveData<Helpcard> {
        return boardgameDao.getHelpcardBy(boardgameId)
    }

    override fun getBoardgameRawBy(boardgameId: Long): LiveData<BoardgameRaw> {
        return boardgameDao.getBoardgameRawBy(boardgameId)
    }

    override fun saveNewBoardgameBy(boardgameRaw: BoardgameRaw) {
        AddBoardgameInfoAndHelpcardAsyncTask(boardgameDao = boardgameDao).execute(boardgameRaw)
    }

    override fun deleteBoardgameBy(boardgameId: Long) {
        DeleteBoardgameInfoAndHelpcardByIdAsyncTask(boardgameDao = boardgameDao).execute(boardgameId)
    }

    override fun update(boardgameInfo: BoardgameInfo) {
        UpdateBoardgameInfoAsyncTask(boardgameDao = boardgameDao).execute(boardgameInfo)
    }

    override fun updateBoardgameBy(boardgameRaw: BoardgameRaw) {
        UpdateBoardgameInfoAndHelpcardByIdAsyncTask(boardgameDao = boardgameDao).execute(boardgameRaw)
    }

    override fun deleteUnlockBoardgames() {
        DeleteUnlockBoardgamesAsyncTask(boardgameDao = boardgameDao).execute()
    }

    companion object {

        private class AddBoardgameInfoAndHelpcardAsyncTask(private val boardgameDao: BoardgameDao) :
            AsyncTask<BoardgameRaw, Void, Void>() {
            override fun doInBackground(vararg data: BoardgameRaw): Void? {
                val boardgameRaw: BoardgameRaw = data[0]
                val boardgameInfo: BoardgameInfo = boardgameRaw.toBoardgameInfo()
                val boardgameId: Long = boardgameDao.add(boardgameInfo)
                val helpcard: Helpcard = boardgameRaw.toHelpcard(boardgameId)
                boardgameDao.add(helpcard)
                return null
            }
        }

        private class UpdateBoardgameInfoAndHelpcardByIdAsyncTask(private val boardgameDao: BoardgameDao) :
            AsyncTask<BoardgameRaw, Void, Void>() {
            override fun doInBackground(vararg data: BoardgameRaw): Void? {
                val boardgameRaw: BoardgameRaw = data[0]
                if (boardgameRaw.id != null) {
                    val boardgameInfo: BoardgameInfo = boardgameRaw.toBoardgameInfo()
                    boardgameDao.update(boardgameInfo)
                    val helpcardDbId: Long = boardgameDao.getHelpcardIdBy(boardgameRaw.id!!)
                    val helpcard: Helpcard = boardgameRaw.toHelpcard(helpcardDbId, boardgameRaw.id!!)
                    boardgameDao.update(helpcard)
                }
                return null
            }
        }

        private class UpdateBoardgameInfoAsyncTask(private val boardgameDao: BoardgameDao) :
            AsyncTask<BoardgameInfo, Void, Void>() {
            override fun doInBackground(vararg boardgameInfos: BoardgameInfo): Void? {
                boardgameDao.update(boardgameInfos[0])
                return null
            }
        }

        private class DeleteBoardgameInfoAndHelpcardByIdAsyncTask(private val boardgameDao: BoardgameDao) :
            AsyncTask<Long, Void, Void>() {
            override fun doInBackground(vararg params: Long?): Void? {
                val boardgameId: Long? = params[0]
                if (boardgameId != null) {
                    boardgameDao.deleteBoardgameInfoBy(boardgameId)
                    boardgameDao.deleteHelpcardBy(boardgameId)
                }
                return null
            }
        }

        private class DeleteUnlockBoardgamesAsyncTask(private val boardgameDao: BoardgameDao) :
            AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                val listIdUnlock: Array<Long> = boardgameDao.getBoardgameIdsByUnlock()

                for (index in 0 until listIdUnlock.size) {
                    boardgameDao.deleteBoardgameInfoBy(listIdUnlock.get(index))
                    boardgameDao.deleteHelpcardBy(listIdUnlock.get(index))
                }
                return null
            }
        }

    }

}