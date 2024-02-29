package space.lopatkin.spb.helpboardgamecard.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import space.lopatkin.spb.helpboardgamecard.data.local.data.source.SettingsLocalDataSource
import space.lopatkin.spb.helpboardgamecard.di.ApplicationModule
import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType
import space.lopatkin.spb.helpboardgamecard.domain.model.Message

private const val APPLICATION_PREFERENCES = "APPLICATION_PREFERENCES"
private const val KEYBOARD_TYPE = "KEYBOARD_TYPE"

class PreferencesSettingsLocalDataSource(
    private val context: Context,
    private val dispatchers: ApplicationModule.CoroutineDispatchers
) : SettingsLocalDataSource {

    private val preferences: SharedPreferences =
        context.getSharedPreferences(APPLICATION_PREFERENCES, Context.MODE_PRIVATE)

    override fun saveKeyboardType(type: KeyboardType): Flow<Message> {
        return flow {
            preferences
                .edit()
                .putInt(KEYBOARD_TYPE, type.ordinal)
                .apply()
            emit(Message.ACTION_ENDED_SUCCESS)
        }.flowOn(dispatchers.io)
    }

    override fun getKeyboardType(): Flow<KeyboardType> {
        return flow {
            val data: Int = preferences.getInt(KEYBOARD_TYPE, -1)
            emit(KeyboardType.getOrdinalFrom(data))
        }.flowOn(dispatchers.io)
    }

}
