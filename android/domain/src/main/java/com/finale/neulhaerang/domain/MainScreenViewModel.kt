package com.finale.neulhaerang.domain

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.LocalDateTime

class MainScreenViewModel : ViewModel() {
    private val _selectedDateTime = mutableStateOf(LocalDateTime.now())

    val selectedDate: State<LocalDate>
        get() = mutableStateOf(_selectedDateTime.value.toLocalDate())

    fun setDateTime(input: LocalDateTime) {
        _selectedDateTime.value = input
    }
}