package com.finale.neulhaerang.data.util

/**
 * API 통신을 위한 wrapper 클래스
 * 데이터를 가지는 성공과 널이 오는 성공, 실패로 이루어짐
 * onSuccess 로 성공 시의 처리를, onFailure 로 실패 시의 처리를 하면 됨
 *
 * @property code http 상태 코드
 */
sealed interface ResponseResult<out T> {
    val code: Int

    data class Success<T>(override val code: Int, val data: T) : ResponseResult<T>
    data class NullSuccess(override val code: Int) : ResponseResult<Nothing>
    data class Failure(
        override val code: Int,
        val message: String,
        val throwable: Throwable? = null,
    ) : ResponseResult<Nothing>
}

inline fun <T> ResponseResult<T>.onSuccess(
    action: (data: T?) -> Unit,
): ResponseResult<T> {
    when (this) {
        is ResponseResult.Success -> action(this.data)
        is ResponseResult.NullSuccess -> action(null)
        else -> {}
    }
    return this
}

inline fun <T> ResponseResult<T>.onFailure(
    action: (error: ResponseResult.Failure) -> Unit,
): ResponseResult<T> {
    when (this) {
        is ResponseResult.Failure -> action(this)
        else -> {}
    }
    return this
}