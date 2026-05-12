package com.pratham.chikitse.ui.ai

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pratham.chikitse.util.GeminiHelper
import com.pratham.chikitse.util.PreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ChatMessage(
    val text: String,
    val isUser: Boolean
)

@HiltViewModel
class AiAssistantViewModel @Inject constructor(
    private val gemini: GeminiHelper,
    private val prefs: PreferencesHelper
) : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val useKannada: StateFlow<Boolean> = prefs.useKannada
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun sendMessage(text: String) {
        if (text.isBlank()) return
        _messages.value = _messages.value + ChatMessage(text, isUser = true)
        _isLoading.value = true
        viewModelScope.launch {
            val response = gemini.askFirstAid(text)
            _messages.value = _messages.value + ChatMessage(response, isUser = false)
            _isLoading.value = false
        }
    }

    fun clearChat() { _messages.value = emptyList() }
}
