package space.lopatkin.spb.helpboardgamecard.ui.addcard;

import space.lopatkin.spb.helpboardgamecard.R;

public enum KeyboardButtonSymbol {

    SYMBOL_1(R.id.action_symbol_one, "1"),
    SYMBOL_2(R.id.action_symbol_two, "2"),
    SYMBOL_3(R.id.action_symbol_three, "3"),
    SYMBOL_4(R.id.action_symbol_four, "4"),
    SYMBOL_5(R.id.action_symbol_five, "5"),
    SYMBOL_6(R.id.action_symbol_six, "6"),
    SYMBOL_7(R.id.action_symbol_seven, "7"),
    SYMBOL_8(R.id.action_symbol_eight, "8"),
    SYMBOL_9(R.id.action_symbol_nine, "9"),
    SYMBOL_0(R.id.action_symbol_zero, "0"),
    SYMBOL_PLUS(R.id.action_symbol_plus, "+"),
    SYMBOL_MINUS(R.id.action_symbol_minus, "-"),
    SYMBOL_MULTIPLY(R.id.action_symbol_multiply, "*"),
    SYMBOL_DIVIDE(R.id.action_symbol_divide, "/"),
    SYMBOL_COLON(R.id.action_symbol_colon, ":"),
    SYMBOL_EQUAL(R.id.action_symbol_equal, "="),
    SYMBOL_PARENTHESES_OPEN(R.id.action_symbol_parentheses_open, "("),
    SYMBOL_PARENTHESES_CLOSE(R.id.action_symbol_parentheses_close, ")"),
    QWERTY_1(R.id.action_qwerty_1, "й"),
    QWERTY_2(R.id.action_qwerty_2, "ц"),
    QWERTY_3(R.id.action_qwerty_3, "у"),
    QWERTY_4(R.id.action_qwerty_4, "к"),
    QWERTY_5(R.id.action_qwerty_5, "е"),
    QWERTY_6(R.id.action_qwerty_6, "н"),
    QWERTY_7(R.id.action_qwerty_7, "г"),
    QWERTY_8(R.id.action_qwerty_8, "ш"),
    QWERTY_9(R.id.action_qwerty_9, "щ"),
    QWERTY_10(R.id.action_qwerty_10, "з"),
    QWERTY_11(R.id.action_qwerty_11, "х"),
    QWERTY_12(R.id.action_qwerty_12, "ф"),
    QWERTY_13(R.id.action_qwerty_13, "ы"),
    QWERTY_14(R.id.action_qwerty_14, "в"),
    QWERTY_15(R.id.action_qwerty_15, "а"),
    QWERTY_16(R.id.action_qwerty_16, "п"),
    QWERTY_17(R.id.action_qwerty_17, "р"),
    QWERTY_18(R.id.action_qwerty_18, "о"),
    QWERTY_19(R.id.action_qwerty_19, "л"),
    QWERTY_20(R.id.action_qwerty_20, "д"),
    QWERTY_21(R.id.action_qwerty_21, "ж"),
    QWERTY_22(R.id.action_qwerty_22, "э"),
    QWERTY_23(R.id.action_qwerty_23, "я"),
    QWERTY_24(R.id.action_qwerty_24, "ч"),
    QWERTY_25(R.id.action_qwerty_25, "с"),
    QWERTY_26(R.id.action_qwerty_26, "м"),
    QWERTY_27(R.id.action_qwerty_27, "и"),
    QWERTY_28(R.id.action_qwerty_28, "т"),
    QWERTY_29(R.id.action_qwerty_29, "ь"),
    QWERTY_30(R.id.action_qwerty_30, "б"),
    QWERTY_31(R.id.action_qwerty_31, "ю");


    private int id;
    private String value;

    KeyboardButtonSymbol(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public static String getStringFrom(int resourceId) {
        for (KeyboardButtonSymbol button : values()) {
            if (button.id == resourceId) {
                return button.getValue();
            }
        }
        throw new IllegalArgumentException("Unknown KeyboardButton resourceId: " + resourceId);
    }

    public String getValue() {
        return value;
    }

}
