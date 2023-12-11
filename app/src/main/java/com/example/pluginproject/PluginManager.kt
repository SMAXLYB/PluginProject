package com.example.pluginproject

import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import android.util.Log
import dalvik.system.DexClassLoader
import java.io.File

/**
 * 插件管理工具，通过此工具来加载插件apk
 */
object PluginManager {
    private val TAG = this::class.simpleName
    const val PLUGIN_APK_NAME = "plugin.apk"
    private const val PLUGIN_DEX_DIR = "pluginDir"

    // 插件apk的class加载器
    lateinit var dexClassLoader: DexClassLoader

    // 插件apk的资源工具
    lateinit var resources: Resources

    fun loadPlugin(context: Context) {

        Log.d(TAG, "loadPlugin: 插件初始化开始.....")

        // 应用私有目录
        val privateDir = context.getExternalFilesDir(null)
        Log.d(TAG, "loadPlugin: 私有目录 = [${privateDir?.absolutePath}]")

        // 1.获取插件apk，一般是从网上下到指定目录，这里直接本地放到目录下面就行
        val pluginApk = File((privateDir?.absolutePath ?: "") + File.separator + PLUGIN_APK_NAME)
        if (!pluginApk.exists()) {
            Log.d(TAG, "loadPlugin: 插件apk不存在")
            return
        }
        val pluginApkPath = pluginApk.absolutePath // 查看apk路径

        // 2.加载插件apk中的dex，也就是class文件
        val pluginDexDir = context.getDir(PLUGIN_DEX_DIR, Context.MODE_PRIVATE) //创建一个Dex输出目录
        Log.d(TAG, "loadPlugin: 插件Dex输出目录 = [${pluginDexDir.absolutePath}]")
        // 参数含义：（dex或apk目录，优化后的dex输出目录，so库目录，父类加载器）
        dexClassLoader = DexClassLoader(pluginApkPath, pluginDexDir.absolutePath, null, context.classLoader)

        // 3.加载插件apk中的资源
        val assetManager = AssetManager::class.java.newInstance()
        // 反射把资源路径设置进去
        val method = assetManager::class.java.getMethod("addAssetPath", String::class.java)
        method.invoke(assetManager, pluginApkPath)
        val appResources = context.resources
        resources = Resources(assetManager, appResources.displayMetrics, appResources.configuration)

        Log.d(TAG, "loadPlugin: 插件初始化完毕!")
    }
}