package space.lopatkin.spb.helpboardgamecard.data.repository

import space.lopatkin.spb.helpboardgamecard.data.local.data.source.SettingsLocalDataSource
import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.repository.SettingsRepository

class SettingsRepositoryImpl(
    private val settingsLocalDataSource: SettingsLocalDataSource
) : SettingsRepository {

    override suspend fun saveKeyboardType(type: KeyboardType): Result<Message> {
        return settingsLocalDataSource.saveKeyboardType(type)
    }

    override suspend fun getKeyboardType(): Result<KeyboardType> {
        return settingsLocalDataSource.getKeyboardType()
    }

}