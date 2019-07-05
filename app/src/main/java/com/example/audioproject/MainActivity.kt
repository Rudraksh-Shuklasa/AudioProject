package com.example.audioproject

import android.content.Context
import android.media.AudioManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.os.Build
import android.app.NotificationManager



class MainActivity : AppCompatActivity() {

    lateinit var audioManger:AudioManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        audioManger = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        btnMainActivitySilent.setOnClickListener {
           audioManger.ringerMode=AudioManager.RINGER_MODE_SILENT
        }

        btnMainActivityNormal.setOnClickListener {
            audioManger.ringerMode=AudioManager.RINGER_MODE_NORMAL
        }

        btnMainActivityVibrate.setOnClickListener {
            audioManger.ringerMode=AudioManager.RINGER_MODE_VIBRATE

        }


    }



    private fun requestDoNotDisturbPermissionOrSetDoNotDisturbApi23AndUp() {
        //TO SUPPRESS API ERROR MESSAGES IN THIS FUNCTION, since Ive no time to figrure our Android SDK suppress stuff
        if (Build.VERSION.SDK_INT < 23) {
            return
        }

        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (notificationManager.isNotificationPolicyAccessGranted) {
            val audioManager = applicationContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL
        } else {
            // Ask the user to grant access
            val intent = Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Check which request we're responding to
        if (requestCode == 1) {
            this.requestDoNotDisturbPermissionOrSetDoNotDisturbApi23AndUp()
        }
    }
}
