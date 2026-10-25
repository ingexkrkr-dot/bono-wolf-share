package com.wolfcustom.vpn

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

/**
 * خدمة مشاركة وتأمين الاتصال بالخلفية
 * حافظت على بنية الخدمة وخصائص الـ Foreground Service
 */
class TetherService : Service() {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // إدارة جلسة VPN ومشاركة الاتصال المشفر
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}