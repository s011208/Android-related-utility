#include <string.h>
#include <jni.h>

jstring Java_com_yenhsun_ndktest_MainActivity_getMagicString(JNIEnv* env, jobject thiz) {

	return (*env)->NewStringUTF(env, "Magic String!");
}
