package space.lopatkin.spb.helpboardgamecard.ui.addcard;

import space.lopatkin.spb.helpboardgamecard.R;

public enum KeyboardButton {

    l1000(R.id.action_chair, R.drawable.ic_keyboard_baseline_chair_34),
    l1001(R.id.action_breakfast, R.drawable.ic_keyboard_baseline_free_breakfast_34),
    l1002(R.id.action_stop, R.drawable.ic_keyboard_baseline_front_hand_34),
    l1003(R.id.action_fastfood, R.drawable.ic_keyboard_baseline_fastfood_34),
    l1004(R.id.action_hammer, R.drawable.ic_keyboard_baseline_gavel_34),
    l1005(R.id.action_house, R.drawable.ic_keyboard_baseline_home_34),
    l1006(R.id.action_pawn, R.drawable.ic_keyboard_flaticon_strategy_34),
    l1007(R.id.action_smartphone, R.drawable.ic_keyboard_baseline_smartphone_34),
    l1008(R.id.action_volume, R.drawable.ic_keyboard_baseline_volume_off_34),
    l1009(R.id.action_scull, R.drawable.ic_keyboard_baseline_scull_34),
    l1010(R.id.action_tile_deck, R.drawable.ic_keyboard_baseline_collections_34),
    l1011(R.id.action_meeple, R.drawable.ic_keyboard_flaticon_pawn_34),
    l1012(R.id.action_dice, R.drawable.ic_keyboard_flaticon_dice_34),
    l1013(R.id.action_table, R.drawable.ic_keyboard_baseline_table_restaurant_34),
    l1014(R.id.action_cards_deck, R.drawable.ic_keyboard_flaticon_playing_cards_34),
    l1015(R.id.action_out_2, R.drawable.ic_keyboard_baseline_pan_tool_alt_34),
    l1016(R.id.action_relax, R.drawable.ic_keyboard_baseline_self_improvement_34),
    l1017(R.id.action_player_tablet, R.drawable.ic_keyboard_baseline_fact_check_34),
    l1018(R.id.action_car, R.drawable.ic_keyboard_flaticon_car_34),
    l1019(R.id.action_boat, R.drawable.ic_keyboard_flaticon_sailing_boat_34),
    l1020(R.id.action_clock, R.drawable.ic_keyboard_baseline_access_time_filled_34),
    l1021(R.id.action_persons, R.drawable.ic_keyboard_baseline_people_alt_34),
    l1022(R.id.action_person, R.drawable.ic_keyboard_baseline_person_34),
    l1023(R.id.action_door, R.drawable.ic_keyboard_baseline_sensor_door_34),
    l1024(R.id.action_arrow_down, R.drawable.ic_keyboard_baseline_redo_34),
    l1025(R.id.action_out, R.drawable.ic_keyboard_flaticon_log_out_34),
    l1026(R.id.action_distance_3, R.drawable.ic_keyboard_baseline_open_in_full_34),
    l1027(R.id.action_swap, R.drawable.ic_keyboard_baseline_swap_horiz_34),
    l1028(R.id.action_swap_2, R.drawable.ic_keyboard_baseline_sync_alt_34),
    l1029(R.id.action_arrow_right, R.drawable.ic_keyboard_baseline_redo_2_34),
    l1030(R.id.action_mix, R.drawable.ic_keyboard_baseline_sync_34),
    l1031(R.id.action_right, R.drawable.ic_keyboard_baseline_east_34),
    l1032(R.id.action_distance_2, R.drawable.ic_keyboard_baseline_settings_ethernet_34),
    l1033(R.id.action_move_right, R.drawable.ic_keyboard_baseline_sync_alt_2_34),
    l1034(R.id.action_warning, R.drawable.ic_keyboard_baseline_error_34),
    l1035(R.id.action_dangerous, R.drawable.ic_keyboard_baseline_dangerous_34);

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
