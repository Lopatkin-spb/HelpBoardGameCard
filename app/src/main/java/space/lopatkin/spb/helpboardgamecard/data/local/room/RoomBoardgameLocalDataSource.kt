package space.lopatkin.spb.helpboardgamecard.data.local.room

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import space.lopatkin.spb.helpboardgamecard.data.local.data.source.BoardgameLocalDataSource
import space.lopatkin.spb.helpboardgamecard.data.local.room.RoomDb.Companion.getInstance
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameInfo
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameRaw
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard
import space.lopatkin.spb.helpboardgamecard.domain.model.Message

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
            val list: List<BoardgameInfo> = boardgameDao.getAllBoardgamesInfo()
            Result.success(list)
        } catch (cause: Throwable) {
            Result.failure(cause)
        }
    }

    override suspend fun getHelpcardBy(boardgameId: Long): Result<Helpcard> {
        return try {
            val dataFromDb: Helpcard? = boardgameDao.getHelpcardBy(boardgameId)
            if (dataFromDb != null) {
                Result.success(dataFromDb)
            } else {
                Result.failure(Exception("NotFoundException (room): query (getHelpcardByBoardgameId) not finished because Helpcard not found in db table"))
            }
        } catch (cause: Throwable) {
            Result.failure(cause)
        }
    }

    override suspend fun getBoardgameRawBy(boardgameId: Long): Result<BoardgameRaw> {
        return try {
            val dataFromDb: BoardgameRaw? = boardgameDao.getBoardgameRawBy(boardgameId)
            if (dataFromDb != null) {
                Result.success(dataFromDb)
            } else {
                Result.failure(Exception("NotFoundException (room): query (getBoardgameRawByBoardgameId) not finished because Boardgame & Helpcard not found in db tables"))
            }
        } catch (cause: Throwable) {
            Result.failure(cause)
        }
    }

    override suspend fun saveNewBoardgameBy(boardgameRaw: BoardgameRaw): Result<Message> {
        return try {
            val boardgameInfo: BoardgameInfo = boardgameRaw.toBoardgameInfo()
            val addedInfoId: Long = boardgameDao.add(boardgameInfo)
            val helpcard: Helpcard = boardgameRaw.toHelpcard(addedInfoId)
            val addedHelpcardId: Long = boardgameDao.add(helpcard)
            if (addedInfoId > 0 && addedHelpcardId > 0) {
                Result.success(Message.ACTION_ENDED_SUCCESS)
            } else {
                val statusDeletedHelpcard: Int = boardgameDao.deleteHelpcardByOwn(addedHelpcardId)
                val statusDeletedInfo: Int = boardgameDao.deleteBoardgameInfoBy(addedInfoId)
                Result.failure(Exception("InvalidOperationException (room): models (Helpcard & BoardgameInfo from BoardgameRaw) not added to db"))
            }
        } catch (cause: Throwable) {
            Result.failure(cause)
        }
    }

    override suspend fun deleteBoardgameBy(boardgameId: Long): Result<Message> {
        return try {
            val deletedHelpcard: Int = boardgameDao.deleteHelpcardBy(boardgameId)
            val deletedInfo: Int = boardgameDao.deleteBoardgameInfoBy(boardgameId)
            if (deletedInfo > 0 && deletedHelpcard > 0) {
                return Result.success(Message.DELETE_ITEM_ACTION_ENDED_SUCCESS)
            } else {
                Result.failure(Exception("InvalidOperationException (room): models (Helpcard & BoardgameInfo) not deleted full"))
            }
        } catch (cause: Throwable) {
            Result.failure(cause)
        }
    }

    override suspend fun update(boardgameInfo: BoardgameInfo): Result<Message> {
        return try {
            val updated: Int = boardgameDao.update(boardgameInfo)
            if (updated > 0) {
                Result.success(Message.ACTION_ENDED_SUCCESS)
            } else {
                Result.failure(Exception("NotFoundException (room): model (BoardgameInfo) not updated because (BoardgameId) not found in db table"))
            }
        } catch (cause: Throwable) {
            Result.failure(cause)
        }
    }

    override suspend fun updateBoardgameBy(boardgameRaw: BoardgameRaw): Result<Message> {
        return try {
            if (boardgameRaw.id != null) {
                val helpcardDbId: Long = boardgameDao.getHelpcardIdBy(boardgameRaw.id!!)

                val helpcard: Helpcard = boardgameRaw.toHelpcard(helpcardDbId, boardgameRaw.id!!)
                val statusUpdatedHelpcard: Int = boardgameDao.update(helpcard)

                val boardgameInfo: BoardgameInfo = boardgameRaw.toBoardgameInfo()
                val statusUpdatedInfo: Int = boardgameDao.update(boardgameInfo)

                if (statusUpdatedInfo > 0 && statusUpdatedHelpcard > 0) {
                    Result.success(Message.ACTION_ENDED_SUCCESS)
                } else {
                    val statusDeletedHelpcard: Int = boardgameDao.deleteHelpcardBy(helpcard.boardgameId)
                    val statusDeletedInfo: Int = boardgameDao.deleteBoardgameInfoBy(boardgameRaw.id!!)
                    Result.failure(Exception("InvalidOperationException (room): models (Helpcard & BoardgameInfo) not updated"))
                }
            } else {
                Result.failure(Exception("NotFoundException (room): Id from BoardgameRaw for update models not found"))
            }
        } catch (cause: Throwable) {
            Result.failure(cause)
        }
    }

    override suspend fun deleteUnlockBoardgames(): Result<Message> {
        return try {
            val listIdUnlock: Array<Long> = boardgameDao.getBoardgameIdsByUnlock()
            if (listIdUnlock.size > 0) {
                for (index in 0 until listIdUnlock.size) {
                    val deletedStatusInfo: Int = boardgameDao.deleteBoardgameInfoBy(listIdUnlock.get(index))
                    val deletedStatusHelpcard: Int = boardgameDao.deleteHelpcardBy(listIdUnlock.get(index))
                }
                Result.success(Message.DELETE_ALL_ACTION_ENDED_SUCCESS)
            } else {
                Result.success(Message.DELETE_ALL_ACTION_STOPPED)
            }
        } catch (cause: Throwable) {
            Result.failure(cause)
        }
    }

}