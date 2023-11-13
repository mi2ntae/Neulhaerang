package com.finale.neulhaerang.common.message

enum class BlockMessage(val message: String) {
    NotTodayBlock("오늘이 아닌 날짜의 체크리스트는 완료할 수 없습니다."),
    IndolenceBlock("나태도가 너무 높습니다! 나태 괴물을 처치하여 나태도를 낮춰보세요!"),
    PastModifyBlock("이전 날짜의 체크리스트는 수정할 수 없습니다."),
}