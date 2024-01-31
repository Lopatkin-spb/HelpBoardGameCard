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
        return GetHelpcardByHelpcardIdUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideDeleteHelpcardUseCase(repository: AppRepository): DeleteHelpcardUseCase {
        return DeleteHelpcardUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideDeleteHelpcardUnlockedByHelpcardIdUseCase(repository: AppRepository): DeleteHelpcardUnlockedByHelpcardIdUseCase {
        return DeleteHelpcardUnlockedByHelpcardIdUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetAllHelpcardsUseCase(repository: AppRepository): GetAllHelpcardsUseCase {
        return GetAllHelpcardsUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideUpdateHelpcardByHelpcardIdUseCase(repository: AppRepository): UpdateHelpcardByHelpcardIdUseCase {
        return UpdateHelpcardByHelpcardIdUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideUpdateHelpcardFavoriteByHelpcardIdUseCase(repository: AppRepository): UpdateHelpcardFavoriteByHelpcardIdUseCase {
        return UpdateHelpcardFavoriteByHelpcardIdUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideUpdateHelpcardLockingByHelpcardIdUseCase(repository: AppRepository): UpdateHelpcardLockingByHelpcardIdUseCase {
        return UpdateHelpcardLockingByHelpcardIdUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideDeleteHelpcardsByUnlockStateUseCase(repository: AppRepository): DeleteHelpcardsByUnlockStateUseCase {
        return DeleteHelpcardsByUnlockStateUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideSaveHelpcardNewByHelpcardIdUseCase(repository: AppRepository): SaveHelpcardNewByHelpcardIdUseCase {
        return SaveHelpcardNewByHelpcardIdUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideSaveKeyboardTypeByUserChoiceUseCase(repository: AppRepository): SaveKeyboardTypeByUserChoiceUseCase {
        return SaveKeyboardTypeByUserChoiceUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetKeyboardTypeUseCase(repository: AppRepository): GetKeyboardTypeUseCase {
        return GetKeyboardTypeUseCase(repository)
    }

}
