package space.lopatkin.spb.helpboardgamecard.di;

import dagger.Module;
import dagger.Provides;
import space.lopatkin.spb.helpboardgamecard.domain.repository.HelpcardRepository;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetDetailsHelpcardByBoardGameIdUseCase;

import javax.inject.Singleton;

@Module
public class DomainModule {

    @Singleton
    @Provides
    public GetDetailsHelpcardByBoardGameIdUseCase provideGetDetailsHelpcardByBoardGameIdUseCase(
            HelpcardRepository repository) {
        return new GetDetailsHelpcardByBoardGameIdUseCase(repository);
    }

}
