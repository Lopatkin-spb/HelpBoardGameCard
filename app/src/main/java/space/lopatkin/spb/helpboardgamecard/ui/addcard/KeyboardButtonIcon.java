package space.lopatkin.spb.helpboardgamecard.ui.addcard;

import space.lopatkin.spb.helpboardgamecard.R;

public enum KeyboardButtonIcon {

    l0000(R.id.action_chair, R.drawable.ic_keyboard_baseline_chair_34, "chair"),
    l0001(R.id.action_breakfast, R.drawable.ic_keyboard_baseline_free_breakfast_34, "cap"),
    l0002(R.id.action_stop, R.drawable.ic_keyboard_baseline_front_hand_34, "stop"),
    l0003(R.id.action_fastfood, R.drawable.ic_keyboard_baseline_fastfood_34, "fastfood"),
    l0004(R.id.action_hammer, R.drawable.ic_keyboard_baseline_gavel_34, "hammer"),
    l0005(R.id.action_house, R.drawable.ic_keyboard_baseline_home_34, "house"),
    l0006(R.id.action_pawn, R.drawable.ic_keyboard_flaticon_strategy_34, "pawn"),
    l0007(R.id.action_smartphone, R.drawable.ic_keyboard_baseline_smartphone_34, "smartphone"),
    l0008(R.id.action_volume, R.drawable.ic_keyboard_baseline_volume_off_34, "volume"),
    l0009(R.id.action_scull, R.drawable.ic_keyboard_baseline_scull_34, "scull"),
    l0010(R.id.action_tile_deck, R.drawable.ic_keyboard_baseline_collections_34, "tile_deck"),
    l0011(R.id.action_meeple, R.drawable.ic_keyboard_flaticon_pawn_34, "meeple"),
    l0012(R.id.action_dice, R.drawable.ic_keyboard_flaticon_dice_34, "dice"),
    l0013(R.id.action_table, R.drawable.ic_keyboard_baseline_table_restaurant_34, "table"),
    l0014(R.id.action_cards_deck, R.drawable.ic_keyboard_flaticon_playing_cards_34, "cards_deck"),
    l0015(R.id.action_out_2, R.drawable.ic_keyboard_baseline_pan_tool_alt_34, "out"),
    l0016(R.id.action_relax, R.drawable.ic_keyboard_baseline_self_improvement_34, "relax"),
    l0017(R.id.action_player_tablet, R.drawable.ic_keyboard_baseline_fact_check_34, "player_tablet"),
    l0018(R.id.action_car, R.drawable.ic_keyboard_flaticon_car_34, "car"),
    l0019(R.id.action_boat, R.drawable.ic_keyboard_flaticon_sailing_boat_34, "boat"),
    l0020(R.id.action_clock, R.drawable.ic_keyboard_baseline_access_time_filled_34, "clock"),
    l0021(R.id.action_persons, R.drawable.ic_keyboard_baseline_people_alt_34, "persons"),
    l0022(R.id.action_person, R.drawable.ic_keyboard_baseline_person_34, "person"),
    l0023(R.id.action_door, R.drawable.ic_keyboard_baseline_sensor_door_34, "door"),
    l0024(R.id.action_arrow_down, R.drawable.ic_keyboard_baseline_redo_34, "down"),
    l0025(R.id.action_out, R.drawable.ic_keyboard_flaticon_log_out_34, "out"),
    l0026(R.id.action_distance_3, R.drawable.ic_keyboard_baseline_open_in_full_34, "distance"),
    l0027(R.id.action_swap, R.drawable.ic_keyboard_baseline_swap_horiz_34, "swap"),
    l0028(R.id.action_swap_2, R.drawable.ic_keyboard_baseline_sync_alt_34, "swap"),
    l0029(R.id.action_arrow_right, R.drawable.ic_keyboard_baseline_redo_2_34, "right"),
    l0030(R.id.action_mix, R.drawable.ic_keyboard_baseline_sync_34, "mix"),
    l0031(R.id.action_right, R.drawable.ic_keyboard_baseline_east_34, "right"),
    l0032(R.id.action_distance_2, R.drawable.ic_keyboard_baseline_settings_ethernet_34, "distance"),
    l0033(R.id.action_move_right, R.drawable.ic_keyboard_baseline_sync_alt_2_34, "right"),
    l0034(R.id.action_warning, R.drawable.ic_keyboard_baseline_error_34, "warning"),
    l0035(R.id.action_dangerous, R.drawable.ic_keyboard_baseline_dangerous_34, "dangerous");
    public static final String SEPARATOR = "#";
    private static final String length = SEPARATOR + getNameFrom(R.id.action_chair);

    private int id;
    private int drawable;
    private String nameFull;

    KeyboardButtonIcon(int id, int drawable, String nameFull) {
        this.id = id;
        this.drawable = drawable;
        this.nameFull = nameFull;
    }

    public static int getDrawableFrom(int resourceId) {
        for (KeyboardButtonIcon button : values()) {
            if (button.id == resourceId) {
                return button.drawable;
            }
        }
        throw new IllegalArgumentException("Unknown KeyboardButton resourceId: " + resourceId);
    }

    public static int getDrawableFrom(String name) {
        for (KeyboardButtonIcon button : values()) {
            if (button.name().equals(name)) {
                return button.drawable;
            }
        }
        throw new IllegalArgumentException("Unknown KeyboardButton name: " + name);
    }

    public static String getNameFrom(int resourceId) {
        for (KeyboardButtonIcon button : values()) {
            if (button.id == resourceId) {
                return button.name();
            }
        }
        throw new IllegalArgumentException("Unknown KeyboardButton resourceId: " + resourceId);
    }

    public static String getNameFullFrom(String name) {
        for (KeyboardButtonIcon button : values()) {
            if (button.name().equals(name)) {
                return button.nameFull;
            }
        }
        throw new IllegalArgumentException("Unknown KeyboardButton name: " + name);
    }

    public static int getLength() {
        return length.length();
    }

}
