package com.pratham.chikitse.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pratham.chikitse.util.PreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val prefs: PreferencesHelper
) : ViewModel() {

    val useKannada: StateFlow<Boolean> = prefs.useKannada
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val darkMode: StateFlow<Boolean> = prefs.darkMode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun toggleKannada(value: Boolean) {
        viewModelScope.launch { prefs.setUseKannada(value) }
    }

    fun toggleDarkMode(value: Boolean) {
        viewModelScope.launch { prefs.setDarkMode(value) }
    }
}
