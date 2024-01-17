package space.lopatkin.spb.keyboard;


public enum SymbolSpecial {

    SPECIAL_1(R.id.action_enter_keyboard, R.string.action_enter_specials),
    SPECIAL_2(R.id.action_space_keyboard, R.string.action_space_specials);

    private int action;
    private int stringResource;

    SymbolSpecial(int action, int stringResource) {
        this.action = action;
        this.stringResource = stringResource;
    }

    public static int getStringResourceFrom(int action) {
        for (SymbolSpecial value : values()) {
            if (value.action == action) {
                return value.stringResource;
            }
        }
        throw new IllegalArgumentException("Unknown symbol special action: " + action);
    }

}
