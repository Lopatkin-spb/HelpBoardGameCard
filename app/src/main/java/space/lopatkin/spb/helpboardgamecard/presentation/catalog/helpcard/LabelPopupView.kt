package space.lopatkin.spb.helpboardgamecard.presentation.catalog.helpcard

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.text.TextPaint
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkRequest
import space.lopatkin.spb.helpboardgamecard.R
import space.lopatkin.spb.helpboardgamecard.databinding.ViewLabelPopupBinding
import space.lopatkin.spb.keyboard.SymbolIcon
import java.util.concurrent.TimeUnit
import java.util.stream.IntStream
import kotlin.math.ceil

class LabelPopupView(
    private val context: Context,
    private val binding: ViewLabelPopupBinding,
    private val lifecycleOwner: LifecycleOwner
) : PopupWindow() {

    private val SEPARATOR: Char = SymbolIcon.SEPARATOR[0]
    private val LABEL_POPUP_VIEW: String = this::class.java.simpleName
    private val VISIBILITY_DURATION: Long = 2
    private val DEFAULT_VALUE: Int = 0
    private val SYMBOL_WIDTH: Int = 38 // ~ 30 - 50
    private val ICON_WIDTH: Int = 88 // ~ 80 - 100

    private var layoutHeight: Int = 0
    private var layoutPadding: Int = 0
    private var textSize: Int = 0

    init {
        obtainStyledAttributes()
        width = WindowManager.LayoutParams.WRAP_CONTENT
        height = WindowManager.LayoutParams.WRAP_CONTENT
        isOutsideTouchable = true
        elevation = 10f
        contentView = binding.root
    }

    override fun showAsDropDown(anchor: View, xoff: Int, yoff: Int) {
        super.showAsDropDown(anchor, xoff, yoff)
        timer()
    }

    fun show(view: View, motionEvent: MotionEvent, textView: TextView) {
        val text: String = textView.text.toString()
        val xOffset: Int = motionEvent.x.toInt()
        val yOffset: Int = motionEvent.y.toInt()
        val cursorLine: Int = getCursorLine(textView, yOffset)

        if (!text.isEmpty() && cursorLine > 0) {
            val textInLine: String = getTextInLine(text, cursorLine)

            if (xOffset < getLineWidth(textInLine)) {
                val imageNumber: Int = getImageNumber(textInLine, xOffset)
                val labelText: String = getLabelText(textInLine, imageNumber)
                if (!labelText.isEmpty()) {
                    binding.textLabelPopup.text = labelText
                    setBackgroundWindow(xOffset, textView)
                    val yOffsetPopup: Int = getYOffsetPopup(textView, yOffset)
                    val xOffsetPopup: Int = getXOffsetPopup(xOffset, textView, labelText)
                    showAsDropDown(view, xOffsetPopup, yOffsetPopup)
                }
            }
        }
    }

    private fun countImagesIn(text: String): Long {
        val charSequence: CharSequence = text
        return charSequence.chars()
            .filter { character -> character == SEPARATOR.code }
            .count()
    }

    private fun getImageNumber(textInLine: String, xOffset: Int): Int {
        val sections: List<Int> = getSectionsWidth(textInLine)
        var imageNumber: Int = 0
        var widthSum: Int = 0

        for (index in 0 until sections.size) {
            val widthSection: Int = sections[index]

            if (index == 0) {
                widthSum = widthSection
            } else {
                widthSum = widthSum + widthSection
            }
            if (xOffset > widthSum) {
                if (widthSection == ICON_WIDTH) {
                    imageNumber = imageNumber + 1
                }
            } else {

                if (widthSection < ICON_WIDTH || widthSection > ICON_WIDTH) {
                    imageNumber = 0
                } else {
                    imageNumber = imageNumber + 1
                }
                break
            }
        }
        return imageNumber
    }

    private fun getLabelText(textInLine: String, imageNumber: Int): String {
        var labelText: String = ""
        for (imageIndex in 0 until countImagesIn(textInLine)) {

            if ((imageIndex + 1).toInt() == imageNumber) {
                val firstCharIndex: Int = getIndexSeparator(textInLine, imageIndex.toInt())
                val lastCharIndex: Int = firstCharIndex + SymbolIcon.CODENAME_LENGTH
                val codename: String = textInLine.substring(firstCharIndex, lastCharIndex)
                labelText = context.resources.getString(SymbolIcon.getStringResourceFrom(codename))
                //maybe set break
            }
        }
        return labelText
    }

    private fun getIndexSeparator(text: String, numberImage: Int): Int {
        val characters: Array<Char> = text.toCharArray().toTypedArray()
        val indexes: Array<Int> = IntStream.range(0, characters.size)
            .filter { index -> characters[index] == SEPARATOR }
            .toArray()
            .toTypedArray()
        return indexes[numberImage]
    }

    private fun getYOffsetPopup(textView: TextView, yOffset: Int): Int {
        val lineHeight: Int = textView.height / textView.lineCount
        val sumLinesHeight: Int = textView.height - (getCursorLine(textView, yOffset) - 1) * lineHeight
        val popupHeight: Int = layoutHeight
        val yOffsetPopup: Int = -sumLinesHeight - popupHeight
        return yOffsetPopup
    }

    private fun getCursorLine(textView: TextView, yOffset: Int): Int {
        val viewHeight: Int = textView.height
        val linesCount: Int = textView.lineCount
        val cursorLineFloat: Float = yOffset.toFloat() / viewHeight * linesCount
        val cursorLine: Int = ceil(cursorLineFloat).toInt()
        return cursorLine
    }

    private fun getLineWidth(text: String): Int {
        val widthInChars: Int = text.chars()
            .map { symbol -> SYMBOL_WIDTH }
            .sum()
        val imagesInLine: Long = countImagesIn(text)
        val widthImagesInChars: Int = (imagesInLine * SymbolIcon.CODENAME_LENGTH * SYMBOL_WIDTH).toInt()
        val widthIcons: Int = (imagesInLine * ICON_WIDTH).toInt()
        val lineWidth: Int = widthInChars - widthImagesInChars + widthIcons
        return lineWidth
    }

    private fun getTextInLine(text: String, cursorLine: Int): String {
        val lines: List<String> = text.split("\n")

        var textInRow: String = ""
        val indexCursorLine: Int = cursorLine - 1
        if (indexCursorLine < lines.size) {
            textInRow = lines[indexCursorLine]
        }
        return textInRow
    }

    private fun getSectionsWidth(textInLine: String): MutableList<Int> {
        val characters: CharArray = textInLine.toCharArray()
        val totalWidth: MutableList<Int> = mutableListOf()
        var sectionCount: Int = 0
        var streamSeparator: Boolean = false
        var symbolIconCount: Int = 0

        for (index in 0 until characters.size) {
            if (characters[index] != SEPARATOR && !streamSeparator) {

                if (totalWidth.isEmpty() || sectionCount >= totalWidth.size) {
                    val sectionWidth: Int = SYMBOL_WIDTH
                    totalWidth.add(sectionCount, sectionWidth)
                } else {
                    var sectionWidth: Int = totalWidth[sectionCount]
                    sectionWidth = sectionWidth + SYMBOL_WIDTH
                    totalWidth.add(sectionCount, sectionWidth)
                }
                if (index + 1 < characters.size && characters[index + 1] == SEPARATOR) {
                    sectionCount = sectionCount + 1
                }
            } else {
                streamSeparator = true
                symbolIconCount = symbolIconCount + 1
                if (symbolIconCount == 1) {
                    totalWidth.add(sectionCount, ICON_WIDTH)
                }
                if (symbolIconCount == SymbolIcon.CODENAME_LENGTH) {
                    streamSeparator = false
                    symbolIconCount = 0
                    if (index + 1 < characters.size) {
                        sectionCount = sectionCount + 1
                    }
                }
            }
        }
        return totalWidth
    }

    private fun timer() {
        val workManager: WorkManager = WorkManager.getInstance(context)
        workManager.cancelAllWorkByTag(LABEL_POPUP_VIEW)
        val newTimer: WorkRequest = OneTimeWorkRequest.Builder(LabelTimerWorker::class.java)
            .addTag(LABEL_POPUP_VIEW)
            .setInitialDelay(VISIBILITY_DURATION, TimeUnit.SECONDS)
            .build()
        workManager.enqueue(newTimer)
        val status: LiveData<WorkInfo> = workManager.getWorkInfoByIdLiveData(newTimer.id)
        status.observe(lifecycleOwner) { timer: WorkInfo ->
            if (timer.state == WorkInfo.State.SUCCEEDED) {
                dismiss()
            }
        }
    }

    private fun setBackgroundWindow(xOffset: Int, textView: TextView) {
        val textViewWidth: Int = textView.width
        if (isNeedRedraw(xOffset, textViewWidth)) {
            binding.layoutPopup.background =
                ContextCompat.getDrawable(context, R.drawable.bg_label_popup_left)
        } else {
            binding.layoutPopup.background =
                ContextCompat.getDrawable(context, R.drawable.bg_label_popup_right)
        }
    }

    private fun isNeedRedraw(xOffset: Int, textViewWidth: Int): Boolean {
        if (xOffset > textViewWidth / 2) {
            return true
        }
        return false
    }

    private fun getXOffsetPopup(xOffset: Int, textView: TextView, labelText: String): Int {
        var xOffsetMirror: Int = xOffset
        val textViewWidth: Int = textView.width
        if (isNeedRedraw(xOffset, textViewWidth)) {
            val paint: TextPaint = TextPaint()
            paint.textSize = textSize.toFloat()
            val textWidthPx: Float = paint.measureText(labelText)
            val horizontalPaddingsPx: Int = layoutPadding + layoutPadding //left & right
            val popupWidth: Int = (horizontalPaddingsPx + textWidthPx).toInt()
            xOffsetMirror = xOffset - popupWidth
        }
        return xOffsetMirror
    }

    @SuppressLint("ResourceType")
    private fun obtainStyledAttributes() {
        val arrayLayouts: IntArray = intArrayOf(android.R.attr.layout_width, android.R.attr.layout_height)
        val arrayPaddings: IntArray = intArrayOf(android.R.attr.padding)
        val arrayTexts: IntArray = intArrayOf(android.R.attr.textSize)
        val attributesLayouts: TypedArray = context.obtainStyledAttributes(R.style.TextLabelPopup, arrayLayouts)
        val attributesPaddings: TypedArray = context.obtainStyledAttributes(R.style.TextLabelPopup, arrayPaddings)
        val attributesTexts: TypedArray = context.obtainStyledAttributes(R.style.TextLabelPopup, arrayTexts)
        try {
            layoutHeight = attributesLayouts.getDimensionPixelSize(1, DEFAULT_VALUE)
            layoutPadding = attributesPaddings.getDimensionPixelOffset(0, DEFAULT_VALUE)
            textSize = attributesTexts.getDimensionPixelOffset(0, DEFAULT_VALUE)
        } finally {
            attributesLayouts.recycle()
            attributesPaddings.recycle()
            attributesTexts.recycle()
        }
    }

    private fun dpTpPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

}
