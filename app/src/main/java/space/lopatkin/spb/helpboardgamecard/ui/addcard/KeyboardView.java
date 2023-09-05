package space.lopatkin.spb.helpboardgamecard.ui.addcard;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import space.lopatkin.spb.helpboardgamecard.R;
import space.lopatkin.spb.helpboardgamecard.databinding.ViewKeyboardBinding;

public class KeyboardView extends ConstraintLayout implements View.OnClickListener {
    private static final int MOVE_CURSOR_TO_THE_END = 1;
    private static final int SPANNABLE_TEXT_LENGTH = KeyboardButtonIcon.getLength();
    private static final boolean UPPER_CASE = true;
    private static final boolean LOWER_CASE = false;
    private static boolean CAPS = UPPER_CASE;
    public static final int DYNAMIC_DRAWABLE_SPAN = DynamicDrawableSpan.ALIGN_BOTTOM;
    public static final int SPANNABLE_SPAN_EXCLUSIVE_EXCLUSIVE = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE;
    private static final String SEPARATOR = KeyboardButtonIcon.SEPARATOR;
    private static KeyboardPart ENABLED_KEYBOARD_PART = KeyboardPart.QWERTY;
    private static KeyboardType VISIBLE_KEYBOARD_TYPE = KeyboardType.QWERTY_AND_NUMBERS_AND_ICONS;

    private static String previousChar = "";
    private ViewKeyboardBinding binding;

    //this will the button resource id to the String value that we want to
    // input when that button is clicked
    private final SparseArray<String> keyBasicValue = new SparseArray<>();
    private Context context;

    //communication link to the EditText
    private InputConnection inputConnection;

    public KeyboardView(@NonNull @NotNull Context context) {
        super(context);
        init(context);
    }

    public KeyboardView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public KeyboardView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public void onClick(View view) {
        if (inputConnection == null) {
            return;
        }
        if (ENABLED_KEYBOARD_PART == KeyboardPart.ICON) {
            ImageSpan span = new ImageSpan(
                    context,
                    KeyboardButtonIcon.getDrawableFrom(view.getId()),
                    DYNAMIC_DRAWABLE_SPAN);
            Spannable icon = new SpannableString(SEPARATOR + KeyboardButtonIcon.getNameFrom(view.getId()));

            icon.setSpan(span, 0, icon.length(), SPANNABLE_SPAN_EXCLUSIVE_EXCLUSIVE);
            inputConnection.commitText(icon, MOVE_CURSOR_TO_THE_END);
        } else {
            String symbol = "";
            if (CAPS == UPPER_CASE) {
                symbol = KeyboardButtonSymbol.getStringFrom(view.getId()).toUpperCase();
            } else {
                symbol = KeyboardButtonSymbol.getStringFrom(view.getId()).toLowerCase();
            }
            capsToLowerCase(view);
            inputConnection.commitText(symbol, MOVE_CURSOR_TO_THE_END);
        }
    }

    //reference to the current EditText's InputConnection
    public void setInputConnection(InputConnection inputConnection) {
        this.inputConnection = inputConnection;
    }

    public void setEnabledKeyboardPart(KeyboardPart part) {
        ENABLED_KEYBOARD_PART = part;
        displayKeyboardPart();
    }

    public void setKeyboardType(KeyboardType type) {
        VISIBLE_KEYBOARD_TYPE = type;
        displayKeyboardType();
    }

    private void init(Context context) {
        this.context = context;
        binding = ViewKeyboardBinding.inflate(LayoutInflater.from(context), this, true);

        setupKeyboardIcon();
        setupKeyboardNumber();
        setupKeyboardQwerty();

        onActionBackspace();
        onActionBackspaceFull();
        onActionEnter();
        onActionShift();
        onActionSwitchQwertyNumber();
        onActionSwitchToIcons();
        onActionSpace();
        onActionDone();

        keyBasicValue.put(R.id.action_enter, "\n");
        keyBasicValue.put(R.id.action_space, " ");
    }

