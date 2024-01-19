package space.lopatkin.spb.helpboardgamecard.ui.catalog.helpcard.cardedit;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import org.jetbrains.annotations.NotNull;
import space.lopatkin.spb.keyboard.SymbolIcon;
import space.lopatkin.spb.keyboard.KeyboardView;

import java.util.stream.IntStream;

public class IconEditView extends AppCompatEditText {

    private static final char SEPARATOR = SymbolIcon.SEPARATOR.charAt(0);
    private Context context;

    public IconEditView(@NonNull @NotNull Context context) {
        super(context);
        this.context = context;
    }

    public IconEditView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public IconEditView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    public void setText(CharSequence charSequence, BufferType type) {
        String text = String.valueOf(charSequence);
        Spannable textWithImages = new SpannableString(text);

        if (!text.isEmpty() && countImagesIn(text) > 0) {
            setSpansToText(textWithImages, text);
        }
        super.setText(textWithImages, type);
    }

    private void setSpansToText(Spannable textWithImages, String text) {
        for (int numberImage = 0; numberImage < countImagesIn(text); numberImage++) {
            int firstCharIndex = getIndexSeparator(text, numberImage);
            int lastCharIndex = firstCharIndex + SymbolIcon.CODENAME_LENGTH;

            setImageToWord(textWithImages, text, firstCharIndex, lastCharIndex);
        }
    }

    private void setImageToWord(Spannable textWithImages, String text, int firstCharIndex, int lastCharIndex) {
        String codename = text.substring(firstCharIndex, lastCharIndex);

        ImageSpan image = new ImageSpan(context,
                SymbolIcon.getDrawableResourceFrom(codename),
                KeyboardView.DYNAMIC_DRAWABLE_SPAN);

        textWithImages.setSpan(image, firstCharIndex, lastCharIndex, KeyboardView.SPANNABLE_SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private long countImagesIn(String text) {
        CharSequence charSequence = text;
        return charSequence.chars()
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
