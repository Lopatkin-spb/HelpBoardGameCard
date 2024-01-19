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
    private static final int SPANNABLE_TEXT_LENGTH = SymbolIcon.CODENAME_LENGTH;
    private static final int DEFAULT_VALUE = 0;
    private static final boolean UPPER_CASE = true;
    private static final boolean LOWER_CASE = false;
    private static boolean CAPS = UPPER_CASE;
    public static final int DYNAMIC_DRAWABLE_SPAN = DynamicDrawableSpan.ALIGN_BOTTOM;
    public static final int SPANNABLE_SPAN_EXCLUSIVE_EXCLUSIVE = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE;
    private static final String SEPARATOR = SymbolIcon.SEPARATOR;
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
                    SymbolIcon.getDrawableResourceFrom(view.getId()),
                    DYNAMIC_DRAWABLE_SPAN);
            Spannable icon = new SpannableString(SymbolIcon.getCodenameFrom(view.getId()));

            icon.setSpan(span, 0, icon.length(), SPANNABLE_SPAN_EXCLUSIVE_EXCLUSIVE);
            inputConnection.commitText(icon, MOVE_CURSOR_TO_THE_END);

        } else if (ENABLED_LAYOUT == KeyboardLayout.NUMBERS) {
            String symbol = getResources().getString(SymbolNumber.getStringResourceFrom(view.getId()));
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
        binding.actionBackspaceKeyboard.setOnClickListener(view -> {
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
        binding.includePartLettersKeyboard.actionBackspaceLetters.setOnClickListener(view -> {
            if (inputConnection == null) {
                return;
            }
            CharSequence text = inputConnection.getExtractedText(new ExtractedTextRequest(), 0).text;

            deleteSymbol(text);
        });
    }

    private void onActionBackspaceFull() {
        binding.actionBackspaceFullKeyboard.setOnClickListener(view -> {
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
        binding.actionEnterKeyboard.setOnClickListener(view -> {
            if (inputConnection == null) {
                return;
            }
            String symbol = getResources().getString(SymbolSpecial.getStringResourceFrom(view.getId()));
            inputConnection.commitText(symbol, MOVE_CURSOR_TO_THE_END);

            scrollEditTextToKeyboard();
        });
    }

    private void onActionShift() {
        binding.includePartLettersKeyboard.actionShiftLetters.setOnClickListener(view -> {
            CAPS = !CAPS;
            changeCapsOnButtons();
        });
    }

    private void onActionSwitchQwertyNumber() {
        binding.actionSwitchLetterNumberKeyboard.setOnClickListener(view -> {
            if (inputConnection == null) {
                return;
            }
            String textSwitchLetters = getResources().getString(R.string.action_switch_to_letter_keyboard);
            if (ENABLED_LAYOUT == KeyboardLayout.ICONS && binding.actionSwitchLetterNumberKeyboard.getText().equals(textSwitchLetters)
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
        binding.actionSwitchToIconsKeyboard.setOnClickListener(view -> {
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
        binding.actionSpaceKeyboard.setOnClickListener(view -> {
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
        binding.actionDoneKeyboard.setOnClickListener(view -> {
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
        binding.includePartLettersKeyboard.action1Letters.setAllCaps(CAPS);
        binding.includePartLettersKeyboard.action2Letters.setAllCaps(CAPS);
        binding.includePartLettersKeyboard.action3Letters.setAllCaps(CAPS);
        binding.includePartLettersKeyboard.action4Letters.setAllCaps(CAPS);
        binding.includePartLettersKeyboard.action5Letters.setAllCaps(CAPS);
        binding.includePartLettersKeyboard.action6Letters.setAllCaps(CAPS);
        binding.includePartLettersKeyboard.action7Letters.setAllCaps(CAPS);
        binding.includePartLettersKeyboard.action8Letters.setAllCaps(CAPS);
        binding.includePartLettersKeyboard.action9Letters.setAllCaps(CAPS);
        binding.includePartLettersKeyboard.action10Letters.setAllCaps(CAPS);
        binding.includePartLettersKeyboard.action11Letters.setAllCaps(CAPS);
        binding.includePartLettersKeyboard.action12Letters.setAllCaps(CAPS);
        binding.includePartLettersKeyboard.action13Letters.setAllCaps(CAPS);
        binding.includePartLettersKeyboard.action14Letters.setAllCaps(CAPS);
        binding.includePartLettersKeyboard.action15Letters.setAllCaps(CAPS);
        binding.includePartLettersKeyboard.action16Letters.setAllCaps(CAPS);
        binding.includePartLettersKeyboard.action17Letters.setAllCaps(CAPS);
        binding.includePartLettersKeyboard.action18Letters.setAllCaps(CAPS);
        binding.includePartLettersKeyboard.action19Letters.setAllCaps(CAPS);
        binding.includePartLettersKeyboard.action20Letters.setAllCaps(CAPS);
        binding.includePartLettersKeyboard.action21Letters.setAllCaps(CAPS);
        binding.includePartLettersKeyboard.action22Letters.setAllCaps(CAPS);
        binding.includePartLettersKeyboard.action23Letters.setAllCaps(CAPS);
        binding.includePartLettersKeyboard.action24Letters.setAllCaps(CAPS);
        binding.includePartLettersKeyboard.action25Letters.setAllCaps(CAPS);
        binding.includePartLettersKeyboard.action26Letters.setAllCaps(CAPS);
        binding.includePartLettersKeyboard.action27Letters.setAllCaps(CAPS);
        binding.includePartLettersKeyboard.action28Letters.setAllCaps(CAPS);
        binding.includePartLettersKeyboard.action29Letters.setAllCaps(CAPS);
        binding.includePartLettersKeyboard.action30Letters.setAllCaps(CAPS);
        binding.includePartLettersKeyboard.action31Letters.setAllCaps(CAPS);

        if (CAPS == UPPER_CASE) {
            binding.includePartLettersKeyboard.actionShiftLetters.setImageResource(R.drawable.ic_keyboard_baseline_upload_34);
        } else {
            binding.includePartLettersKeyboard.actionShiftLetters.setImageResource(R.drawable.ic_keyboard_outline_upload_34);
        }
    }

    private void displayLetterLayout() {
        binding.includePartNumbersKeyboard.layoutPartNumbers.setVisibility(View.GONE);
        binding.includePartIconsKeyboard.containerKeyboardIcon.setVisibility(View.GONE);
        binding.includePartLettersKeyboard.layoutPartLetters.setVisibility(View.VISIBLE);

        binding.actionBackspaceKeyboard.setVisibility(View.GONE);
        binding.actionBackspaceFullKeyboard.setVisibility(View.GONE);
        binding.actionEnterKeyboard.setVisibility(View.GONE);
        binding.actionSwitchLetterNumberKeyboard.setText(R.string.action_switch_to_number_keyboard);
        if (ENABLED_CAPABILITIES == KeyboardCapabilities.LETTERS_AND_NUMBERS) {
            binding.actionSwitchToIconsKeyboard.setVisibility(View.GONE);
        } else {
            binding.actionSwitchToIconsKeyboard.setVisibility(View.VISIBLE);
        }
    }

    private void displayIconLayout() {
        binding.includePartNumbersKeyboard.layoutPartNumbers.setVisibility(View.GONE);
        binding.includePartLettersKeyboard.layoutPartLetters.setVisibility(View.GONE);
        binding.includePartIconsKeyboard.containerKeyboardIcon.setVisibility(View.VISIBLE);

        binding.actionBackspaceKeyboard.setVisibility(View.VISIBLE);
        binding.actionBackspaceFullKeyboard.setVisibility(View.VISIBLE);
        binding.actionEnterKeyboard.setVisibility(View.VISIBLE);
        binding.actionSwitchToIconsKeyboard.setVisibility(View.VISIBLE);
        binding.actionSwitchLetterNumberKeyboard.setText(R.string.action_switch_to_letter_keyboard);
    }

    private void displayNumberLayout() {
        binding.includePartLettersKeyboard.layoutPartLetters.setVisibility(View.GONE);
        binding.includePartIconsKeyboard.containerKeyboardIcon.setVisibility(View.GONE);
        binding.includePartNumbersKeyboard.layoutPartNumbers.setVisibility(View.VISIBLE);

        binding.actionBackspaceKeyboard.setVisibility(View.VISIBLE);
        binding.actionBackspaceFullKeyboard.setVisibility(View.VISIBLE);
        binding.actionEnterKeyboard.setVisibility(View.VISIBLE);
        binding.actionSwitchLetterNumberKeyboard.setText(R.string.action_switch_to_letter_keyboard);
        if (ENABLED_CAPABILITIES == KeyboardCapabilities.LETTERS_AND_NUMBERS) {
            binding.actionSwitchToIconsKeyboard.setVisibility(View.GONE);
        } else {
            binding.actionSwitchToIconsKeyboard.setVisibility(View.VISIBLE);
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
        binding.includePartIconsKeyboard.actionPawn.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionHammer.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionHouse.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionScull.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionBreakfast.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionMeeple.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionTable.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionBoat.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionClock.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionCar.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionRocket.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionFastfood.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionFlask.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionJewel.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionShield.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionPerson.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionPersons.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionDoor.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionTablet.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionCurtain.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionChair.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionStop.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionSmartphone.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionVolume.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionTileDeck.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionSchool.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionDice1.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionCardsDeck.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionDice2.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionTarget.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionDeck1.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionDeck2.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionTile1.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionDeck3.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionShirt1.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionShirt2.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionTiles.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionAircraft.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionVictoryPoint1.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionVictoryPoint2.setOnClickListener(this);


//green
        binding.includePartIconsKeyboard.actionArrowGive.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionArrowTake.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionArrowDown.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionArrowLeft.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionArrowShuffle1.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionArrowShuffle2.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionSwap.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionSplit.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionMerge.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionFast.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionDistance.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionRelax.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionOut2.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionEvery.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionRepeat.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionFlip.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionCalculate1.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionCalculate2.setOnClickListener(this);


//red
        binding.includePartIconsKeyboard.actionWarning.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionDangerous.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionDangerous2.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionDangerous3.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionDangerous4.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionDangerous5.setOnClickListener(this);

//white
        binding.includePartIconsKeyboard.actionColorBlack.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionColorBlue.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionColorGreen.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionColorRed.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionColorYellow.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionCommit.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionAll.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionMin.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionMax.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionInfinitely.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionTotal.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionRandom.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionAll2.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionEvery2.setOnClickListener(this);
        binding.includePartIconsKeyboard.actionAll3.setOnClickListener(this);


    }

    private void setListenerToNumberButtons() {
        binding.includePartNumbersKeyboard.actionOneNumbers.setOnClickListener(this);
        binding.includePartNumbersKeyboard.actionTwoNumbers.setOnClickListener(this);
        binding.includePartNumbersKeyboard.actionThreeNumbers.setOnClickListener(this);
        binding.includePartNumbersKeyboard.actionFourNumbers.setOnClickListener(this);
        binding.includePartNumbersKeyboard.actionFiveNumbers.setOnClickListener(this);
        binding.includePartNumbersKeyboard.actionSevenNumbers.setOnClickListener(this);
        binding.includePartNumbersKeyboard.actionSixNumbers.setOnClickListener(this);
        binding.includePartNumbersKeyboard.actionEightNumbers.setOnClickListener(this);
        binding.includePartNumbersKeyboard.actionNineNumbers.setOnClickListener(this);
        binding.includePartNumbersKeyboard.actionZeroNumbers.setOnClickListener(this);

        binding.includePartNumbersKeyboard.actionPlusNumbers.setOnClickListener(this);
        binding.includePartNumbersKeyboard.actionMinusNumbers.setOnClickListener(this);
        binding.includePartNumbersKeyboard.actionMultiplyNumbers.setOnClickListener(this);
        binding.includePartNumbersKeyboard.actionDivideNumbers.setOnClickListener(this);
        binding.includePartNumbersKeyboard.actionColonNumbers.setOnClickListener(this);
        binding.includePartNumbersKeyboard.actionEqualNumbers.setOnClickListener(this);
        binding.includePartNumbersKeyboard.actionParenthesesOpenNumbers.setOnClickListener(this);
        binding.includePartNumbersKeyboard.actionParenthesesCloseNumbers.setOnClickListener(this);
    }

    private void setListenerToLetterButtons() {
        binding.includePartLettersKeyboard.action1Letters.setOnClickListener(this);
        binding.includePartLettersKeyboard.action2Letters.setOnClickListener(this);
        binding.includePartLettersKeyboard.action3Letters.setOnClickListener(this);
        binding.includePartLettersKeyboard.action4Letters.setOnClickListener(this);
        binding.includePartLettersKeyboard.action5Letters.setOnClickListener(this);
        binding.includePartLettersKeyboard.action6Letters.setOnClickListener(this);
        binding.includePartLettersKeyboard.action7Letters.setOnClickListener(this);
        binding.includePartLettersKeyboard.action8Letters.setOnClickListener(this);
        binding.includePartLettersKeyboard.action9Letters.setOnClickListener(this);
        binding.includePartLettersKeyboard.action10Letters.setOnClickListener(this);
        binding.includePartLettersKeyboard.action11Letters.setOnClickListener(this);
        binding.includePartLettersKeyboard.action12Letters.setOnClickListener(this);
        binding.includePartLettersKeyboard.action13Letters.setOnClickListener(this);
        binding.includePartLettersKeyboard.action14Letters.setOnClickListener(this);
        binding.includePartLettersKeyboard.action15Letters.setOnClickListener(this);
        binding.includePartLettersKeyboard.action16Letters.setOnClickListener(this);
        binding.includePartLettersKeyboard.action17Letters.setOnClickListener(this);
        binding.includePartLettersKeyboard.action18Letters.setOnClickListener(this);
        binding.includePartLettersKeyboard.action19Letters.setOnClickListener(this);
        binding.includePartLettersKeyboard.action20Letters.setOnClickListener(this);
        binding.includePartLettersKeyboard.action21Letters.setOnClickListener(this);
        binding.includePartLettersKeyboard.action22Letters.setOnClickListener(this);
        binding.includePartLettersKeyboard.action23Letters.setOnClickListener(this);
        binding.includePartLettersKeyboard.action24Letters.setOnClickListener(this);
        binding.includePartLettersKeyboard.action25Letters.setOnClickListener(this);
        binding.includePartLettersKeyboard.action26Letters.setOnClickListener(this);
        binding.includePartLettersKeyboard.action27Letters.setOnClickListener(this);
        binding.includePartLettersKeyboard.action28Letters.setOnClickListener(this);
        binding.includePartLettersKeyboard.action29Letters.setOnClickListener(this);
        binding.includePartLettersKeyboard.action30Letters.setOnClickListener(this);
        binding.includePartLettersKeyboard.action31Letters.setOnClickListener(this);
    }

}
