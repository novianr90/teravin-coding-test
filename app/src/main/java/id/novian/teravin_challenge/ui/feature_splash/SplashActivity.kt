package id.novian.teravin_challenge.ui.feature_splash

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint
import id.novian.teravin_challenge.MainActivity
import id.novian.teravin_challenge.R
import id.novian.teravin_challenge.base.BaseActivity
import id.novian.teravin_challenge.data.dataSource.local.LocalDatabase
import id.novian.teravin_challenge.databinding.ActivitySplashBinding
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    @Inject
    lateinit var localDatabase: LocalDatabase

    override val bindingInflater: (LayoutInflater) -> ActivitySplashBinding
        get() = ActivitySplashBinding::inflate

    override fun setup() {
        if (!isNetworkConnected()) {
            showNetworkUnavailableNotification()
        }

        if (!isNetworkConnected() && isLocalDataEmpty()) {
            showNetworkAndDataEmptyNotification()
        }

        navigateToMain()
    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }

    private fun isLocalDataEmpty(): Boolean {
        return localDatabase.dao.getAllPopularMovies().isEmpty.blockingGet()
    }

    private fun showNetworkAndDataEmptyNotification() {
        val channelId = "Network and Data Empty"
        val notificationId = 2
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("No Data Available")
            .setContentText("Local data is empty and the device is not connected to the network.")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()
        notificationManager.notify(notificationId, notification)
    }

    private fun showNetworkUnavailableNotification() {
        val channelId = "Network Unavailable"
        val notificationId = 3
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("No Network Connection")
            .setContentText("The device is not connected to the network.")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        notificationManager.notify(notificationId, notification)
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}