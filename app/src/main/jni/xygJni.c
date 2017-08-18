#include "com_ranger_xyg_xygapp_demos_jni_NdkJniUtils.h"

JNIEXPORT jstring JNICALL
Java_com_ranger_xyg_xygapp_demos_jni_NdkJniUtils_getSampleName(JNIEnv *env, jobject instance) {

    // TODO

    const char *returnValue = "Java_com_ranger_xyg_xygapp_demos_jni_NdkJniUtils_getSampleName";
    return (*env)->NewStringUTF(env, returnValue);
}