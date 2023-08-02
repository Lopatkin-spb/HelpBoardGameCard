package space.lopatkin.spb.helpboardgamecard.di;

import dagger.Module;
import dagger.Provides;
import space.lopatkin.spb.helpboardgamecard.domain.repository.HelpcardRepository;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.AddNewHelpcardUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.DeleteHelpcardByIdUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.DeleteHelpcardUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.DeleteHelpcardsByLockUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetAllHelpcardsUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetDetailsHelpcardByBoardGameIdUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.UpdateHelpcardByObjectUseCase;

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
    public AddNewHelpcardUseCase provideAddNewHelpcardUseCase(
            HelpcardRepository repository) {
        return new AddNewHelpcardUseCase(repository);
    }

}
