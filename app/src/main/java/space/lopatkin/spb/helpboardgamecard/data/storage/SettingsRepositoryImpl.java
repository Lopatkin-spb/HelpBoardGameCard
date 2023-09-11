package space.lopatkin.spb.helpboardgamecard.data.storage;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import space.lopatkin.spb.helpboardgamecard.data.repository.SettingsRepository;

public class SettingsRepositoryImpl implements SettingsRepository {
    private final static String APPLICATION_PREFERENCES = SettingsRepositoryImpl.class.getSimpleName() + "APPLICATION_PREFERENCES";
    private final static String KEYBOARD_VARIANT = "KEYBOARD_VARIANT";

    private Context context;
    private SharedPreferences preferences;

    public SettingsRepositoryImpl(Application application) {
        this.context = application.getApplicationContext();
        preferences = context.getSharedPreferences(APPLICATION_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    public void saveKeyboardVariant(int keyboardVariant) {
        preferences
                .edit()
                .putInt(KEYBOARD_VARIANT, keyboardVariant)
                .apply();
    }

    @Override
    public int getKeyboardVariant() {
        return preferences
                .getInt(KEYBOARD_VARIANT, -1);
    }

}
