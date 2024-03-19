package space.lopatkin.spb.helpboardgamecard.presentation.addcard

import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameRaw
import space.lopatkin.spb.helpboardgamecard.domain.model.Message

data class AddcardUiState(
    val isLoading: Boolean = false,
    val isSaveCompleted: Boolean = false,
    val message: Message? = null,
    val newBoardgame: BoardgameRaw? = null
)
