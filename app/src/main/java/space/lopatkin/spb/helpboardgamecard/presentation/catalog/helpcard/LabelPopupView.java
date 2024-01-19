package space.lopatkin.spb.helpboardgamecard.presentation.catalog.helpcard;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.text.TextPaint;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import space.lopatkin.spb.helpboardgamecard.R;
import space.lopatkin.spb.helpboardgamecard.databinding.ViewLabelPopupBinding;
import space.lopatkin.spb.keyboard.SymbolIcon;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static androidx.work.WorkManager.getInstance;

public class LabelPopupView extends PopupWindow {
    private static final char SEPARATOR = SymbolIcon.SEPARATOR.charAt(0);
    private static final String LABEL_POPUP_VIEW = LabelPopupView.class.getSimpleName();
    private static final int VISIBILITY_DURATION = 2;
    private static final int DEFAULT_VALUE = 0;
    private static final int ICON_WIDTH = 88; // ~ 80 - 100
    private static final int SYMBOL_WIDTH = 38; // ~ 30 - 50
    private ViewLabelPopupBinding binding;
    private LifecycleOwner lifecycleOwner;
    private Context context;
    private int layoutHeight;
    private int layoutPadding;
    private int textSize;

    public LabelPopupView(Context context, ViewLabelPopupBinding binding, LifecycleOwner lifecycleOwner) {
        this.context = context;
        this.binding = binding;
        this.lifecycleOwner = lifecycleOwner;

        layoutHeight = 0;
        layoutPadding = 0;
        textSize = 0;
        obtainStyledAttributes();

        setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(true);
        setElevation(10);
        setContentView(binding.getRoot());
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        super.showAsDropDown(anchor, xoff, yoff);
        timer();
    }

    public void show(View view, MotionEvent motionEvent, TextView textView) {
        String text = textView.getText().toString();
        int xOffset = (int) motionEvent.getX();
        int yOffset = (int) motionEvent.getY();
        int cursorLine = getCursorLine(textView, yOffset);

        if (!text.isEmpty() && cursorLine > 0) {
            String textInLine = getTextInLine(text, cursorLine);

            if (xOffset < getLineWidth(textInLine)) {
                int imageNumber = getImageNumber(textInLine, xOffset);
                String labelText = getLabelText(textInLine, imageNumber);
                if (!labelText.isEmpty()) {
                    binding.textLabelPopup.setText(labelText);
                    setBackgroundWindow(xOffset, textView);
                    int yOffsetPopup = getYOffsetPopup(textView, yOffset);
                    int xOffsetPopup = getXOffsetPopup(xOffset, textView, labelText);
                    showAsDropDown(view, xOffsetPopup, yOffsetPopup);
                }

            }
        }
    }

    private long countImagesIn(String text) {
        CharSequence charSequence = text;
        return charSequence.chars()
                .filter(character -> character == SEPARATOR)
                .count();
    }

    private int getImageNumber(String textInLine, int xOffset) {
        List<Integer> sections = getSectionsWidth(textInLine);

        int imageNumber = 0;
        int widthSum = 0;

        for (int index = 0; index < sections.size(); index++) {
            int widthSection = sections.get(index);
            if (index == 0) {
                widthSum = widthSection;
            } else {
                widthSum = widthSum + widthSection;
            }
            if (xOffset > widthSum) {
                if (widthSection == ICON_WIDTH) {
                    imageNumber = imageNumber + 1;
                }
            } else {
                if (widthSection < ICON_WIDTH || widthSection > ICON_WIDTH) {
                    imageNumber = 0;
                } else {
                    imageNumber = imageNumber + 1;
                }
                break;
            }
        }
        return imageNumber;
    }

