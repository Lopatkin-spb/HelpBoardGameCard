package space.lopatkin.spb.keyboard;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Insets;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowMetrics;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import android.widget.ScrollView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import org.jetbrains.annotations.NotNull;
import space.lopatkin.spb.keyboard.databinding.ViewKeyboardBinding;

public class KeyboardView extends ConstraintLayout implements View.OnClickListener {
    private static final int MOVE_CURSOR_TO_THE_END = 1;
    private static final int SPANNABLE_TEXT_LENGTH = KeyboardButtonIcon.getLength();
    private static final int DEFAULT_VALUE = 0;
    private static final boolean UPPER_CASE = true;
    private static final boolean LOWER_CASE = false;
    private static boolean CAPS = UPPER_CASE;
    public static final int DYNAMIC_DRAWABLE_SPAN = DynamicDrawableSpan.ALIGN_BOTTOM;
    public static final int SPANNABLE_SPAN_EXCLUSIVE_EXCLUSIVE = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE;
    private static final String SEPARATOR = KeyboardButtonIcon.SEPARATOR;
    private int keyboardHeight;
    private int currentLinesCount = 0;
    private int heightFragment;
    private ScrollView scrollView;
    private EditText currentEnableEditTextView;
    private static KeyboardLayout ENABLED_LAYOUT = KeyboardLayout.LETTERS;
    private static KeyboardCapabilities ENABLED_CAPABILITIES = KeyboardCapabilities.LETTERS_AND_NUMBERS_AND_ICONS;
    private static String previousChar = "";
    private ViewKeyboardBinding binding;

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
    public void setVisibility(int visibility) {
        if (visibility == VISIBLE) {
            this.createKeyboard();
        }

        super.setVisibility(visibility);
    }

    @Override
    public void onClick(View view) {
        if (inputConnection == null) {
            return;
        }
        if (ENABLED_LAYOUT == KeyboardLayout.ICONS) {
            ImageSpan span = new ImageSpan(
                    context,
                    KeyboardButtonIcon.getDrawableFrom(view.getId()),
                    DYNAMIC_DRAWABLE_SPAN);
            Spannable icon = new SpannableString(SEPARATOR + KeyboardButtonIcon.getNameFrom(view.getId()));

            icon.setSpan(span, 0, icon.length(), SPANNABLE_SPAN_EXCLUSIVE_EXCLUSIVE);
            inputConnection.commitText(icon, MOVE_CURSOR_TO_THE_END);

        } else if (ENABLED_LAYOUT == KeyboardLayout.NUMBERS) {
            String symbol = KeyboardButtonSymbol.getStringFrom(view.getId());
            inputConnection.commitText(symbol, MOVE_CURSOR_TO_THE_END);

        } else {
            String symbol = "";
            if (CAPS == UPPER_CASE) {
                symbol = getResources().getString(SymbolLetter.getStringResourceFrom(view.getId())).toUpperCase();
                CAPS = LOWER_CASE;
                changeCapsOnButtons();
            } else {
                symbol = getResources().getString(SymbolLetter.getStringResourceFrom(view.getId())).toLowerCase();
            }
            inputConnection.commitText(symbol, MOVE_CURSOR_TO_THE_END);
        }
        scrollEditTextToKeyboard();
    }

    private void createKeyboard() {
        if (ENABLED_CAPABILITIES == KeyboardCapabilities.LETTERS_AND_NUMBERS) {
            ENABLED_LAYOUT = KeyboardLayout.LETTERS;
            displayLetterLayout();
        } else if (ENABLED_CAPABILITIES == KeyboardCapabilities.LETTERS_AND_NUMBERS_AND_ICONS){
            ENABLED_LAYOUT = KeyboardLayout.ICONS;
            displayIconLayout();
        }
        CAPS = UPPER_CASE;
        changeCapsOnButtons();
    }

    //reference to the current EditText's InputConnection
    public void setInputConnection(InputConnection inputConnection) {
        this.inputConnection = inputConnection;
        currentLinesCount = 0;
    }

