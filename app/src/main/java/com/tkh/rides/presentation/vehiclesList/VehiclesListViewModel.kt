package com.tkh.rides.presentation.vehiclesList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tkh.rides.domain.model.Vehicle
import com.tkh.rides.domain.repository.RidesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehiclesListViewModel @Inject constructor(
    private val repository: RidesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Vehicle>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Vehicle>>> = _uiState

    private val _size = MutableStateFlow(0)
    val size = _size.asStateFlow()

    fun onEvent(event: ListEvent) {

        when (event) {
            is ListEvent.OnSizeChange -> {
                _size.value = event.size
            }

            is ListEvent.OnSearch -> {
                getVehicles()
            }

            is ListEvent.OnCarClick -> {

            }
        }
    }


    fun getVehicles() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            repository.getVehicleList(_size.value)
                .collect { vehicles ->
                    _uiState.value = UiState.Success(vehicles)
                }
        }
    }

}

sealed class ListEvent {
    data class OnSizeChange(val size: Int) : ListEvent()
    object OnSearch : ListEvent()
    object OnCarClick : ListEvent()
}

sealed interface UiState<out T> {

    data class Success<T>(val data: T) : UiState<T>

    data class Error(val message: String) : UiState<Nothing>

    object Loading : UiState<Nothing>

}