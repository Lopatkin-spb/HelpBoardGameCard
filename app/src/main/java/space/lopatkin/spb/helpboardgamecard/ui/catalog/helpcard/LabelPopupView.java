package space.lopatkin.spb.helpboardgamecard.ui.catalog.helpcard;

import android.content.Context;
import android.content.res.Resources;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import space.lopatkin.spb.helpboardgamecard.databinding.ViewLabelPopupBinding;
import space.lopatkin.spb.helpboardgamecard.ui.addcard.KeyboardButtonIcon;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static androidx.work.WorkManager.getInstance;

public class LabelPopupView extends PopupWindow {
    private static final char SEPARATOR = KeyboardButtonIcon.SEPARATOR.charAt(0);
    private static final String LABEL_POPUP_VIEW = LabelPopupView.class.getSimpleName();
    private static final int VISIBILITY_DURATION = 2;
    public static final int TEXT_LABEL_POPUP_HEIGHT = 53;
    private static final int ICON_WIDTH = 88; // ~ 80 - 100
    private static final int SYMBOL_WIDTH = 38; // ~ 30 - 50
    private ViewLabelPopupBinding binding;
    private LifecycleOwner lifecycleOwner;
    private Context context;

    public LabelPopupView(Context context, ViewLabelPopupBinding binding, LifecycleOwner lifecycleOwner) {
        this.context = context;
        this.binding = binding;
        this.lifecycleOwner = lifecycleOwner;

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
                String nameFull = getNameFull(textInLine, imageNumber);
                if (!nameFull.isEmpty()) {
                    binding.textLabelPopup.setText(nameFull);
                    int yOffsetPopup = getYOffsetPopup(textView, yOffset);
                    showAsDropDown(view, xOffset, yOffsetPopup);
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

    private String getNameFull(String text, int imageNumber) {
        String nameFull = "";
        for (int imageIndex = 0; imageIndex < countImagesIn(text); imageIndex++) {
            if (imageIndex + 1 == imageNumber) {

                int firstCharIndex = getIndexSeparator(text, imageIndex);
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

    private int getLineWidth(String text) {
        int widthInChars = text.chars()
                .map(symbol -> SYMBOL_WIDTH)
                .sum();
        long imagesInLine = countImagesIn(text);
        int widthImagesInChars = (int) (imagesInLine * KeyboardButtonIcon.getLength() * SYMBOL_WIDTH);
        int widthIcons = (int) (imagesInLine * ICON_WIDTH);
        int lineWidth = widthInChars - widthImagesInChars + widthIcons;
        return lineWidth;
    }

    private String getTextInLine(String text, int cursorLine) {
        String[] lines = text.split("\n");
        return lines[cursorLine - 1];
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
                if (symbolIconCount == KeyboardButtonIcon.getLength()) {
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

}
