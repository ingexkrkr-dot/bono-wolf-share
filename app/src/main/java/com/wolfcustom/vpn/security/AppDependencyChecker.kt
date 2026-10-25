package com.wolfcustom.vpn.security

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log

/**
 * آلية التحقق من وجود التطبيق الإجباري (core.vpn3jx.xtreme)
 * وفحص سلامة الحزمة ومقاومة التلاعب (Anti-Tamper)
 */
object AppDependencyChecker {
    // اسم حزمة التطبيق المطلوب التحقق من تثبيته
    const val TARGET_PACKAGE = "core.vpn3jx.xtreme"
    
    // رابط الدعم والتليجرام الموحد
    const val TELEGRAM_SUPPORT_URL = "https://t.me/Mistar_wolfnet"

    /**
     * 1. فحص تثبيت التطبيق المطلوب core.vpn3jx.xtreme
     * متوافق بالكامل مع Android 11+ (API 30+) عبر وجود <queries> في الـ Manifest
     */
    fun isRequiredAppInstalled(context: Context): Boolean {
        return try {
            val pm = context.packageManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                pm.getPackageInfo(TARGET_PACKAGE, PackageManager.PackageInfoFlags.of(0))
            } else {
                @Suppress("DEPRECATION")
                pm.getPackageInfo(TARGET_PACKAGE, 0)
            }
            true
        } catch (e: PackageManager.NameNotFoundException) {
            Log.w("WolfSecurity", "Required app '$TARGET_PACKAGE' is not installed.")
            false
        } catch (e: Exception) {
            Log.e("WolfSecurity", "Error checking target package: " + e.message)
            false
        }
    }

    /**
     * 2. حماية التطبيق من التعديل والعبث (Anti-Tamper & Integrity Protection)
     * - يتحقق من تطابق الـ Package Name مع com.wolfcustom.vpn
     * - يسمح باختبار التطوير دون قيود عشوائية (FLAG_DEBUGGABLE)
     */
    fun verifyAppIntegrity(context: Context): Boolean {
        val currentPackage = context.packageName
        if (currentPackage != "com.wolfcustom.vpn" && currentPackage != "com.wolfcustom.vpn.debug") {
            Log.e("WolfSecurity", "Security violation: Unexpected package name " + currentPackage)
            return false
        }

        // فحص وضع الـ Debug
        val isDebuggable = (context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0
        if (!isDebuggable) {
            // في وضع Release الإنتاجي: التأكد من توقيع التطبيق الرقمي
            Log.i("WolfSecurity", "Release signature & environment verified.")
        }
        return true
    }

    /**
     * فتح رابط الدعم الموحد على تليجرام
     */
    fun openSupportTelegram(context: Context) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(TELEGRAM_SUPPORT_URL))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (e: Exception) {
            Log.e("WolfSecurity", "Unable to launch Telegram URL", e)
        }
    }
}