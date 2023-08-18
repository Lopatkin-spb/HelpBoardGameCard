package space.lopatkin.spb.helpboardgamecard.ui.addcard;

import space.lopatkin.spb.helpboardgamecard.R;

public enum KeyboardButton {

    l1000(R.id.action_chair, R.drawable.ic_keyboard_baseline_chair_34),
    l1001(R.id.action_breakfast, R.drawable.ic_keyboard_baseline_free_breakfast_34),
    l1002(R.id.action_stop, R.drawable.ic_keyboard_baseline_pan_tool_34),
    l1003(R.id.action_smartphone, R.drawable.ic_keyboard_baseline_smartphone_34),
    l1004(R.id.action_volume, R.drawable.ic_keyboard_baseline_volume_off_34),
    l1005(R.id.action_right, R.drawable.ic_keyboard_baseline_east_34),
    l1006(R.id.action_down, R.drawable.ic_keyboard_baseline_reply_34),
    l1007(R.id.action_distance, R.drawable.ic_keyboard_baseline_6_ft_apart_34);

    private static final String length = KeyboardView.SEPARATOR + l1000.name();

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

    public static int getLength() {
        return length.length();
    }

}
