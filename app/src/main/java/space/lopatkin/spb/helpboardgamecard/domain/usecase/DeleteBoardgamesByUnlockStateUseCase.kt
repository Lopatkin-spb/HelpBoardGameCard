package space.lopatkin.spb.helpboardgamecard.domain.usecase

import space.lopatkin.spb.helpboardgamecard.domain.repository.AppRepository

class DeleteBoardgamesByUnlockStateUseCase(private val repository: AppRepository) {

    fun execute() {
        repository.deleteUnlockBoardgames()
    }

}
