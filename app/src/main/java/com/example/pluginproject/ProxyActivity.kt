package com.example.pluginproject

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.os.PersistableBundle
import androidx.core.os.bundleOf
import com.example.stander.ActivityInterface

/**
 * 代理activity，相当与一个壳，占位用，里面的东西都是插件activity中的, 插件activity中的回调都是在这里进行转发执行
 * 宿主act -> 代理act -> 插件act
 */
class ProxyActivity : Activity() {

    companion object {
        private val TAG = this::class.simpleName
        private const val KEY_CLAZZ_NAME = "className"
    }

    private lateinit var activityInterface: ActivityInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 要跳转的插件activity
        val className = intent?.getStringExtra(KEY_CLAZZ_NAME)

        val pluginActivityClass = classLoader.loadClass(className)
        val constructor = pluginActivityClass.getConstructor()
        val pluginActivity = constructor.newInstance()
        activityInterface = pluginActivity as ActivityInterface
        activityInterface.attachToAppContext(this) // 注入上下文

        activityInterface.onCreate(bundleOf("msg" to "这是宿主发给插件的消息"))
    }

    override fun startActivity(intent: Intent?) {
        // 插件内部跳转时，重新开一个proxy
        val className = intent?.getStringExtra(KEY_CLAZZ_NAME)
        val newIntent = Intent(this, ProxyActivity::class.java)
            .apply {
                putExtra(KEY_CLAZZ_NAME, className)
            }
        super.startActivity(newIntent)
    }

    override fun onStart() {
        super.onStart()
        activityInterface.onStart()
    }

    override fun onResume() {
        super.onResume()
        activityInterface.onResume()
    }

    override fun onPause() {
        super.onPause()
        activityInterface.onPause()
    }

    override fun onStop() {
        super.onStop()
        activityInterface.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        activityInterface.onDestroy()
    }

    override fun getResources(): Resources {
        // 不能使用宿主里面的resource，要使用插件里面的
        // return super.getResources()
        return PluginManager.resources
    }

    override fun getClassLoader(): ClassLoader {
        // 不能使用宿主里面的
        // return super.getClassLoader()
        return PluginManager.dexClassLoader
    }
}