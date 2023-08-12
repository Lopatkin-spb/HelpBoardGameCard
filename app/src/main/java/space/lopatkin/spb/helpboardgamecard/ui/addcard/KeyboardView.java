package space.lopatkin.spb.helpboardgamecard.ui.addcard;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputConnection;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import org.jetbrains.annotations.NotNull;
import space.lopatkin.spb.helpboardgamecard.R;
import space.lopatkin.spb.helpboardgamecard.databinding.ViewKeyboardBinding;

public class KeyboardView extends ConstraintLayout implements View.OnClickListener {
    private ViewKeyboardBinding binding;

    //this will the button resource id to the String value that we want to
    // input when that button is clicked
    private SparseArray<String> keyValues = new SparseArray<>();

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
        Log.d("myLogs", "CustomKeyboard onClick start:  ");

        //do nothing if the InputConnection has not been set yet
        if (inputConnection == null)
            return;

        //delete text or input key value
        //all communications goes through the InputConnection
        if (view.getId() == R.id.action_del) {
            CharSequence selectedText = inputConnection.getSelectedText(0);
            if (TextUtils.isEmpty(selectedText)) {
                //no selection, so delete previous character
                inputConnection.deleteSurroundingText(1, 0);
            } else {
                //delete selection
                inputConnection.commitText("", 1);
            }

        } else {
            String value = keyValues.get(view.getId());
            inputConnection.commitText(value, 1);
        }

        Log.d("myLogs", "CustomKeyboard onClick end:  ");
    }

    //reference to the current EditText's InputConnection
    public void setInputConnection(InputConnection inputConnection) {
        this.inputConnection = inputConnection;
    }

    private void init(Context context) {
        binding = ViewKeyboardBinding.inflate(LayoutInflater.from(context), this, true);

        binding.action1.setOnClickListener(this);
        binding.action2.setOnClickListener(this);
        binding.action3.setOnClickListener(this);
        binding.action4.setOnClickListener(this);
        binding.action5.setOnClickListener(this);
        binding.action6.setOnClickListener(this);
        binding.action7.setOnClickListener(this);
        binding.actionEnter.setOnClickListener(this);
        binding.actionDel.setOnClickListener(this);

        //map buttons Ids to input strings
        keyValues.put(R.id.action_1, "1");
        keyValues.put(R.id.action_2, "2");
        keyValues.put(R.id.action_3, "3");
        keyValues.put(R.id.action_4, "4");
        keyValues.put(R.id.action_5, "5");
        keyValues.put(R.id.action_6, "6");
        keyValues.put(R.id.action_7, "7");
        keyValues.put(R.id.action_enter, "\n");
    }

}