    private void setupKeyboardIcon() {
        //blue
        binding.keyboardIcon.actionChair.setOnClickListener(this);
        binding.keyboardIcon.actionBreakfast.setOnClickListener(this);
        binding.keyboardIcon.actionStop.setOnClickListener(this);
        binding.keyboardIcon.actionFastfood.setOnClickListener(this);
        binding.keyboardIcon.actionHammer.setOnClickListener(this);
        binding.keyboardIcon.actionHouse.setOnClickListener(this);
        binding.keyboardIcon.actionPawn.setOnClickListener(this);
        binding.keyboardIcon.actionSmartphone.setOnClickListener(this);
        binding.keyboardIcon.actionVolume.setOnClickListener(this);
        binding.keyboardIcon.actionScull.setOnClickListener(this);
        binding.keyboardIcon.actionTileDeck.setOnClickListener(this);
        binding.keyboardIcon.actionMeeple.setOnClickListener(this);
        binding.keyboardIcon.actionDice.setOnClickListener(this);
        binding.keyboardIcon.actionTable.setOnClickListener(this);
        binding.keyboardIcon.actionCardsDeck.setOnClickListener(this);
        binding.keyboardIcon.actionOut2.setOnClickListener(this);
        binding.keyboardIcon.actionRelax.setOnClickListener(this);
        binding.keyboardIcon.actionPlayerTablet.setOnClickListener(this);
        binding.keyboardIcon.actionCar.setOnClickListener(this);
        binding.keyboardIcon.actionBoat.setOnClickListener(this);
        binding.keyboardIcon.actionClock.setOnClickListener(this);
        binding.keyboardIcon.actionPerson.setOnClickListener(this);
        binding.keyboardIcon.actionPersons.setOnClickListener(this);
        binding.keyboardIcon.actionDoor.setOnClickListener(this);

//green
        binding.keyboardIcon.actionArrowDown.setOnClickListener(this);
        binding.keyboardIcon.actionOut.setOnClickListener(this);
        binding.keyboardIcon.actionDistance3.setOnClickListener(this);
        binding.keyboardIcon.actionSwap.setOnClickListener(this);
        binding.keyboardIcon.actionSwap2.setOnClickListener(this);
        binding.keyboardIcon.actionArrowRight.setOnClickListener(this);
        binding.keyboardIcon.actionMix.setOnClickListener(this);
        binding.keyboardIcon.actionRight.setOnClickListener(this);
        binding.keyboardIcon.actionDistance2.setOnClickListener(this);
        binding.keyboardIcon.actionMoveRight.setOnClickListener(this);
//red
        binding.keyboardIcon.actionWarning.setOnClickListener(this);
        binding.keyboardIcon.actionDangerous.setOnClickListener(this);
    }

    private void setupKeyboardNumber() {
        binding.keyboardNumber.actionSymbolOne.setOnClickListener(this);
        binding.keyboardNumber.actionSymbolTwo.setOnClickListener(this);
        binding.keyboardNumber.actionSymbolThree.setOnClickListener(this);
        binding.keyboardNumber.actionSymbolFour.setOnClickListener(this);
        binding.keyboardNumber.actionSymbolFive.setOnClickListener(this);
        binding.keyboardNumber.actionSymbolSeven.setOnClickListener(this);
        binding.keyboardNumber.actionSymbolSix.setOnClickListener(this);
        binding.keyboardNumber.actionSymbolEight.setOnClickListener(this);
        binding.keyboardNumber.actionSymbolNine.setOnClickListener(this);
        binding.keyboardNumber.actionSymbolZero.setOnClickListener(this);

        binding.keyboardNumber.actionSymbolPlus.setOnClickListener(this);
        binding.keyboardNumber.actionSymbolMinus.setOnClickListener(this);
        binding.keyboardNumber.actionSymbolMultiply.setOnClickListener(this);
        binding.keyboardNumber.actionSymbolDivide.setOnClickListener(this);
        binding.keyboardNumber.actionSymbolColon.setOnClickListener(this);
        binding.keyboardNumber.actionSymbolEqual.setOnClickListener(this);
        binding.keyboardNumber.actionSymbolParenthesesOpen.setOnClickListener(this);
        binding.keyboardNumber.actionSymbolParenthesesClose.setOnClickListener(this);
    }

