package space.lopatkin.spb.helpboardgamecard.data.repository

import space.lopatkin.spb.helpboardgamecard.data.local.data.source.BoardgameLocalDataSource
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameInfo
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameRaw
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.repository.BoardgameRepository

class BoardgameRepositoryImpl(
    private val boardgameLocalDataSource: BoardgameLocalDataSource
) : BoardgameRepository {

    override suspend fun getAllBoardgamesInfo(): Result<List<BoardgameInfo>> {
        return boardgameLocalDataSource.getAllBoardgamesInfo()
    }

    override suspend fun getHelpcardBy(boardgameId: Long): Result<Helpcard> {
        return boardgameLocalDataSource.getHelpcardBy(boardgameId)
    }

    override suspend fun getBoardgameRawBy(boardgameId: Long): Result<BoardgameRaw> {
        return boardgameLocalDataSource.getBoardgameRawBy(boardgameId)
    }

    override suspend fun deleteBoardgameBy(boardgameId: Long): Result<Message> {
        return boardgameLocalDataSource.deleteBoardgameBy(boardgameId)
    }

    override suspend fun update(boardgameInfo: BoardgameInfo): Result<Message> {
        return boardgameLocalDataSource.update(boardgameInfo)
    }

    override suspend fun deleteUnlockBoardgames(): Result<Message> {
        return boardgameLocalDataSource.deleteUnlockBoardgames()
    }

    override suspend fun saveNewBoardgameBy(boardgameRaw: BoardgameRaw): Result<Message> {
        return boardgameLocalDataSource.saveNewBoardgameBy(boardgameRaw)
    }

    override suspend fun updateBoardgameBy(boardgameRaw: BoardgameRaw): Result<Message> {
        return boardgameLocalDataSource.updateBoardgameBy(boardgameRaw)
    }

}