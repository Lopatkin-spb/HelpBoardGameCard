package space.lopatkin.spb.helpboardgamecard.data.storage.preferences

import android.content.Context
import android.content.SharedPreferences
import space.lopatkin.spb.helpboardgamecard.data.storage.repository.SettingsRepository

private const val APPLICATION_PREFERENCES = "APPLICATION_PREFERENCES"
private const val KEYBOARD_TYPE = "KEYBOARD_TYPE"

class SettingsRepositoryImpl(private val context: Context) : SettingsRepository {

    private val preferences: SharedPreferences = context.getSharedPreferences(APPLICATION_PREFERENCES, Context.MODE_PRIVATE)

    override fun saveKeyboardType(type: Int) {
        preferences
            .edit()
            .putInt(KEYBOARD_TYPE, type)
            .apply()
    }

    override fun getKeyboardType(): Int {
        return preferences
            .getInt(KEYBOARD_TYPE, -1)
    }

}
