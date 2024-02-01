package space.lopatkin.spb.helpboardgamecard.presentation.catalog.helpcard

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class LabelTimerWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        return Result.success()
    }

}
