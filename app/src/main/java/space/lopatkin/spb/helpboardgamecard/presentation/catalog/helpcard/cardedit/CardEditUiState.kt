package space.lopatkin.spb.helpboardgamecard.presentation.catalog.helpcard.cardedit

import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameRaw
import space.lopatkin.spb.helpboardgamecard.domain.model.Message

data class CardEditUiState(
    val isLoading: Boolean = false,
    val isUpdateStart: Boolean = false,
    val isUpdateCompleted: Boolean = false,
    val message: Message? = null,
    val boardgameId: Long? = null,
    val boardgameRaw: BoardgameRaw? = null
)
