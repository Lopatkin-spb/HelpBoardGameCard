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

    override suspend fun saveKeyboardType(type: Int): Message {
        return try {
            preferences
                .edit()
                .putInt(KEYBOARD_TYPE, type)
                .apply()
            Message.ACTION_ENDED_SUCCESS
        } catch (error: Throwable) {
            Message.ACTION_ENDED_ERROR
        }
    }

    override suspend fun getKeyboardType(): Int {
        return try {
            preferences
                .getInt(KEYBOARD_TYPE, -1)
        } catch (error: Throwable) {
            KeyboardType.CUSTOM.ordinal
        }
    }

}
