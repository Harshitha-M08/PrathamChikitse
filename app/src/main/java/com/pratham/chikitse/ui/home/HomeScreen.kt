package com.pratham.chikitse.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pratham.chikitse.data.model.Emergency
import com.pratham.chikitse.ui.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigate: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val emergencies by viewModel.emergencies.collectAsState()
    val useKannada by viewModel.useKannada.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = if (useKannada) "ಪ್ರಥಮ ಚಿಕಿತ್ಸೆ" else "Pratham Chikitse",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (useKannada) "ತುರ್ತು ಪ್ರಥಮ ಚಿಕಿತ್ಸೆ ಮಾರ್ಗದರ್ಶಿ" else "Emergency First Aid Guide",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    IconButton(onClick = { onNavigate(Screen.Search.route) }) {
                        Icon(Icons.Default.Search, contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.onPrimary)
                    }
                    IconButton(onClick = { onNavigate(Screen.Settings.route) }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings",
                            tint = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text(if (useKannada) "ಮನೆ" else "Home") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { onNavigate(Screen.Hospitals.route) },
                    icon = { Icon(Icons.Default.LocalHospital, contentDescription = null) },
                    label = { Text(if (useKannada) "ಆಸ್ಪತ್ರೆ" else "Hospitals") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { onNavigate(Screen.AiAssistant.route) },
                    icon = { Icon(Icons.Default.SmartToy, contentDescription = null) },
                    label = { Text(if (useKannada) "AI ಸಹಾಯ" else "AI Help") }
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 12.dp)
        ) {
            // Emergency banner
            EmergencyBanner(useKannada)

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (useKannada) "ತುರ್ತು ಪರಿಸ್ಥಿತಿ ಆಯ್ಕೆಮಾಡಿ" else "Select Emergency Type",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            if (emergencies.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(emergencies) { emergency ->
                        EmergencyCard(
                            emergency = emergency,
                            useKannada = useKannada,
                            onClick = { onNavigate(Screen.EmergencyDetail.createRoute(emergency.id)) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EmergencyBanner(useKannada: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.LocalPhone,
                contentDescription = null,
                tint = Color(0xFFD32F2F),
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = if (useKannada) "ತುರ್ತು ಸಹಾಯವಾಣಿ" else "Emergency Helpline",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color(0xFFB71C1C)
                )
                Text(
                    text = "108 — Ambulance",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD32F2F)
                )
                Text(
                    text = if (useKannada) "ಸರ್ಕಾರಿ ಉಚಿತ ಆಂಬ್ಯುಲೆನ್ಸ್ ಸೇವೆ" else "Govt. Free Ambulance Service",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF757575)
                )
            }
        }
    }
}

@Composable
fun EmergencyCard(
    emergency: Emergency,
    useKannada: Boolean,
    onClick: () -> Unit
) {
    val cardColor = try {
        Color(android.graphics.Color.parseColor(emergency.colorHex))
    } catch (e: Exception) {
        MaterialTheme.colorScheme.primary
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = categoryIcon(emergency.category),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (useKannada) emergency.nameKn else emergency.nameEn,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = emergency.category,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
fun categoryIcon(category: String) = when (category) {
    "Cardiac"       -> Icons.Default.FavoriteBorder
    "Airway"        -> Icons.Default.Air
    "Trauma"        -> Icons.Default.LocalHospital
    "Burns"         -> Icons.Default.Whatshot
    "Poison"        -> Icons.Default.Warning
    "Neurological"  -> Icons.Default.Psychology
    "Medical"       -> Icons.Default.MedicalServices
    "Environmental" -> Icons.Default.WbSunny
    "Respiratory"   -> Icons.Default.Air
    else            -> Icons.Default.Emergency
}
