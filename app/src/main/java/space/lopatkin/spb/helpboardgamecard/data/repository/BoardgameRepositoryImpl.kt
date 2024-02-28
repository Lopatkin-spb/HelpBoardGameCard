package space.lopatkin.spb.helpboardgamecard.data.repository

import kotlinx.coroutines.flow.Flow
import space.lopatkin.spb.helpboardgamecard.data.local.data.source.BoardgameLocalDataSource
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameInfo
import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameRaw
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.repository.BoardgameRepository

class BoardgameRepositoryImpl(
    private val boardgameLocalDataSource: BoardgameLocalDataSource
) : BoardgameRepository {

    override fun getAllBoardgamesInfo(): Flow<List<BoardgameInfo>> {
        return boardgameLocalDataSource.getAllBoardgamesInfo()
    }

    override fun getHelpcardBy(boardgameId: Long): Flow<Helpcard> {
        return boardgameLocalDataSource.getHelpcardBy(boardgameId)
    }

    override fun getBoardgameRawBy(boardgameId: Long): Flow<BoardgameRaw> {
        return boardgameLocalDataSource.getBoardgameRawBy(boardgameId)
    }

    override fun deleteBoardgameBy(boardgameId: Long): Flow<Message> {
        return boardgameLocalDataSource.deleteBoardgameBy(boardgameId)
    }

    override fun update(boardgameInfo: BoardgameInfo): Flow<Message> {
        return boardgameLocalDataSource.update(boardgameInfo)
    }

    override fun deleteUnlockBoardgames(): Flow<Message> {
        return boardgameLocalDataSource.deleteUnlockBoardgames()
    }

    override fun saveNewBoardgameBy(boardgameRaw: BoardgameRaw): Flow<Message> {
        return boardgameLocalDataSource.saveNewBoardgameBy(boardgameRaw)
    }

    override fun updateBoardgameBy(boardgameRaw: BoardgameRaw): Flow<Message> {
        return boardgameLocalDataSource.updateBoardgameBy(boardgameRaw)
    }

}