-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile
-keepattributes RuntimeVisibleAnnotations,AnnotationDefault
-keepattributes Signature,*Annotation*,InnerClasses,EnclosingMethod

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
-keep class com.practicum.list.core.data.remote.dto.** { *; }
-keepclassmembers class com.practicum.list.core.data.remote.dto.** {
    <init>(...);
}

-dontwarn retrofit2.**
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

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

-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-keep @androidx.room.Dao interface *
-keep class androidx.room.Room { *; }
-keep class * extends androidx.room.RoomDatabase {
    static *** create(...);
}
-keepclassmembers class * {
    @androidx.room.Query *;
    @androidx.room.Insert *;
    @androidx.room.Update *;
    @androidx.room.Delete *;
    @androidx.room.Transaction *;
}
-keep class com.practicum.list.core.data.local.** { *; }

-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.hilt.android.internal.managers.ViewComponentManager$FragmentContextWrapper { *; }
-keepclasseswithmembers class * {
    @dagger.hilt.** *;
}
-keepclasseswithmembers class * {
    @javax.inject.Inject <init>(...);
}
-keep class * extends androidx.lifecycle.ViewModel {
    <init>(...);
}
-keep class * extends androidx.lifecycle.AndroidViewModel {
    <init>(...);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class com.practicum.list.core.common.domain.MeasureUnit { *; }
-keep class com.practicum.list.core.common.domain.MeasureUnit$* { *; }

