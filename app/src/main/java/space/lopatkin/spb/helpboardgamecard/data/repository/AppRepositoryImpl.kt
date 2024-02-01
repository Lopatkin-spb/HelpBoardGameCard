package space.lopatkin.spb.helpboardgamecard.data.repository

import androidx.lifecycle.LiveData
import space.lopatkin.spb.helpboardgamecard.data.storage.repository.DatabaseRepository
import space.lopatkin.spb.helpboardgamecard.data.storage.repository.SettingsRepository
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard
import space.lopatkin.spb.helpboardgamecard.domain.repository.AppRepository

class AppRepositoryImpl(
    private val databaseRepository: DatabaseRepository,
    private val settingsRepository: SettingsRepository
) : AppRepository {

    override fun getHelpcard(boardGameId: Int): LiveData<Helpcard> {
        return databaseRepository.getHelpcard(boardGameId = boardGameId)
    }

    override fun getAllHelpcards(): LiveData<List<Helpcard>> {
        return databaseRepository.getAllHelpcards()
    }

    override fun delete(helpcard: Helpcard) {
        databaseRepository.delete(helpcard = helpcard)
    }

    override fun delete(id: Int) {
        databaseRepository.delete(id = id)
    }

    override fun update(helpcard: Helpcard) {
        databaseRepository.update(helpcard = helpcard)
    }

    override fun deleteAllUnlockHelpcards() {
        databaseRepository.deleteAllUnlockHelpcards()
    }

    override fun saveNewHelpcard(helpcard: Helpcard) {
        databaseRepository.saveNewHelpcard(helpcard = helpcard)
    }

    override fun saveKeyboardType(type: Int) {
        settingsRepository.saveKeyboardType(type = type)
    }

    override fun getKeyboardType(): Int {
        return settingsRepository.getKeyboardType()
    }

}