    public void setCapabilities(KeyboardCapabilities capabilities) {
        ENABLED_CAPABILITIES = capabilities;
    }

    private void init(Context context) {
        this.context = context;
        binding = ViewKeyboardBinding.inflate(LayoutInflater.from(context), this, true);

        setListenerToIconButtons();
        setListenerToNumberButtons();
        setListenerToLetterButtons();

        onActionBackspace();
        onActionBackspaceFull();
        onActionEnter();
        onActionShift();
        onActionSwitchQwertyNumber();
        onActionSwitchToIcons();
        onActionSpace();
        onActionDone();
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
        binding.keyboardQwerty.actionBackspaceLetters.setOnClickListener(view -> {
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
            String symbol = getResources().getString(SymbolSpecial.getStringResourceFrom(view.getId()));
            inputConnection.commitText(symbol, MOVE_CURSOR_TO_THE_END);

            scrollEditTextToKeyboard();
        });
    }

    private void onActionShift() {
        binding.keyboardQwerty.actionShiftLetters.setOnClickListener(view -> {
            CAPS = !CAPS;
            changeCapsOnButtons();
        });
    }

    private void onActionSwitchQwertyNumber() {
        binding.actionSwitchQwertyNumber.setOnClickListener(view -> {
            if (inputConnection == null) {
                return;
            }
            if (ENABLED_LAYOUT == KeyboardLayout.ICONS && binding.actionSwitchQwertyNumber.getText().equals("абв")
                    || ENABLED_LAYOUT == KeyboardLayout.NUMBERS) {
                ENABLED_LAYOUT = KeyboardLayout.LETTERS;
                displayLetterLayout();
            } else {
                ENABLED_LAYOUT = KeyboardLayout.NUMBERS;
                displayNumberLayout();
            }
        });
    }