    private String getLabelText(String textInLine, int imageNumber) {
        String labelText = "";
        for (int imageIndex = 0; imageIndex < countImagesIn(textInLine); imageIndex++) {
            if (imageIndex + 1 == imageNumber) {

                int firstCharIndex = getIndexSeparator(textInLine, imageIndex);
                int lastCharIndex = firstCharIndex + SymbolIcon.CODENAME_LENGTH;
                String codename = textInLine.substring(firstCharIndex, lastCharIndex);

                labelText = context.getResources().getString(SymbolIcon.getStringResourceFrom(codename));
            }
        }
        return labelText;
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
        int popupHeight = layoutHeight;
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

    private int getLineWidth(String text) {
        int widthInChars = text.chars()
                .map(symbol -> SYMBOL_WIDTH)
                .sum();
        long imagesInLine = countImagesIn(text);
        int widthImagesInChars = (int) (imagesInLine * SymbolIcon.CODENAME_LENGTH * SYMBOL_WIDTH);
        int widthIcons = (int) (imagesInLine * ICON_WIDTH);
        int lineWidth = widthInChars - widthImagesInChars + widthIcons;
        return lineWidth;
    }

    private String getTextInLine(String text, int cursorLine) {
        String[] lines = text.split("\n");

        String textInRow = "";
        if (cursorLine - 1 < lines.length) {
            textInRow = lines[cursorLine - 1];
        }
        return textInRow;
    }

    private ArrayList<Integer> getSectionsWidth(String textInLine) {
        char[] characters = textInLine.toCharArray();
        ArrayList<Integer> sectionsWidth = new ArrayList<>();

        int sectionCount = 0;
        boolean streamSeparator = false;
        int symbolIconCount = 0;

        for (int index = 0; index < characters.length; index++) {
            if (characters[index] != SEPARATOR && !streamSeparator) {

                if (sectionsWidth.isEmpty() || sectionCount >= sectionsWidth.size()) {
                    int sectionWidth = SYMBOL_WIDTH;
                    sectionsWidth.add(sectionCount, sectionWidth);
                } else {
                    int sectionWidth = sectionsWidth.get(sectionCount);
                    sectionWidth = sectionWidth + SYMBOL_WIDTH;
                    sectionsWidth.set(sectionCount, sectionWidth);
                }
                if (index + 1 < characters.length && characters[index + 1] == SEPARATOR) {
                    sectionCount = sectionCount + 1;
                }

            } else {
                streamSeparator = true;
                symbolIconCount = symbolIconCount + 1;
                if (symbolIconCount == 1) {
                    sectionsWidth.add(sectionCount, ICON_WIDTH);
                }
                if (symbolIconCount == SymbolIcon.CODENAME_LENGTH) {
                    streamSeparator = false;
                    symbolIconCount = 0;
                    if (index + 1 < characters.length) {
                        sectionCount = sectionCount + 1;
                    }
                }

            }
        }
        return sectionsWidth;
    }

    private void timer() {
        WorkManager workManager = getInstance(context);

        workManager.cancelAllWorkByTag(LABEL_POPUP_VIEW);

        WorkRequest newTimer = new OneTimeWorkRequest.Builder(LabelTimerWorker.class)
                .addTag(LABEL_POPUP_VIEW)
                .setInitialDelay(VISIBILITY_DURATION, TimeUnit.SECONDS)
                .build();

        workManager.enqueue(newTimer);

        LiveData<WorkInfo> status = workManager.getWorkInfoByIdLiveData(newTimer.getId());

        status.observe(lifecycleOwner, timer -> {
            if (timer.getState() == WorkInfo.State.SUCCEEDED) {
                dismiss();
            }
        });
    }

    private void setBackgroundWindow(int xOffset, TextView textView) {
        int textViewWidth = textView.getWidth();
        if (isNeedRedraw(xOffset, textViewWidth)) {
            binding.layoutPopup.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_label_popup_left));
        } else {
            binding.layoutPopup.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_label_popup_right));
        }
    }

    private boolean isNeedRedraw(int xOffset, int textViewWidth) {
        if (xOffset > textViewWidth / 2) {
            return true;
        }
        return false;
    }

    private int getXOffsetPopup(int xOffset, TextView textView, String nameFull) {
        int xOffsetMirror = xOffset;
        int textViewWidth = textView.getWidth();
        if (isNeedRedraw(xOffset, textViewWidth)) {
            TextPaint paint = new TextPaint();
            paint.setTextSize(textSize);
            float textWidthPx = paint.measureText(nameFull);
            int horizontalPaddingsPx = layoutPadding + layoutPadding;
            int popupWidth = (int) (horizontalPaddingsPx + textWidthPx);
            xOffsetMirror = xOffset - popupWidth;
        }
        return xOffsetMirror;
    }

    private void obtainStyledAttributes() {
        int[] arrayLayouts = new int[]{android.R.attr.layout_width, android.R.attr.layout_height};
        int[] arrayPaddings = new int[]{android.R.attr.padding};
        int[] arrayTexts = new int[]{android.R.attr.textSize};

        TypedArray attributesLayouts = context.obtainStyledAttributes(R.style.TextLabelPopup, arrayLayouts);
        TypedArray attributesPaddings = context.obtainStyledAttributes(R.style.TextLabelPopup, arrayPaddings);
        TypedArray attributesTexts = context.obtainStyledAttributes(R.style.TextLabelPopup, arrayTexts);

        try {
            layoutHeight = attributesLayouts.getDimensionPixelSize(1, DEFAULT_VALUE);
            layoutPadding = attributesPaddings.getDimensionPixelOffset(0, DEFAULT_VALUE);
            textSize = attributesTexts.getDimensionPixelOffset(0, DEFAULT_VALUE);
        } finally {
            attributesLayouts.recycle();
            attributesPaddings.recycle();
            attributesTexts.recycle();
        }
    }

}
