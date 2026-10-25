# ====================================================================
# قواعد الحماية ومقاومة التلاعب والتشفير لتطبيق Wolf Custom VPN
# ====================================================================

# 1. حماية التوقيع والبيانات الوصفية
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes InnerClasses
-keepattributes EnclosingMethod

# 2. الحفاظ على واجهات ونواة Shizuku و AIDL و Native Go
-keep class dev.rikka.shizuku.** { *; }
-keep interface ** extends android.os.IInterface { *; }
-keepclasseswithmembernames class * {
    native <methods>;
}

# 3. تشويش وتعتيم كود المنطق الداخلي والحماية (Anti-Tamper)
-repackageclasses 'com.wolfcustom.vpn.internal'
-allowaccessmodification

# 4. الحفاظ على نقاط دخول التطبيق الرئيسية
-keep class com.wolfcustom.vpn.MainActivity { *; }
-keep class com.wolfcustom.vpn.ShizziApp { *; }
-keep class com.wolfcustom.vpn.TetherService { *; }
-keep class com.wolfcustom.vpn.security.AppDependencyChecker { *; }