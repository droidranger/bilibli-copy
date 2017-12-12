#!/bin/sh
./gradlew :$1:assembleDebug
if [ $? -eq 0 ]; then
    if [ -n $1 ]; then
        if [ $1 = "app" ]; then
            adb install -r app/build/outputs/apk/debug/app-debug.apk
            echo "install success"
        elif [ $1 = "music" ]; then
            adb install -r music/build/outputs/apk/debug/music-debug.apk
            echo "install success"
        fi
    else
        adb install -r app/build/outputs/apk/debug/app-debug.apk
        echo "install success"
    fi
fi
