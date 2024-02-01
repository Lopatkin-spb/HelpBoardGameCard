package space.lopatkin.spb.helpboardgamecard.di

import dagger.Component
import space.lopatkin.spb.helpboardgamecard.presentation.MainActivity
import space.lopatkin.spb.helpboardgamecard.presentation.addcard.AddCardFragment
import space.lopatkin.spb.helpboardgamecard.presentation.catalog.CatalogFragment
import space.lopatkin.spb.helpboardgamecard.presentation.catalog.helpcard.HelpcardFragment
import space.lopatkin.spb.helpboardgamecard.presentation.catalog.helpcard.cardedit.CardEditFragment
import space.lopatkin.spb.helpboardgamecard.presentation.settings.SettingsFragment
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        DomainModule::class,
        DataModule::class]
)
interface ApplicationComponent {

    fun inject(activity: MainActivity)
    fun inject(fragment: CatalogFragment)
    fun inject(fragment: AddCardFragment)
    fun inject(fragment: HelpcardFragment)
    fun inject(fragment: CardEditFragment)
    fun inject(fragment: SettingsFragment)

}
