package com.finale.neulhaerang.data.util

/**
 * API 통신을 위한 wrapper 클래스
 * 성공과 실패로 이루어짐
 * onSuccess 로 성공 시의 처리를, onFailure 로 실패 시의 처리를 하면 됨
 *
 * @property code http 상태 코드
 */
sealed interface ResponseResult<out T> {
    val code: Int

    data class Success<T>(override val code: Int, val data: T?) : ResponseResult<T>
    data class Failure(
        override val code: Int,
        val message: String,
        val throwable: Throwable? = null,
    ) : ResponseResult<Nothing>
}

/**
 * ResponseResult가 성공했을 때의 함수
 * @param action ( code, data? ) -> Unit
 */
inline fun <T> ResponseResult<T>.onSuccess(
    action: (ResponseResult.Success<T>) -> Unit,
): ResponseResult<T> {
    if (this is ResponseResult.Success) {
        action(this)
    }
    return this
}

/**
 * ResponseResult가 실패했을 때의 함수
 * @param action ( code, message, throwable? ) -> Unit
 */
inline fun <T> ResponseResult<T>.onFailure(
    action: (error: ResponseResult.Failure) -> Unit,
): ResponseResult<T> {
    if (this is ResponseResult.Failure) {
        action(this)
    }
    return this
}