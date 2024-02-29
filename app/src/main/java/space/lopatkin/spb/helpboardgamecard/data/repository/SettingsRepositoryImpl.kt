package space.lopatkin.spb.helpboardgamecard.data.repository

import kotlinx.coroutines.flow.Flow
import space.lopatkin.spb.helpboardgamecard.data.local.data.source.SettingsLocalDataSource
import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType
import space.lopatkin.spb.helpboardgamecard.domain.repository.SettingsRepository
import space.lopatkin.spb.helpboardgamecard.domain.model.Completable

class SettingsRepositoryImpl(
    private val settingsLocalDataSource: SettingsLocalDataSource
) : SettingsRepository {

    override fun saveKeyboardType(type: KeyboardType): Flow<Completable> {
        return settingsLocalDataSource.saveKeyboardType(type)
    }

    override fun getKeyboardType(): Flow<KeyboardType> {
        return settingsLocalDataSource.getKeyboardType()
    }

}