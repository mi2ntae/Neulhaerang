package com.finale.neulhaerang.data.model.request

/**
 * 루틴 삭제 request dto
 * @property dailyRoutineId
 * @property routineId
 * @property never
 */
data class RoutineDeleteReqDto(
    val dailyRoutineId: Long,
    val routineId: Long,
    val never: Boolean,
)
