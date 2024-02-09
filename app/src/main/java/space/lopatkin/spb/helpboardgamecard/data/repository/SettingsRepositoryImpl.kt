package space.lopatkin.spb.helpboardgamecard.data.repository

import space.lopatkin.spb.helpboardgamecard.data.local.data.source.SettingsLocalDataSource
import space.lopatkin.spb.helpboardgamecard.domain.repository.SettingsRepository

class SettingsRepositoryImpl(
    private val settingsLocalDataSource: SettingsLocalDataSource
) : SettingsRepository {

    override fun saveKeyboardType(type: Int) {
        settingsLocalDataSource.saveKeyboardType(type = type)
    }

    override fun getKeyboardType(): Int {
        return settingsLocalDataSource.getKeyboardType()
    }

}