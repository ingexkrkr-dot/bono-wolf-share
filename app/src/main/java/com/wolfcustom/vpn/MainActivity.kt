package com.wolfcustom.vpn

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.wolfcustom.vpn.ui.WolfNetShareScreen
import com.wolfcustom.vpn.ui.MissingDependencyScreen
import com.wolfcustom.vpn.security.AppDependencyChecker

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. فحص سلامة وتوقيع التطبيق (Anti-Tamper & Integrity Check)
        AppDependencyChecker.verifyAppIntegrity(this)

        // 2. التحقق من تثبيت تطبيق core.vpn3jx.xtreme المطلوب عند تشغيل التطبيق
        val isTargetAppInstalled = AppDependencyChecker.isRequiredAppInstalled(this)

        if (!isTargetAppInstalled) {
            Toast.makeText(
                this,
                "يجب تثبيت التطبيق المطلوب أولاً",
                Toast.LENGTH_LONG
            ).show()
        }

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (isTargetAppInstalled) {
                        // إذا كان التطبيق مثبتاً، يستمر Wolf Custom VPN في التشغيل بشكل طبيعي
                        WolfNetShareScreen()
                    } else {
                        // إذا لم يكن التطبيق مثبتاً، منع المستخدم من الدخول وإظهار شاشة توجيه واضحة
                        MissingDependencyScreen(
                            onRetry = {
                                if (AppDependencyChecker.isRequiredAppInstalled(this@MainActivity)) {
                                    recreate()
                                } else {
                                    Toast.makeText(
                                        this@MainActivity,
                                        "يجب تثبيت التطبيق المطلوب أولاً",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                            onClose = { finish() }
                        )
                    }
                }
            }
        }
    }
}