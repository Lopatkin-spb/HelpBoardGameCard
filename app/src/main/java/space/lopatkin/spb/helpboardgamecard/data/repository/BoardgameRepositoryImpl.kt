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

    override fun getHelpcardBy(boardgameId: Long): LiveData<Helpcard> {
        return boardgameLocalDataSource.getHelpcardBy(boardgameId)
    }

    override fun getBoardgameRawBy(boardgameId: Long): LiveData<BoardgameRaw> {
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

    override fun saveNewBoardgameBy(boardgameRaw: BoardgameRaw) {
        boardgameLocalDataSource.saveNewBoardgameBy(boardgameRaw)
    }

    override fun updateBoardgameBy(boardgameRaw: BoardgameRaw) {
        boardgameLocalDataSource.updateBoardgameBy(boardgameRaw)
    }

}