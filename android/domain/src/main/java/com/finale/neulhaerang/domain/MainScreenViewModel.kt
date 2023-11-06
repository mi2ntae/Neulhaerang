package com.finale.neulhaerang.domain

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.finale.neulhaerang.data.CheckList
import java.time.LocalDate
import java.time.LocalDateTime

class MainScreenViewModel : ViewModel() {
    private val _selectedDateTime = mutableStateOf(LocalDateTime.now())
    private val _routineList = mutableStateListOf<CheckList>() // TODO Routine 객체 생성 후 교체
    private val _todoList = mutableStateListOf<CheckList>() // TODO todo 객체 생성 후 교체
    private val _letterText = mutableStateOf("")

    val selectedDate: LocalDate
        get() = _selectedDateTime.value.toLocalDate()
    val lastDay
        get() = selectedDate.lengthOfMonth()
    val routineList: List<CheckList>
        get() = _routineList
    val todoList: List<CheckList>
        get() = _todoList
    val letterText: String
        get() = _letterText.value

    fun setDateTime(input: LocalDateTime) {
        _selectedDateTime.value = input
    }

    fun initCheckList(routines: List<CheckList>, todos: List<CheckList>) {
        initRoutine(routines)
        initTodo(todos)
    }

    fun initRoutine(routines: List<CheckList>) {
        _routineList.clear()
        _routineList.addAll(routines)
    }

    fun addRoutine(routine: CheckList) {
        _routineList.add(routine)
    }

    fun initTodo(todos: List<CheckList>) {
        _todoList.clear()
        _todoList.addAll(todos)
    }

    fun addTodo(todo: CheckList) {
        _todoList.add(todo)
    }

    fun setLetterText(input: String) {
        _letterText.value = input
    }
}