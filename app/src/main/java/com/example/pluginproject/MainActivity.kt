package com.example.pluginproject

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import java.io.File

/**
 * 这是主app
 */
class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = this::class.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 请求读写权限
        // if (
        //     ContextCompat.checkSelfPermission(
        //         this,
        //         Manifest.permission.WRITE_EXTERNAL_STORAGE
        //     ) != PackageManager.PERMISSION_GRANTED
        //     || ContextCompat.checkSelfPermission(
        //         this,
        //         Manifest.permission.READ_EXTERNAL_STORAGE
        //     ) != PackageManager.PERMISSION_GRANTED
        // ) {
        //     ActivityCompat.requestPermissions(
        //         this,
        //         arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
        //         0
        //     );
        // }

        findViewById<Button>(R.id.btn_init_plugin).setOnClickListener {
            PluginManager.loadPlugin(this)
        }

        findViewById<Button>(R.id.btn_to_plugin).setOnClickListener {
            // 先判断插件包是否存在
            val privateDir = getExternalFilesDir(null)
            val pluginApk = File((privateDir?.absolutePath ?: "") + File.separator + PluginManager.PLUGIN_APK_NAME)
            if (!pluginApk.exists()) {
                Toast.makeText(this, "插件包不存在", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 跳转
            val intent = Intent(this, ProxyActivity::class.java).apply {
                putExtra("className", "com.example.plugin.PluginActivity")
            }
            startActivity(intent)
        }
    }
}