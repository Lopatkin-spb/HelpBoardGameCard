package space.lopatkin.spb.helpboardgamecard.ui.utils.keyboard;

public enum KeyboardVariant {
    CUSTOM, DEFAULT;

    public static KeyboardVariant getOrdinalFrom(String name) {
        for (KeyboardVariant variant : values()) {
            if (variant.name().equalsIgnoreCase(name)) {
                return variant;
            }
        }
        throw new IllegalArgumentException("Unknown KeyboardVariant name: " + name);
    }

    public static KeyboardVariant getOrdinalTrueFrom(int value) {
        for (KeyboardVariant variant : values()) {
            if (variant.ordinal() == value) {
                return variant;
            }
        }
        return KeyboardVariant.CUSTOM;
//        throw new IllegalArgumentException("Unknown KeyboardVariant value: " + value);
    }

}
