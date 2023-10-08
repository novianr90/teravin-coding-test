package id.novian.teravin_challenge

import android.content.IntentFilter
import android.view.LayoutInflater
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import dagger.hilt.android.AndroidEntryPoint
import id.novian.teravin_challenge.base.BaseActivity
import id.novian.teravin_challenge.databinding.ActivityMainBinding
import id.novian.teravin_challenge.ui.service.MovieUpdateReceiver

@AndroidEntryPoint
class MainActivity: BaseActivity<ActivityMainBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun setup() {
        val broadcastReceiver = MovieUpdateReceiver()
        val filter = IntentFilter("MovieUpdateCompleted")
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter)
    }
}