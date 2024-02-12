package space.lopatkin.spb.helpboardgamecard.data.local.room

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import space.lopatkin.spb.helpboardgamecard.data.local.data.source.BoardgameLocalDataSource
import space.lopatkin.spb.helpboardgamecard.data.local.room.RoomDb.Companion.getInstance
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameInfo
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameRaw
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import kotlin.Result

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

    override suspend fun getHelpcardBy(boardgameId: Long): Result<Helpcard> {
        return try {
            val data: Helpcard = boardgameDao.getHelpcardBy(boardgameId)
            Result.success(data)
        } catch (cause: Throwable) {
            Result.failure(cause)
        }
    }

    override suspend fun getBoardgameRawBy(boardgameId: Long): Result<BoardgameRaw> {
        return try {
            val data: BoardgameRaw = boardgameDao.getBoardgameRawBy(boardgameId)
            Result.success(data)
        } catch (cause: Throwable) {
            Result.failure(cause)
        }
    }

    override suspend fun saveNewBoardgameBy(boardgameRaw: BoardgameRaw): Message {
        return try {
            val boardgameInfo: BoardgameInfo = boardgameRaw.toBoardgameInfo()
            val boardgameId: Long = boardgameDao.add(boardgameInfo)
            val helpcard: Helpcard = boardgameRaw.toHelpcard(boardgameId)
            boardgameDao.add(helpcard)
            Message.ACTION_ENDED_SUCCESS
        } catch (error: Throwable) {
            Message.ACTION_ENDED_ERROR
        }
    }

    override fun deleteBoardgameBy(boardgameId: Long) {
        DeleteBoardgameInfoAndHelpcardByIdAsyncTask(boardgameDao = boardgameDao).execute(boardgameId)
    }

    override fun update(boardgameInfo: BoardgameInfo) {
        UpdateBoardgameInfoAsyncTask(boardgameDao = boardgameDao).execute(boardgameInfo)
    }

    override suspend fun updateBoardgameBy(boardgameRaw: BoardgameRaw): Message {
        return try {
            val data: BoardgameRaw = boardgameRaw
            if (data.id != null) {
                val boardgameInfo: BoardgameInfo = data.toBoardgameInfo()
                boardgameDao.update(boardgameInfo)
                val helpcardDbId: Long = boardgameDao.getHelpcardIdBy(data.id!!)
                val helpcard: Helpcard = data.toHelpcard(helpcardDbId, data.id!!)
                boardgameDao.update(helpcard)
            }
            Message.ACTION_ENDED_SUCCESS
        } catch (error: Throwable) {
            Message.ACTION_ENDED_ERROR
        }
    }

    override fun deleteUnlockBoardgames() {
        DeleteUnlockBoardgamesAsyncTask(boardgameDao = boardgameDao).execute()
    }

    companion object {

        private class UpdateBoardgameInfoAsyncTask(private val boardgameDao: BoardgameDao) :
            AsyncTask<BoardgameInfo, Void, Void>() {
            override fun doInBackground(vararg boardgameInfos: BoardgameInfo): Void? {
//                boardgameDao.update(boardgameInfos[0])
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