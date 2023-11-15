package com.finale.neulhaerang.common.message

enum class BlockMessage(val message: String) {
    NotTodayBlock("오늘이 아닌 날짜의 체크리스트는 완료할 수 없습니다."),
    IndolenceBlock("나태도가 너무 높습니다! 나태 괴물을 처치하여 나태도를 낮춰보세요!"),
    PastModifyBlock("이전 날짜의 체크리스트는 수정할 수 없습니다."),
    TirednessBlock(
        "피로도에 따른 오늘의 투두 생성 갯수를 모두 소모하였어요.\n" +
                "충분한 휴식을 취하고 내일 활동을 계속해봐요!"
    ),
    DailyCountBlock(
        "한 날짜에 생성 가능한 투두 개수를 모두 소모하였어요.\n" +
                "충부한 휴식을 취하고 당일이 되면 추가로 투두를 생성할 수 있어요."
    )
}