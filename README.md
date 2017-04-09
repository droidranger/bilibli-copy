# bilibli-copy
bilibili-copy


 编译错误：
 Error:Execution failed for task ':app:processDebugManifest'. > Manifest merger failed :
 Attribute application@theme value=(@style/AppTheme) from AndroidManifest.xml:11:9-40
 is also present at [com.github.Todd-Davies:ProgressWheel:1.2] AndroidManifest.xml:
 13:9-56 value=(@android:style/Theme.NoTitleBar).
 Suggestion: add 'tools:replace="android:theme"' to <application> element
 at AndroidManifest.xml:5:5-38:19 to override.
 解决方案：
 在清单文件AndroidManifest.xml中修改两个地方
 1.在<manifest> 下添加  xmlns:tools="http://schemas.android.com/tools"
 2.在<application> 下添加tools:replace="android:icon, android:theme"
