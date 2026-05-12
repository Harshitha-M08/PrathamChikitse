package com.pratham.chikitse.ui.emergency

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pratham.chikitse.data.model.Emergency
import com.pratham.chikitse.data.repository.EmergencyRepository
import com.pratham.chikitse.util.PreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmergencyDetailViewModel @Inject constructor(
    private val repo: EmergencyRepository,
    private val prefs: PreferencesHelper,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val emergencyId: Int = checkNotNull(savedStateHandle["id"])

    private val _emergency = MutableStateFlow<Emergency?>(null)
    val emergency: StateFlow<Emergency?> = _emergency.asStateFlow()

    val useKannada: StateFlow<Boolean> = prefs.useKannada
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    init {
        viewModelScope.launch {
            _emergency.value = repo.getEmergencyById(emergencyId)
        }
    }
}
