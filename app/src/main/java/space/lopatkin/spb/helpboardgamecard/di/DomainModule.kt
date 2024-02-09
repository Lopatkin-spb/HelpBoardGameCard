package space.lopatkin.spb.helpboardgamecard.di

import dagger.Module
import dagger.Provides
import space.lopatkin.spb.helpboardgamecard.domain.repository.BoardgameRepository
import space.lopatkin.spb.helpboardgamecard.domain.repository.SettingsRepository
import space.lopatkin.spb.helpboardgamecard.domain.usecase.*
import javax.inject.Singleton

@Module
class DomainModule {

    @Singleton
    @Provides
    fun provideGetHelpcardByBoardgameIdUseCase(repository: BoardgameRepository): GetHelpcardByBoardgameIdUseCase {
        return GetHelpcardByBoardgameIdUseCase(repository = repository)
    }

    @Singleton
    @Provides
    fun provideDeleteBoardgameUnlockedByBoardgameIdUseCase(repository: BoardgameRepository): DeleteBoardgameUnlockedByBoardgameIdUseCase {
        return DeleteBoardgameUnlockedByBoardgameIdUseCase(repository = repository)
    }

    @Singleton
    @Provides
    fun provideGetAllBoardgamesInfoUseCase(repository: BoardgameRepository): GetAllBoardgamesInfoUseCase {
        return GetAllBoardgamesInfoUseCase(repository = repository)
    }

    @Singleton
    @Provides
    fun provideUpdateBoardgameByBoardgameIdUseCase(repository: BoardgameRepository): UpdateBoardgameByBoardgameIdUseCase {
        return UpdateBoardgameByBoardgameIdUseCase(repository = repository)
    }

    @Singleton
    @Provides
    fun provideUpdateBoardgameFavoriteByBoardgameIdUseCase(repository: BoardgameRepository): UpdateBoardgameFavoriteByBoardgameIdUseCase {
        return UpdateBoardgameFavoriteByBoardgameIdUseCase(repository = repository)
    }

    @Singleton
    @Provides
    fun provideUpdateBoardgameLockingByBoardgameIdUseCase(repository: BoardgameRepository): UpdateBoardgameLockingByBoardgameIdUseCase {
        return UpdateBoardgameLockingByBoardgameIdUseCase(repository = repository)
    }

    @Singleton
    @Provides
    fun provideDeleteBoardgamesByUnlockStateUseCase(repository: BoardgameRepository): DeleteBoardgamesByUnlockStateUseCase {
        return DeleteBoardgamesByUnlockStateUseCase(repository = repository)
    }

    @Singleton
    @Provides
    fun provideSaveBoardgameNewByBoardgameIdUseCase(repository: BoardgameRepository): SaveBoardgameNewByBoardgameIdUseCase {
        return SaveBoardgameNewByBoardgameIdUseCase(repository = repository)
    }

    @Singleton
    @Provides
    fun provideSaveKeyboardTypeByUserChoiceUseCase(repository: SettingsRepository): SaveKeyboardTypeByUserChoiceUseCase {
        return SaveKeyboardTypeByUserChoiceUseCase(repository = repository)
    }

    @Singleton
    @Provides
    fun provideGetKeyboardTypeUseCase(repository: SettingsRepository): GetKeyboardTypeUseCase {
        return GetKeyboardTypeUseCase(repository = repository)
    }

    @Singleton
    @Provides
    fun provideGetBoardgameRawByBoardgameIdUseCase(repository: BoardgameRepository): GetBoardgameRawByBoardgameIdUseCase {
        return GetBoardgameRawByBoardgameIdUseCase(repository = repository)
    }

}
