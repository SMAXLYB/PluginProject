package com.example.plugin

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import com.example.stander.ActivityInterface

/**
 * 所有插件activity的基类，名义上是activity，但是实际没有activity的能力，要借助外部activity
 * 这里继承activity只是为了过编译
 */
open class BaseActivity : ActivityInterface {

    protected lateinit var proxyActivity: Activity

    override fun attachToAppContext(activity: Activity) {
        proxyActivity = activity
    }

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
    }

    @SuppressLint("MissingSuperCall")
    override fun onStart() {
    }

    @SuppressLint("MissingSuperCall")
    override fun onResume() {
    }

    @SuppressLint("MissingSuperCall")
    override fun onPause() {
    }

    @SuppressLint("MissingSuperCall")
    override fun onStop() {
    }

    @SuppressLint("MissingSuperCall")
    override fun onDestroy() {
    }

    fun setContentView(@LayoutRes layoutId: Int) {
        proxyActivity.setContentView(layoutId)
    }

    fun <T> findViewById(@IdRes resId: Int): T? {
        return proxyActivity.findViewById(resId)
    }

    /**
     * 插件activity跳转到别的activity
     */
    fun startActivity(intent: Intent) {
        val newIntent = Intent().apply {
            putExtra("className", intent.component?.className)
        }
        proxyActivity.startActivity(newIntent)
    }
}