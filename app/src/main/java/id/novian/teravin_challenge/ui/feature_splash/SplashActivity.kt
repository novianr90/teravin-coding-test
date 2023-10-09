package id.novian.teravin_challenge.ui.feature_splash

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.app.NotificationCompat
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import id.novian.teravin_challenge.MainActivity
import id.novian.teravin_challenge.R
import id.novian.teravin_challenge.base.BaseActivity
import id.novian.teravin_challenge.data.dataSource.local.LocalDatabase
import id.novian.teravin_challenge.databinding.ActivitySplashBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    @Inject
    lateinit var localDatabase: LocalDatabase

    override val bindingInflater: (LayoutInflater) -> ActivitySplashBinding
        get() = ActivitySplashBinding::inflate

    override fun setup() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)

                navigateToMain()
            }

            override fun onLost(network: Network) {
                super.onLost(network)

                showNetworkUnavailableNotification()
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        }

        if (!isNetworkConnected() && isLocalDataEmpty()) {
            showNetworkAndDataEmptyNotification()
        }
    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }

    private fun isLocalDataEmpty(): Boolean {

        var existOrNot = false

        lifecycleScope.launch {
            existOrNot = localDatabase.dao.getAllPopularMovies().isEmpty()
        }

        return existOrNot
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