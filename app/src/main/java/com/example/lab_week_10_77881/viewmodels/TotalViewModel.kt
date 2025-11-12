package com.example.lab_week_10_77881.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TotalViewModel : ViewModel() {

    private val _total = MutableLiveData(0)
    val total: LiveData<Int> get() = _total

    fun incrementTotal() {
        val current = _total.value ?: 0
        _total.value = current + 1
    }
}
