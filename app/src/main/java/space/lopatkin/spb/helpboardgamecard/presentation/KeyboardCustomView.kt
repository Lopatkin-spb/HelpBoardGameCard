package space.lopatkin.spb.helpboardgamecard.presentation

import android.content.Context
import android.util.AttributeSet
import org.greenrobot.eventbus.EventBus
import space.lopatkin.spb.keyboard.KeyboardView

class KeyboardCustomView : KeyboardView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun createDoneEvent() {
        super.createDoneEvent()
        EventBus.getDefault().post(KeyboardDoneEvent())
    }

}
