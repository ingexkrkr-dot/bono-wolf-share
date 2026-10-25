package com.wolfcustom.vpn.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wolfcustom.vpn.security.AppDependencyChecker

val NeonCyan = Color(0xFF00F0FF)
val NeonBlue = Color(0xFF0A84FF)
val StatusGreen = Color(0xFF10B981)

const val TELEGRAM_SUPPORT_URL = "https://t.me/Mistar_wolfnet"

@Composable
fun WolfNetShareScreen(
    isConnected: Boolean = true,
    connectionDuration: String = "00:43:25",
    uploadData: String = "0.82 GB",
    downloadData: String = "1.34 GB",
    connectedDevicesCount: Int = 3
) {
    val context = LocalContext.current
    var showMenuDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF020617), Color(0xFF030D26), Color(0xFF020617))
                )
            )
            .padding(horizontal = 18.dp, vertical = 12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // 1. Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { showMenuDialog = true }) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
                }

                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .shadow(12.dp, CircleShape, spotColor = NeonCyan)
                            .background(Color(0xFF0B1936), CircleShape)
                            .border(1.5.dp, NeonCyan, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("🐺", fontSize = 18.sp)
                    }

                    Row {
                        Text("Wolf ", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text("Custom ", color = NeonCyan, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text("VPN", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Row {
                    IconButton(onClick = { showMenuDialog = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Options", tint = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 2. Glowing Circular Antenna Gauge
            Box(
                modifier = Modifier.size(240.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawCircle(color = NeonCyan.copy(alpha = 0.15f), radius = size.minDimension / 2)
                    drawCircle(
                        brush = Brush.sweepGradient(listOf(NeonCyan, Color.Transparent, NeonBlue, NeonCyan)),
                        radius = size.minDimension / 2.1f,
                        style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(190.dp)
                        .clip(CircleShape)
                        .background(Brush.radialGradient(listOf(Color(0xFF041838), Color(0xFF020B1C))))
                        .border(2.dp, NeonCyan.copy(alpha = 0.7f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Wifi, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(54.dp))
                        Spacer(modifier = Modifier.height(6.dp))
                        Text("Connection Status", color = Color(0xFF94A3B8), fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            modifier = Modifier
                                .background(Color(0xFF022C22), RoundedCornerShape(20.dp))
                                .border(1.dp, StatusGreen.copy(alpha = 0.6f), RoundedCornerShape(20.dp))
                                .padding(horizontal = 14.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(modifier = Modifier.size(8.dp).background(StatusGreen, CircleShape))
                            Text(if (isConnected) "Active - Connected" else "Inactive", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // 3. Connection Duration
            CyberCard(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(Icons.Default.Timer, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(32.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text("Connection Duration", color = Color(0xFF94A3B8), fontSize = 11.sp)
                        Text(connectionDuration, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 2.sp)
                    }
                }
            }

            // 4. Data Usage Grid
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                CyberCard(modifier = Modifier.weight(1f)) {
                    Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(40.dp).background(Color(0xFF082F49), CircleShape).border(1.dp, NeonCyan.copy(alpha = 0.5f), CircleShape), contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Upload, contentDescription = null, tint = NeonCyan)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("Upload Data", color = Color(0xFF94A3B8), fontSize = 10.sp)
                            Text(uploadData, color = NeonCyan, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                CyberCard(modifier = Modifier.weight(1f)) {
                    Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(40.dp).background(Color(0xFF082F49), CircleShape).border(1.dp, NeonCyan.copy(alpha = 0.5f), CircleShape), contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Download, contentDescription = null, tint = NeonCyan)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("Download Data", color = Color(0xFF94A3B8), fontSize = 10.sp)
                            Text(downloadData, color = NeonCyan, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // 5. Connected Devices Card
            CyberCard(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(42.dp).background(Color(0xFF082F49), CircleShape).border(1.dp, NeonCyan.copy(alpha = 0.5f), CircleShape), contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Devices, contentDescription = null, tint = NeonCyan)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("Connected Devices", color = Color(0xFF94A3B8), fontSize = 11.sp)
                            Text(connectedDevicesCount.toString() + " Devices", color = NeonCyan, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.width(1.dp).height(40.dp).background(Color(0xFF1E293B)))
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("Galaxy A52", color = Color(0xFFE2E8F0), fontSize = 10.sp)
                            Text("Laptop PC", color = Color(0xFFE2E8F0), fontSize = 10.sp)
                            Text("Redmi Note", color = Color(0xFFE2E8F0), fontSize = 10.sp)
                        }
                        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = NeonCyan)
                    }
                }
            }

            // 6. Support & Community Link Card (Author / GitHub / Report a bug -> t.me/Mistar_wolfnet)
            CyberCard(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(TELEGRAM_SUPPORT_URL))
                            context.startActivity(intent)
                        }
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(40.dp).background(Color(0xFF082F49), CircleShape).border(1.dp, NeonCyan.copy(alpha = 0.5f), CircleShape), contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Send, contentDescription = null, tint = NeonCyan)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("Official Channel & Support", color = Color(0xFF94A3B8), fontSize = 11.sp)
                            Text("t.me/Mistar_wolfnet", color = NeonCyan, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    Icon(Icons.Default.OpenInNew, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(18.dp))
                }
            }
        }

        // About / Support Dialog displaying requested links pointing to t.me/Mistar_wolfnet
        if (showMenuDialog) {
            AlertDialog(
                onDismissRequest = { showMenuDialog = false },
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("🐺", fontSize = 20.sp)
                        Text("Wolf Custom VPN", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("معلومات التطبيق والدعم:", color = Color(0xFFE2E8F0), fontWeight = FontWeight.SemiBold)
                        
                        OutlinedButton(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(TELEGRAM_SUPPORT_URL))
                                context.startActivity(intent)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Author: Mistar_wolfnet")
                        }

                        OutlinedButton(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(TELEGRAM_SUPPORT_URL))
                                context.startActivity(intent)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("GitHub: Mistar_wolfnet")
                        }

                        OutlinedButton(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(TELEGRAM_SUPPORT_URL))
                                context.startActivity(intent)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Report a bug: Mistar_wolfnet")
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showMenuDialog = false }) {
                        Text("إغلاق", color = NeonCyan)
                    }
                },
                containerColor = Color(0xFF07142A)
            )
        }
    }
}

/**
 * شاشة الحظر عند عدم تثبيت التطبيق الإجباري core.vpn3jx.xtreme
 */
@Composable
fun MissingDependencyScreen(
    onRetry: () -> Unit = {},
    onClose: () -> Unit = {}
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF020617), Color(0xFF030D26), Color(0xFF020617))
                )
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Color(0xFF1E1128), CircleShape)
                    .border(2.dp, Color(0xFFFF5252), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("⚠️", fontSize = 38.sp)
            }

            Text(
                "يجب تثبيت التطبيق المطلوب أولاً",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                "يتطلب تشغيل Wolf Custom VPN وجود الحزمة:
core.vpn3jx.xtreme
على جهازك لمتابعة الاتصال.",
                color = Color(0xFFE2E8F0),
                fontSize = 14.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(TELEGRAM_SUPPORT_URL))
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(containerColor = NeonCyan),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(0.9f).height(46.dp)
            ) {
                Text("تحميل التطبيق عبر تليجرام", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }

            OutlinedButton(
                onClick = onRetry,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = NeonCyan),
                border = BorderStroke(1.dp, NeonCyan.copy(alpha = 0.7f)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(0.9f).height(46.dp)
            ) {
                Text("إعادة الفحص والتحقق", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }

            TextButton(onClick = onClose) {
                Text("إغلاق التطبيق", color = Color(0xFF94A3B8), fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun CyberCard(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier = modifier
            .shadow(10.dp, RoundedCornerShape(14.dp), spotColor = NeonCyan.copy(alpha = 0.4f))
            .background(Brush.linearGradient(listOf(Color(0xFF04132B), Color(0xFF020B1C))), shape = RoundedCornerShape(14.dp))
            .border(1.2.dp, Brush.horizontalGradient(listOf(NeonCyan.copy(alpha = 0.8f), NeonBlue.copy(alpha = 0.3f), NeonCyan.copy(alpha = 0.8f))), shape = RoundedCornerShape(14.dp))
    ) {
        content()
    }
}