package space.lopatkin.spb.helpboardgamecard.data

import androidx.lifecycle.LiveData
import space.lopatkin.spb.helpboardgamecard.data.repository.DatabaseRepository
import space.lopatkin.spb.helpboardgamecard.data.repository.SettingsRepository
import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard
import space.lopatkin.spb.helpboardgamecard.domain.repository.AppRepository

class AppRepositoryImpl(
    private val databaseRepository: DatabaseRepository,
    private val settingsRepository: SettingsRepository
) : AppRepository {
    override fun getHelpcard(boardGameId: Int): LiveData<Helpcard> {
        return databaseRepository.getHelpcard(boardGameId)
    }

    override fun getAllHelpcards(): LiveData<List<Helpcard>> {
        return databaseRepository.getAllHelpcards()
    }

    override fun delete(helpcard: Helpcard) {
        databaseRepository.delete(helpcard)
    }

    override fun delete(id: Int) {
        databaseRepository.delete(id)
    }

    override fun update(helpcard: Helpcard) {
        databaseRepository.update(helpcard)
    }

    override fun deleteAllUnlockHelpcards() {
        databaseRepository.deleteAllUnlockHelpcards()
    }

    override fun saveNewHelpcard(helpcard: Helpcard) {
        databaseRepository.saveNewHelpcard(helpcard)
    }

    override fun saveKeyboardType(type: Int) {
        settingsRepository.saveKeyboardType(type)
    }

    override fun getKeyboardType(): Int {
        return settingsRepository.getKeyboardType()
    }

}