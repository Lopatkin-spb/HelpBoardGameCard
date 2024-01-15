package space.lopatkin.spb.helpboardgamecard.domain.model;

public enum KeyboardType {
    CUSTOM, DEFAULT;

    public static KeyboardType getOrdinalFrom(String name) {
        for (KeyboardType type : values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return KeyboardType.CUSTOM;
    }

    public static KeyboardType getOrdinalFrom(int value) {
        for (KeyboardType type : values()) {
            if (type.ordinal() == value) {
                return type;
            }
        }
        return KeyboardType.CUSTOM;
    }

}
