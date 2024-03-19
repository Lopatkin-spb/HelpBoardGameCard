package space.lopatkin.spb.helpboardgamecard.presentation.catalog

import space.lopatkin.spb.helpboardgamecard.domain.model.BoardgameInfo
import space.lopatkin.spb.helpboardgamecard.domain.model.Message

data class CatalogUiState(
    val isLoading: Boolean = false,
    val message: Message? = null,
    val list: List<BoardgameInfo> = emptyList()
)
