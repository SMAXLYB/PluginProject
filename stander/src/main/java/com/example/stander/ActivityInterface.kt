package com.example.stander

import android.app.Activity
import android.os.Bundle

/**
 * 插件activity和主activity都要依赖的标准，也是通信接口
 * 定义了插件activity的生命周期回调方法
 */
interface ActivityInterface {
    /**
     * 将主activity传入，后续插件activity都使用这个作为context，因为插件activity本身是没有上下文环境的
     */
    fun attachToAppContext(activity: Activity)
    fun onCreate(bundle: Bundle?)
    fun onStart()
    fun onResume()
    fun onPause()
    fun onStop()
    fun onDestroy()
}