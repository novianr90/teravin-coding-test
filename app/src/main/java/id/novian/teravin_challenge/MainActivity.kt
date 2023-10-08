package id.novian.teravin_challenge

import android.content.Intent
import android.content.IntentFilter
import android.view.LayoutInflater
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import dagger.hilt.android.AndroidEntryPoint
import id.novian.teravin_challenge.base.BaseActivity
import id.novian.teravin_challenge.databinding.ActivityMainBinding
import id.novian.teravin_challenge.ui.service.MovieUpdateReceiver
import id.novian.teravin_challenge.ui.service.MovieUpdateService

@AndroidEntryPoint
class MainActivity: BaseActivity<ActivityMainBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    private val broadcastReceiver = MovieUpdateReceiver()

    override fun setup() {

        val serviceIntent = Intent(this, MovieUpdateService::class.java)
        startService(serviceIntent)

        val filter = IntentFilter("UPDATE_COMPLETE")
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
    }
}