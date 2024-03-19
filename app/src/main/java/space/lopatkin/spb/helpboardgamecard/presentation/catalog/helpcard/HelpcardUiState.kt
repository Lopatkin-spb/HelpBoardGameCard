package space.lopatkin.spb.helpboardgamecard.presentation.catalog.helpcard

import space.lopatkin.spb.helpboardgamecard.domain.model.Helpcard
import space.lopatkin.spb.helpboardgamecard.domain.model.Message

data class HelpcardUiState(
    val isLoading: Boolean = false,
    val isNavigate: Boolean = false,
    val message: Message? = null,
    val boardgameId: Long? = null,
    val helpcard: Helpcard? = null
)
