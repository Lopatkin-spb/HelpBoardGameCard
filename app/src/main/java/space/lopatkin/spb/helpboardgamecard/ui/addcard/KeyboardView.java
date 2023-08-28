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
    private static final int SPANNABLE_TEXT_LENGTH = KeyboardButton.getLength();
    public static final int DYNAMIC_DRAWABLE_SPAN = DynamicDrawableSpan.ALIGN_BOTTOM;
    public static final String SEPARATOR = "#";
    private static KeyboardType VISIBLE_TYPE;
    private ViewKeyboardBinding binding;

    //this will the button resource id to the String value that we want to
    // input when that button is clicked
    private final SparseArray<String> keyBasicValue = new SparseArray<>();
    private final SparseArray<String> keySymbolValue = new SparseArray<>();
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
        if (VISIBLE_TYPE == KeyboardType.ICON) {
            ImageSpan span = new ImageSpan(
                    context,
                    KeyboardButton.getDrawableFrom(view.getId()),
                    DYNAMIC_DRAWABLE_SPAN);
            Spannable icon = new SpannableString(SEPARATOR + KeyboardButton.getNameFrom(view.getId()));

            //todo: разобраться с вариантами SPAN_EXCLUSIVE_EXCLUSIVE
            icon.setSpan(span, 0, icon.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            inputConnection.commitText(icon, MOVE_CURSOR_TO_THE_END);
        } else {
            String symbol = keySymbolValue.get(view.getId());
            inputConnection.commitText(symbol, MOVE_CURSOR_TO_THE_END);
        }
    }

    //reference to the current EditText's InputConnection
    public void setInputConnection(InputConnection inputConnection) {
        this.inputConnection = inputConnection;
    }

    private void init(Context context) {
        this.context = context;
        binding = ViewKeyboardBinding.inflate(LayoutInflater.from(context), this, true);
        VISIBLE_TYPE = KeyboardType.ICON;

        setupKeyboardIcon();
        setupKeyboardNumber();

        onActionBackspace();
        onActionEnter();
        onActionSwitch();
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

        keySymbolValue.put(R.id.action_symbol_one, "1");
        keySymbolValue.put(R.id.action_symbol_two, "2");
        keySymbolValue.put(R.id.action_symbol_three, "3");
        keySymbolValue.put(R.id.action_symbol_four, "4");
        keySymbolValue.put(R.id.action_symbol_five, "5");
        keySymbolValue.put(R.id.action_symbol_six, "6");
        keySymbolValue.put(R.id.action_symbol_seven, "7");
        keySymbolValue.put(R.id.action_symbol_eight, "8");
        keySymbolValue.put(R.id.action_symbol_nine, "9");
        keySymbolValue.put(R.id.action_symbol_zero, "0");

        keySymbolValue.put(R.id.action_symbol_plus, "+");
        keySymbolValue.put(R.id.action_symbol_minus, "-");
        keySymbolValue.put(R.id.action_symbol_multiply, "*");
        keySymbolValue.put(R.id.action_symbol_divide, "/");
        keySymbolValue.put(R.id.action_symbol_colon, ":");
        keySymbolValue.put(R.id.action_symbol_equal, "=");
        keySymbolValue.put(R.id.action_symbol_parentheses_open, "(");
        keySymbolValue.put(R.id.action_symbol_parentheses_close, ")");
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
    }

    private void onActionEnter() {
        binding.actionEnter.setOnClickListener(view -> {
            if (inputConnection == null) {
                return;
            }
            inputConnection.commitText(keyBasicValue.get(view.getId()), MOVE_CURSOR_TO_THE_END);
        });
    }

    private void onActionSwitch() {
        binding.actionSwitch.setOnClickListener(view -> {
            if (inputConnection == null) {
                return;
            }
            if (VISIBLE_TYPE == KeyboardType.ICON) {
                binding.keyboardIcon.containerKeyboardIcon.setVisibility(View.GONE);
                binding.keyboardNumber.containerKeyboardNumber.setVisibility(View.VISIBLE);
                VISIBLE_TYPE = KeyboardType.NUMBER;
                binding.actionSwitch.setText("icons");
            } else {
                binding.keyboardNumber.containerKeyboardNumber.setVisibility(View.GONE);
                binding.keyboardIcon.containerKeyboardIcon.setVisibility(View.VISIBLE);
                VISIBLE_TYPE = KeyboardType.ICON;
                binding.actionSwitch.setText("123");
            }
        });
    }

    private void onActionSpace() {
        binding.actionSpace.setOnClickListener(view -> {
            if (inputConnection == null) {
                return;
            }
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
        } else {
            //delete selection
            inputConnection.commitText("", MOVE_CURSOR_TO_THE_END);
        }
    }

}
