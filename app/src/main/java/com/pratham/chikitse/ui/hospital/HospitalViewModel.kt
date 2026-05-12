package com.pratham.chikitse.ui.hospital

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pratham.chikitse.data.model.Hospital
import com.pratham.chikitse.data.repository.HospitalRepository
import com.pratham.chikitse.util.PreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HospitalViewModel @Inject constructor(
    private val hospitalRepo: HospitalRepository,
    private val prefs: PreferencesHelper
) : ViewModel() {

    val hospitals: StateFlow<List<Hospital>> = hospitalRepo
        .getAllHospitals()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val useKannada: StateFlow<Boolean> = prefs.useKannada
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    init {
        viewModelScope.launch { hospitalRepo.seedIfNeeded() }
    }
}
