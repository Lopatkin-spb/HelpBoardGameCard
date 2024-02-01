package space.lopatkin.spb.helpboardgamecard.domain.model

enum class KeyboardType {
    CUSTOM,
    DEFAULT;

    companion object {

        fun getOrdinalFrom(name: String): KeyboardType {
            for (type in values()) {
                if (type.name.equals(other = name, ignoreCase = true)) {
                    return type
                }
            }
            return CUSTOM
        }

        fun getOrdinalFrom(value: Int): KeyboardType {
            for (type in values()) {
                if (type.ordinal == value) {
                    return type
                }
            }
            return CUSTOM
        }
    }

}
