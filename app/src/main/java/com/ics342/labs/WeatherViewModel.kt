package com.ics342.labs

import android.util.Log
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val apiService: ApiService): ViewModel() {

    private val _weatherData: MutableLiveData<WeatherData> = MutableLiveData()
    val weatherData: LiveData<WeatherData> get() = _weatherData
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error
    private var zipCode: String = ""
    fun updateZipCode(newZipCode: String) {
        zipCode = newZipCode
    }
    fun isValidZipCode(): Boolean {
        return zipCode.length == 5 && zipCode.all { it.isDigit() }
    }

    fun viewAppeared() = viewModelScope.launch {
        try {
            _weatherData.value = apiService.getWeatherData(zipCode)
        } catch (e: Exception) {
            Log.e("ZipCodeError", "Error fetching data for that zipcode. Try again.", e)
            _error.value = "Zipcode failure.: ${e.localizedMessage}"
        }
    }
}