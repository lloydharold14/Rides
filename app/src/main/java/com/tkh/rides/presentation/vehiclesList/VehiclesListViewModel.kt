package com.tkh.rides.presentation.vehiclesList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tkh.rides.domain.model.Vehicle
import com.tkh.rides.domain.repository.RidesRepository
import com.tkh.rides.domain.use_case.CheckSizeRange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehiclesListViewModel @Inject constructor(
    private val repository: RidesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(VehicleUiState(size = 1))
    val uiState: StateFlow<VehicleUiState> = _uiState.asStateFlow()

    private val eventChannel = Channel<ListEvent>()
    val eventFlow = eventChannel.receiveAsFlow()

    fun onEvent(event: ListEvent) {

        when (event) {
            is ListEvent.OnSizeChange -> {
                _uiState.update {
                    it.copy(
                        size = event.size
                    )
                }
            }

            is ListEvent.OnSearch -> {

                val inRange = CheckSizeRange().invoke(_uiState.value.size)

                if (inRange) {

                    getVehicles()
                } else {
                    triggerErrorMessage()
                }
            }

            is ListEvent.onError -> {

            }
        }
    }

    fun triggerErrorMessage() = viewModelScope.launch {
        eventChannel.send(ListEvent.onError("The number should be in the range 1 to 100"))
    }

    private fun getVehicles() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isSearching = true
                )
            }

            repository.getVehicleList(_uiState.value.size)
                .collect { vehicles ->
                    _uiState.update {
                        it.copy(
                            isSearching = false,
                            vehicles = vehicles
                        )
                    }
                }
        }
    }

}

sealed class ListEvent {
    data class OnSizeChange(val size: Int) : ListEvent()
    object OnSearch : ListEvent()

    data class onError(val message: String) : ListEvent()
}


data class VehicleUiState(

    val size: Int,
    val isSearching: Boolean = false,
    val vehicles: List<Vehicle> = emptyList()
)