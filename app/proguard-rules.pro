# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\dev-tools\android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Retrofit2
-dontwarn retrofit2.**
-dontnote retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# Butter Knife
-keep class butterknife.** { *; }
-dontnote butterknife.**
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
  @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
  @butterknife.* <methods>;
}

# Skip Warning classes
-dontwarn sun.misc.Unsafe
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn java.nio.file.**

# Vending Licensing
-dontnote com.google.vending.licensing.ILicensingService
-dontnote com.android.vending.licensing.ILicensingService

# Android Support
-keep class android.support.** { *; }
-dontnote android.support.**

# Gson
-keep class com.google.gson.** { *; }
-dontnote com.google.gson.**

# OkHttp3
-keep class okhttp3.** { *; }
-dontnote okhttp3.**

# Okio
-keep class okio.** { *; }

# RxJava/RxAndroid
-keep class rx.** { *; }
-dontnote rx.**

# greenDAO
-keepclassmembers class * extends de.greenrobot.dao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties

# Keep all classes in "pearson" package which are used for Retrofit2
-keepclassmembers class com.dictionary.hckhanh.pearsondictionary.pearson.data.** {
  <fields>;
}
-keepclassmembers interface com.dictionary.hckhanh.pearsondictionary.pearson.service.ContentApis {
  *;
}
