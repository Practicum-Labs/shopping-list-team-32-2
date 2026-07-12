# default: proguard-android-optimize.txt
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile
-keepattributes RuntimeVisibleAnnotations,AnnotationDefault
-keepattributes Signature,*Annotation*,InnerClasses,EnclosingMethod

# Moshi: @JsonClass(generateAdapter = true) + KotlinJsonAdapterFactory
-keep class com.squareup.moshi.** { *; }
-dontwarn com.squareup.moshi.**
-keep class kotlin.Metadata { *; }
-keepclassmembers class * {
    @com.squareup.moshi.FromJson *;
    @com.squareup.moshi.ToJson *;
}
-keep class **JsonAdapter { *; }
-keepclassmembers class **JsonAdapter {
    <init>(...);
}
-keep class com.practicum.list.core.data.network.dto.** { *; }
-keepclassmembers class com.practicum.list.core.data.network.dto.** {
    <init>(...);
}

# kotlinx.serialization — type-safe Navigation routes (@Serializable)
-dontwarn kotlinx.serialization.**
-keep,includedescriptorclasses class com.practicum.list.core.navigation.**$$serializer { *; }
-keepclassmembers class com.practicum.list.core.navigation.** {
    *** Companion;
}
-keepclasseswithmembers class com.practicum.list.core.navigation.** {
    kotlinx.serialization.KSerializer serializer(...);
}
-keepclassmembers @kotlinx.serialization.Serializable class ** {
    *** Companion;
    *** INSTANCE;
    kotlinx.serialization.KSerializer serializer(...);
}

# Enums used via Room TypeConverters / reflection-ish lookups
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

