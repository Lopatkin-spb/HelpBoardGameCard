package space.lopatkin.spb.helpboardgamecard.application

import android.app.Application
import space.lopatkin.spb.helpboardgamecard.di.ApplicationComponent
import space.lopatkin.spb.helpboardgamecard.di.ApplicationModule
import space.lopatkin.spb.helpboardgamecard.di.DaggerApplicationComponent

class HelpBoardGameCardApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        applicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(context = this))
            .build()
    }

}
