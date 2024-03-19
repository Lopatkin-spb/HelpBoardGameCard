package space.lopatkin.spb.helpboardgamecard.presentation.settings

import space.lopatkin.spb.helpboardgamecard.domain.model.KeyboardType
import space.lopatkin.spb.helpboardgamecard.domain.model.Message

data class SettingsUiState(
    val isLoading: Boolean = false,
    val message: Message? = null,
    val keyboard: KeyboardType? = null
)
