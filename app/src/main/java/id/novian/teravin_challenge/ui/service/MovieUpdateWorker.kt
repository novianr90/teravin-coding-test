package id.novian.teravin_challenge.ui.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.hilt.work.HiltWorker
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import id.novian.teravin_challenge.data.dataSource.network.ApiService
import id.novian.teravin_challenge.data.mapper.toDomain
import id.novian.teravin_challenge.data.mapper.toMovieEntity
import id.novian.teravin_challenge.domain.repository.MovieRepository
import io.reactivex.rxjava3.core.Scheduler
import retrofit2.awaitResponse
import javax.inject.Named

@HiltWorker
class MovieUpdateWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val repo: MovieRepository,
    private val dataSourceNetwork: ApiService
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            val response = dataSourceNetwork.getPopularMovies().awaitResponse()

            if (response.isSuccessful) {
                val res = response.body()!!.results
                    .map {
                        it.toDomain()
                    }
                repo.updateDataOnLocal(res)
            }

            Log.i("worker", "data size is ${response.body()?.results?.size}")

            val intent = Intent("UPDATE_COMPLETE")
            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)

            val intentWorker = Intent("worker_success")
            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intentWorker)

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            val intent = Intent("FAILED_TO_UPDATE")
            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
            Result.failure()
        }
    }


}