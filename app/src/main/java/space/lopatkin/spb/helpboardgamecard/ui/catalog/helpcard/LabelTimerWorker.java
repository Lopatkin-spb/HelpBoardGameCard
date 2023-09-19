package space.lopatkin.spb.helpboardgamecard.ui.catalog.helpcard;


import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import org.jetbrains.annotations.NotNull;

public class LabelTimerWorker extends Worker {

    public LabelTimerWorker(@NonNull @NotNull Context context, @NonNull @NotNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @NotNull
    @Override
    public Result doWork() {
        return Result.success();
    }

}
