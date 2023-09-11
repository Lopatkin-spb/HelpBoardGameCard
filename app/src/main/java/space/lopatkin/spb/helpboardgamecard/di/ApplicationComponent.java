package space.lopatkin.spb.helpboardgamecard.di;

import dagger.Component;
import space.lopatkin.spb.helpboardgamecard.ui.addcard.AddCardFragment;
import space.lopatkin.spb.helpboardgamecard.ui.catalog.CatalogFragment;
import space.lopatkin.spb.helpboardgamecard.ui.catalog.helpcard.cardedit.CardEditFragment;
import space.lopatkin.spb.helpboardgamecard.ui.catalog.helpcard.HelpcardFragment;
import space.lopatkin.spb.helpboardgamecard.ui.settings.SettingsFragment;

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
