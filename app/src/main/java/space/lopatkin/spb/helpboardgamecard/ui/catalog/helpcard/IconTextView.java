package space.lopatkin.spb.helpboardgamecard.ui.catalog.helpcard;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import org.jetbrains.annotations.NotNull;
import space.lopatkin.spb.helpboardgamecard.R;
import space.lopatkin.spb.helpboardgamecard.ui.addcard.KeyboardButton;
import space.lopatkin.spb.helpboardgamecard.ui.addcard.KeyboardView;

public class IconTextView extends AppCompatTextView {

    private Context context;

    public IconTextView(@NonNull @NotNull Context context) {
        super(context);
        this.context = context;
//        setTextTempState(context);
    }

    public IconTextView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

//        setTextTempState(context);
    }

    public IconTextView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

//        setTextTempState(context);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {

        Log.d("myLogs", "IconTextView setText start: text = " + text + "; type = " + type);

        CharSequence textOutput = "";

        Spannable image = new SpannableString(text);

        if (!text.toString().isEmpty() && isSeparator(text)) {

            Log.d("myLogs", "IconTextView setText if start: text with separator = " + text);

            StringBuffer buffer = new StringBuffer(text);
            int indexSeparator = buffer.indexOf(KeyboardView.SEPARATOR);
            textOutput = buffer.substring(indexSeparator + 1);

            Log.d("myLogs", "IconTextView setText: text without separator = " + textOutput);

            ImageSpan span = new ImageSpan(context, KeyboardButton.getDrawableFrom((String) textOutput), DynamicDrawableSpan.ALIGN_BASELINE);


            image = new SpannableString(KeyboardView.SEPARATOR + textOutput);
            image.setSpan(span, 0, image.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        }

        super.setText(image, type);
    }


    private void setTextTempState(Context context) {

        ImageSpan span = new ImageSpan(getContext(), R.drawable.ic_menu_slideshow, DynamicDrawableSpan.ALIGN_BASELINE);

        Spannable image = new SpannableString(KeyboardView.SEPARATOR + "play");
        image.setSpan(span, 2, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        setText(image);
    }

    private boolean isSeparator(CharSequence text) {
        StringBuffer buffer = new StringBuffer(text);
        int indexLastSeparator = buffer.lastIndexOf(KeyboardView.SEPARATOR);
        return indexLastSeparator >= 0 ? true : false;
    }

}
