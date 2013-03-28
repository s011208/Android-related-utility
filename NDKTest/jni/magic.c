#include <string.h>
#include <jni.h>

jstring Java_com_yenhsun_ndktest_MainActivity_getSecString(JNIEnv* env, jobject thiz) {

	return (*env)->NewStringUTF(env, "Toast text from second");
}
