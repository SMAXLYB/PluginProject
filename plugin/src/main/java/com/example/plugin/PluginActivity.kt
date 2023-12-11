package com.example.plugin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button

/**
 * 这是插件app，会被动态安装加载到主app中去
 */
class PluginActivity : BaseActivity() {

    companion object {
        private val TAG = this::class.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plugin)

        val msg = savedInstanceState?.getString("msg")
        Log.d(TAG, "onCreate: 宿主发的消息 = [${msg}]")

        findViewById<Button>(R.id.btn_to_main)?.setOnClickListener {
            startActivity(Intent(proxyActivity, OtherActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }
}