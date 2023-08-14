package space.lopatkin.spb.helpboardgamecard.ui.addcard;

import space.lopatkin.spb.helpboardgamecard.R;

public enum KeyboardButton {

    SLIDESHOW(R.id.action_icon, R.drawable.ic_menu_slideshow),
    STYLE(R.id.action_style, R.drawable.ic_keyboard_round_style_44),
    STOREFRONT(R.id.action_store, R.drawable.ic_keyboard_round_storefront_24),
    CAR(R.id.action_car, R.drawable.ic_keyboard_round_directions_car_34);

    private int id;
    private int drawable;

    KeyboardButton(int id, int drawable) {
        this.id = id;
        this.drawable = drawable;
    }

    public static int getDrawableFrom(int resourceId) {
        for (KeyboardButton button : values()) {
            if (button.id == resourceId) {
                return button.drawable;
            }
        }
        throw new IllegalArgumentException("Unknown KeyboardButton resourceId: " + resourceId);
    }

    public static int getDrawableFrom(String name) {
        for (KeyboardButton button : values()) {
            if (button.name().equals(name)) {
                return button.drawable;
            }
        }
        throw new IllegalArgumentException("Unknown KeyboardButton name: " + name);
    }

    public static String getNameFrom(int resourceId) {
        for (KeyboardButton button : values()) {
            if (button.id == resourceId) {
                return button.name();
            }
        }
        throw new IllegalArgumentException("Unknown KeyboardButton resourceId: " + resourceId);
    }

}
