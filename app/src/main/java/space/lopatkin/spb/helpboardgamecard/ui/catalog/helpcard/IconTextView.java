package space.lopatkin.spb.helpboardgamecard.ui.catalog.helpcard;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import org.jetbrains.annotations.NotNull;
import space.lopatkin.spb.helpboardgamecard.ui.addcard.KeyboardButtonIcon;
import space.lopatkin.spb.helpboardgamecard.ui.addcard.KeyboardView;

import java.util.stream.IntStream;

public class IconTextView extends AppCompatTextView {

    private static final char SEPARATOR = KeyboardButtonIcon.SEPARATOR.charAt(0);
    private Context context;

    public IconTextView(@NonNull @NotNull Context context) {
        super(context);
        this.context = context;
    }

    public IconTextView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public IconTextView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        Spannable textWithImages = new SpannableString(text);

        if (!text.toString().isEmpty() && countImagesIn(text) > 0) {
            setImagesToText(textWithImages, text);
        }

        super.setText(textWithImages, type);
    }

    private void setImagesToText(Spannable textWithImages, CharSequence rawText) {
        String text = (String) rawText;

        for (int numberImage = 0; numberImage < countImagesIn(rawText); numberImage++) {
            int firstCharIndex = getIndexSeparator(text, numberImage);
            int lastCharIndex = firstCharIndex + KeyboardButtonIcon.getLength();

            String name = text.substring(firstCharIndex + 1, lastCharIndex);
            ImageSpan span = new ImageSpan(context,
                    KeyboardButtonIcon.getDrawableFrom(name),
                    KeyboardView.DYNAMIC_DRAWABLE_SPAN);

            textWithImages.setSpan(span, firstCharIndex, lastCharIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    private long countImagesIn(CharSequence text) {
        return text.chars()
                .filter(character -> character == SEPARATOR)
                .count();
    }

    private int getIndexSeparator(String text, int numberImage) {
        char[] characters = text.toCharArray();

        int[] indexes = IntStream.range(0, characters.length)
                .filter(index -> characters[index] == SEPARATOR)
                .toArray();

        return indexes[numberImage];
    }

}
