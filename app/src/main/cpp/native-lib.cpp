#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_ex_revolut_MainActivity_getBaseApiUrl(
        JNIEnv *env,
        jobject /* this */) {
    std::string apiUrl = "https://revolut.duckdns.org";
    return env->NewStringUTF(apiUrl.c_str());
}
