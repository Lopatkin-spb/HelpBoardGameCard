package space.lopatkin.spb.helpboardgamecard.presentation.catalog.helpcard

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import space.lopatkin.spb.keyboard.KeyboardView
import space.lopatkin.spb.keyboard.SymbolIcon
import java.util.stream.IntStream

class IconTextView : AppCompatTextView {

    private val SEPARATOR: Char = SymbolIcon.SEPARATOR[0]
    private var context: Context

    constructor(context: Context) : super(context) {
        this.context = context
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        this.context = context
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        this.context = context
    }

    override fun setText(charSequence: CharSequence, type: BufferType) {
        val text: String = charSequence.toString()
        val textPainted: Spannable = SpannableString(text)

        if (!text.isEmpty() && countImagesIn(text) > 0) {
            setSpansToText(textPainted, text)
        }

        super.setText(textPainted, type)
    }

    private fun setSpansToText(textPainted: Spannable, text: String) {
        for (indexImage in 0 until countImagesIn(text)) {
            val firstCharIndex: Int = getIndexSeparator(text, indexImage.toInt())
            val lastCharIndex: Int = firstCharIndex + SymbolIcon.CODENAME_LENGTH
            setImageToWord(textPainted, text, firstCharIndex, lastCharIndex)
        }
    }

    private fun setImageToWord(textPainted: Spannable, text: String, firstCharIndex: Int, lastCharIndex: Int) {
        val codename: String = text.substring(firstCharIndex, lastCharIndex)
        val image: ImageSpan = ImageSpan(
            context,
            SymbolIcon.getDrawableResourceFrom(codename),
            KeyboardView.DYNAMIC_DRAWABLE_SPAN
        )
        textPainted.setSpan(image, firstCharIndex, lastCharIndex, KeyboardView.SPANNABLE_SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    private fun countImagesIn(text: String): Long {
        val charSequence: CharSequence = text
        return charSequence.chars()
            .filter { character: Int -> character == SEPARATOR.code }
            .count()
    }

    private fun getIndexSeparator(text: String, numberImage: Int): Int {
        val characters: Array<Char> = text.toCharArray().toTypedArray()
        val indexes: Array<Int> = IntStream.range(0, characters.size)
            .filter { index: Int -> characters[index] == SEPARATOR }
            .toArray()
            .toTypedArray()
        return indexes[numberImage]
    }

}
