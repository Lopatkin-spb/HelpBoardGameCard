package space.lopatkin.spb.helpboardgamecard.presentation;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import space.lopatkin.spb.keyboard.KeyboardView;

public class KeyboardCustomView extends KeyboardView {
    public KeyboardCustomView(@NonNull @NotNull Context context) {
        super(context);
    }

    public KeyboardCustomView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public KeyboardCustomView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void createDoneEvent() {
        super.createDoneEvent();

        EventBus.getDefault().post(new KeyboardDoneEvent());
    }

}
