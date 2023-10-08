package id.novian.teravin_challenge.ui.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import dagger.hilt.android.AndroidEntryPoint
import id.novian.teravin_challenge.domain.repository.MovieRepository
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class MovieUpdateService: Service() {

    @Inject
    lateinit var repo: MovieRepository

    @Inject
    @Named("IO")
    lateinit var io: Scheduler

    private var updated = false

    private val CHANNEL_ID = "MovieUpdateChannel"
    private val NOTIFICATION_ID = 1
    private lateinit var localBroadcastManager: LocalBroadcastManager

    private var disposable: Disposable? = null

    private val updateHandler = Handler(Looper.getMainLooper())

    private val updateRunnable = object : Runnable {
        override fun run() {
            disposable = repo.getAllPopularMovies()
                .flatMapCompletable {
                    repo.updateDataOnLocal(it)
                }
                .subscribeOn(io)
                .subscribe({
                    updated = true
                }, {
                    it.printStackTrace()
                    // Send notif error
                    updated = false
                })

            // Send Broadcast
            val updateIntent = if (updated) {
                Intent("UPDATE_COMPLETE")
            } else {
                Intent("FAILED_TO_UPDATE")
            }
            localBroadcastManager.sendBroadcast(updateIntent)
            showNotification(updated)

            // Set run every 1m
            updateHandler.postDelayed(this, 60000)
        }
    }

    override fun onCreate() {
        super.onCreate()
        localBroadcastManager = LocalBroadcastManager.getInstance(this)
        createNotificationChannel()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        updateHandler.post(updateRunnable)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
        updateHandler.removeCallbacks(updateRunnable)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Movie Update Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun showNotification(updateCondition: Boolean) {

        val message = if (updateCondition) {
            "Old data has been deleted and new data is available."
        } else {
            "Data failed to update."
        }

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Movie Update")
            .setContentText(message)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

}