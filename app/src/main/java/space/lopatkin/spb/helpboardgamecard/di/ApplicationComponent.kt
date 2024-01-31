package space.lopatkin.spb.helpboardgamecard.di;

import dagger.Component;
import space.lopatkin.spb.helpboardgamecard.presentation.addcard.AddCardFragment;
import space.lopatkin.spb.helpboardgamecard.presentation.catalog.CatalogFragment;
import space.lopatkin.spb.helpboardgamecard.presentation.catalog.helpcard.cardedit.CardEditFragment;
import space.lopatkin.spb.helpboardgamecard.presentation.catalog.helpcard.HelpcardFragment;
import space.lopatkin.spb.helpboardgamecard.presentation.settings.SettingsFragment;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        DomainModule.class,
        DataModule.class
})
public interface ApplicationComponent {

    void inject(CatalogFragment fragment);

    void inject(AddCardFragment fragment);

    void inject(HelpcardFragment fragment);

    void inject(CardEditFragment fragment);

    void inject(SettingsFragment fragment);

}
