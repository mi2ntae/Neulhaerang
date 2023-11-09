package com.finale.neulhaerang.common.message

/**
 * 통신 에러 메세지
 */
enum class ErrorMessage(
    val message: String,
) {
    Code400("잘못된 입력 값 입니다"),
    Code403("로그인 시간이 만료되었습니다"),
    Code500("현재 서버 점검 중 입니다. 관리자에게 문의해주세요"),
    CodeUnknown("알 수 없는 문제가 발생했습니다. 잠시 후 시도해 주세요"),
}
