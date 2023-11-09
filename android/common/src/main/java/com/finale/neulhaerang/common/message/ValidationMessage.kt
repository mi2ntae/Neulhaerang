package com.finale.neulhaerang.common.message

/**
 * 체크리스트 생성시 입력값 확인용 메세지
 */
enum class ValidationMessage(
    val message: String,
) {
    NoContent("체크리스트 내용을 입력해 주세요"),
    NoRepeat("루틴의 반복 요일을 설정해 주세요"),
    PastTime("현재 시간 이후로 시간을 설정해 주세요"),
}