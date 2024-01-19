package space.lopatkin.spb.helpboardgamecard.data.storage.preferences;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import space.lopatkin.spb.helpboardgamecard.data.repository.SettingsRepository;

public class SettingsRepositoryImpl implements SettingsRepository {
    private final static String APPLICATION_PREFERENCES = SettingsRepositoryImpl.class.getSimpleName() + "APPLICATION_PREFERENCES";
    private final static String KEYBOARD_TYPE = "KEYBOARD_TYPE";

    private Context context;
    private SharedPreferences preferences;

    public SettingsRepositoryImpl(Application application) {
        this.context = application.getApplicationContext();
        preferences = context.getSharedPreferences(APPLICATION_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    public void saveKeyboardType(int type) {
        preferences
                .edit()
                .putInt(KEYBOARD_TYPE, type)
                .apply();
    }

    @Override
    public int getKeyboardType() {
        return preferences
                .getInt(KEYBOARD_TYPE, -1);
    }

}