    private void setupKeyboardQwerty() {
        binding.keyboardQwerty.actionQwerty1.setOnClickListener(this);
        binding.keyboardQwerty.actionQwerty2.setOnClickListener(this);
        binding.keyboardQwerty.actionQwerty3.setOnClickListener(this);
        binding.keyboardQwerty.actionQwerty4.setOnClickListener(this);
        binding.keyboardQwerty.actionQwerty5.setOnClickListener(this);
        binding.keyboardQwerty.actionQwerty6.setOnClickListener(this);
        binding.keyboardQwerty.actionQwerty7.setOnClickListener(this);
        binding.keyboardQwerty.actionQwerty8.setOnClickListener(this);
        binding.keyboardQwerty.actionQwerty9.setOnClickListener(this);
        binding.keyboardQwerty.actionQwerty10.setOnClickListener(this);
        binding.keyboardQwerty.actionQwerty11.setOnClickListener(this);
        binding.keyboardQwerty.actionQwerty12.setOnClickListener(this);
        binding.keyboardQwerty.actionQwerty13.setOnClickListener(this);
        binding.keyboardQwerty.actionQwerty14.setOnClickListener(this);
        binding.keyboardQwerty.actionQwerty15.setOnClickListener(this);
        binding.keyboardQwerty.actionQwerty16.setOnClickListener(this);
        binding.keyboardQwerty.actionQwerty17.setOnClickListener(this);
        binding.keyboardQwerty.actionQwerty18.setOnClickListener(this);
        binding.keyboardQwerty.actionQwerty19.setOnClickListener(this);
        binding.keyboardQwerty.actionQwerty20.setOnClickListener(this);
        binding.keyboardQwerty.actionQwerty21.setOnClickListener(this);
        binding.keyboardQwerty.actionQwerty22.setOnClickListener(this);
        binding.keyboardQwerty.actionQwerty23.setOnClickListener(this);
        binding.keyboardQwerty.actionQwerty24.setOnClickListener(this);
        binding.keyboardQwerty.actionQwerty25.setOnClickListener(this);
        binding.keyboardQwerty.actionQwerty26.setOnClickListener(this);
        binding.keyboardQwerty.actionQwerty27.setOnClickListener(this);
        binding.keyboardQwerty.actionQwerty28.setOnClickListener(this);
        binding.keyboardQwerty.actionQwerty29.setOnClickListener(this);
        binding.keyboardQwerty.actionQwerty30.setOnClickListener(this);
        binding.keyboardQwerty.actionQwerty31.setOnClickListener(this);
    }

    private void onActionBackspace() {
        binding.actionBackspace.setOnClickListener(view -> {
            if (inputConnection == null) {
                return;
            }
            CharSequence text = inputConnection.getExtractedText(new ExtractedTextRequest(), 0).text;

            if (isIcon(text)) {
                deleteIcon(text);
            } else {
                deleteSymbol(text);
            }
        });
        binding.keyboardQwerty.actionQwertyBackspace.setOnClickListener(view -> {
            if (inputConnection == null) {
                return;
            }
            CharSequence text = inputConnection.getExtractedText(new ExtractedTextRequest(), 0).text;

            deleteSymbol(text);
        });
    }

    private void onActionBackspaceFull() {
        binding.actionBackspaceFull.setOnClickListener(view -> {
            if (inputConnection == null) {
                return;
            }
            CharSequence currentText = inputConnection.getExtractedText(new ExtractedTextRequest(), 0).text;
            if (!TextUtils.isEmpty(currentText)) {

                CharSequence textBeforeCursor = inputConnection.getTextBeforeCursor(currentText.length(), 0);
                CharSequence textAfterCursor = inputConnection.getTextAfterCursor(currentText.length(), 0);

                inputConnection.deleteSurroundingText(textBeforeCursor.length(), textAfterCursor.length());
            }
        });
    }

    private void onActionEnter() {
        binding.actionEnter.setOnClickListener(view -> {
            if (inputConnection == null) {
                return;
            }
            inputConnection.commitText(keyBasicValue.get(view.getId()), MOVE_CURSOR_TO_THE_END);
        });
    }

    private void onActionShift() {
        binding.keyboardQwerty.actionQwertyShift.setOnClickListener(view -> {
            CAPS = !CAPS;
            changeCapsOnButtons();
        });
    }

    private void onActionSwitchQwertyNumber() {
        binding.actionSwitchQwertyNumber.setOnClickListener(view -> {
            if (inputConnection == null) {
                return;
            }
            if (ENABLED_KEYBOARD_PART == KeyboardPart.ICON && binding.actionSwitchQwertyNumber.getText().equals("абв")
                    || ENABLED_KEYBOARD_PART == KeyboardPart.NUMBER) {
                ENABLED_KEYBOARD_PART = KeyboardPart.QWERTY;
                displayKeyboardQwerty();
            } else {
                ENABLED_KEYBOARD_PART = KeyboardPart.NUMBER;
                displayKeyboardNumber();
            }
        });
    }

    private void onActionSwitchToIcons() {
        binding.actionSwitchToIcons.setOnClickListener(view -> {
            if (inputConnection == null) {
                return;
            }
            if (ENABLED_KEYBOARD_PART == KeyboardPart.QWERTY || ENABLED_KEYBOARD_PART == KeyboardPart.NUMBER) {
                ENABLED_KEYBOARD_PART = KeyboardPart.ICON;
                displayKeyboardIcon();
            }
        });
    }