    private void onActionSwitchToIcons() {
        binding.actionSwitchToIcons.setOnClickListener(view -> {
            if (inputConnection == null) {
                return;
            }
            if (ENABLED_LAYOUT == KeyboardLayout.LETTERS || ENABLED_LAYOUT == KeyboardLayout.NUMBERS) {
                ENABLED_LAYOUT = KeyboardLayout.ICONS;
                displayIconLayout();
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

            String symbol = getResources().getString(SymbolSpecial.getStringResourceFrom(view.getId()));
            inputConnection.commitText(symbol, MOVE_CURSOR_TO_THE_END);
        });
    }

    private void onActionDone() {
        binding.actionDone.setOnClickListener(view -> {
            if (inputConnection == null) {
                return;
            }
            createDoneEvent();
        });
    }

    protected void createDoneEvent() {

    };

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
        }
//        else {
//            //delete selection
//            inputConnection.commitText("", MOVE_CURSOR_TO_THE_END);
//        }
    }

    private void changeCapsOnButtons() {
        binding.keyboardQwerty.action1Letters.setAllCaps(CAPS);
        binding.keyboardQwerty.action2Letters.setAllCaps(CAPS);
        binding.keyboardQwerty.action3Letters.setAllCaps(CAPS);
        binding.keyboardQwerty.action4Letters.setAllCaps(CAPS);
        binding.keyboardQwerty.action5Letters.setAllCaps(CAPS);
        binding.keyboardQwerty.action6Letters.setAllCaps(CAPS);
        binding.keyboardQwerty.action7Letters.setAllCaps(CAPS);
        binding.keyboardQwerty.action8Letters.setAllCaps(CAPS);
        binding.keyboardQwerty.action9Letters.setAllCaps(CAPS);
        binding.keyboardQwerty.action10Letters.setAllCaps(CAPS);
        binding.keyboardQwerty.action11Letters.setAllCaps(CAPS);
        binding.keyboardQwerty.action12Letters.setAllCaps(CAPS);
        binding.keyboardQwerty.action13Letters.setAllCaps(CAPS);
        binding.keyboardQwerty.action14Letters.setAllCaps(CAPS);
        binding.keyboardQwerty.action15Letters.setAllCaps(CAPS);
        binding.keyboardQwerty.action16Letters.setAllCaps(CAPS);
        binding.keyboardQwerty.action17Letters.setAllCaps(CAPS);
        binding.keyboardQwerty.action18Letters.setAllCaps(CAPS);
        binding.keyboardQwerty.action19Letters.setAllCaps(CAPS);
        binding.keyboardQwerty.action20Letters.setAllCaps(CAPS);
        binding.keyboardQwerty.action21Letters.setAllCaps(CAPS);
        binding.keyboardQwerty.action22Letters.setAllCaps(CAPS);
        binding.keyboardQwerty.action23Letters.setAllCaps(CAPS);
        binding.keyboardQwerty.action24Letters.setAllCaps(CAPS);
        binding.keyboardQwerty.action25Letters.setAllCaps(CAPS);
        binding.keyboardQwerty.action26Letters.setAllCaps(CAPS);
        binding.keyboardQwerty.action27Letters.setAllCaps(CAPS);
        binding.keyboardQwerty.action28Letters.setAllCaps(CAPS);
        binding.keyboardQwerty.action29Letters.setAllCaps(CAPS);
        binding.keyboardQwerty.action30Letters.setAllCaps(CAPS);
        binding.keyboardQwerty.action31Letters.setAllCaps(CAPS);

        if (CAPS == UPPER_CASE) {
            binding.keyboardQwerty.actionShiftLetters.setImageResource(R.drawable.ic_keyboard_baseline_upload_34);
        } else {
            binding.keyboardQwerty.actionShiftLetters.setImageResource(R.drawable.ic_keyboard_outline_upload_34);
        }
    }

    private void displayLetterLayout() {
        binding.keyboardNumber.containerKeyboardNumber.setVisibility(View.GONE);
        binding.keyboardIcon.containerKeyboardIcon.setVisibility(View.GONE);
        binding.keyboardQwerty.layoutPartLetters.setVisibility(View.VISIBLE);

        binding.actionBackspace.setVisibility(View.GONE);
        binding.actionBackspaceFull.setVisibility(View.GONE);
        binding.actionEnter.setVisibility(View.GONE);
        binding.actionSwitchQwertyNumber.setText("123");
        if (ENABLED_CAPABILITIES == KeyboardCapabilities.LETTERS_AND_NUMBERS) {
            binding.actionSwitchToIcons.setVisibility(View.GONE);
        } else {
            binding.actionSwitchToIcons.setVisibility(View.VISIBLE);
        }
    }

    private void displayIconLayout() {
        binding.keyboardNumber.containerKeyboardNumber.setVisibility(View.GONE);
        binding.keyboardQwerty.layoutPartLetters.setVisibility(View.GONE);
        binding.keyboardIcon.containerKeyboardIcon.setVisibility(View.VISIBLE);

        binding.actionBackspace.setVisibility(View.VISIBLE);
        binding.actionBackspaceFull.setVisibility(View.VISIBLE);
        binding.actionEnter.setVisibility(View.VISIBLE);
        binding.actionSwitchToIcons.setVisibility(View.VISIBLE);
        binding.actionSwitchQwertyNumber.setText("абв");
    }

    private void displayNumberLayout() {
        binding.keyboardQwerty.layoutPartLetters.setVisibility(View.GONE);
        binding.keyboardIcon.containerKeyboardIcon.setVisibility(View.GONE);
        binding.keyboardNumber.containerKeyboardNumber.setVisibility(View.VISIBLE);

        binding.actionBackspace.setVisibility(View.VISIBLE);
        binding.actionBackspaceFull.setVisibility(View.VISIBLE);
        binding.actionEnter.setVisibility(View.VISIBLE);
        binding.actionSwitchQwertyNumber.setText("абв");
        if (ENABLED_CAPABILITIES == KeyboardCapabilities.LETTERS_AND_NUMBERS) {
            binding.actionSwitchToIcons.setVisibility(View.GONE);
        } else {
            binding.actionSwitchToIcons.setVisibility(View.VISIBLE);
        }
    }

    // Disable all touchs to keyboard except buttons.
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    public void setHeightFragment(int heightFragment) {
        this.heightFragment = heightFragment;
    }

    public void setScrollView(ScrollView scrollView) {
        this.scrollView = scrollView;
    }

    private int getLinesFromView() {
        if (currentEnableEditTextView == null) {
            return 1;
        }
        return currentEnableEditTextView.getLineCount();
    }

    private void scrollEditTextToKeyboard() {
        if (currentLinesCount == 0) {
            currentLinesCount = getLinesFromView();
        } else if (getLinesFromView() > currentLinesCount) {
            obtainStyledAttributes();
            int yTopKeyboard = heightFragment - keyboardHeight;
            int heightView = currentEnableEditTextView.getHeight() + currentEnableEditTextView.getLineHeight();

            int[] yTopViewTouch = new int[2];
            currentEnableEditTextView.getLocationOnScreen(yTopViewTouch);
            int yBottomViewTouchInFragment = yTopViewTouch[1] - getStatusBarHeight() - getToolbarHeight() + heightView;

            if (yBottomViewTouchInFragment > yTopKeyboard) {
                int scrollingDelta = yBottomViewTouchInFragment - yTopKeyboard;
                scrollView.smoothScrollBy(0, +scrollingDelta);
            } else {
                int scrollingDelta = yTopKeyboard - yBottomViewTouchInFragment;
                scrollView.smoothScrollBy(0, -scrollingDelta);
            }
        }
        currentLinesCount = getLinesFromView();
    }

    public void scrollEditTextToKeyboard(EditText view) {
        currentEnableEditTextView = view;
        obtainStyledAttributes();
        int yTopKeyboard = heightFragment - keyboardHeight;
        int heightView = view.getHeight();
        int[] viewCoordinates = new int[2];
        view.getLocationOnScreen(viewCoordinates);
        int yBottomViewTouchInFragment = viewCoordinates[1] - getStatusBarHeight() - getToolbarHeight() + heightView;

        if (yBottomViewTouchInFragment > yTopKeyboard) {
            int scrollingDelta = yBottomViewTouchInFragment - yTopKeyboard;
            scrollView.smoothScrollBy(0, +scrollingDelta);
        } else {
            int scrollingDelta = yTopKeyboard - yBottomViewTouchInFragment;
            scrollView.smoothScrollBy(0, -scrollingDelta);
        }
    }

    private int getToolbarHeight() {
        int toolbarHeight = 0;
        TypedValue attributes = new TypedValue();

        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, attributes, true)) {
            toolbarHeight = TypedValue.complexToDimensionPixelSize(attributes.data, getResources().getDisplayMetrics());
        }
        return toolbarHeight;
    }

    private int getStatusBarHeight() {
        int statusBarHeight = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            WindowMetrics metrics = ((Activity) context).getWindowManager().getCurrentWindowMetrics();
            Insets insets = metrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
            statusBarHeight = insets.top;
        }
        return statusBarHeight;
    }

    private void obtainStyledAttributes() {
        int[] arrayLayouts = new int[]{android.R.attr.layout_width, android.R.attr.layout_height};

        TypedArray attributes = context.obtainStyledAttributes(R.style.KeyboardView, arrayLayouts);
        try {
            keyboardHeight = attributes.getDimensionPixelSize(1, DEFAULT_VALUE);
        } finally {
            attributes.recycle();
        }
    }

    private void setListenerToIconButtons() {
        //blue
        binding.keyboardIcon.actionPawn.setOnClickListener(this);
        binding.keyboardIcon.actionHammer.setOnClickListener(this);
        binding.keyboardIcon.actionHouse.setOnClickListener(this);
        binding.keyboardIcon.actionScull.setOnClickListener(this);
        binding.keyboardIcon.actionBreakfast.setOnClickListener(this);
        binding.keyboardIcon.actionMeeple.setOnClickListener(this);
        binding.keyboardIcon.actionTable.setOnClickListener(this);
        binding.keyboardIcon.actionBoat.setOnClickListener(this);
        binding.keyboardIcon.actionClock.setOnClickListener(this);
        binding.keyboardIcon.actionCar.setOnClickListener(this);
        binding.keyboardIcon.actionRocket.setOnClickListener(this);
        binding.keyboardIcon.actionFastfood.setOnClickListener(this);
        binding.keyboardIcon.actionFlask.setOnClickListener(this);
        binding.keyboardIcon.actionJewel.setOnClickListener(this);
        binding.keyboardIcon.actionShield.setOnClickListener(this);
        binding.keyboardIcon.actionPerson.setOnClickListener(this);
        binding.keyboardIcon.actionPersons.setOnClickListener(this);
        binding.keyboardIcon.actionDoor.setOnClickListener(this);
        binding.keyboardIcon.actionTablet.setOnClickListener(this);
        binding.keyboardIcon.actionCurtain.setOnClickListener(this);
        binding.keyboardIcon.actionChair.setOnClickListener(this);
        binding.keyboardIcon.actionStop.setOnClickListener(this);
        binding.keyboardIcon.actionSmartphone.setOnClickListener(this);
        binding.keyboardIcon.actionVolume.setOnClickListener(this);
        binding.keyboardIcon.actionTileDeck.setOnClickListener(this);
        binding.keyboardIcon.actionSchool.setOnClickListener(this);
        binding.keyboardIcon.actionDice1.setOnClickListener(this);
        binding.keyboardIcon.actionCardsDeck.setOnClickListener(this);
        binding.keyboardIcon.actionDice2.setOnClickListener(this);
        binding.keyboardIcon.actionTarget.setOnClickListener(this);
        binding.keyboardIcon.actionDeck1.setOnClickListener(this);
        binding.keyboardIcon.actionDeck2.setOnClickListener(this);
        binding.keyboardIcon.actionTile1.setOnClickListener(this);
        binding.keyboardIcon.actionDeck3.setOnClickListener(this);
        binding.keyboardIcon.actionShirt1.setOnClickListener(this);
        binding.keyboardIcon.actionShirt2.setOnClickListener(this);
        binding.keyboardIcon.actionTiles.setOnClickListener(this);
        binding.keyboardIcon.actionAircraft.setOnClickListener(this);
        binding.keyboardIcon.actionVictoryPoint1.setOnClickListener(this);
        binding.keyboardIcon.actionVictoryPoint2.setOnClickListener(this);


//green
        binding.keyboardIcon.actionArrowGive.setOnClickListener(this);
        binding.keyboardIcon.actionArrowTake.setOnClickListener(this);
        binding.keyboardIcon.actionArrowDown.setOnClickListener(this);
        binding.keyboardIcon.actionArrowLeft.setOnClickListener(this);
        binding.keyboardIcon.actionArrowShuffle1.setOnClickListener(this);
        binding.keyboardIcon.actionArrowShuffle2.setOnClickListener(this);
        binding.keyboardIcon.actionSwap.setOnClickListener(this);
        binding.keyboardIcon.actionSplit.setOnClickListener(this);
        binding.keyboardIcon.actionMerge.setOnClickListener(this);
        binding.keyboardIcon.actionFast.setOnClickListener(this);
        binding.keyboardIcon.actionDistance.setOnClickListener(this);
        binding.keyboardIcon.actionRelax.setOnClickListener(this);
        binding.keyboardIcon.actionOut2.setOnClickListener(this);
        binding.keyboardIcon.actionEvery.setOnClickListener(this);
        binding.keyboardIcon.actionRepeat.setOnClickListener(this);
        binding.keyboardIcon.actionFlip.setOnClickListener(this);
        binding.keyboardIcon.actionCalculate1.setOnClickListener(this);
        binding.keyboardIcon.actionCalculate2.setOnClickListener(this);


//red
        binding.keyboardIcon.actionWarning.setOnClickListener(this);
        binding.keyboardIcon.actionDangerous.setOnClickListener(this);
        binding.keyboardIcon.actionDangerous2.setOnClickListener(this);
        binding.keyboardIcon.actionDangerous3.setOnClickListener(this);
        binding.keyboardIcon.actionDangerous4.setOnClickListener(this);
        binding.keyboardIcon.actionDangerous5.setOnClickListener(this);

//white
        binding.keyboardIcon.actionColorBlack.setOnClickListener(this);
        binding.keyboardIcon.actionColorBlue.setOnClickListener(this);
        binding.keyboardIcon.actionColorGreen.setOnClickListener(this);
        binding.keyboardIcon.actionColorRed.setOnClickListener(this);
        binding.keyboardIcon.actionColorYellow.setOnClickListener(this);
        binding.keyboardIcon.actionCommit.setOnClickListener(this);
        binding.keyboardIcon.actionAll.setOnClickListener(this);
        binding.keyboardIcon.actionMin.setOnClickListener(this);
        binding.keyboardIcon.actionMax.setOnClickListener(this);
        binding.keyboardIcon.actionInfinitely.setOnClickListener(this);
        binding.keyboardIcon.actionTotal.setOnClickListener(this);
        binding.keyboardIcon.actionRandom.setOnClickListener(this);
        binding.keyboardIcon.actionAll2.setOnClickListener(this);
        binding.keyboardIcon.actionEvery2.setOnClickListener(this);
        binding.keyboardIcon.actionAll3.setOnClickListener(this);


    }

    private void setListenerToNumberButtons() {
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

    private void setListenerToLetterButtons() {
        binding.keyboardQwerty.action1Letters.setOnClickListener(this);
        binding.keyboardQwerty.action2Letters.setOnClickListener(this);
        binding.keyboardQwerty.action3Letters.setOnClickListener(this);
        binding.keyboardQwerty.action4Letters.setOnClickListener(this);
        binding.keyboardQwerty.action5Letters.setOnClickListener(this);
        binding.keyboardQwerty.action6Letters.setOnClickListener(this);
        binding.keyboardQwerty.action7Letters.setOnClickListener(this);
        binding.keyboardQwerty.action8Letters.setOnClickListener(this);
        binding.keyboardQwerty.action9Letters.setOnClickListener(this);
        binding.keyboardQwerty.action10Letters.setOnClickListener(this);
        binding.keyboardQwerty.action11Letters.setOnClickListener(this);
        binding.keyboardQwerty.action12Letters.setOnClickListener(this);
        binding.keyboardQwerty.action13Letters.setOnClickListener(this);
        binding.keyboardQwerty.action14Letters.setOnClickListener(this);
        binding.keyboardQwerty.action15Letters.setOnClickListener(this);
        binding.keyboardQwerty.action16Letters.setOnClickListener(this);
        binding.keyboardQwerty.action17Letters.setOnClickListener(this);
        binding.keyboardQwerty.action18Letters.setOnClickListener(this);
        binding.keyboardQwerty.action19Letters.setOnClickListener(this);
        binding.keyboardQwerty.action20Letters.setOnClickListener(this);
        binding.keyboardQwerty.action21Letters.setOnClickListener(this);
        binding.keyboardQwerty.action22Letters.setOnClickListener(this);
        binding.keyboardQwerty.action23Letters.setOnClickListener(this);
        binding.keyboardQwerty.action24Letters.setOnClickListener(this);
        binding.keyboardQwerty.action25Letters.setOnClickListener(this);
        binding.keyboardQwerty.action26Letters.setOnClickListener(this);
        binding.keyboardQwerty.action27Letters.setOnClickListener(this);
        binding.keyboardQwerty.action28Letters.setOnClickListener(this);
        binding.keyboardQwerty.action29Letters.setOnClickListener(this);
        binding.keyboardQwerty.action30Letters.setOnClickListener(this);
        binding.keyboardQwerty.action31Letters.setOnClickListener(this);
    }

}
