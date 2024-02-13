package space.lopatkin.spb.helpboardgamecard.data.local.room

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import space.lopatkin.spb.helpboardgamecard.data.local.data.source.BoardgameLocalDataSource
import space.lopatkin.spb.helpboardgamecard.data.local.room.RoomDb.Companion.getInstance
import space.lopatkin.spb.helpboardgamecard.domain.model.*

class RoomBoardgameLocalDataSource(
    private val context: Context,
    private val scope: CoroutineScope
) : BoardgameLocalDataSource {
    private val boardgameDao: BoardgameDao

    init {
        val database: RoomDb = getInstance(context = context, scope = scope)
        boardgameDao = database.boardgameDao()
    }

    override suspend fun getAllBoardgamesInfo(): Result<List<BoardgameInfo>> {
        return try {
            Result.success(boardgameDao.getAllBoardgamesInfo())
        } catch (cause: Throwable) {
            Result.failure(cause)
        }
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

    override suspend fun deleteBoardgameBy(boardgameId: Long): Result<Message> {
        return try {
            val deletedInfo: Int = boardgameDao.deleteBoardgameInfoBy(boardgameId)
            val deletedHelpcard: Int = boardgameDao.deleteHelpcardBy(boardgameId)
            if (deletedInfo > 0 && deletedHelpcard > 0) {
                return Result.success(Message.DELETE_ITEM_ACTION_ENDED_SUCCESS)
            } else {
                return Result.failure(DataPassError("Data pass is null", IllegalStateException()))
            }
        } catch (cause: Throwable) {
            Result.failure(cause)
        }
    }

    override suspend fun update(boardgameInfo: BoardgameInfo): Result<Message> {
        return try {
            val updated: Int = boardgameDao.update(boardgameInfo)
            if (updated > 0) {
                return Result.success(Message.ACTION_ENDED_SUCCESS)
            } else {
                return Result.failure(DataPassError("Data pass is null", IllegalStateException()))
            }
        } catch (cause: Throwable) {
            Result.failure(cause)
        }
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

    override suspend fun deleteUnlockBoardgames(): Result<Message> {
        return try {
            val listIdUnlock: Array<Long> = boardgameDao.getBoardgameIdsByUnlock()

            for (index in 0 until listIdUnlock.size) {
                val deletedStatusInfo: Int = boardgameDao.deleteBoardgameInfoBy(listIdUnlock.get(index))
                val deletedStatusHelpcard: Int = boardgameDao.deleteHelpcardBy(listIdUnlock.get(index))

                if (deletedStatusInfo == 0 && deletedStatusHelpcard == 0) {
                    return Result.failure(DataPassError("Data pass is null", IllegalStateException()))
                }
            }
            Result.success(Message.DELETE_ALL_ACTION_ENDED_SUCCESS)
        } catch (cause: Throwable) {
            Result.failure(cause)
        }
    }

    override suspend fun getBoardgamesIdByUnlock(): Result<Array<Long>> {
        return try {
            val listIdUnlock: Array<Long> = boardgameDao.getBoardgameIdsByUnlock()

            Result.success(listIdUnlock)
        } catch (cause: Throwable) {
            Result.failure(cause)
        }
    }

}