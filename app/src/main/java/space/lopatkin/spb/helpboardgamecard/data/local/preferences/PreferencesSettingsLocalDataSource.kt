package space.lopatkin.spb.helpboardgamecard.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import space.lopatkin.spb.helpboardgamecard.data.local.data.source.SettingsLocalDataSource
import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType
import space.lopatkin.spb.helpboardgamecard.domain.model.Message

private const val APPLICATION_PREFERENCES = "APPLICATION_PREFERENCES"
private const val KEYBOARD_TYPE = "KEYBOARD_TYPE"

class PreferencesSettingsLocalDataSource(private val context: Context) : SettingsLocalDataSource {

    private val preferences: SharedPreferences =
        context.getSharedPreferences(APPLICATION_PREFERENCES, Context.MODE_PRIVATE)

    override suspend fun saveKeyboardType(type: KeyboardType): Result<Message> {
        return try {
            preferences
                .edit()
                .putInt(KEYBOARD_TYPE, type.ordinal)
                .apply()
            Result.success(Message.ACTION_ENDED_SUCCESS)
        } catch (cause: Throwable) {
            Result.failure(cause)
        }
    }

    override suspend fun getKeyboardType(): Result<KeyboardType> {
        return try {
            val data: Int = preferences.getInt(KEYBOARD_TYPE, -1)
            Result.success(KeyboardType.getOrdinalFrom(data))
        } catch (cause: Throwable) {
            Result.failure(cause)
        }
    }

}
