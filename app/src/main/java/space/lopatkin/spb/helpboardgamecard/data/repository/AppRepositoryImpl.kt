package space.lopatkin.spb.helpboardgamecard.data.repository

import androidx.lifecycle.LiveData
import space.lopatkin.spb.helpboardgamecard.data.storage.repository.DatabaseRepository
import space.lopatkin.spb.helpboardgamecard.data.storage.repository.SettingsRepository
import space.lopatkin.spb.helpboardgamecard.domain.model.*
import space.lopatkin.spb.helpboardgamecard.domain.repository.AppRepository

class AppRepositoryImpl(
    private val databaseRepository: DatabaseRepository,
    private val settingsRepository: SettingsRepository
) : AppRepository {

    override fun getAllBoardgamesInfo(): LiveData<List<BoardgameInfo>> {
        return databaseRepository.getAllBoardgamesInfo()
    }

    override fun getHelpcardBy(boardgameId: Long): LiveData<Helpcard> {
        return databaseRepository.getHelpcardBy(boardgameId)
    }

    override fun getBoardgameRawBy(boardgameId: Long): LiveData<BoardgameRaw> {
        return databaseRepository.getBoardgameRawBy(boardgameId)
    }

    override fun deleteBoardgameBy(boardgameId: Long) {
        databaseRepository.deleteBoardgameBy(boardgameId)
    }

    override fun update(boardgameInfo: BoardgameInfo) {
        databaseRepository.update(boardgameInfo)
    }

    override fun deleteUnlockBoardgames() {
        databaseRepository.deleteUnlockBoardgames()
    }

    override fun saveNewBoardgameBy(boardgameRaw: BoardgameRaw) {
        databaseRepository.saveNewBoardgameBy(boardgameRaw)
    }

    override fun updateBoardgameBy(boardgameRaw: BoardgameRaw) {
        databaseRepository.updateBoardgameBy(boardgameRaw)
    }

    override fun saveKeyboardType(type: Int) {
        settingsRepository.saveKeyboardType(type = type)
    }

    override fun getKeyboardType(): Int {
        return settingsRepository.getKeyboardType()
    }

}