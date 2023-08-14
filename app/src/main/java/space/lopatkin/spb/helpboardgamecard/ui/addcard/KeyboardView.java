package space.lopatkin.spb.helpboardgamecard.ui.addcard;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
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
    private SparseArray<Integer> keyValuesImage = new SparseArray<>();

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

//        binding.action1.setOnClickListener(this);
//        binding.action2.setOnClickListener(this);
//        binding.action3.setOnClickListener(this);
//        binding.action4.setOnClickListener(this);
//        binding.action5.setOnClickListener(this);
//        binding.action6.setOnClickListener(this);
////        binding.action7.setOnClickListener(this);
//        binding.actionEnter.setOnClickListener(this);
//        binding.actionDel.setOnClickListener(this);

        //map buttons Ids to input strings
//        keyValues.put(R.id.action_1, "1");
//        keyValues.put(R.id.action_2, "2");
//        keyValues.put(R.id.action_3, "3");
//        keyValues.put(R.id.action_4, "4");
//        keyValues.put(R.id.action_5, "5");
//        keyValues.put(R.id.action_6, "6");
////        keyValues.put(R.id.action_7, "7");
//        keyValues.put(R.id.action_enter, "\n");

        binding.actionCar.setOnClickListener(this);
        binding.actionStore.setOnClickListener(this);
        binding.actionStyle.setOnClickListener(this);
        binding.actionIcon.setOnClickListener(this);
        binding.actionEnter.setOnClickListener(this);
        binding.actionDel.setOnClickListener(this);




        keyValuesImage.put(R.id.action_car, R.drawable.ic_keyboard_round_directions_car_34);
        keyValuesImage.put(R.id.action_store, R.drawable.ic_keyboard_round_storefront_24);
        keyValuesImage.put(R.id.action_style, R.drawable.ic_keyboard_round_style_44);
        keyValuesImage.put(R.id.action_icon, R.drawable.ic_menu_slideshow);
        keyValues.put(R.id.action_enter, "\n");

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
            case R.id.action_del:
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

//        Drawable drawable = ResourcesCompat.getDrawable(getResources(), keyValuesImage.get(view.getId()), null);
//        if (drawable == null)
//            return;
//        drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());


//        ImageSpan span = new ImageSpan(context, keyValuesImage.get(view.getId()), DynamicDrawableSpan.ALIGN_BASELINE);
//
//        Spannable value = new SpannableString("play");
//        value.setSpan(span, 2, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        inputConnection.commitText(value, 1);
//
//        Log.d("myLogs", "CustomKeyboard onClickImage end:  ");




        switch (view.getId()) {
            case R.id.action_del:
                CharSequence selectedText = inputConnection.getSelectedText(0);
                CharSequence currentText = inputConnection.getExtractedText(new ExtractedTextRequest(), 0).text;

//                if (TextUtils.isEmpty(selectedText)) {
//                    //no selection, so delete previous character
//                    inputConnection.deleteSurroundingText(1, 0);
//                } else
                    if (isSeparator(currentText)) {

                        StringBuffer buffer = new StringBuffer(currentText);
                        buffer.reverse();
                        int indexLastSeparator = buffer.indexOf(SEPARATOR);

                        inputConnection.deleteSurroundingText(indexLastSeparator + 1, 0);

                } else {
                    //delete selection
                    inputConnection.commitText("", MOVE_CURSOR_TO_THE_END);
                }
                break;
            case R.id.action_enter:
                String string = keyValues.get(view.getId());
                inputConnection.commitText(string, MOVE_CURSOR_TO_THE_END);
                break;
            default:
//                ImageSpan span = new ImageSpan(context, keyValuesImage.get(view.getId()), DynamicDrawableSpan.ALIGN_BASELINE);
                ImageSpan span = new ImageSpan(context, KeyboardButton.getDrawableFrom(view.getId()), DynamicDrawableSpan.ALIGN_BASELINE);



//                Spannable image = new SpannableString(SEPARATOR + "play");
                Spannable image = new SpannableString(SEPARATOR + KeyboardButton.getNameFrom(view.getId()));

                //разобраться с вариантами SPAN_EXCLUSIVE_EXCLUSIVE
                image.setSpan(span, 0, image.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                inputConnection.commitText(image, MOVE_CURSOR_TO_THE_END);
        }

        Log.d("myLogs", "CustomKeyboard onClickImage end:  ");

    }

    private boolean isSeparator(CharSequence text) {
        StringBuffer buffer = new StringBuffer(text);
        int indexLastSeparator = buffer.lastIndexOf(SEPARATOR);
        return indexLastSeparator > 0 ? true : false;
    }


}
