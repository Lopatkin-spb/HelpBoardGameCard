package space.lopatkin.spb.helpboardgamecard.data.local.room

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import space.lopatkin.spb.helpboardgamecard.data.local.data.source.BoardgameLocalDataSource
import space.lopatkin.spb.helpboardgamecard.data.local.room.RoomDb.Companion.getInstance
import space.lopatkin.spb.helpboardgamecard.di.ApplicationModule
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameInfo
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameRaw
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard
import space.lopatkin.spb.helpboardgamecard.domain.model.Message

class RoomBoardgameLocalDataSource(
    private val context: Context,
    private val scope: CoroutineScope,
    private val dispatchers: ApplicationModule.CoroutineDispatchers

) : BoardgameLocalDataSource {
    private val boardgameDao: BoardgameDao

    init {
        val database: RoomDb = getInstance(context, scope)
        boardgameDao = database.boardgameDao()
    }

    override fun getAllBoardgamesInfo(): Flow<List<BoardgameInfo>> {
        return flow {
            try {
                emit(boardgameDao.getAllBoardgamesInfo())
            } catch (cause: Throwable) {
                throw cause
            }
        }.flowOn(dispatchers.io)
    }

    override fun getHelpcardBy(boardgameId: Long): Flow<Helpcard> {
        return flow {
            try {
                val dataFromDb: Helpcard? = boardgameDao.getHelpcardBy(boardgameId)
                if (dataFromDb != null) {
                    emit(dataFromDb)
                } else {
                    throw Exception("NotFoundException (room): query (getHelpcardByBoardgameId) not finished because Helpcard not found in db table")
                }
            } catch (cause: Throwable) {
                throw cause
            }
        }.flowOn(dispatchers.io)
    }

    override fun getBoardgameRawBy(boardgameId: Long): Flow<BoardgameRaw> {
        return flow {
            try {
                val dataFromDb: BoardgameRaw? = boardgameDao.getBoardgameRawBy(boardgameId)
                if (dataFromDb != null) {
                    emit(dataFromDb)
                } else {
                    throw Exception("NotFoundException (room): query (getBoardgameRawByBoardgameId) not finished because Boardgame & Helpcard not found in db tables")
                }
            } catch (cause: Throwable) {
                throw cause
            }
        }.flowOn(dispatchers.io)
    }

    override fun saveNewBoardgameBy(boardgameRaw: BoardgameRaw): Flow<Message> {
        return flow {
            try {
                val boardgameInfo: BoardgameInfo = boardgameRaw.toBoardgameInfo()
                val addedInfoId: Long = boardgameDao.add(boardgameInfo)
                val helpcard: Helpcard = boardgameRaw.toHelpcard(addedInfoId)
                val addedHelpcardId: Long = boardgameDao.add(helpcard)
                if (addedInfoId > 0 && addedHelpcardId > 0) {
                    emit(Message.ACTION_ENDED_SUCCESS)
                } else {
                    val statusDeletedHelpcard: Int = boardgameDao.deleteHelpcardByOwn(addedHelpcardId)
                    val statusDeletedInfo: Int = boardgameDao.deleteBoardgameInfoBy(addedInfoId)
                    throw Exception("InvalidOperationException (room): models (Helpcard & BoardgameInfo from BoardgameRaw) not added to db")
                }
            } catch (cause: Throwable) {
                throw cause
            }
        }.flowOn(dispatchers.io)
    }

    override fun deleteBoardgameBy(boardgameId: Long): Flow<Message> {
        return flow {
            try {
                val deletedHelpcard: Int = boardgameDao.deleteHelpcardBy(boardgameId)
                val deletedInfo: Int = boardgameDao.deleteBoardgameInfoBy(boardgameId)
                if (deletedInfo > 0 && deletedHelpcard > 0) {
                    emit(Message.ACTION_ENDED_SUCCESS)
                } else {
                    throw Exception("InvalidOperationException (room): models (Helpcard & BoardgameInfo) not deleted full")
                }
            } catch (cause: Throwable) {
                throw cause
            }
        }.flowOn(dispatchers.io)
    }

    override fun update(boardgameInfo: BoardgameInfo): Flow<Message> {
        return flow {
            try {
                val updated: Int = boardgameDao.update(boardgameInfo)
                if (updated > 0) {
                    emit(Message.ACTION_ENDED_SUCCESS)
                } else {
                    throw Exception("NotFoundException (room): model (BoardgameInfo) not updated because (BoardgameId) not found in db table")
                }
            } catch (cause: Throwable) {
                throw cause
            }
        }.flowOn(dispatchers.io)
    }

    override fun updateBoardgameBy(boardgameRaw: BoardgameRaw): Flow<Message> {
        return flow {
            try {
                if (boardgameRaw.id != null) {
                    val helpcardDbId: Long = boardgameDao.getHelpcardIdBy(boardgameRaw.id!!)

                    val helpcard: Helpcard = boardgameRaw.toHelpcard(helpcardDbId, boardgameRaw.id!!)
                    val statusUpdatedHelpcard: Int = boardgameDao.update(helpcard)

                    val boardgameInfo: BoardgameInfo = boardgameRaw.toBoardgameInfo()
                    val statusUpdatedInfo: Int = boardgameDao.update(boardgameInfo)

                    if (statusUpdatedInfo > 0 && statusUpdatedHelpcard > 0) {
                        emit(Message.ACTION_ENDED_SUCCESS)
                    } else {
                        val statusDeletedHelpcard: Int = boardgameDao.deleteHelpcardBy(helpcard.boardgameId)
                        val statusDeletedInfo: Int = boardgameDao.deleteBoardgameInfoBy(boardgameRaw.id!!)
                        throw Exception("InvalidOperationException (room): models (Helpcard & BoardgameInfo) not updated")
                    }
                } else {
                    throw Exception("NotFoundException (room): Id from BoardgameRaw for update models not found")
                }
            } catch (cause: Throwable) {
                throw cause
            }
        }.flowOn(dispatchers.io)
    }

    override fun deleteUnlockBoardgames(): Flow<Message> {
        return flow {
            try {
                val listIdUnlock: Array<Long> = boardgameDao.getBoardgameIdsByUnlock()
                if (listIdUnlock.size > 0) {
                    for (index in 0 until listIdUnlock.size) {
                        val deletedStatusInfo: Int = boardgameDao.deleteBoardgameInfoBy(listIdUnlock.get(index))
                        val deletedStatusHelpcard: Int = boardgameDao.deleteHelpcardBy(listIdUnlock.get(index))
                    }
                    emit(Message.ACTION_ENDED_SUCCESS)
                } else {
                    throw Exception("NotFoundException (room): (Boardgame)s not deleted because not files for deleting")
                }
            } catch (cause: Throwable) {
                throw cause
            }
        }.flowOn(dispatchers.io)
    }

}