    private void onActionSpace() {
        binding.actionSpace.setOnClickListener(view -> {
            if (inputConnection == null) {
                return;
            }
            CAPS = UPPER_CASE;
            changeCapsOnButtons();
            inputConnection.commitText(keyBasicValue.get(view.getId()), MOVE_CURSOR_TO_THE_END);
        });
    }

    private void onActionDone() {
        binding.actionDone.setOnClickListener(view -> {
            if (inputConnection == null) {
                return;
            }
            EventBus.getDefault().post(new KeyboardDoneEvent());
        });
    }

    private boolean isSeparator(CharSequence text) {
        StringBuffer buffer = new StringBuffer(text);
        int indexLastSeparator = buffer.lastIndexOf(SEPARATOR);
        return indexLastSeparator >= 0 ? true : false;
    }

    private boolean isIcon(CharSequence text) {
        if (TextUtils.isEmpty(text)) {
            return false;
        }
        StringBuffer buffer = new StringBuffer(text);
        int indexSeparator = 0;
        if (buffer.length() >= SPANNABLE_TEXT_LENGTH) {
            indexSeparator = buffer.length() - SPANNABLE_TEXT_LENGTH;
        }
        String symbol = String.valueOf(buffer.charAt(indexSeparator));
        return symbol.equalsIgnoreCase(SEPARATOR);
    }

    private void deleteIcon(CharSequence text) {
        if (isSeparator(text)) {
            StringBuffer buffer = new StringBuffer(text);
            buffer.reverse();
            int indexLastIconSeparator = buffer.indexOf(SEPARATOR);
            inputConnection.deleteSurroundingText(indexLastIconSeparator + 1, 0);
        } else {
            // Delete selection text
            inputConnection.commitText("", MOVE_CURSOR_TO_THE_END);
        }
    }

    private void deleteSymbol(CharSequence currentText) {
//                CharSequence selectedText = inputConnection.getSelectedText(0);
//                if (TextUtils.isEmpty(selectedText)) {

        if (!TextUtils.isEmpty(currentText)) {
            //no selection, so delete previous character
            inputConnection.deleteSurroundingText(1, 0);
            if (currentText.length() == 1) {
                CAPS = UPPER_CASE;
                changeCapsOnButtons();
            }
        } else {
            //delete selection
            inputConnection.commitText("", MOVE_CURSOR_TO_THE_END);
        }
    }

    private void changeCapsOnButtons() {
        binding.keyboardQwerty.actionQwerty1.setAllCaps(CAPS);
        binding.keyboardQwerty.actionQwerty2.setAllCaps(CAPS);
        binding.keyboardQwerty.actionQwerty3.setAllCaps(CAPS);
        binding.keyboardQwerty.actionQwerty4.setAllCaps(CAPS);
        binding.keyboardQwerty.actionQwerty5.setAllCaps(CAPS);
        binding.keyboardQwerty.actionQwerty6.setAllCaps(CAPS);
        binding.keyboardQwerty.actionQwerty7.setAllCaps(CAPS);
        binding.keyboardQwerty.actionQwerty8.setAllCaps(CAPS);
        binding.keyboardQwerty.actionQwerty9.setAllCaps(CAPS);
        binding.keyboardQwerty.actionQwerty10.setAllCaps(CAPS);
        binding.keyboardQwerty.actionQwerty11.setAllCaps(CAPS);
        binding.keyboardQwerty.actionQwerty12.setAllCaps(CAPS);
        binding.keyboardQwerty.actionQwerty13.setAllCaps(CAPS);
        binding.keyboardQwerty.actionQwerty14.setAllCaps(CAPS);
        binding.keyboardQwerty.actionQwerty15.setAllCaps(CAPS);
        binding.keyboardQwerty.actionQwerty16.setAllCaps(CAPS);
        binding.keyboardQwerty.actionQwerty17.setAllCaps(CAPS);
        binding.keyboardQwerty.actionQwerty18.setAllCaps(CAPS);
        binding.keyboardQwerty.actionQwerty19.setAllCaps(CAPS);
        binding.keyboardQwerty.actionQwerty20.setAllCaps(CAPS);
        binding.keyboardQwerty.actionQwerty21.setAllCaps(CAPS);
        binding.keyboardQwerty.actionQwerty22.setAllCaps(CAPS);
        binding.keyboardQwerty.actionQwerty23.setAllCaps(CAPS);
        binding.keyboardQwerty.actionQwerty24.setAllCaps(CAPS);
        binding.keyboardQwerty.actionQwerty25.setAllCaps(CAPS);
        binding.keyboardQwerty.actionQwerty26.setAllCaps(CAPS);
        binding.keyboardQwerty.actionQwerty27.setAllCaps(CAPS);
        binding.keyboardQwerty.actionQwerty28.setAllCaps(CAPS);
        binding.keyboardQwerty.actionQwerty29.setAllCaps(CAPS);
        binding.keyboardQwerty.actionQwerty30.setAllCaps(CAPS);
        binding.keyboardQwerty.actionQwerty31.setAllCaps(CAPS);

        if (CAPS == UPPER_CASE) {
            binding.keyboardQwerty.actionQwertyShift.setImageResource(R.drawable.ic_keyboard_baseline_upload_34);
        } else {
            binding.keyboardQwerty.actionQwertyShift.setImageResource(R.drawable.ic_keyboard_outline_upload_34);
        }
    }

