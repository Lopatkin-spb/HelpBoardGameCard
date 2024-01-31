package space.lopatkin.spb.helpboardgamecard.application;

import android.app.Application;
import space.lopatkin.spb.helpboardgamecard.di.ApplicationComponent;
import space.lopatkin.spb.helpboardgamecard.di.ApplicationModule;
import space.lopatkin.spb.helpboardgamecard.di.DaggerApplicationComponent;

public class HelpBoardGameCardApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        initDagger();

    }

    private void initDagger() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

}
