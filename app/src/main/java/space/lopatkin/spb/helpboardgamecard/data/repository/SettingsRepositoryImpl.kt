package space.lopatkin.spb.helpboardgamecard.data.repository

import kotlinx.coroutines.flow.Flow
import space.lopatkin.spb.helpboardgamecard.data.local.data.source.SettingsLocalDataSource
import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType
import space.lopatkin.spb.helpboardgamecard.domain.model.Message
import space.lopatkin.spb.helpboardgamecard.domain.repository.SettingsRepository

class SettingsRepositoryImpl(
    private val settingsLocalDataSource: SettingsLocalDataSource
) : SettingsRepository {

    override fun saveKeyboardType(type: KeyboardType): Flow<Message> {
        return settingsLocalDataSource.saveKeyboardType(type)
    }

    override fun getKeyboardType(): Flow<KeyboardType> {
        return settingsLocalDataSource.getKeyboardType()
    }

}