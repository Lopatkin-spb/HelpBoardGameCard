package space.lopatkin.spb.helpboardgamecard.di;

import dagger.Module;
import dagger.Provides;
import space.lopatkin.spb.helpboardgamecard.domain.repository.AppRepository;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.DeleteHelpcardUnlockedByHelpcardIdUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.DeleteHelpcardUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.DeleteHelpcardsByUnlockStateUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetAllHelpcardsUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetHelpcardByHelpcardIdUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.GetKeyboardTypeUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.SaveHelpcardNewByHelpcardIdUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.SaveKeyboardTypeByUserChoiceUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.UpdateHelpcardByHelpcardIdUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.UpdateHelpcardFavoriteByHelpcardIdUseCase;
import space.lopatkin.spb.helpboardgamecard.domain.usecase.UpdateHelpcardLockingByHelpcardIdUseCase;

import javax.inject.Singleton;

@Module
public class DomainModule {

    @Singleton
    @Provides
    public GetHelpcardByHelpcardIdUseCase provideGetHelpcardByHelpcardIdUseCase(AppRepository repository) {
        return new GetHelpcardByHelpcardIdUseCase(repository);
    }

    @Singleton
    @Provides
    public DeleteHelpcardUseCase provideDeleteHelpcardUseCase(AppRepository repository) {
        return new DeleteHelpcardUseCase(repository);
    }

    @Singleton
    @Provides
    public DeleteHelpcardUnlockedByHelpcardIdUseCase provideDeleteHelpcardUnlockedByHelpcardIdUseCase(AppRepository repository) {
        return new DeleteHelpcardUnlockedByHelpcardIdUseCase(repository);
    }

    @Singleton
    @Provides
    public GetAllHelpcardsUseCase provideGetAllHelpcardsUseCase(AppRepository repository) {
        return new GetAllHelpcardsUseCase(repository);
    }

    @Singleton
    @Provides
    public UpdateHelpcardByHelpcardIdUseCase provideUpdateHelpcardByHelpcardIdUseCase(AppRepository repository) {
        return new UpdateHelpcardByHelpcardIdUseCase(repository);
    }

    @Singleton
    @Provides
    public UpdateHelpcardFavoriteByHelpcardIdUseCase provideUpdateHelpcardFavoriteByHelpcardIdUseCase(AppRepository repository) {
        return new UpdateHelpcardFavoriteByHelpcardIdUseCase(repository);
    }

    @Singleton
    @Provides
    public UpdateHelpcardLockingByHelpcardIdUseCase provideUpdateHelpcardLockingByHelpcardIdUseCase(AppRepository repository) {
        return new UpdateHelpcardLockingByHelpcardIdUseCase(repository);
    }

    @Singleton
    @Provides
    public DeleteHelpcardsByUnlockStateUseCase provideDeleteHelpcardsByUnlockStateUseCase(AppRepository repository) {
        return new DeleteHelpcardsByUnlockStateUseCase(repository);
    }

    @Singleton
    @Provides
    public SaveHelpcardNewByHelpcardIdUseCase provideSaveHelpcardNewByHelpcardIdUseCase(AppRepository repository) {
        return new SaveHelpcardNewByHelpcardIdUseCase(repository);
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
