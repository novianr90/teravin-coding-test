package id.novian.teravin_challenge

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.view.LayoutInflater
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import id.novian.teravin_challenge.base.BaseActivity
import id.novian.teravin_challenge.databinding.ActivityMainBinding
import id.novian.teravin_challenge.ui.feature_home.HomeFragment
import id.novian.teravin_challenge.ui.service.MovieUpdateReceiver
import id.novian.teravin_challenge.ui.service.MovieUpdateService

@AndroidEntryPoint
class MainActivity: BaseActivity<ActivityMainBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    private val broadcastReceiver = MovieUpdateReceiver()

    private val workerReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            if (p1?.action == "worker_success") {
                showSnackbar("Local storage successfully updated")
            }
        }
    }

    override fun setup() {

        val serviceIntent = Intent(this, MovieUpdateService::class.java)
        startService(serviceIntent)

        val filter = IntentFilter("UPDATE_COMPLETE")
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter)

        val filterWorker = IntentFilter("worker_success")
        LocalBroadcastManager.getInstance(this).registerReceiver(workerReceiver, filterWorker)

        val fragmentHome = HomeFragment.newInstance()
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragmentHome)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
        LocalBroadcastManager.getInstance(this).unregisterReceiver(workerReceiver)
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }
}