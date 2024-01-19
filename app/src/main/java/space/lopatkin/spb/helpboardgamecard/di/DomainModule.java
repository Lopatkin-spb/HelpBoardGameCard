package space.lopatkin.spb.helpboardgamecard.di;

import dagger.Module;
import dagger.Provides;
import space.lopatkin.spb.helpboardgamecard.domain.repository.AppRepository;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.DeleteHelpcardByIdUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.DeleteHelpcardUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.DeleteHelpcardsByLockUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetAllHelpcardsUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetDetailsHelpcardByBoardGameIdUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetKeyboardTypeUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.SaveKeyboardTypeByUserChoiceUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.SaveNewHelpcardUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.UpdateHelpcardByObjectUseCase;

import javax.inject.Singleton;

@Module
public class DomainModule {

    @Singleton
    @Provides
    public GetDetailsHelpcardByBoardGameIdUseCase provideGetDetailsHelpcardByBoardGameIdUseCase(AppRepository repository) {
        return new GetDetailsHelpcardByBoardGameIdUseCase(repository);
    }

    @Singleton
    @Provides
    public DeleteHelpcardUseCase provideDeleteHelpcardUseCase(AppRepository repository) {
        return new DeleteHelpcardUseCase(repository);
    }

    @Singleton
    @Provides
    public DeleteHelpcardByIdUseCase provideDeleteHelpcardByIdUseCase(AppRepository repository) {
        return new DeleteHelpcardByIdUseCase(repository);
    }

    @Singleton
    @Provides
    public GetAllHelpcardsUseCase provideGetAllHelpcardsUseCase(AppRepository repository) {
        return new GetAllHelpcardsUseCase(repository);
    }

    @Singleton
    @Provides
    public UpdateHelpcardByObjectUseCase provideUpdateHelpcardByObjectUseCase(AppRepository repository) {
        return new UpdateHelpcardByObjectUseCase(repository);
    }

    @Singleton
    @Provides
    public DeleteHelpcardsByLockUseCase provideDeleteHelpcardsByLockUseCase(AppRepository repository) {
        return new DeleteHelpcardsByLockUseCase(repository);
    }

    @Singleton
    @Provides
    public SaveNewHelpcardUseCase provideSaveNewHelpcardUseCase(AppRepository repository) {
        return new SaveNewHelpcardUseCase(repository);
    }

    @Singleton
    @Provides
    public SaveKeyboardTypeByUserChoiceUseCase provideSaveKeyboardTypeByUserChoiceUseCase(AppRepository repository) {
        return new SaveKeyboardTypeByUserChoiceUseCase(repository);
    }

    @Singleton
    @Provides
    public GetKeyboardTypeUseCase provideGetKeyboardTypeUseCase(AppRepository repository) {
        return new GetKeyboardTypeUseCase(repository);
    }

}
