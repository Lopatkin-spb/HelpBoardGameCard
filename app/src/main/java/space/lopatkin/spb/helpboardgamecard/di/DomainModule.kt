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
    fun provideGetHelpcardByBoardgameIdUseCase(repository: AppRepository): GetHelpcardByBoardgameIdUseCase {
        return GetHelpcardByBoardgameIdUseCase(repository = repository)
    }

    @Singleton
    @Provides
    fun provideDeleteBoardgameUnlockedByBoardgameIdUseCase(repository: AppRepository): DeleteBoardgameUnlockedByBoardgameIdUseCase {
        return DeleteBoardgameUnlockedByBoardgameIdUseCase(repository = repository)
    }

    @Singleton
    @Provides
    fun provideGetAllBoardgamesInfoUseCase(repository: AppRepository): GetAllBoardgamesInfoUseCase {
        return GetAllBoardgamesInfoUseCase(repository = repository)
    }

    @Singleton
    @Provides
    fun provideUpdateBoardgameByBoardgameIdUseCase(repository: AppRepository): UpdateBoardgameByBoardgameIdUseCase {
        return UpdateBoardgameByBoardgameIdUseCase(repository = repository)
    }

    @Singleton
    @Provides
    fun provideUpdateBoardgameFavoriteByBoardgameIdUseCase(repository: AppRepository): UpdateBoardgameFavoriteByBoardgameIdUseCase {
        return UpdateBoardgameFavoriteByBoardgameIdUseCase(repository = repository)
    }

    @Singleton
    @Provides
    fun provideUpdateBoardgameLockingByBoardgameIdUseCase(repository: AppRepository): UpdateBoardgameLockingByBoardgameIdUseCase {
        return UpdateBoardgameLockingByBoardgameIdUseCase(repository = repository)
    }

    @Singleton
    @Provides
    fun provideDeleteBoardgamesByUnlockStateUseCase(repository: AppRepository): DeleteBoardgamesByUnlockStateUseCase {
        return DeleteBoardgamesByUnlockStateUseCase(repository = repository)
    }

    @Singleton
    @Provides
    fun provideSaveBoardgameNewByBoardgameIdUseCase(repository: AppRepository): SaveBoardgameNewByBoardgameIdUseCase {
        return SaveBoardgameNewByBoardgameIdUseCase(repository = repository)
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

    @Singleton
    @Provides
    fun provideGetBoardgameRawByBoardgameIdUseCase(repository: AppRepository): GetBoardgameRawByBoardgameIdUseCase {
        return GetBoardgameRawByBoardgameIdUseCase(repository = repository)
    }

}
