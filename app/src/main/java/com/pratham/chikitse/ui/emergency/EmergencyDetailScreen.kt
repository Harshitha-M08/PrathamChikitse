package com.pratham.chikitse.ui.emergency

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pratham.chikitse.data.model.Emergency
import com.pratham.chikitse.ui.home.categoryIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmergencyDetailScreen(
    onBack: () -> Unit,
    viewModel: EmergencyDetailViewModel = hiltViewModel()
) {
    val emergency by viewModel.emergency.collectAsState()
    val useKannada by viewModel.useKannada.collectAsState()

    if (emergency == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val e = emergency!!
    val cardColor = try {
        Color(android.graphics.Color.parseColor(e.colorHex))
    } catch (ex: Exception) { MaterialTheme.colorScheme.primary }

    val steps: List<String> = parseJsonList(if (useKannada) e.stepsKn else e.stepsEn)
    val dos:   List<String> = parseJsonList(if (useKannada) e.dosKn   else e.dosEn)
    val donts: List<String> = parseJsonList(if (useKannada) e.dontsKn else e.dontsEn)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (useKannada) e.nameKn else e.nameEn,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back",
                            tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = cardColor,
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
        ) {
            // Header banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(cardColor)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = categoryIcon(e.category),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = e.category,
                        color = Color.White.copy(alpha = 0.8f),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }

            // 108 Call to Action
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Emergency, null, tint = Color(0xFFD32F2F),
                        modifier = Modifier.size(28.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(
                        if (useKannada)
                            "ತೀವ್ರ ತುರ್ತು ಪರಿಸ್ಥಿತಿಯಲ್ಲಿ ತಕ್ಷಣ 108 ಕರೆ ಮಾಡಿ"
                        else
                            "For severe emergencies call 108 immediately",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFFB71C1C)
                    )
                }
            }

            Column(modifier = Modifier.padding(horizontal = 12.dp)) {
                // Steps
                SectionHeader(
                    title = if (useKannada) "ಏನು ಮಾಡಬೇಕು — ಹಂತ ಹಂತವಾಗಿ" else "Step-by-Step Actions",
                    icon = Icons.Default.List,
                    color = cardColor
                )
                steps.forEachIndexed { i, step ->
                    StepRow(number = i + 1, text = step, color = cardColor)
                }

                Spacer(Modifier.height(12.dp))

                // DOs
                SectionHeader(
                    title = if (useKannada) "ಮಾಡಬೇಕಾದ ಕೆಲಸಗಳು ✓" else "DO's ✓",
                    icon = Icons.Default.CheckCircle,
                    color = Color(0xFF2E7D32)
                )
                dos.forEach { DosRow(it) }

                Spacer(Modifier.height(12.dp))

                // DON'Ts
                SectionHeader(
                    title = if (useKannada) "ಮಾಡಬಾರದ ಕೆಲಸಗಳು ✗" else "DON'Ts ✗",
                    icon = Icons.Default.Cancel,
                    color = Color(0xFFC62828)
                )
                donts.forEach { DontsRow(it) }

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun SectionHeader(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color) {
    Row(
        modifier = Modifier.padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(22.dp))
        Spacer(Modifier.width(8.dp))
        Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = color)
    }
}

@Composable
fun StepRow(number: Int, text: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(color),
            contentAlignment = Alignment.Center
        ) {
            Text("$number", color = Color.White,
                style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.width(10.dp))
        Text(text, style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f))
    }
    HorizontalDivider(modifier = Modifier.padding(vertical = 2.dp), color = Color.LightGray.copy(alpha = 0.5f))
}

@Composable
fun DosRow(text: String) {
    Row(modifier = Modifier.padding(vertical = 3.dp), verticalAlignment = Alignment.Top) {
        Icon(Icons.Default.Check, null, tint = Color(0xFF2E7D32),
            modifier = Modifier.size(18.dp).padding(top = 2.dp))
        Spacer(Modifier.width(8.dp))
        Text(text, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun DontsRow(text: String) {
    Row(modifier = Modifier.padding(vertical = 3.dp), verticalAlignment = Alignment.Top) {
        Icon(Icons.Default.Close, null, tint = Color(0xFFC62828),
            modifier = Modifier.size(18.dp).padding(top = 2.dp))
        Spacer(Modifier.width(8.dp))
        Text(text, style = MaterialTheme.typography.bodyMedium)
    }
}

fun parseJsonList(json: String): List<String> {
    return try {
        val type = object : TypeToken<List<String>>() {}.type
        Gson().fromJson(json, type)
    } catch (e: Exception) { emptyList() }
}
