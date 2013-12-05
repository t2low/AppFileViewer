AppFileViewer
=============

当ライブラリを利用するプロジェクトのAndroidManifest.xmlに以下を追記する。

```
        <activity
            android:name="com.tappli.android.appfileviewer.MainActivity"
            android:label="AppFileViewer" >
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
```
