package space.lopatkin.spb.helpboardgamecard.data.repository

import androidx.lifecycle.LiveData
import space.lopatkin.spb.helpboardgamecard.data.local.data.source.BoardgameLocalDataSource
import space.lopatkin.spb.helpboardgamecard.domain.model.*
import space.lopatkin.spb.helpboardgamecard.domain.repository.BoardgameRepository

class BoardgameRepositoryImpl(
    private val boardgameLocalDataSource: BoardgameLocalDataSource
) : BoardgameRepository {

    override fun getAllBoardgamesInfo(): LiveData<List<BoardgameInfo>> {
        return boardgameLocalDataSource.getAllBoardgamesInfo()
    }

    override suspend fun getHelpcardBy(boardgameId: Long): Result<Helpcard> {
        return boardgameLocalDataSource.getHelpcardBy(boardgameId)
    }

    override suspend fun getBoardgameRawBy(boardgameId: Long): Result<BoardgameRaw> {
        return boardgameLocalDataSource.getBoardgameRawBy(boardgameId)
    }

    override fun deleteBoardgameBy(boardgameId: Long) {
        boardgameLocalDataSource.deleteBoardgameBy(boardgameId)
    }

    override fun update(boardgameInfo: BoardgameInfo) {
        boardgameLocalDataSource.update(boardgameInfo)
    }

    override fun deleteUnlockBoardgames() {
        boardgameLocalDataSource.deleteUnlockBoardgames()
    }

    override suspend fun saveNewBoardgameBy(boardgameRaw: BoardgameRaw): Message {
        return boardgameLocalDataSource.saveNewBoardgameBy(boardgameRaw)
    }

    override suspend fun updateBoardgameBy(boardgameRaw: BoardgameRaw): Message {
        return boardgameLocalDataSource.updateBoardgameBy(boardgameRaw)
    }

}