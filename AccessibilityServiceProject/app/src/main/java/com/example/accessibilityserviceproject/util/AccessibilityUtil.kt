package com.example.accessibilityserviceproject.util

import android.text.TextUtils
import android.text.TextUtils.SimpleStringSplitter
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log

/**
 * @author 张坚鸿
 * @date 2019/9/6 16:32
 */
object AccessibilityUtil {
    fun isSettingOpen(service: Class<*>, cxt: Context): Boolean {
        try {
            val enable = Settings.Secure.getInt(
                cxt.contentResolver,
                Settings.Secure.ACCESSIBILITY_ENABLED,
                0
            )
            if (enable != 1) return false
            val services = Settings.Secure.getString(
                cxt.contentResolver,
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
            )
            if (!TextUtils.isEmpty(services)) {
                val split = SimpleStringSplitter(':')
                split.setString(services)
                // 遍历所有已开启的辅助服务名
                while (split.hasNext()) {
                    if (split.next()
                            .equals(cxt.packageName + "/" + service.name, ignoreCase = true)
                    ) {
                        return true
                    }
                }
            }
        } catch (e: Throwable) { //若出现异常，则说明该手机设置被厂商篡改了,需要适配
            Log.e(ContentValues.TAG, "isSettingOpen: " + e.message)
        }
        return false
    }

    /**
     * 跳转到系统设置：开启辅助服务
     */
    fun jumpToSetting(cxt: Context) {
        try {
            cxt.startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        } catch (e: Throwable) { //若出现异常，则说明该手机设置被厂商篡改了,需要适配
            try {
                val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                cxt.startActivity(intent)
            } catch (e2: Throwable) {
                Log.e(ContentValues.TAG, "jumpToSetting: " + e2.message)
            }
        }
    }
}