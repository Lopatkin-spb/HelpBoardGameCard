package space.lopatkin.spb.helpboardgamecard.data.repository

import space.lopatkin.spb.helpboardgamecard.data.local.data.source.SettingsLocalDataSource
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.repository.SettingsRepository

class SettingsRepositoryImpl(
    private val settingsLocalDataSource: SettingsLocalDataSource
) : SettingsRepository {

    override suspend fun saveKeyboardType(type: Int): Message {
        return settingsLocalDataSource.saveKeyboardType(type = type)
    }

    override suspend fun getKeyboardType(): Int {
        return settingsLocalDataSource.getKeyboardType()
    }

}