package space.lopatkin.spb.helpboardgamecard.di;

import dagger.Component;
import space.lopatkin.spb.helpboardgamecard.ui.catalog.CatalogFragment;
import space.lopatkin.spb.helpboardgamecard.ui.helpcard.HelpcardFragment;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        AppModule.class,
        DomainModule.class,
        DataModule.class
})
public interface AppComponent {

    void inject(CatalogFragment fragment);

    void inject(HelpcardFragment fragment);

}
