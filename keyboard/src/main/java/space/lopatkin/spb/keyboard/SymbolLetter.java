package space.lopatkin.spb.keyboard;


public enum SymbolLetter {

    LETTER_1(R.id.action_1_letters, R.string.action_1_letters),
    LETTER_2(R.id.action_2_letters, R.string.action_2_letters),
    LETTER_3(R.id.action_3_letters, R.string.action_3_letters),
    LETTER_4(R.id.action_4_letters, R.string.action_4_letters),
    LETTER_5(R.id.action_5_letters, R.string.action_5_letters),
    LETTER_6(R.id.action_6_letters, R.string.action_6_letters),
    LETTER_7(R.id.action_7_letters, R.string.action_7_letters),
    LETTER_8(R.id.action_8_letters, R.string.action_8_letters),
    LETTER_9(R.id.action_9_letters, R.string.action_9_letters),
    LETTER_10(R.id.action_10_letters, R.string.action_10_letters),
    LETTER_11(R.id.action_11_letters, R.string.action_11_letters),
    LETTER_12(R.id.action_12_letters, R.string.action_12_letters),
    LETTER_13(R.id.action_13_letters, R.string.action_13_letters),
    LETTER_14(R.id.action_14_letters, R.string.action_14_letters),
    LETTER_15(R.id.action_15_letters, R.string.action_15_letters),
    LETTER_16(R.id.action_16_letters, R.string.action_16_letters),
    LETTER_17(R.id.action_17_letters, R.string.action_17_letters),
    LETTER_18(R.id.action_18_letters, R.string.action_18_letters),
    LETTER_19(R.id.action_19_letters, R.string.action_19_letters),
    LETTER_20(R.id.action_20_letters, R.string.action_20_letters),
    LETTER_21(R.id.action_21_letters, R.string.action_21_letters),
    LETTER_22(R.id.action_22_letters, R.string.action_22_letters),
    LETTER_23(R.id.action_23_letters, R.string.action_23_letters),
    LETTER_24(R.id.action_24_letters, R.string.action_24_letters),
    LETTER_25(R.id.action_25_letters, R.string.action_25_letters),
    LETTER_26(R.id.action_26_letters, R.string.action_26_letters),
    LETTER_27(R.id.action_27_letters, R.string.action_27_letters),
    LETTER_28(R.id.action_28_letters, R.string.action_28_letters),
    LETTER_29(R.id.action_29_letters, R.string.action_29_letters),
    LETTER_30(R.id.action_30_letters, R.string.action_30_letters),
    LETTER_31(R.id.action_31_letters, R.string.action_31_letters);

    private int action;
    private int stringResource;

    SymbolLetter(int action, int stringResource) {
        this.action = action;
        this.stringResource = stringResource;
    }

    public static int getStringResourceFrom(int action) {
        for (SymbolLetter value : values()) {
            if (value.action == action) {
                return value.stringResource;
            }
        }
        throw new IllegalArgumentException("Unknown letter action: " + action);
    }

}
