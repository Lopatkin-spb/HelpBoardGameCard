package space.lopatkin.spb.helpboardgamecard.di

import dagger.Module
import dagger.Provides
import space.lopatkin.spb.helpboardgamecard.domain.repository.AppRepository
import space.lopatkin.spb.helpboardgamecard.domain.usecase.*
import javax.inject.Singleton

@Module
class DomainModule {

    @Singleton
    @Provides
    fun provideGetHelpcardByHelpcardIdUseCase(repository: AppRepository): GetHelpcardByHelpcardIdUseCase {
        return GetHelpcardByHelpcardIdUseCase(repository = repository)
    }

    @Singleton
    @Provides
    fun provideDeleteHelpcardUseCase(repository: AppRepository): DeleteHelpcardUseCase {
        return DeleteHelpcardUseCase(repository = repository)
    }

    @Singleton
    @Provides
    fun provideDeleteHelpcardUnlockedByHelpcardIdUseCase(repository: AppRepository): DeleteHelpcardUnlockedByHelpcardIdUseCase {
        return DeleteHelpcardUnlockedByHelpcardIdUseCase(repository = repository)
    }

    @Singleton
    @Provides
    fun provideGetAllHelpcardsUseCase(repository: AppRepository): GetAllHelpcardsUseCase {
        return GetAllHelpcardsUseCase(repository = repository)
    }

    @Singleton
    @Provides
    fun provideUpdateHelpcardByHelpcardIdUseCase(repository: AppRepository): UpdateHelpcardByHelpcardIdUseCase {
        return UpdateHelpcardByHelpcardIdUseCase(repository = repository)
    }

    @Singleton
    @Provides
    fun provideUpdateHelpcardFavoriteByHelpcardIdUseCase(repository: AppRepository): UpdateHelpcardFavoriteByHelpcardIdUseCase {
        return UpdateHelpcardFavoriteByHelpcardIdUseCase(repository = repository)
    }

    @Singleton
    @Provides
    fun provideUpdateHelpcardLockingByHelpcardIdUseCase(repository: AppRepository): UpdateHelpcardLockingByHelpcardIdUseCase {
        return UpdateHelpcardLockingByHelpcardIdUseCase(repository = repository)
    }

    @Singleton
    @Provides
    fun provideDeleteHelpcardsByUnlockStateUseCase(repository: AppRepository): DeleteHelpcardsByUnlockStateUseCase {
        return DeleteHelpcardsByUnlockStateUseCase(repository = repository)
    }

    @Singleton
    @Provides
    fun provideSaveHelpcardNewByHelpcardIdUseCase(repository: AppRepository): SaveHelpcardNewByHelpcardIdUseCase {
        return SaveHelpcardNewByHelpcardIdUseCase(repository = repository)
    }

    @Singleton
    @Provides
    fun provideSaveKeyboardTypeByUserChoiceUseCase(repository: AppRepository): SaveKeyboardTypeByUserChoiceUseCase {
        return SaveKeyboardTypeByUserChoiceUseCase(repository = repository)
    }

    @Singleton
    @Provides
    fun provideGetKeyboardTypeUseCase(repository: AppRepository): GetKeyboardTypeUseCase {
        return GetKeyboardTypeUseCase(repository = repository)
    }

}
