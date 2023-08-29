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
    SYMBOL_PARENTHESES_CLOSE(R.id.action_symbol_parentheses_close, ")");

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
