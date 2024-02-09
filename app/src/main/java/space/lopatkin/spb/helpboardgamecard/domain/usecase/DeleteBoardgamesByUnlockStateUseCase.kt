package space.lopatkin.spb.helpboardgamecard.domain.usecase

import space.lopatkin.spb.helpboardgamecard.domain.repository.BoardgameRepository

class DeleteBoardgamesByUnlockStateUseCase(private val repository: BoardgameRepository) {

    fun execute() {
        repository.deleteUnlockBoardgames()
    }

}
