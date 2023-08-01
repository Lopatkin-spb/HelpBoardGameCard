package space.lopatkin.spb.helpboardgamecard.app;

import android.app.Application;
import space.lopatkin.spb.helpboardgamecard.di.AppComponent;
import space.lopatkin.spb.helpboardgamecard.di.AppModule;
import space.lopatkin.spb.helpboardgamecard.di.DaggerAppComponent;

public class App extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

}