    private void displayKeyboardPart() {
        if (ENABLED_KEYBOARD_PART == KeyboardPart.QWERTY) {
            displayKeyboardQwerty();
            CAPS = UPPER_CASE;
            changeCapsOnButtons();
        } else if (ENABLED_KEYBOARD_PART == KeyboardPart.ICON) {
            displayKeyboardIcon();
            binding.actionSwitchQwertyNumber.setText("абв");
            CAPS = UPPER_CASE;
            changeCapsOnButtons();
        } else {
            displayKeyboardNumber();
        }
    }

    private void displayKeyboardType() {
        if (VISIBLE_KEYBOARD_TYPE == KeyboardType.QWERTY_AND_NUMBERS) {
            displayKeyboardWithSymbols();
            CAPS = UPPER_CASE;
            changeCapsOnButtons();
        } else {
            displayKeyboardsWithIconsAndSymbols();
            binding.actionSwitchQwertyNumber.setText("абв");
            CAPS = UPPER_CASE;
            changeCapsOnButtons();
        }
    }

    private void displayKeyboardQwerty() {
        binding.keyboardNumber.containerKeyboardNumber.setVisibility(View.GONE);
        binding.keyboardIcon.containerKeyboardIcon.setVisibility(View.GONE);
        binding.keyboardQwerty.containerKeyboardQwerty.setVisibility(View.VISIBLE);

        binding.actionBackspace.setVisibility(View.GONE);
        binding.actionBackspaceFull.setVisibility(View.GONE);
        binding.actionEnter.setVisibility(View.GONE);
        binding.actionSwitchQwertyNumber.setText("123");
    }

    private void displayKeyboardWithSymbols() {
        displayKeyboardQwerty();

        binding.actionSwitchToIcons.setVisibility(View.GONE);
    }

    private void displayKeyboardIcon() {
        binding.keyboardNumber.containerKeyboardNumber.setVisibility(View.GONE);
        binding.keyboardQwerty.containerKeyboardQwerty.setVisibility(View.GONE);
        binding.keyboardIcon.containerKeyboardIcon.setVisibility(View.VISIBLE);

        binding.actionBackspace.setVisibility(View.VISIBLE);
        binding.actionBackspaceFull.setVisibility(View.VISIBLE);
        binding.actionEnter.setVisibility(View.VISIBLE);
    }

    private void displayKeyboardsWithIconsAndSymbols() {
        displayKeyboardIcon();

        binding.actionSwitchToIcons.setVisibility(View.VISIBLE);
    }

    private void displayKeyboardNumber() {
        binding.keyboardQwerty.containerKeyboardQwerty.setVisibility(View.GONE);
        binding.keyboardIcon.containerKeyboardIcon.setVisibility(View.GONE);
        binding.keyboardNumber.containerKeyboardNumber.setVisibility(View.VISIBLE);

        binding.actionBackspace.setVisibility(View.VISIBLE);
        binding.actionBackspaceFull.setVisibility(View.VISIBLE);
        binding.actionEnter.setVisibility(View.VISIBLE);
        binding.actionSwitchQwertyNumber.setText("абв");
    }

    private void capsToLowerCase(View view) {
        String currentChar = KeyboardButtonSymbol.getStringFrom(view.getId());
        if (!previousChar.isEmpty()) {
            CAPS = LOWER_CASE;
            changeCapsOnButtons();
        }
        previousChar = currentChar;
    }

}
