package id.novian.teravin_challenge.ui.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MovieUpdateReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        p0?.let {
            val serviceIntent = Intent(it, MovieUpdateService::class.java)
            it.startService(serviceIntent)
        }
    }
}