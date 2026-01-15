#include <jni.h>
#include <string>
#include <sys/stat.h>


extern "C"
JNIEXPORT jstring JNICALL Java_test_kyrie_core_utility_CryptoHelperBridge_getXORedResult(
        JNIEnv *env, jobject obj, jbyteArray first, jbyteArray second) {
    jlong size = env->GetArrayLength(first);
    char *result = new char[size + 1];
    jboolean isCopy;
    jbyte *firstByteArr = env->GetByteArrayElements(first, &isCopy);
    jbyte *secondByteArr = env->GetByteArrayElements(second, &isCopy);

    if (env->GetArrayLength(second) != size) {
        delete[] result;
        return NULL;
    }

    int i;
    for (i = 0; i < size; i++) {
        result[i] = firstByteArr[i] ^ secondByteArr[i];
    }
    result[size] = 0;
    //cleanup
    env->ReleaseByteArrayElements(first, firstByteArr, JNI_ABORT);
    env->ReleaseByteArrayElements(second, secondByteArr, JNI_ABORT);
    jstring jResult = env->NewStringUTF(result);
    delete[] result;
    return jResult;
}