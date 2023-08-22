package space.lopatkin.spb.helpboardgamecard.ui.addcard;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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
    public static final String SEPARATOR = "#";

    private ViewKeyboardBinding binding;

    //this will the button resource id to the String value that we want to
    // input when that button is clicked
    private SparseArray<String> keyValues = new SparseArray<>();
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

//        onClickString(view);
        onClickImage(view);

    }

    //reference to the current EditText's InputConnection
    public void setInputConnection(InputConnection inputConnection) {
        this.inputConnection = inputConnection;
    }

    private void init(Context context) {
        this.context = context;
        binding = ViewKeyboardBinding.inflate(LayoutInflater.from(context), this, true);

        binding.actionChair.setOnClickListener(this);
        binding.actionBreakfast.setOnClickListener(this);
        binding.actionStop.setOnClickListener(this);
        binding.actionSmartphone.setOnClickListener(this);
        binding.actionVolume.setOnClickListener(this);
        binding.actionRight.setOnClickListener(this);
        binding.actionDown.setOnClickListener(this);
        binding.actionDistance.setOnClickListener(this);

        binding.actionBackspace.setOnClickListener(this);
        binding.actionSpace.setOnClickListener(this);
        binding.actionEnter.setOnClickListener(this);
        binding.actionDone.setOnClickListener(this);

        keyValues.put(R.id.action_enter, "\n");
        keyValues.put(R.id.action_space, " ");
    }

    //------------------------------------------------

    private void onClickString(View view) {

        Log.d("myLogs", "CustomKeyboard onClickString start:  ");

        //do nothing if the InputConnection has not been set yet
        if (inputConnection == null)
            return;

        //delete text or input key value
        //all communications goes through the InputConnection
        switch (view.getId()) {
            case R.id.action_backspace:
                CharSequence selectedText = inputConnection.getSelectedText(0);
                if (TextUtils.isEmpty(selectedText)) {
                    //no selection, so delete previous character
                    inputConnection.deleteSurroundingText(1, 0);
                } else {
                    //delete selection
                    inputConnection.commitText("", 1);
                }
                break;
//            case R.id.action_shift:
//                caps = !caps;
//                keyboard.setShifted(caps);
//                kv.iinvalidateAllKeys();
//                break;
//            case R.id.action_done:
//
//                break;
            default:
                String value = keyValues.get(view.getId());
//                if (Character.isLetter(value) && caps) {
//                    value = Character.toUpperCase(value);
//                }
                inputConnection.commitText(value, 1);
        }

        Log.d("myLogs", "CustomKeyboard onClickString end:  ");


    }


    private void onClickImage(View view) {
        Log.d("myLogs", "CustomKeyboard onClickImage start:  ");

        //do nothing if the InputConnection has not been set yet
        if (inputConnection == null)
            return;

        switch (view.getId()) {
            case R.id.action_backspace:
                CharSequence currentText = inputConnection.getExtractedText(new ExtractedTextRequest(), 0).text;

                if (isSeparator(currentText)) {
                        StringBuffer buffer = new StringBuffer(currentText);
                        buffer.reverse();
                        int indexLastSeparator = buffer.indexOf(SEPARATOR);
                        inputConnection.deleteSurroundingText(indexLastSeparator + 1, 0);
                } else {
                    // Delete selection text
                    inputConnection.commitText("", MOVE_CURSOR_TO_THE_END);
                }
                break;
            case R.id.action_enter:
            case R.id.action_space:
                inputConnection.commitText(keyValues.get(view.getId()), MOVE_CURSOR_TO_THE_END);
                break;
            case R.id.action_done:
                EventBus.getDefault().post(new KeyboardDoneEvent());
                break;
            default:
                ImageSpan span = new ImageSpan(
                        context,
                        KeyboardButton.getDrawableFrom(view.getId()),
                        DynamicDrawableSpan.ALIGN_BASELINE);

                Spannable image = new SpannableString(SEPARATOR + KeyboardButton.getNameFrom(view.getId()));

                //todo: разобраться с вариантами SPAN_EXCLUSIVE_EXCLUSIVE
                image.setSpan(span, 0, image.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                inputConnection.commitText(image, MOVE_CURSOR_TO_THE_END);
        }

        Log.d("myLogs", "CustomKeyboard onClickImage end:  ");

    }

    private boolean isSeparator(CharSequence text) {
        StringBuffer buffer = new StringBuffer(text);
        int indexLastSeparator = buffer.lastIndexOf(SEPARATOR);
        return indexLastSeparator >= 0 ? true : false;
    }

}
