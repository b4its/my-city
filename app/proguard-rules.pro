# ===================================================================
# ATURAN UNTUK KOTLINX SERIALIZATION (Versi Disederhanakan)
# ===================================================================
-keepclassmembers class * {
    @kotlinx.serialization.Serializable *;
}
-keep class *$$serializer { *; }
-keepnames class * {
    @kotlinx.serialization.Serializable *;
}


# ===================================================================
# ATURAN UNTUK LIBRARY UMUM
# ===================================================================

# Aturan untuk Retrofit & OkHttp
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn retrofit2.Platform$Java8
-dontwarn okhttp3.internal.platform.ConscryptPlatform


# Aturan untuk Coroutines
-keepclassmembers class kotlinx.coroutines.internal.MainDispatcherFactory {
    kotlinx.coroutines.MainCoroutineDispatcher createDispatcher(java.util.List);
}


# ===================================================================
# ATURAN KHUSUS UNTUK JETPACK COMPOSE
# ===================================================================
-keepclassmembers class androidx.compose.runtime.Composer {
    <methods>;
}
-keep class androidx.compose.runtime.internal.ComposableLambda {
    <fields>;
    <init>(...);
}
-keepclassmembers class * {
    @androidx.compose.runtime.Composable <methods>;
}
-keepclassmembers class **.R$* {
    <fields>;
}