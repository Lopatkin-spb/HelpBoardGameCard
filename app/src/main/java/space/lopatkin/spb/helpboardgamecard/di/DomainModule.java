package space.lopatkin.spb.helpboardgamecard.di;

import dagger.Module;
import dagger.Provides;
import space.lopatkin.spb.helpboardgamecard.domain.repository.HelpcardRepository;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.*;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.SaveNewHelpcardUseCase;

import javax.inject.Singleton;

@Module
public class DomainModule {

    @Singleton
    @Provides
    public GetDetailsHelpcardByBoardGameIdUseCase provideGetDetailsHelpcardByBoardGameIdUseCase(
            HelpcardRepository repository) {
        return new GetDetailsHelpcardByBoardGameIdUseCase(repository);
    }

    @Singleton
    @Provides
    public DeleteHelpcardUseCase provideDeleteHelpcardUseCase(
            HelpcardRepository repository) {
        return new DeleteHelpcardUseCase(repository);
    }

    @Singleton
    @Provides
    public DeleteHelpcardByIdUseCase provideDeleteHelpcardByIdUseCase(
            HelpcardRepository repository) {
        return new DeleteHelpcardByIdUseCase(repository);
    }

    @Singleton
    @Provides
    public GetAllHelpcardsUseCase provideGetAllHelpcardsUseCase(
            HelpcardRepository repository) {
        return new GetAllHelpcardsUseCase(repository);
    }

    @Singleton
    @Provides
    public UpdateHelpcardByObjectUseCase provideUpdateHelpcardByObjectUseCase(
            HelpcardRepository repository) {
        return new UpdateHelpcardByObjectUseCase(repository);
    }

    @Singleton
    @Provides
    public DeleteHelpcardsByLockUseCase provideDeleteHelpcardsByLockUseCase(
            HelpcardRepository repository) {
        return new DeleteHelpcardsByLockUseCase(repository);
    }

    @Singleton
    @Provides
    public SaveNewHelpcardUseCase provideSaveNewHelpcardUseCase(
            HelpcardRepository repository) {
        return new SaveNewHelpcardUseCase(repository);
    }

}
