package space.lopatkin.spb.keyboard;


public enum SymbolNumber {

    NUMBER_1(R.id.action_one_numbers, R.string.action_one_numbers),
    NUMBER_2(R.id.action_two_numbers, R.string.action_two_numbers),
    NUMBER_3(R.id.action_three_numbers, R.string.action_three_numbers),
    NUMBER_4(R.id.action_four_numbers, R.string.action_four_numbers),
    NUMBER_5(R.id.action_five_numbers, R.string.action_five_numbers),
    NUMBER_6(R.id.action_six_numbers, R.string.action_six_numbers),
    NUMBER_7(R.id.action_seven_numbers, R.string.action_seven_numbers),
    NUMBER_8(R.id.action_eight_numbers, R.string.action_eight_numbers),
    NUMBER_9(R.id.action_nine_numbers, R.string.action_nine_numbers),
    NUMBER_0(R.id.action_zero_numbers, R.string.action_zero_numbers),
    NUMBER_PLUS(R.id.action_plus_numbers, R.string.action_plus_numbers),
    NUMBER_MINUS(R.id.action_minus_numbers, R.string.action_minus_numbers),
    NUMBER_MULTIPLY(R.id.action_multiply_numbers, R.string.action_multiply_numbers),
    NUMBER_DIVIDE(R.id.action_divide_numbers, R.string.action_divide_numbers),
    NUMBER_COLON(R.id.action_colon_numbers, R.string.action_colon_numbers),
    NUMBER_EQUAL(R.id.action_equal_numbers, R.string.action_equal_numbers),
    NUMBER_PARENTHESES_OPEN(R.id.action_parentheses_open_numbers, R.string.action_parentheses_open_numbers),
    NUMBER_PARENTHESES_CLOSE(R.id.action_parentheses_close_numbers, R.string.action_parentheses_close_numbers);

    private int action;
    private int stringResource;

    SymbolNumber(int action, int stringResource) {
        this.action = action;
        this.stringResource = stringResource;
    }

    public static int getStringResourceFrom(int action) {
        for (SymbolNumber value : values()) {
            if (value.action == action) {
                return value.stringResource;
            }
        }
        throw new IllegalArgumentException("Unknown symbol number action: " + action);
    }

}
