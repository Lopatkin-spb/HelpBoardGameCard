package space.lopatkin.spb.helpboardgamecard.ui.catalog.helpcard;

import android.content.res.Resources;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import space.lopatkin.spb.helpboardgamecard.databinding.ViewLabelPopupBinding;
import space.lopatkin.spb.helpboardgamecard.ui.addcard.KeyboardButtonIcon;

import java.util.stream.IntStream;

public class LabelPopupView extends PopupWindow {
    private ViewLabelPopupBinding binding;
    private static final char SEPARATOR = KeyboardButtonIcon.SEPARATOR.charAt(0);
    public static final int TEXT_LABEL_POPUP_HEIGHT = 53;

    public LabelPopupView(ViewLabelPopupBinding binding) {
        this.binding = binding;

        setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(true);
        setElevation(10);
        setContentView(binding.getRoot());
    }

    public void show(View view, MotionEvent motionEvent, TextView textView) {
        String text = textView.getText().toString();
        int xOffset = (int) motionEvent.getX();
        int yOffset = (int) motionEvent.getY();
        int cursorLine = getCursorLine(textView, yOffset);

        if (!text.isEmpty() && cursorLine > 0 && xOffset < getLineWidth(text, cursorLine)) {
            int iconWidth = 90; // ~ 80 - 100

            String textInLine = getTextInLine(text, cursorLine);
            long imagesCount = countImagesIn(textInLine);

            int indexImage = getNumberImage(xOffset, iconWidth, imagesCount);

            String nameFull = getNameFull(textInLine, indexImage);
            if (!nameFull.isEmpty()) {
                binding.textLabelPopup.setText(nameFull);
                int yOffsetPopup = getYOffsetPopup(textView, yOffset);
                showAsDropDown(view, xOffset, yOffsetPopup);
            }
        }
    }

    private long countImagesIn(String text) {
        CharSequence charSequence = text;
        return charSequence.chars()
                .filter(character -> character == SEPARATOR)
                .count();
    }

    private int getNumberImage(int x, int iconWidth, long images) {
        int target = x / iconWidth;
        int result = target + 1;
        if (result > images) {
            result = 0;
        }
        return result;
    }

    private String getNameFull(String text, int target) {
        String nameFull = "";
        for (int numberImage = 0; numberImage < countImagesIn(text); numberImage++) {
            if (numberImage + 1 == target) {

                int firstCharIndex = getIndexSeparator(text, numberImage);
                int lastCharIndex = firstCharIndex + KeyboardButtonIcon.getLength();
                String name = text.substring(firstCharIndex + 1, lastCharIndex);

                nameFull = KeyboardButtonIcon.getNameFullFrom(name);
            }
        }
        return nameFull;
    }

    private int getIndexSeparator(String text, int numberImage) {
        char[] characters = text.toCharArray();

        int[] indexes = IntStream.range(0, characters.length)
                .filter(index -> characters[index] == SEPARATOR)
                .toArray();

        return indexes[numberImage];
    }

    private int getYOffsetPopup(TextView textView, int yOffset) {
        int lineHeight = textView.getHeight() / textView.getLineCount();
        int sumLinesHeight = textView.getHeight() - ((getCursorLine(textView, yOffset) - 1) * lineHeight);
        int popupHeight = dpTpPx(TEXT_LABEL_POPUP_HEIGHT);
        int yOffsetPopup = -sumLinesHeight - popupHeight;
        return yOffsetPopup;
    }

    private static int dpTpPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    private int getCursorLine(TextView textView, int yOffset) {
        int viewHeight = textView.getHeight();
        int linesCount = textView.getLineCount();
        float cursorLineFloat = (float) yOffset / viewHeight * linesCount;
        int cursorLine = (int) Math.ceil(cursorLineFloat);
        return cursorLine;
    }

    private int getLineWidth(String text, int cursorLine) {
        long imagesInLine = countImagesIn(getTextInLine(text, cursorLine));
        int iconWidth = 90; // ~ 80 - 100
        int lineWidth = (int) (imagesInLine * iconWidth);
        return lineWidth;
    }

    private String getTextInLine(String text, int cursorLine) {
        String[] lines = text.split("\n");
        return lines[cursorLine - 1];
    }

}
