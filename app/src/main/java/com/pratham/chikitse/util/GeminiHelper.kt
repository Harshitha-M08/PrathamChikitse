package com.pratham.chikitse.util

import android.util.Log
import com.pratham.chikitse.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeminiHelper @Inject constructor() {

    private val groqApiKey: String by lazy { BuildConfig.GROQ_API_KEY }
    private val groqModel = "llama-3.1-8b-instant"
    private val groqApiUrl = "https://api.groq.com/openai/v1/chat/completions"

    private val systemPrompt = """
        You are Pratham Chikitse AI, a first-aid assistant for rural Karnataka, India.
        You provide clear, simple, life-saving first-aid advice in both English and Kannada.
        Always:
        1. Start with the most critical action.
        2. Keep steps numbered and simple.
        3. Always recommend calling 108 (ambulance) for serious emergencies.
        4. Do NOT replace medical professionals — always advise seeing a doctor.
        5. If the user writes in Kannada, respond primarily in Kannada with English alongside.
        6. Be concise — users are in emergencies and need fast answers.
    """.trimIndent()

    private fun isQuotaOrBillingError(message: String): Boolean {
        val lower = message.lowercase()
        return lower.contains("resource_exhausted") ||
            lower.contains("quota exceeded") ||
            lower.contains("billing") ||
            lower.contains("rate limit") ||
            lower.contains("free_tier") ||
            lower.contains("please retry in")
    }

    suspend fun askFirstAid(userQuestion: String): String {
        return withContext(Dispatchers.IO) {
            try {
                val payload = JSONObject()
                    .put("model", groqModel)
                    .put(
                        "messages",
                        JSONArray()
                            .put(JSONObject().put("role", "system").put("content", systemPrompt))
                            .put(JSONObject().put("role", "user").put("content", userQuestion))
                    )
                    .put("temperature", 0.2)
                    .put("max_tokens", 500)

                val connection = (URL(groqApiUrl).openConnection() as HttpURLConnection).apply {
                    requestMethod = "POST"
                    connectTimeout = 30000
                    readTimeout = 30000
                    doOutput = true
                    setRequestProperty("Authorization", "Bearer $groqApiKey")
                    setRequestProperty("Content-Type", "application/json")
                }

                connection.outputStream.use { outputStream ->
                    outputStream.write(payload.toString().toByteArray(StandardCharsets.UTF_8))
                }

                val responseCode = connection.responseCode
                val responseText = (if (responseCode in 200..299) {
                    connection.inputStream
                } else {
                    connection.errorStream ?: connection.inputStream
                })?.use { stream ->
                    BufferedReader(InputStreamReader(stream, StandardCharsets.UTF_8)).use { reader ->
                        reader.readText()
                    }
                }.orEmpty()

                if (responseCode !in 200..299) {
                    val message = try {
                        JSONObject(responseText).optJSONObject("error")?.optString("message")
                            ?: responseText
                    } catch (_: Exception) {
                        responseText
                    }

                    Log.e("GeminiHelper", "Groq request failed: HTTP $responseCode, $message")

                    return@withContext when {
                        isQuotaOrBillingError(message) || responseCode == 429 ->
                            "Error: Groq quota or rate limit reached. Please try again later."
                        responseCode == 401 || responseCode == 403 ->
                            "Error: Groq API key is invalid or not authorized. Please check the key."
                        else -> "Error: $message"
                    }
                }

                val content = JSONObject(responseText)
                    .optJSONArray("choices")
                    ?.optJSONObject(0)
                    ?.optJSONObject("message")
                    ?.optString("content")

                content?.trim().takeUnless { it.isNullOrEmpty() }
                    ?: "Sorry, I could not generate a response. Please try again."
            } catch (e: Exception) {
                Log.e("GeminiHelper", "askFirstAid failed", e)
                val msg = e.localizedMessage ?: e.message ?: "Unknown error occurred. Check your API key and internet connection."
                "Error: $msg"
            }
        }
    }
}
