package com.pratham.chikitse.ui.home

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
class HomeViewModel @Inject constructor(
    private val emergencyRepo: EmergencyRepository,
    private val prefs: PreferencesHelper
) : ViewModel() {

    val emergencies: StateFlow<List<Emergency>> = emergencyRepo
        .getAllEmergencies()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val useKannada: StateFlow<Boolean> = prefs.useKannada
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    init {
        viewModelScope.launch { emergencyRepo.seedIfNeeded() }
    }
}
